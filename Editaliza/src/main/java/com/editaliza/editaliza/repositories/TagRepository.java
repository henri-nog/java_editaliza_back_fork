package com.editaliza.editaliza.repositories;

import com.editaliza.editaliza.models.TagData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TagRepository extends JpaRepository<TagData, Long> {
    
    /**
     * Busca uma Tag pelo nome, que é único.
     */
    Optional<TagData> findByName(String name);

    /**
     * Verifica se uma Tag com o nome fornecido existe.
     */
    boolean existsByName(String name);
}