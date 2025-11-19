// src/main/java/com/editaliza/editaliza/services/EditalService.java
package com.editaliza.editaliza.services;

import com.editaliza.editaliza.models.Edital;
import com.editaliza.editaliza.models.Proposer;
import com.editaliza.editaliza.models.TagData;
import com.editaliza.editaliza.models.EditalStatus;
import com.editaliza.editaliza.repositories.EditalRepository;
import com.editaliza.editaliza.repositories.ProposerRepository;
import com.editaliza.editaliza.repositories.TagRepository;
import com.editaliza.editaliza.exceptions.ResourceNotFoundException;
import com.editaliza.editaliza.exceptions.BusinessException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Date;

@Service
@Transactional(readOnly = true)
public class EditalService {

    private final EditalRepository editalRepository;
    private final ProposerRepository proposerRepository;
    private final TagRepository tagRepository;

    public EditalService(EditalRepository editalRepository, ProposerRepository proposerRepository, TagRepository tagRepository) {
        this.editalRepository = editalRepository;
        this.proposerRepository = proposerRepository;
        this.tagRepository = tagRepository;
    }

    // -------------------------------------------------------------------------
    // READ OPERATIONS
    // -------------------------------------------------------------------------

    public Edital findEditalById(Long id) {
        return editalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Edital", "ID", id));
    }

    public List<Edital> findAllPublishedEditals() {
        // Encontra todos os editais que podem ser vistos pelo público
        return editalRepository.findByStatusIn(List.of(EditalStatus.PUBLISHED, EditalStatus.OPEN, EditalStatus.CLOSED));
    }

    public List<Edital> findEditalsByProposer(Long proposerId) {
        return editalRepository.findByProposer_Id(proposerId);
    }

    // -------------------------------------------------------------------------
    // WRITE OPERATIONS
    // -------------------------------------------------------------------------

    @Transactional
    public Edital createEdital(Edital edital, Long proposerId) {
        // 1. Busca e valida o Proponente
        Proposer proposer = proposerRepository.findById(proposerId)
                .orElseThrow(() -> new ResourceNotFoundException("Proponente", "ID", proposerId));

        // 2. Seta o relacionamento (gerenciamento bidirecional no Proposer.addEdital)
        proposer.addEdital(edital);
        edital.setProposer(proposer);

        // 3. Validação de Regra de Negócio
        try {
            edital.validate();
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Falha na validação do Edital: " + e.getMessage());
        }

        // 4. Salva (Proposer será atualizado via Cascade/Auditing)
        return editalRepository.save(edital);
    }

    @Transactional
    public Edital updateEdital(Long id, Edital editalDetails) {
        Edital edital = findEditalById(id);
        
        // Regra de Negócio: Não pode editar um Edital Cancelado
        if (EditalStatus.CANCELLED.equals(edital.getStatus())) {
            throw new BusinessException("Não é permitido editar um Edital Cancelado.");
        }

        // Atualiza campos
        edital.setTitle(editalDetails.getTitle());
        edital.setDescription(editalDetails.getDescription());
        edital.setPublishDate(editalDetails.getPublishDate());
        edital.setEndDate(editalDetails.getEndDate());
        edital.setInscriptionLink(editalDetails.getInscriptionLink());
        edital.setCompleteEditalLink(editalDetails.getCompleteEditalLink());
        edital.setImgCoverUrl(editalDetails.getImgCoverUrl());

        // A validação de datas e campos obrigatórios é feita no @PreUpdate
        try {
            edital.validate();
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Falha na atualização do Edital: " + e.getMessage());
        }

        return editalRepository.save(edital);
    }

    @Transactional
    public Edital updateEditalStatus(Long id, EditalStatus newStatus) {
        Edital edital = findEditalById(id);

        // Lógica de transição de status
        if (edital.getStatus().equals(EditalStatus.CANCELLED)) {
             throw new BusinessException("Um Edital Cancelado não pode mudar de status.");
        }
        
        // Implementar lógica de transição permitida (ex: DRAFT -> PUBLISHED -> OPEN -> CLOSED)
        // Por simplicidade, faremos a transição direta
        edital.setStatus(newStatus);
        
        // Se for para PUBLISHED, sete a data de publicação se estiver nula
        if (EditalStatus.PUBLISHED.equals(newStatus) && edital.getPublishDate() == null) {
            edital.setPublishDate(new Date());
        }

        // O Edital.updateStatus() pode ser chamado por um cronjob, mas aqui o status é forçado.
        edital.updateTimestamp();

        return editalRepository.save(edital);
    }

    @Transactional
    public void deleteEdital(Long id) {
        Edital edital = findEditalById(id);
        
        // Regra de Negócio: Não deletar editais abertos/publicados, apenas rascunhos ou cancelados.
        if (EditalStatus.OPEN.equals(edital.getStatus()) || EditalStatus.PUBLISHED.equals(edital.getStatus())) {
            throw new BusinessException("Não é permitido deletar um Edital que não seja rascunho, fechado ou cancelado.");
        }

        // Garante a remoção bidirecional (Proposer.removeEdital)
        edital.getProposer().removeEdital(edital.getId()); 
        
        // A remoção de Comentários é garantida pelo `orphanRemoval=true` em Edital.java
        editalRepository.delete(edital);
    }
    
    // -------------------------------------------------------------------------
    // TAG MANAGEMENT
    // -------------------------------------------------------------------------

    @Transactional
    public Edital addTagToEdital(Long editalId, Long tagId) {
        Edital edital = findEditalById(editalId);
        TagData tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "ID", tagId));

        edital.addTag(tag); // Método de conveniência na entidade
        return editalRepository.save(edital);
    }

    @Transactional
    public Edital removeTagFromEdital(Long editalId, Long tagId) {
        Edital edital = findEditalById(editalId);
        
        edital.removeTag(tagId); // Método de conveniência na entidade
        return editalRepository.save(edital);
    }
}