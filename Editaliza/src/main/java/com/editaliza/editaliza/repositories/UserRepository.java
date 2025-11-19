package com.editaliza.editaliza.repositories;

import com.editaliza.editaliza.models.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, Long> {
    
    /**
     * Busca um UserData (Artist ou Proposer) pelo email, que é único.
     */
    Optional<UserData> findByEmail(String email);

    boolean existsByEmail(String email);

    /**
     * Busca um UserData pelo ID.
     */
    // Este método já existe por herdar JpaRepository, mas pode ser explicitado se necessário.
    // Optional<UserData> findById(Long id);
}