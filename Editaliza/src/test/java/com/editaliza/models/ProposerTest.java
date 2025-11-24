package com.editaliza.editaliza.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class ProposerTest {

    @Test
    void testValidCnpj() {
        Proposer p = new Proposer();
        p.setCnpj("12.345.678/0001-95"); // CNPJ válido de exemplo

        Assertions.assertTrue(p.isValidCnpj());
    }

    @Test
    void testInvalidCnpj() {
        Proposer p = new Proposer();
        p.setCnpj("11.111.111/1111-11"); // repetidos → inválido

        Assertions.assertFalse(p.isValidCnpj());
    }

    @Test
    void testFormattedCnpj() {
        Proposer p = new Proposer();
        p.setCnpj("12345678000195");

        Assertions.assertEquals("12.345.678/0001-95", p.getFormattedCnpj());
    }

    @Test
    void testValidateNameThrows() {
        Proposer p = new Proposer();
        p.setName("");

        Assertions.assertThrows(IllegalArgumentException.class, p::validateName);
    }

    @Test
    void testValidateEmailThrows() {
        Proposer p = new Proposer();
        p.setEmail("email-invalido");

        Assertions.assertThrows(IllegalArgumentException.class, p::validateEmail);
    }

    @Test
    void testValidateCnpjThrows() {
        Proposer p = new Proposer();
        p.setCnpj("123"); // inválido

        Assertions.assertThrows(IllegalArgumentException.class, p::validateCnpj);
    }

    @Test
    void testAddEdital() {
        Proposer p = new Proposer("123", "Nome", "email@email.com", null, "12.345.678/0001-95");
        Edital edital = new Edital();

        p.addEdital(edital);

        Assertions.assertEquals(1, p.getListEdital().size());
        Assertions.assertEquals(p, edital.getProposer());
    }

    @Test
    void testRemoveEdital() {
        Proposer p = new Proposer("123", "Nome", "email@email.com", null, "12.345.678/0001-95");
        Edital edital = new Edital();
        edital.setId(10L);

        p.addEdital(edital);
        p.removeEdital(10L);

        Assertions.assertEquals(0, p.getListEdital().size());
    }

    @Test
    void testCountEditalsByStatus() {
        Proposer p = new Proposer();

        Edital e1 = new Edital();
        e1.setStatus(EditalStatus.OPEN);

        Edital e2 = new Edital();
        e2.setStatus(EditalStatus.OPEN);

        Edital e3 = new Edital();
        e3.setStatus(EditalStatus.CLOSED);

        p.addEdital(e1);
        p.addEdital(e2);
        p.addEdital(e3);

        Assertions.assertEquals(2, p.countEditalsByStatus(EditalStatus.OPEN));
        Assertions.assertEquals(1, p.countEditalsByStatus(EditalStatus.CLOSED));
    }
}
