// src/main/java/com/editaliza/editaliza/services/ArtistService.java
package com.editaliza.editaliza.services;

import com.editaliza.editaliza.models.Artist;
import com.editaliza.editaliza.models.TagData;
import com.editaliza.editaliza.repositories.ArtistRepository;
import com.editaliza.editaliza.repositories.TagRepository;
import com.editaliza.editaliza.exceptions.ResourceNotFoundException;
import com.editaliza.editaliza.exceptions.BusinessException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final TagRepository tagRepository; // Necessário para gerenciar Tags

    public ArtistService(ArtistRepository artistRepository, TagRepository tagRepository) {
        this.artistRepository = artistRepository;
        this.tagRepository = tagRepository;
    }

    // -------------------------------------------------------------------------
    // READ OPERATIONS
    // -------------------------------------------------------------------------

    public Artist findArtistById(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artista", "ID", id));
    }

    public List<Artist> findAllArtists() {
        return artistRepository.findAll();
    }

    // -------------------------------------------------------------------------
    // WRITE OPERATIONS
    // -------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    @Transactional
    public Artist createArtist(Artist artist) {
        // A validação de nome, email e CPF já ocorre no @PrePersist da entidade
        try {
            artist.validate();
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Falha na criação do Artista: " + e.getMessage());
        }

        // Validação adicional: garante que o email não está em uso (além do unique do DB)
        if (((Optional<Artist>) artistRepository.findByEmail(artist.getEmail())).isPresent()) {
            throw new BusinessException("Email já cadastrado");
        }

        return artistRepository.save(artist);
    }

    @Transactional
    public Artist updateArtist(Long id, Artist artistDetails) {
        Artist artist = findArtistById(id);

        // 1. Atualiza os campos
        artist.setName(artistDetails.getName());
        // A atualização do Email/CPF deve ser tratada com cuidado, requerendo regras de negócio mais robustas
        // Por simplicidade, assumimos que apenas o nome e imgUrl serão atualizados
        artist.setImgUrl(artistDetails.getImgUrl());
        
        // 2. Valida e Salva
        try {
            // A validação da entidade garante a integridade dos dados atualizados
            artist.validateName(); 
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Falha na atualização do Artista: " + e.getMessage());
        }

        return artistRepository.save(artist);
    }

    @Transactional
    public void deleteArtist(Long id) {
        Artist artist = findArtistById(id);
        // Não precisamos nos preocupar com comentários ou tags, pois
        // cascade = CascadeType.ALL, orphanRemoval = true no UserData e Artist gerenciam isso.
        artistRepository.delete(artist);
    }

    // -------------------------------------------------------------------------
    // TAG MANAGEMENT
    // -------------------------------------------------------------------------

    @Transactional
    public Artist addTagToArtist(Long artistId, Long tagId) {
        Artist artist = findArtistById(artistId);
        TagData tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "ID", tagId));

        artist.addTag(tag); // Método de conveniência na entidade
        return artistRepository.save(artist);
    }

    @Transactional
    public Artist removeTagFromArtist(Long artistId, Long tagId) {
        Artist artist = findArtistById(artistId);
        
        artist.removeTag(tagId); // Método de conveniência na entidade
        return artistRepository.save(artist);
    }
}