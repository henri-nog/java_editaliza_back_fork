// src/main/java/com/editaliza/editaliza/controllers/EditalController.java
package com.editaliza.editaliza.controllers;

import com.editaliza.editaliza.models.Edital;
import com.editaliza.editaliza.services.EditalService;
import com.editaliza.editaliza.dtos.EditalCreationRequest; // Importa DTO
import com.editaliza.editaliza.dtos.EditalStatusUpdateRequest; // Importa DTO
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/editais")
public class EditalController {

    private final EditalService editalService;

    public EditalController(EditalService editalService) {
        this.editalService = editalService;
    }

    // ------------------- CREATE -------------------

    @PostMapping
    public ResponseEntity<Edital> createEdital(@RequestBody EditalCreationRequest request) {
        // CORREÇÃO: Usando Getters para acessar campos privados do DTO
        if (request.getEdital() == null || request.getProposerId() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        // CORREÇÃO: Usando Getters para passar os dados para o Service
        Edital createdEdital = editalService.createEdital(request.getEdital(), request.getProposerId());
        return new ResponseEntity<>(createdEdital, HttpStatus.CREATED);
    }

    // ------------------- READ -------------------

    @GetMapping
    public ResponseEntity<List<Edital>> getAllPublishedEditals() {
        List<Edital> editals = editalService.findAllPublishedEditals();
        return ResponseEntity.ok(editals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Edital> getEditalById(@PathVariable Long id) {
        Edital edital = editalService.findEditalById(id);
        return ResponseEntity.ok(edital);
    }

    @GetMapping("/proposer/{proposerId}")
    public ResponseEntity<List<Edital>> getEditalsByProposer(@PathVariable Long proposerId) {
        List<Edital> editals = editalService.findEditalsByProposer(proposerId);
        return ResponseEntity.ok(editals);
    }

    // ------------------- UPDATE -------------------

    @PutMapping("/{id}")
    public ResponseEntity<Edital> updateEdital(@PathVariable Long id, @RequestBody Edital editalDetails) {
        Edital updatedEdital = editalService.updateEdital(id, editalDetails);
        return ResponseEntity.ok(updatedEdital);
    }
    
    // Atualização de Status (endpoint específico)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Edital> updateEditalStatus(@PathVariable Long id, @RequestBody EditalStatusUpdateRequest request) {
        // CORREÇÃO: Usando Getter para acessar campo privado do DTO
        if (request.getNewStatus() == null) {
            return ResponseEntity.badRequest().build();
        }

        // CORREÇÃO: Usando Getter para passar o dado para o Service
        Edital updatedEdital = editalService.updateEditalStatus(id, request.getNewStatus());
        return ResponseEntity.ok(updatedEdital);
    }

    // ------------------- DELETE -------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEdital(@PathVariable Long id) {
        editalService.deleteEdital(id);
        return ResponseEntity.noContent().build();
    }
    
    // ------------------- TAGS -------------------
    
    @PostMapping("/{editalId}/tags/{tagId}")
    public ResponseEntity<Edital> addTagToEdital(@PathVariable Long editalId, @PathVariable Long tagId) {
        Edital edital = editalService.addTagToEdital(editalId, tagId);
        return ResponseEntity.ok(edital);
    }

    @DeleteMapping("/{editalId}/tags/{tagId}")
    public ResponseEntity<Edital> removeTagFromEdital(@PathVariable Long editalId, @PathVariable Long tagId) {
        Edital edital = editalService.removeTagFromEdital(editalId, tagId);
        return ResponseEntity.ok(edital);
    }
}