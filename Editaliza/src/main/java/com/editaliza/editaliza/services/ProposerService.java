// src/main/java/com/editaliza/editaliza/services/ProposerService.java
package com.editaliza.editaliza.services;

import com.editaliza.editaliza.models.Proposer;
import com.editaliza.editaliza.repositories.ProposerRepository;
import com.editaliza.editaliza.exceptions.ResourceNotFoundException;
import com.editaliza.editaliza.exceptions.BusinessException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProposerService {

    private final ProposerRepository proposerRepository;

    public ProposerService(ProposerRepository proposerRepository) {
        this.proposerRepository = proposerRepository;
    }

    // -------------------------------------------------------------------------
    // READ OPERATIONS
    // -------------------------------------------------------------------------

    public Proposer findProposerById(Long id) {
        return proposerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proponente", "ID", id));
    }

    public List<Proposer> findAllProposers() {
        return proposerRepository.findAll();
    }

    // -------------------------------------------------------------------------
    // WRITE OPERATIONS
    // -------------------------------------------------------------------------

    @Transactional
    public Proposer createProposer(Proposer proposer) {
        try {
            proposer.validate();
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Falha na criação do Proponente: " + e.getMessage());
        }

        // Validação adicional: garante que o email não está em uso
        if (proposerRepository.findByEmail(proposer.getEmail()).isPresent()) {
            throw new BusinessException("Email já cadastrado");
        }
        
        return proposerRepository.save(proposer);
    }

    @Transactional
    public Proposer updateProposer(Long id, Proposer proposerDetails) {
        Proposer proposer = findProposerById(id);

        // 1. Atualiza os campos
        proposer.setName(proposerDetails.getName());
        proposer.setImgUrl(proposerDetails.getImgUrl());
        
        // 2. Valida e Salva
        try {
            proposer.validateName();
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Falha na atualização do Proponente: " + e.getMessage());
        }

        return proposerRepository.save(proposer);
    }

    @Transactional
    public void deleteProposer(Long id) {
        Proposer proposer = findProposerById(id);
        // O `orphanRemoval = true` em Edital.java (listComment) cuidará dos editais e comentários relacionados.
        proposerRepository.delete(proposer);
    }
}