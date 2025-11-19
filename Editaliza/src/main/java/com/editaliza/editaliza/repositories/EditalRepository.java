package com.editaliza.editaliza.repositories;

import com.editaliza.editaliza.models.Edital;
import com.editaliza.editaliza.models.EditalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EditalRepository extends JpaRepository<Edital, Long> {
    
    /**
     * Busca todos os editais com um status específico.
     */
    List<Edital> findByStatus(EditalStatus status);

    /**
     * Busca todos os editais criados por um Proposer específico (pelo ID do Proposer).
     * Usa o relacionamento "proposer" na classe Edital.
     */
    List<Edital> findByProposer_Id(Long proposerId);

    /**
     * Busca editais que possuem uma Tag específica pelo nome da Tag.
     */
    List<Edital> findByListTags_Name(String tagName);
    
    /**
     * Busca os editais pelo título, ignorando case, para uma busca simples.
     */
    List<Edital> findByTitleContainingIgnoreCase(String title);

    List<Edital> findByStatusIn(List<EditalStatus> of);
}