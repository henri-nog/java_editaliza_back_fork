package com.editaliza.editaliza.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ArtistTest {

    @Test
    @DisplayName("Testa um CPF válido")
    void testCpfValido() {
        Artist artist = new Artist("123", "João", "email@email.com", null,
                "52998224725"); // CPF válido

        Assertions.assertTrue(artist.isValidCpf());
    }

    @Test
    @DisplayName("Testa um CPF inválido")
    void testCpfInvalido() {
        Artist artist = new Artist("123", "João", "email@email.com", null,
                "11111111111"); 

        Assertions.assertFalse(artist.isValidCpf());
    }

    @Test
    void testValidateCpfLancaExcecao() {
        Artist artist = new Artist("123", "João", "email@email.com", null,
                "12345678900");

        Assertions.assertThrows(IllegalArgumentException.class, artist::validateCpf);
    }

    // -----------------------------
    // TESTES DE EMAIL
    // -----------------------------

    @Test
    @DisplayName("Testa um email válido")
    void testValidateEmailValido() {
        Artist artist = new Artist("123", "João", "teste@example.com", null, "52998224725");
        Assertions.assertDoesNotThrow(artist::validateEmail);
    }

    @Test
    @DisplayName("Testa um email inválido")
    void testValidateEmailInvalido() {
        Artist artist = new Artist("123", "João", "email-invalido", null, "52998224725");

        Assertions.assertThrows(IllegalArgumentException.class, artist::validateEmail);
    }

    // -----------------------------
    // TESTES DE NOME
    // -----------------------------

    @Test
    @DisplayName("Testa um nome válido")
    void testValidateNameValido() {
        Artist artist = new Artist("123", "Maria", "email@email.com", null, "52998224725");

        Assertions.assertDoesNotThrow(artist::validateName);
    }

    @Test
    @DisplayName("Testa um nome inválido")
    void testValidateNameInvalido() {
        Artist artist = new Artist("123", "", "email@email.com", null, "52998224725");

        Assertions.assertThrows(IllegalArgumentException.class, artist::validateName);
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

        Assertions.assertEquals(1, artist.getListTags().size());
        Assertions.assertTrue(artist.hasTag(1L));
    }

    @Test
    void testAddTagDuplicadaNaoRepete() {
        Artist artist = new Artist();
        TagData tag = new TagData();
        tag.setId(1L);
        tag.setName("Arte");

        artist.addTag(tag);
        artist.addTag(tag); // não deve adicionar de novo

        Assertions.assertEquals(1, artist.getListTags().size());
    }

    @Test
    void testRemoveTag() {
        Artist artist = new Artist();

        TagData tag1 = new TagData();
        tag1.setId(10L);
        tag1.setName("Dança");

        artist.addTag(tag1);

        artist.removeTag(10L);

        Assertions.assertEquals(0, artist.getListTags().size());
        Assertions.assertFalse(artist.hasTag(10L));
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

        Assertions.assertEquals(1, nomes.size());
        Assertions.assertEquals("Fotografia", nomes.get(0));
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

        Assertions.assertEquals(2, tagsRed.size());
    }
}
