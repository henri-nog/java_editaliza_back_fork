package com.editaliza.models;

import com.editaliza.editaliza.models.Artist;
import com.editaliza.editaliza.models.TagData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ArtistTest {

    @Test
    @DisplayName("Testa um CPF válido")
    void testCpfValido() {
        Artist artist = new Artist("123", "João", "email@email.com", null,
                "52998224725"); 

        assertTrue(artist.isValidCpf());
    }

    @Test
    @DisplayName("Testa um CPF inválido")
    void testCpfInvalido() {
        Artist artist = new Artist("123", "João", "email@email.com", null,
                "11111111111"); 

        assertFalse(artist.isValidCpf());
    }

    @Test
    void testValidateCpfLancaExcecao() {
        Artist artist = new Artist("123", "João", "email@email.com", null,
                "12345678900");

        assertThrows(IllegalArgumentException.class, artist::validateCpf);
    }

    // -----------------------------
    // TESTES DE EMAIL
    // -----------------------------

    @Test
    @DisplayName("Testa um email válido")
    void testValidateEmailValido() {
        Artist artist = new Artist("123", "João", "teste@example.com", null, "52998224725");
        assertDoesNotThrow(artist::validateEmail);
    }

    @Test
    @DisplayName("Testa um email inválido")
    void testValidateEmailInvalido() {
        Artist artist = new Artist("123", "João", "email-invalido", null, "52998224725");

        assertThrows(IllegalArgumentException.class, artist::validateEmail);
    }

    // -----------------------------
    // TESTES DE NOME
    // -----------------------------

    @Test
    @DisplayName("Testa um nome válido")
    void testValidateNameValido() {
        Artist artist = new Artist("123", "Maria", "email@email.com", null, "52998224725");

        assertDoesNotThrow(artist::validateName);
    }

    @Test
    @DisplayName("Testa um nome inválido")
    void testValidateNameInvalido() {
        Artist artist = new Artist("123", "", "email@email.com", null, "52998224725");

        assertThrows(IllegalArgumentException.class, artist::validateName);
    }

    // -----------------------------
    // TESTES DE TAGS
    // -----------------------------

    @Test
    void testAddTag() {
        Artist artist = new Artist();
        TagData tag = new TagData();
        tag.setId(1L);
        tag.setName("Pintura");

        artist.addTag(tag);

        assertEquals(1, artist.getListTags().size());
        assertTrue(artist.hasTag(1L));
    }

    @Test
    void testAddTagDuplicadaNaoRepete() {
        Artist artist = new Artist();
        TagData tag = new TagData();
        tag.setId(1L);
        tag.setName("Arte");

        artist.addTag(tag);
        artist.addTag(tag); // não deve adicionar de novo

        assertEquals(1, artist.getListTags().size());
    }

    @Test
    void testRemoveTag() {
        Artist artist = new Artist();

        TagData tag1 = new TagData();
        tag1.setId(10L);
        tag1.setName("Dança");

        artist.addTag(tag1);

        artist.removeTag(10L);

        assertEquals(0, artist.getListTags().size());
        assertFalse(artist.hasTag(10L));
    }

    // -----------------------------
    // TESTE getTagNames()
    // -----------------------------

    @Test
    void testGetTagNames() {
        Artist artist = new Artist();

        TagData tag = new TagData();
        tag.setId(3L);
        tag.setName("Fotografia");

        artist.addTag(tag);

        List<String> nomes = artist.getTagNames();

        assertEquals(1, nomes.size());
        assertEquals("Fotografia", nomes.get(0));
    }

    // -----------------------------
    // TESTE getTagsByColor()
    // -----------------------------

    @Test
    void testGetTagsByColor() {
        Artist artist = new Artist();

        TagData tag1 = new TagData();
        tag1.setId(1L);
        tag1.setColor("red");

        TagData tag2 = new TagData();
        tag2.setId(2L);
        tag2.setColor("blue");

        TagData tag3 = new TagData();
        tag3.setId(3L);
        tag3.setColor("red");

        artist.addTag(tag1);
        artist.addTag(tag2);
        artist.addTag(tag3);

        List<TagData> tagsRed = artist.getTagsByColor("red");

        assertEquals(2, tagsRed.size());
    }
}
