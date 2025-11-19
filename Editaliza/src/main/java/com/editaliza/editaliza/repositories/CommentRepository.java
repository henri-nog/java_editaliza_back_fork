package com.editaliza.editaliza.repositories;

import com.editaliza.editaliza.models.CommentData;
import com.editaliza.editaliza.models.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentData, Long> {
    
    /**
     * Busca todos os comentários associados a um Edital.
     */
    List<CommentData> findByEdital_Id(Long editalId);

    /**
     * Busca comentários com um status de aprovação específico.
     */
    List<CommentData> findByStatus(CommentStatus status);

    /**
     * Busca todos os comentários feitos por um determinado usuário.
     * ⚠️ CORRIGIDO: Usa a propriedade 'author' da entidade CommentData.
     */
    List<CommentData> findByAuthor_Id(Long userId); // ✅ ESTA É A CORREÇÃO NECESSÁRIA
}