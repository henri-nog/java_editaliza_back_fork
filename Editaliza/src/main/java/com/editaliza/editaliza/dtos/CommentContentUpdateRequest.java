// src/main/java/com/editaliza/editaliza/dtos/CommentContentUpdateRequest.java
package com.editaliza.editaliza.dtos;

// Esta classe é pública e está em seu próprio arquivo.
public class CommentContentUpdateRequest {
    private String newContent;
    private Long userId; // ID do usuário que está editando

    // Construtor padrão
    public CommentContentUpdateRequest() {}

    // Getters e Setters
    public String getNewContent() { return newContent; }
    public void setNewContent(String newContent) { this.newContent = newContent; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}