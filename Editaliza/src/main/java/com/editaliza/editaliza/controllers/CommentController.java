// src/main/java/com/editaliza/editaliza/controllers/CommentController.java
package com.editaliza.editaliza.controllers;

import com.editaliza.editaliza.models.CommentData;
import com.editaliza.editaliza.services.CommentService;
import com.editaliza.editaliza.dtos.CommentCreationRequest; // Importa DTO
import com.editaliza.editaliza.dtos.CommentContentUpdateRequest; // Importa DTO
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // ------------------- CREATE -------------------

    @PostMapping
    public ResponseEntity<CommentData> createComment(@RequestBody CommentCreationRequest request) {
        // CORREÇÃO: Usando Getters para acessar campos privados do DTO
        if (request.getComment() == null || request.getAuthorId() == null || request.getEditalId() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        // CORREÇÃO: Usando Getters para passar os dados para o Service
        CommentData createdComment = commentService.createComment(request.getComment(), request.getAuthorId(), request.getEditalId());
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    // ------------------- READ -------------------
    
    // Comentários aprovados por Edital (público)
    @GetMapping("/edital/{editalId}/approved")
    public ResponseEntity<List<CommentData>> getApprovedCommentsByEdital(@PathVariable Long editalId) {
        List<CommentData> comments = commentService.findApprovedCommentsByEdital(editalId);
        return ResponseEntity.ok(comments);
    }

    // Comentários por Autor
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<CommentData>> getCommentsByAuthor(@PathVariable Long authorId) {
        List<CommentData> comments = commentService.findCommentsByAuthor(authorId);
        return ResponseEntity.ok(comments);
    }

    // Buscar Comentário por ID
    @GetMapping("/{id}")
    public ResponseEntity<CommentData> getCommentById(@PathVariable Long id) {
        CommentData comment = commentService.findCommentById(id);
        return ResponseEntity.ok(comment);
    }
    
    // ------------------- UPDATE -------------------

    // Atualiza apenas o conteúdo de um comentário (requer que seja o autor)
    @PatchMapping("/{commentId}/content")
    public ResponseEntity<CommentData> updateCommentContent(@PathVariable Long commentId, @RequestBody CommentContentUpdateRequest request) {
        // CORREÇÃO: Usando Getters para acessar campos privados do DTO
        if (request.getNewContent() == null || request.getUserId() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        // CORREÇÃO: Usando Getters para passar os dados para o Service
        CommentData updatedComment = commentService.updateCommentContent(commentId, request.getUserId(), request.getNewContent());
        return ResponseEntity.ok(updatedComment);
    }

    // ------------------- MODERATION -------------------
    
    // Aprovação de Comentário (requer privilégios de moderação)
    @PatchMapping("/{commentId}/approve")
    public ResponseEntity<CommentData> approveComment(@PathVariable Long commentId) {
        CommentData approvedComment = commentService.approveComment(commentId);
        return ResponseEntity.ok(approvedComment);
    }

    // ------------------- DELETE -------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}