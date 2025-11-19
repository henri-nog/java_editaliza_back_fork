package com.editaliza.editaliza.repositories;

import com.editaliza.editaliza.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    
    /**
     * Busca um Artist pelo CPF, que é único.
     */
    Optional<Artist> findByCpf(String cpf);
    
    /**
     * Busca Artists que estão associados a uma Tag específica pelo nome da Tag.
     * Usa a sintaxe de property expressions do Spring Data JPA: [nomeDaLista]_[nomeDoCampo]
     */
    List<Artist> findByListTags_Name(String tagName);

    public Object findByEmail(String email);
}