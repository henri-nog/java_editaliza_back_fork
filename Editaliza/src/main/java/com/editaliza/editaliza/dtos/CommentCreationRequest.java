// src/main/java/com/editaliza/editaliza/dtos/CommentCreationRequest.java
package com.editaliza.editaliza.dtos;

import com.editaliza.editaliza.models.CommentData;

// Esta classe é pública e está em seu próprio arquivo.
public class CommentCreationRequest {
    private CommentData comment;
    private Long authorId;
    private Long editalId;

    // Construtor padrão
    public CommentCreationRequest() {}

    // Getters e Setters
    public CommentData getComment() { return comment; }
    public void setComment(CommentData comment) { this.comment = comment; }
    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    public Long getEditalId() { return editalId; }
    public void setEditalId(Long editalId) { this.editalId = editalId; }
}