package com.editaliza.editaliza.models;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Classe Artist que herda de UserData
@Entity
@DiscriminatorValue("ARTIST")
public class Artist extends UserData {
    
    // CORREÇÃO CRÍTICA: nullable alterado para TRUE para a estratégia SINGLE_TABLE.
    // A obrigatoriedade é garantida pela validação da aplicação (validateCpf()).
    @Column(nullable = true, unique = true, length = 14)
    private String cpf;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "artist_tags",
        joinColumns = @JoinColumn(name = "artist_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagData> listTags;

    // Construtor padrão
    public Artist() {
        super();
        this.listTags = new ArrayList<>();
    }

    // Construtor com parâmetros
    public Artist(String password, String name, String email, String imgUrl, String cpf) {
        super(password, name, email, imgUrl);
        this.cpf = cpf;
        this.listTags = new ArrayList<>();
    }

    /**
     * Sobrescreve para retornar o tipo fixo
     */
    @Override
    public String getUserType() {
        return "ARTIST";
    }

    // Callbacks JPA (Validação mantida)
    @PrePersist
    @PreUpdate
    protected void validateBeforeSave() {
        validate();
    }

    // Método para validar CPF (sem alterações)
    public boolean isValidCpf() {
        if (this.cpf == null || this.cpf.isEmpty()) {
            return false;
        }

        String cleanCpf = this.cpf.replaceAll("[^\\d]", "");

        if (cleanCpf.length() != 11) {
            return false;
        }

        if (cleanCpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int sum = 0;
        int remainder;

        for (int i = 1; i <= 9; i++) {
            sum += Character.getNumericValue(cleanCpf.charAt(i - 1)) * (11 - i);
        }
        remainder = (sum * 10) % 11;
        if (remainder == 10 || remainder == 11) remainder = 0;
        if (remainder != Character.getNumericValue(cleanCpf.charAt(9))) {
            return false;
        }

        sum = 0;
        for (int i = 1; i <= 10; i++) {
            sum += Character.getNumericValue(cleanCpf.charAt(i - 1)) * (12 - i);
        }
        remainder = (sum * 10) % 11;
        if (remainder == 10 || remainder == 11) remainder = 0;
        return remainder == Character.getNumericValue(cleanCpf.charAt(10));
    }

    // Método para formatar CPF (sem alterações)
    public String getFormattedCpf() {
        if (this.cpf == null || this.cpf.isEmpty()) {
            return "";
        }

        String cleanCpf = this.cpf.replaceAll("[^\\d]", "");

        if (cleanCpf.length() == 11) {
            return cleanCpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        }

        return this.cpf;
    }

    // Validações (Refinado para usar void e lançar exceção)
    public void validateCpf() {
        if (this.cpf == null || this.cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }

        if (!isValidCpf()) {
            throw new IllegalArgumentException("CPF inválido");
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
        validateCpf();
    }

    // Método para adicionar tag à lista (CORRIGIDO: Removida a lógica incorreta de TagData)
    public void addTag(TagData tag) {
        boolean exists = this.listTags.stream()
            .anyMatch(t -> t.getId() != null && t.getId().equals(tag.getId()));
        
        if (!exists) {
            this.listTags.add(tag);
            // Gerencia o outro lado (opcional, mas recomendado para consistência)
            if (!tag.getArtists().contains(this)) {
                tag.getArtists().add(this);
            }
            this.updateTimestamp();
        }
    }

    // Método para remover tag da lista (Corrigido para usar a lógica de Artist/TagData)
    public void removeTag(Long tagId) {
        TagData tagToRemove = this.listTags.stream()
            .filter(tag -> tag.getId() != null && tag.getId().equals(tagId))
            .findFirst()
            .orElse(null);

        if (tagToRemove != null) {
            this.listTags.remove(tagToRemove);
            // Gerencia o outro lado (opcional, mas recomendado para consistência)
            tagToRemove.getArtists().remove(this);
            this.updateTimestamp();
        }
    }

    // Método para verificar se tem uma tag específica
    public boolean hasTag(Long tagId) {
        return this.listTags.stream()
            .anyMatch(tag -> tag.getId() != null && tag.getId().equals(tagId));
    }

    // Método para obter nomes das tags
    public List<String> getTagNames() {
        return this.listTags.stream()
            .map(TagData::getName)
            .collect(Collectors.toList());
    }

    // Método para filtrar tags por cor
    public List<TagData> getTagsByColor(String color) {
        return this.listTags.stream()
            .filter(tag -> color.equals(tag.getColor()))
            .collect(Collectors.toList());
    }

    // Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<TagData> getListTags() {
        return listTags;
    }

    public void setListTags(List<TagData> listTags) {
        this.listTags = listTags;
    }
}