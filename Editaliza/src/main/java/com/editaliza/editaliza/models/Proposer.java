package com.editaliza.editaliza.models;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

// Classe Proposer que herda de UserData
@Entity
@DiscriminatorValue("PROPOSER")
public class Proposer extends UserData {
    
    // CORREÇÃO CRÍTICA: nullable alterado para TRUE para a estratégia SINGLE_TABLE.
    // A obrigatoriedade é garantida pela validação da aplicação (validateCnpj()).
    @Column(nullable = true, unique = true, length = 18)
    private String cnpj;

    // Mapeado por 'proposer' em Edital.java (Corrigido para Proposer)
    @OneToMany(mappedBy = "proposer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Edital> listEdital;

    // Construtor padrão
    public Proposer() {
        super();
        this.listEdital = new ArrayList<>();
    }

    // Construtor com parâmetros
    public Proposer(String password, String name, String email, String imgUrl, String cnpj) {
        super(password, name, email, imgUrl);
        this.cnpj = cnpj;
        this.listEdital = new ArrayList<>();
    }

    /**
     * Sobrescreve para retornar o tipo fixo
     */
    @Override
    public String getUserType() {
        return "PROPOSER";
    }

    // Callbacks JPA (Validação mantida)
    @PrePersist
    @PreUpdate
    protected void validateBeforeSave() {
        validate();
    }

    // Método para validar CNPJ (implementação básica)
    public boolean isValidCnpj() {
        if (this.cnpj == null || this.cnpj.isEmpty()) {
            return false;
        }

        String cleanCnpj = this.cnpj.replaceAll("[^\\d]", "");

        if (cleanCnpj.length() != 14) {
            return false;
        }

        if (cleanCnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(cleanCnpj.charAt(i)) * weights1[i];
        }
        int remainder = sum % 11;
        int digit1 = (remainder < 2) ? 0 : 11 - remainder;
        if (digit1 != Character.getNumericValue(cleanCnpj.charAt(12))) {
            return false;
        }

        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += Character.getNumericValue(cleanCnpj.charAt(i)) * weights2[i];
        }
        remainder = sum % 11;
        int digit2 = (remainder < 2) ? 0 : 11 - remainder;
        return digit2 == Character.getNumericValue(cleanCnpj.charAt(13));
    }

    // Método para formatar CNPJ
    public String getFormattedCnpj() {
        if (this.cnpj == null || this.cnpj.isEmpty()) {
            return "";
        }

        String cleanCnpj = this.cnpj.replaceAll("[^\\d]", "");

        if (cleanCnpj.length() == 14) {
            return cleanCnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
        }

        return this.cnpj;
    }

    // Validações (Refinado para usar void e lançar exceção)
    public void validateCnpj() {
        if (this.cnpj == null || this.cnpj.trim().isEmpty()) {
            throw new IllegalArgumentException("CNPJ é obrigatório");
        }

        if (!isValidCnpj()) {
            throw new IllegalArgumentException("CNPJ inválido");
        }
    }

    public void validateName() {
        if (this.getName() == null || this.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
    }

    public void validateEmail() {
        if (this.getEmail() == null || this.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }

        String emailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        if (!this.getEmail().matches(emailRegex)) {
            throw new IllegalArgumentException("Email inválido");
        }
    }

    // Método para validar todos os campos
    public void validate() {
        validateName();
        validateEmail();
        validateCnpj();
    }

    // Método para adicionar edital à lista (assume que Edital.setProposer existe)
    public void addEdital(Edital edital) {
        if (!this.listEdital.contains(edital)) {
            this.listEdital.add(edital);
            // Gerencia o outro lado do relacionamento
            edital.setProposer(this);
            this.updateTimestamp();
        }
    }

    // Método para remover edital da lista
    public void removeEdital(Long editalId) {
        Edital editalToRemove = this.listEdital.stream()
            .filter(edital -> edital.getId() != null && edital.getId().equals(editalId))
            .findFirst()
            .orElse(null);

        if (editalToRemove != null) {
            this.listEdital.remove(editalToRemove);
            editalToRemove.setProposer(null);
            this.updateTimestamp();
        }
    }

    // Método para obter edital por ID
    public Edital getEditalById(Long editalId) {
        return this.listEdital.stream()
            .filter(edital -> edital.getId() != null && edital.getId().equals(editalId))
            .findFirst()
            .orElse(null);
    }

    // Método para contar editais por status
    public long countEditalsByStatus(EditalStatus status) {
        return this.listEdital.stream()
            .filter(edital -> status.equals(edital.getStatus()))
            .count();
    }

    // Método para obter editais abertos
    public List<Edital> getOpenEditais() {
        return this.listEdital.stream()
            .filter(Edital::isOpen)
            .toList();
    }

    // Método para obter editais expirados
    public List<Edital> getExpiredEditais() {
        return this.listEdital.stream()
            .filter(Edital::isExpired)
            .toList();
    }

    // Getters e Setters
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<Edital> getListEdital() {
        return listEdital;
    }

    public void setListEdital(List<Edital> listEdital) {
        this.listEdital = listEdital;
    }
}