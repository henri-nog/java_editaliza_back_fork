package com.editaliza.Editaliza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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

        TagData t1 = new TagData();
        t1.setColor("red");
        TagData t2 = new TagData();
        t2.setColor("blue");
        TagData t3 = new TagData();
        t3.setColor("red");

        filtro.addTag(t1);
        filtro.addTag(t2);
        filtro.addTag(t3);
    }

    @Test
    void testeGetTagsByColor() {
        // Resultado esperado: apenas as tags "red"
        List<TagData> esperado = new ArrayList<>();
        TagData e1 = new TagData();
        e1.setColor("red");
        TagData e2 = new TagData();
        e2.setColor("red");
        esperado.add(e1);
        esperado.add(e2);

        List<TagData> resultado = filtro.getTagsByColor("red");

        assertEquals(esperado.size(), resultado.size());
    }
}
