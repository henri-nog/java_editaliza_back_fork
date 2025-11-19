package com.editaliza.editaliza.models;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


// Classe TagData com JPA/Hibernate
@Entity
@Table(name = "tags")
@EntityListeners(AuditingEntityListener.class)
public class TagData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 7)
    private String color;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToMany(mappedBy = "listTags")
    private List<Edital> editais;

    // Adicionado mapeamento reverso para Artist (para coerência e uso correto)
    @ManyToMany(mappedBy = "listTags")
    private List<Artist> artists; 

    // Padrão para validar cor hexadecimal
    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("^#[0-9A-Fa-f]{6}$");

    // Construtor padrão (Datas não são inicializadas, Auditing se encarrega)
    public TagData() {
        this.editais = new ArrayList<>();
        this.artists = new ArrayList<>();
    }

    // Construtor com parâmetros
    public TagData(String name, String description, String color) {
        this();
        this.name = name;
        this.description = description;
        this.color = color;
    }

    // Callbacks JPA (Removida a inicialização de datas, mantida a validação)
    @PrePersist
    @PreUpdate
    protected void validateBeforeSave() {
        if (!validateName()) {
            throw new IllegalArgumentException("Nome da tag é obrigatório");
        }
        if (!isValidColor()) {
            throw new IllegalArgumentException("Cor da tag deve estar no formato hexadecimal (#RRGGBB)");
        }
    }

    // Método para atualizar updatedAt
    public void updateTimestamp() {
        this.updatedAt = new Date();
    }

    // Método para validar cor hex
    public boolean isValidColor() {
        return this.color != null && HEX_COLOR_PATTERN.matcher(this.color).matches();
    }

    // Método para validar se o nome é válido
    public boolean validateName() {
        return this.name != null && !this.name.trim().isEmpty();
    }

    // Método para adicionar edital
    public void addEdital(Edital edital) {
        if (!this.editais.contains(edital)) {
            this.editais.add(edital);
            // Gerencia o outro lado do relacionamento
            if (!edital.getListTags().contains(this)) {
                edital.getListTags().add(this);
            }
        }
    }

    // Método para remover edital
    public void removeEdital(Edital edital) {
        if (this.editais.contains(edital)) {
            this.editais.remove(edital);
             // Gerencia o outro lado do relacionamento
            edital.getListTags().remove(this);
        }
    }

    // Métodos para Artist (Coerência com Artist.java)
    public void addArtist(Artist artist) {
        if (!this.artists.contains(artist)) {
            this.artists.add(artist);
            // Gerencia o outro lado do relacionamento
            if (!artist.getListTags().contains(this)) {
                artist.getListTags().add(this);
            }
        }
    }

    public void removeArtist(Artist artist) {
        if (this.artists.contains(artist)) {
            this.artists.remove(artist);
            // Gerencia o outro lado do relacionamento
            artist.getListTags().remove(this);
        }
    }


    // Método para converter cor hex em RGB
    public int[] getRGBColor() {
        if (!isValidColor()) {
            return new int[]{0, 0, 0};
        }

        String hex = this.color.substring(1);
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);

        return new int[]{r, g, b};
    }

    // Método para normalizar nome (lowercase, sem espaços extras)
    public String getNormalizedName() {
        return this.name.trim().toLowerCase();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Edital> getEditais() {
        return editais;
    }

    public void setEditais(List<Edital> editais) {
        this.editais = editais;
    }
    
    // Método getArtists corrigido para retornar o tipo correto (List<Artist>)
    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}