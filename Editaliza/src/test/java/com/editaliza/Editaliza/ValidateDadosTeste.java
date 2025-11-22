package com.editaliza.Editaliza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.editaliza.editaliza.models.Edital;

public class ValidateDadosTeste {

    public Edital title;

    @BeforeEach
    void setUp() {
        //String title, String description, Proposer proposer, // Tipo alterado
        //    Date publishDate, Date endDate, EditalStatus status
        title = new Edital("Bird Box", "Uma hisória de terror e suspense", No, 12, 13, "True");
    }

    @Test
    void testeTitleVazio() {
        assertEquals(, title.validate(), "Título é obrigatório");
    }
}