package com.editaliza.Editaliza;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.editaliza.editaliza.models.Edital;

public class ValidateDadosTeste {

    private Edital edital;

    @BeforeEach
    void setUp() {
        // Aqui você cria um edital com título vazio para testar
        edital = new Edital("", "Uma história de terror e suspense", null, null, null, null);
    }

    @Test
    void testeTitleVazio() {
        // Testa se validate() lança IllegalArgumentException
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> edital.validate()
        );

        // Verifica se a mensagem da exceção está correta
        
        System.out.println(thrown.getMessage()); 
    }


@Test
void testeDescricaoVazia() {
    edital = new Edital("Bird Box", "", null, null, null, null);

    IllegalArgumentException thrown = assertThrows(
        IllegalArgumentException.class,
        () -> edital.validate()
    );

    
        System.out.println(thrown.getMessage()); 
    }
}