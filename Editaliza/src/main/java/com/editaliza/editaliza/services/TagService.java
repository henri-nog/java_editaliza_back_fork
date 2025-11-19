// src/main/java/com/editaliza/editaliza/services/TagService.java
package com.editaliza.editaliza.services;

import com.editaliza.editaliza.models.TagData;
import com.editaliza.editaliza.repositories.TagRepository;
import com.editaliza.editaliza.exceptions.ResourceNotFoundException;
import com.editaliza.editaliza.exceptions.BusinessException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    // -------------------------------------------------------------------------
    // READ OPERATIONS
    // -------------------------------------------------------------------------

    public TagData findTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "ID", id));
    }

    public List<TagData> findAllTags() {
        return tagRepository.findAll();
    }

    // -------------------------------------------------------------------------
    // WRITE OPERATIONS
    // -------------------------------------------------------------------------

    @Transactional
    public TagData createTag(TagData tag) {
        // A validação de nome e cor já ocorre no @PrePersist da entidade
        try {
            // Garante que o nome é válido
            if (!tag.validateName()) {
                throw new IllegalArgumentException("Nome da tag é obrigatório");
            }
            // Garante que a cor é válida
            if (!tag.isValidColor()) {
                 throw new IllegalArgumentException("Cor da tag deve estar no formato hexadecimal (#RRGGBB)");
            }
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Falha na validação da Tag: " + e.getMessage());
        }

        // Validação de unicidade do nome
        if (tagRepository.findByName(tag.getName()).isPresent()) {
            throw new BusinessException("Nome da tag já existe");
        }

        return tagRepository.save(tag);
    }

    @Transactional
    public TagData updateTag(Long id, TagData tagDetails) {
        TagData tag = findTagById(id);

        // Não é permitido alterar o nome, pois é unique, ou requer lógica mais complexa
        tag.setDescription(tagDetails.getDescription());
        tag.setColor(tagDetails.getColor());
        
        // Validação da cor
        try {
            if (!tag.isValidColor()) {
                 throw new IllegalArgumentException("Cor da tag deve estar no formato hexadecimal (#RRGGBB)");
            }
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Falha na atualização da Tag: " + e.getMessage());
        }
        
        return tagRepository.save(tag);
    }

    @Transactional
    public void deleteTag(Long id) {
        TagData tag = findTagById(id);
        
        // Remoção da Tag do lado de Edital e Artist (Muitos-para-Muitos)
        // Como o relacionamento não tem `orphanRemoval=true` ou `CascadeType.REMOVE`,
        // a exclusão da Tag só é segura se você remover todas as referências
        // manualmente ou garantir que a exclusão da Tag não seja permitida
        // se houver editais/artistas associados (o que seria uma Business Rule).
        
        // Por simplicidade aqui, permitiremos a exclusão, mas note que o JPA
        // removerá as entradas na tabela de junção `artist_tags` e `edital_tags`.

        tagRepository.delete(tag);
    }
}