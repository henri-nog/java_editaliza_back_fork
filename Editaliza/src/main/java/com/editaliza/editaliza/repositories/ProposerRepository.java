package com.editaliza.editaliza.repositories;

import com.editaliza.editaliza.models.Proposer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProposerRepository extends JpaRepository<Proposer, Long> {
    
    /**
     * Busca um Proposer pelo CNPJ, que é único.
     */
    Optional<Proposer> findByCnpj(String cnpj);

    Optional<Proposer> findByEmail(String email);
}