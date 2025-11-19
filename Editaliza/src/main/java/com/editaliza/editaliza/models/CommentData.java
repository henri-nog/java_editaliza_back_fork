package com.editaliza.editaliza.models;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;


// Classe CommentData com JPA/Hibernate
@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
public class CommentData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_name", nullable = false, length = 100)
    private String authorName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // Usando tipo primitivo boolean, non-nullable no banco
    @Column(nullable = false)
    private boolean approved; 

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // CORRIGIDO: Renomeado para 'author' (Coerência com UserData.listComment)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserData author;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "edital_id", nullable = false)
    private Edital edital;

    // Construtor padrão
    public CommentData() {
        this.approved = false; 
        this.status = CommentStatus.PENDING;
    }

    // Construtor com parâmetros
    public CommentData(String authorName, String content, UserData author, 
                       Edital edital, Boolean approved, CommentStatus status) {
        this();
        this.authorName = authorName;
        this.content = content;
        this.author = author;
        this.edital = edital;
        if (approved != null) {
            this.approved = approved;
        }
        if (status != null) {
            this.status = status;
        }
    }

    // Callbacks JPA (Auditing lida com datas, este cuida da validação)
    @PrePersist
    @PreUpdate
    protected void validateBeforeSave() {
        validate();
    }

    // Método para atualizar updatedAt
    public void updateTimestamp() {
        this.updatedAt = new Date();
    }

    // Métodos de Lógica de Negócio

    public void approve() {
        this.approved = true;
        this.status = CommentStatus.APPROVED;
        this.updateTimestamp();
    }

    public void reject() {
        this.approved = false;
        this.status = CommentStatus.REJECTED;
        this.updateTimestamp();
    }

    public void hide() {
        this.approved = false;
        this.status = CommentStatus.HIDDEN;
        this.updateTimestamp();
    }

    public void flag() {
        this.status = CommentStatus.FLAGGED;
        this.updateTimestamp();
    }

    public boolean validateContent() {
        if (this.content == null || this.content.trim().isEmpty()) {
            return false;
        }
        if (this.content.length() > 1000) {
            return false;
        }
        return this.content.trim().length() >= 3;
    }

    public boolean validateAuthorName() {
        if (this.authorName == null || this.authorName.trim().isEmpty()) {
            return false;
        }
        return this.authorName.length() <= 100;
    }

    public void validate() {
        if (!validateAuthorName()) {
            throw new IllegalArgumentException("Nome do autor é inválido");
        }
        if (!validateContent()) {
            throw new IllegalArgumentException("Conteúdo do comentário é inválido");
        }
        if (this.author == null) {
            throw new IllegalArgumentException("Autor é obrigatório");
        }
        if (this.edital == null) {
            throw new IllegalArgumentException("Edital é obrigatório");
        }
    }

    public boolean canBeEditedBy(Long userId) {
        return this.author.getId().equals(userId) && 
               CommentStatus.PENDING.equals(this.status);
    }

    public boolean canBeModerated() {
        return CommentStatus.PENDING.equals(this.status) || 
               CommentStatus.FLAGGED.equals(this.status);
    }

    public boolean isPubliclyVisible() {
        return this.approved && 
               CommentStatus.APPROVED.equals(this.status);
    }

    public String getSummary(int maxLength) {
        if (this.content.length() <= maxLength) {
            return this.content;
        }
        return this.content.substring(0, maxLength).trim() + "...";
    }

    public String getSummary() {
        return getSummary(100);
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public CommentStatus getStatus() {
        return status;
    }

    public void setStatus(CommentStatus status) {
        this.status = status;
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

    // Getters e Setters corrigidos (usando 'author')
    public UserData getAuthor() {
        return author;
    }

    public void setAuthor(UserData author) {
        this.author = author;
    }

    public Edital getEdital() {
        return edital;
    }

    public void setEdital(Edital edital) {
        this.edital = edital;
    }
}