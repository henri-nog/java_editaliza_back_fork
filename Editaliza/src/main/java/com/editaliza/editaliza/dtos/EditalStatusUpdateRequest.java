// src/main/java/com/editaliza/editaliza/dtos/EditalStatusUpdateRequest.java
package com.editaliza.editaliza.dtos;

import com.editaliza.editaliza.models.EditalStatus;

// Esta classe é pública e está em seu próprio arquivo.
public class EditalStatusUpdateRequest {
    private EditalStatus newStatus;

    // Construtor padrão
    public EditalStatusUpdateRequest() {}

    // Construtor com campo
    public EditalStatusUpdateRequest(EditalStatus newStatus) {
        this.newStatus = newStatus;
    }

    // Getters e Setters
    public EditalStatus getNewStatus() { return newStatus; }
    public void setNewStatus(EditalStatus newStatus) { this.newStatus = newStatus; }
}