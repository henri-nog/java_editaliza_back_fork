// src/main/java/com/editaliza/editaliza/services/CommentService.java
package com.editaliza.editaliza.services;

import com.editaliza.editaliza.models.CommentData;
import com.editaliza.editaliza.models.Edital;
import com.editaliza.editaliza.models.UserData;
import com.editaliza.editaliza.repositories.CommentRepository;
import com.editaliza.editaliza.repositories.EditalRepository;
import com.editaliza.editaliza.repositories.UserRepository;
import com.editaliza.editaliza.exceptions.ResourceNotFoundException;
import com.editaliza.editaliza.exceptions.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EditalRepository editalRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository,
            EditalRepository editalRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.editalRepository = editalRepository;
    }

    // -------------------------------------------------------------------------
    // READ OPERATIONS
    // -------------------------------------------------------------------------

    public CommentData findCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentário", "ID", id));
    }

    public List<CommentData> findApprovedCommentsByEdital(Long editalId) {
        // Busca todos os comentários do edital e filtra com o método da entidade
        return commentRepository.findByEdital_Id(editalId)
                .stream()
                .filter(CommentData::isPubliclyVisible)
                .toList();
    }

    public List<CommentData> findCommentsByAuthor(Long authorId) {
        return commentRepository.findByAuthor_Id(authorId);
    }

    // -------------------------------------------------------------------------
    // WRITE OPERATIONS
    // -------------------------------------------------------------------------

    @Transactional
    public CommentData createComment(CommentData comment, Long authorId, Long editalId) {
        // 1. Busca e valida os relacionamentos (Autor e Edital)
        UserData author = userRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Autor", "ID", authorId));

        Edital edital = editalRepository.findById(editalId)
                .orElseThrow(() -> new ResourceNotFoundException("Edital", "ID", editalId));

        // 2. Seta os relacionamentos
        comment.setAuthor(author);
        comment.setEdital(edital);

        // 3. Validação de Regra de Negócio (ex: Conteúdo Vazio, Tamanho)
        try {
            comment.validate(); // Usa o método de validação da entidade
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Falha na validação do Comentário: " + e.getMessage());
        }

        // 4. Salva e garante a consistência bidirecional
        CommentData savedComment = commentRepository.save(comment);
        // Garante que o objeto do autor e do edital no cache de persistência sejam
        // atualizados
        author.addComment(savedComment);
        edital.addComment(savedComment);

        return savedComment;
    }

    // -------------------------------------------------------------------------
    // MODERATION OPERATIONS
    // -------------------------------------------------------------------------

    @Transactional
    public CommentData approveComment(Long commentId) {
        CommentData comment = findCommentById(commentId);

        // Verifica se o comentário está em um status que permite moderação
        if (!comment.canBeModerated()) {
            throw new BusinessException(
                    "Comentário ID " + commentId + " não pode ser aprovado. Status atual: " + comment.getStatus());
        }

        comment.approve(); // Lógica de negócio da entidade
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        CommentData commentToDelete = findCommentById(commentId);

        // Antes de deletar, remove referências bidirecionais (boa prática de limpeza)
        // Este método removeComment(CommentData) está OK (você já o tem em UserData)
        commentToDelete.getAuthor().removeComment(commentToDelete);

        // ESTE MÉTODO VAI FUNCIONAR AGORA, POIS EDITAL TERÁ O
        // removeComment(CommentData)
        commentToDelete.getEdital().removeComment(commentToDelete);

        commentRepository.delete(commentToDelete);
    }

    @Transactional
    public CommentData updateCommentContent(Long commentId, Long userId, String newContent) {
        CommentData comment = findCommentById(commentId);

        // Regra de Negócio: O usuário deve ser o autor E o status deve permitir edição.
        if (!comment.canBeEditedBy(userId)) {
            throw new BusinessException("Comentário não pode ser editado. Não é o autor ou status não é PENDING.");
        }

        comment.setContent(newContent);

        // Revalida o conteúdo
        try {
            if (!comment.validateContent()) {
                throw new IllegalArgumentException("Conteúdo do comentário é inválido após a edição");
            }
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Falha na atualização do Comentário: " + e.getMessage());
        }

        return commentRepository.save(comment);
    }
}