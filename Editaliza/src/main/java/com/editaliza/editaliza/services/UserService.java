package com.editaliza.editaliza.services;

import com.editaliza.editaliza.models.UserData;
import com.editaliza.editaliza.repositories.UserRepository;
import com.editaliza.editaliza.exceptions.ResourceNotFoundException;
import com.editaliza.editaliza.exceptions.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // Otimiza a maioria dos métodos
public class UserService {

    private final UserRepository userRepository;

    /**
     * Injeção de dependência via Construtor. 
     * Isso facilita a criação de mocks para o UserRepository nos testes JUnit.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // -------------------------------------------------------------------------
    // READ OPERATIONS
    // -------------------------------------------------------------------------

    public List<UserData> findAllUsers() {
        return userRepository.findAll();
    }

    public UserData findUserById(Long id) {
        // Lança exceção customizada se não encontrar (tratamento de erro para o Controller)
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "ID", id));
    }

    public UserData findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "Email", email));
    }

    // -------------------------------------------------------------------------
    // WRITE OPERATIONS
    // -------------------------------------------------------------------------

    @Transactional
    public UserData saveNewUser(UserData user) {
        // Validação de negócio antes de salvar
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException("O email '" + user.getEmail() + "' já está cadastrado.");
        }
        
        // A validação de campos (nome, senha, etc.) é feita na Entidade (@PrePersist)
        // Se a Entidade lançar uma IllegalArgumentException, o @Transactional irá dar rollback.

        return userRepository.save(user);
    }

    @Transactional
    public UserData updateUser(Long id, UserData userDetails) {
        UserData existingUser = findUserById(id); // Garante que o usuário existe

        // 1. Validação de Email (evita que o email mude para um que já existe)
        if (!userDetails.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.existsByEmail(userDetails.getEmail())) {
                throw new BusinessException("O novo email '" + userDetails.getEmail() + "' já está cadastrado por outro usuário.");
            }
        }

        // 2. Aplica atualizações
        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setImgUrl(userDetails.getImgUrl());
        // A atualização de senha deve ser um método separado por segurança

        // 3. Persiste (o JPA detecta as mudanças e o @Transactional salva no commit)
        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        // Usa o findById para verificar a existência e lançar 404 se não existir
        UserData userToDelete = findUserById(id); 
        userRepository.delete(userToDelete);
    }

    // -------------------------------------------------------------------------
    // SECURITY/BUSINESS OPERATIONS
    // -------------------------------------------------------------------------

    @Transactional
    public UserData alterPassword(Long id, String newPassword) {
        UserData existingUser = findUserById(id);
        
        // ⚠️ Nota de Segurança: A senha deve ser HASHADA antes de ser salva.
        // Exemplo: existingUser.setPassword(passwordEncoder.encode(newPassword));
        existingUser.setPassword(newPassword); 
        
        return userRepository.save(existingUser);
    }
}