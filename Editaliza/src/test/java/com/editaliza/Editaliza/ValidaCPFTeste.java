package com.editaliza.Editaliza;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.editaliza.editaliza.models.Artist;

//Verifica se o CPF, dentro de 'models/Artist.java', é válido e inválido
public class ValidaCPFTeste {

    private Artist cpf;

    @BeforeEach
    void setUp() {
        cpf = new Artist();
    }

    @Test
    void testeCPFValido() {
        cpf.setCpf("12345678909"); 
        assertTrue(cpf.isValidCpf());
    }

    @Test
    void testeCpfInvalido() {
        cpf.setCpf(""); 
        assertFalse(cpf.isValidCpf());
    }
}