package com.editaliza.Editaliza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.editaliza.editaliza.models.Artist;
import com.editaliza.editaliza.models.TagData;

public class FiltrarTagsPorCorTeste {

    private Artist filtro;

    @BeforeEach
    void setUp() {
        filtro = new Artist();

        // Adiciona algumas tags para testar
        filtro.addTag(new TagData("red"));
        filtro.addTag(new TagData("blue"));
        filtro.addTag(new TagData("red"));   // outra tag vermelha
    }

    @Test
    void testeGetTagsByColor() {

        // Resultado esperado: apenas as tags "red"
        List<TagData> esperado = List.of(
            new TagData("red"),
            new TagData("red")
        );

        List<TagData> resultado = filtro.getTagsByColor("red");

        assertEquals(esperado.size(), resultado.size());
    }
}