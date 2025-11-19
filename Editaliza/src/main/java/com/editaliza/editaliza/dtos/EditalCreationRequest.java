// src/main/java/com/editaliza/editaliza/dtos/EditalCreationRequest.java
package com.editaliza.editaliza.dtos;

import com.editaliza.editaliza.models.Edital;

// Esta classe é pública e está em seu próprio arquivo.
public class EditalCreationRequest {
    private Edital edital;
    private Long proposerId;

    // Construtor padrão (necessário para desserialização JSON)
    public EditalCreationRequest() {}

    // Construtor com todos os campos (opcional)
    public EditalCreationRequest(Edital edital, Long proposerId) {
        this.edital = edital;
        this.proposerId = proposerId;
    }

    // Getters e Setters (Boa prática para classes DTO)
    public Edital getEdital() { return edital; }
    public void setEdital(Edital edital) { this.edital = edital; }
    public Long getProposerId() { return proposerId; }
    public void setProposerId(Long proposerId) { this.proposerId = proposerId; }
}