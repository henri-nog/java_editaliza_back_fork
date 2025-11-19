// src/main/java/com/editaliza/editaliza/controllers/ProposerController.java
package com.editaliza.editaliza.controllers;

import com.editaliza.editaliza.models.Proposer;
import com.editaliza.editaliza.services.ProposerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proposers")
public class ProposerController {

    private final ProposerService proposerService;

    public ProposerController(ProposerService proposerService) {
        this.proposerService = proposerService;
    }

    // ------------------- CREATE -------------------

    @PostMapping
    public ResponseEntity<Proposer> createProposer(@RequestBody Proposer proposer) {
        Proposer createdProposer = proposerService.createProposer(proposer);
        return new ResponseEntity<>(createdProposer, HttpStatus.CREATED);
    }

    // ------------------- READ -------------------

    @GetMapping
    public ResponseEntity<List<Proposer>> getAllProposers() {
        List<Proposer> proposers = proposerService.findAllProposers();
        return ResponseEntity.ok(proposers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proposer> getProposerById(@PathVariable Long id) {
        Proposer proposer = proposerService.findProposerById(id);
        return ResponseEntity.ok(proposer);
    }

    // ------------------- UPDATE -------------------

    @PutMapping("/{id}")
    public ResponseEntity<Proposer> updateProposer(@PathVariable Long id, @RequestBody Proposer proposerDetails) {
        Proposer updatedProposer = proposerService.updateProposer(id, proposerDetails);
        return ResponseEntity.ok(updatedProposer);
    }

    // ------------------- DELETE -------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProposer(@PathVariable Long id) {
        proposerService.deleteProposer(id);
        return ResponseEntity.noContent().build();
    }
}