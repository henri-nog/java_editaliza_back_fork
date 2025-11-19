package com.editaliza.editaliza.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

// Classe Edital com JPA/Hibernate
@Entity
@Table(name = "editais")
@EntityListeners(AuditingEntityListener.class)
public class Edital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "publish_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EditalStatus status;

    @Column(name = "inscription_link", length = 500)
    private String inscriptionLink;

    @Column(name = "complete_edital_link", length = 500)
    private String completeEditalLink;

    @Column(name = "img_cover_url", length = 500)
    private String imgCoverUrl;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // CORRIGIDO: Tipo alterado para Proposer (Coerência com a regra de negócio)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proposer_id", nullable = false)
    private Proposer proposer;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "edital_tags", joinColumns = @JoinColumn(name = "edital_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<TagData> listTags;

    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CommentData> listComment;

    // Construtor padrão
    public Edital() {
        this.status = EditalStatus.DRAFT;
        this.listTags = new ArrayList<>();
        this.listComment = new ArrayList<>();
        // Datas não são inicializadas, Auditing se encarrega
    }

    // Construtor com parâmetros
    public Edital(String title, String description, Proposer proposer, // Tipo alterado
            Date publishDate, Date endDate, EditalStatus status) {
        this();
        this.title = title;
        this.description = description;
        this.proposer = proposer;
        this.publishDate = publishDate;
        this.endDate = endDate;
        if (status != null) {
            this.status = status;
        }
    }

    // Callbacks JPA (Validação mantida)
    @PrePersist
    @PreUpdate
    protected void validateBeforeSave() {
        validate();
    }

    // Removida validateProposerType() pois o tipo já é garantido como Proposer.
    public void validateProposer() {
        if (this.proposer == null) {
            throw new IllegalArgumentException("Proponente do edital é obrigatório");
        }
    }

    // Método para atualizar updatedAt
    public void updateTimestamp() {
        this.updatedAt = new Date();
    }

    // Método para validar dados antes de salvar/atualizar
    public void validate() {
        validateProposer();

        if (this.title == null || this.title.trim().isEmpty()) {
            throw new IllegalArgumentException("Título é obrigatório");
        }

        if (this.description == null || this.description.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }

        if (this.endDate != null && this.publishDate != null &&
                !this.endDate.after(this.publishDate)) {
            throw new IllegalArgumentException(
                    "Data de encerramento deve ser posterior à data de publicação");
        }
    }

    // Método para adicionar tag
    public void addTag(TagData tag) {
        boolean exists = this.listTags.stream()
                .anyMatch(t -> t.getId() != null && t.getId().equals(tag.getId()));

        if (!exists) {
            this.listTags.add(tag);
            // Gerencia o outro lado (opcional, mas recomendado para consistência)
            if (!tag.getEditais().contains(this)) {
                tag.getEditais().add(this);
            }
            this.updateTimestamp();
        }
    }

    // Método para remover tag
    public void removeTag(Long tagId) {
        TagData tagToRemove = this.listTags.stream()
                .filter(tag -> tag.getId() != null && tag.getId().equals(tagId))
                .findFirst()
                .orElse(null);

        if (tagToRemove != null) {
            this.listTags.remove(tagToRemove);
            // Gerencia o outro lado (opcional, mas recomendado para consistência)
            tagToRemove.getEditais().remove(this);
            this.updateTimestamp();
        }
    }

    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentData> comments = new ArrayList<>(); // Supondo que você tem esta lista

    // Método de conveniência para gerenciar o relacionamento bidirecional

    public void addComment(CommentData comment) {
        if (!this.comments.contains(comment)) {
            this.comments.add(comment);
            comment.setEdital(this);
        }
    }

    public void removeComment(CommentData comment) {
        // 1. Remove da lista do Edital
        if (this.comments.contains(comment)) {
            this.comments.remove(comment);

            // 2. Remove o link na outra ponta (boas práticas)
            if (comment.getEdital() != null && comment.getEdital().equals(this)) {
                comment.setEdital(null);
            }
            // Se a classe Edital tiver um método updateTimestamp(), chame-o aqui
            // this.updateTimestamp();
        }
    }

    // Método para verificar se o edital está aberto para inscrições
    public boolean isOpen() {
        Date now = new Date();
        return EditalStatus.OPEN.equals(this.status) &&
                this.publishDate != null &&
                !this.publishDate.after(now) &&
                (this.endDate == null || this.endDate.after(now));
    }

    // Método para verificar se o edital está expirado
    public boolean isExpired() {
        if (this.endDate == null)
            return false;
        return new Date().after(this.endDate);
    }

    // Método para atualizar status baseado nas datas
    public void updateStatus() {
        Date now = new Date();

        if (EditalStatus.PUBLISHED.equals(this.status) &&
                this.publishDate != null && !this.publishDate.after(now)) {
            this.status = EditalStatus.OPEN;
        }

        if (EditalStatus.OPEN.equals(this.status) &&
                this.endDate != null && !this.endDate.after(now)) {
            this.status = EditalStatus.CLOSED;
        }

        this.updateTimestamp();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public EditalStatus getStatus() {
        return status;
    }

    public void setStatus(EditalStatus status) {
        this.status = status;
    }

    public String getInscriptionLink() {
        return inscriptionLink;
    }

    public void setInscriptionLink(String inscriptionLink) {
        this.inscriptionLink = inscriptionLink;
    }

    public String getCompleteEditalLink() {
        return completeEditalLink;
    }

    public void setCompleteEditalLink(String completeEditalLink) {
        this.completeEditalLink = completeEditalLink;
    }

    public String getImgCoverUrl() {
        return imgCoverUrl;
    }

    public void setImgCoverUrl(String imgCoverUrl) {
        this.imgCoverUrl = imgCoverUrl;
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

    // CORRIGIDO: Retorna Proposer
    public Proposer getProposer() {
        return proposer;
    }

    // CORRIGIDO: Aceita Proposer
    public void setProposer(Proposer proposer) {
        this.proposer = proposer;
    }

    public List<TagData> getListTags() {
        return listTags;
    }

    public void setListTags(List<TagData> listTags) {
        this.listTags = listTags;
    }

    public List<CommentData> getListComment() {
        return listComment;
    }

    public void setListComment(List<CommentData> listComment) {
        this.listComment = listComment;
    }

    public List<CommentData> getComments() {
        return comments;
    }

    public void setComments(List<CommentData> comments) {
        this.comments = comments;
    }
}