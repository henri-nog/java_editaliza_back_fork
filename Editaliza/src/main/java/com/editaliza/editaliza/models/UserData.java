package com.editaliza.editaliza.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// Classe abstrata base para todos os usuários (Artist e Proposer)
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@EntityListeners(AuditingEntityListener.class) // Habilita a auditoria automática de datas
public abstract class UserData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonIgnore // Garante que a senha não seja serializada para JSON
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "img_url", length = 500)
    private String imgUrl;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Coluna para o tipo de usuário (preenchida automaticamente pelo JPA)
    @Column(name = "user_type", insertable = false, updatable = false)
    @SuppressWarnings("unused")
    private String userType;

    // Relacionamento OTM para CommentData. MappedBy deve ser "author"
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CommentData> listComment;

    // Construtor padrão
    public UserData() {
        this.listComment = new ArrayList<>();
    }

    // Construtor com parâmetros
    public UserData(String password, String name, String email, String imgUrl) {
        this();
        this.password = password;
        this.name = name;
        this.email = email;
        this.imgUrl = imgUrl;
    }

    // Método abstrato obrigatório para retornar o tipo de usuário
    public abstract String getUserType();

    // Método para atualizar updatedAt manualmente (Redundante com Auditing, mas mantido)
    public void updateTimestamp() {
        this.updatedAt = new Date();
    }

    // Métodos para gerenciar o relacionamento bidirecional com CommentData

    public void addComment(CommentData comment) {
        if (!this.listComment.contains(comment)) {
            this.listComment.add(comment);
            // Define o autor na outra ponta
            comment.setAuthor(this); 
            this.updateTimestamp();
        }
    }

    public void removeComment(CommentData comment) {
        if (this.listComment.contains(comment)) {
            this.listComment.remove(comment);
            // Remove o link na outra ponta
            comment.setAuthor(null);
            this.updateTimestamp();
        }
    }

    // Getters e Setters (Mantidos por convenção)
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<CommentData> getListComment() {
        return listComment;
    }

    public void setListComment(List<CommentData> listComment) {
        this.listComment = listComment;
    }
}