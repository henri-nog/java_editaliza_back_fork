package com.editaliza.editaliza.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TagDataTest {

    @Test
    void testValidateNameOk() {
        TagData tag = new TagData("Fotografia", "descrição", "#FF5733");
        Assertions.assertTrue(tag.validateName());
    }

    @Test
    void testValidateNameFail() {
        TagData tag = new TagData("", "descrição", "#FF5733");
        Assertions.assertFalse(tag.validateName());
    }

    @Test
    void testValidHexColor() {
        TagData tag = new TagData("Arte", "descrição", "#A1B2C3");
        Assertions.assertTrue(tag.isValidColor());
    }

    @Test
    void testInvalidHexColor() {
        TagData tag = new TagData("Arte", "descrição", "ABC123");
        Assertions.assertFalse(tag.isValidColor());
    }

    @Test
    void testGetRGBColor() {
        TagData tag = new TagData("Cor", null, "#FF8800");
        int[] rgb = tag.getRGBColor();

        Assertions.assertArrayEquals(new int[]{255, 136, 0}, rgb);
    }

    @Test
    void testGetRGBColorInvalid() {
        TagData tag = new TagData("Cor", null, "INVALIDO");
        int[] rgb = tag.getRGBColor();

        Assertions.assertArrayEquals(new int[]{0, 0, 0}, rgb);
    }

    @Test
    void testNormalizedName() {
        TagData tag = new TagData("  FOTOGRAFIA  ", "", "#FF5733");
        Assertions.assertEquals("fotografia", tag.getNormalizedName());
    }

    @Test
    void testAddEdital() {
        TagData tag = new TagData("Foto", "", "#FF5733");
        Edital edital = new Edital();

        tag.addEdital(edital);

        Assertions.assertEquals(1, tag.getEditais().size());
        Assertions.assertTrue(edital.getListTags().contains(tag));
    }

    @Test
    void testRemoveEdital() {
        TagData tag = new TagData("Foto", "", "#FF5733");
        Edital edital = new Edital();

        tag.addEdital(edital);
        tag.removeEdital(edital);

        Assertions.assertFalse(tag.getEditais().contains(edital));
        Assertions.assertFalse(edital.getListTags().contains(tag));
    }

    @Test
    void testAddArtist() {
        TagData tag = new TagData("Cor", "", "#FF5733");
        Artist artist = new Artist();

        tag.addArtist(artist);

        Assertions.assertEquals(1, tag.getArtists().size());
        Assertions.assertTrue(artist.getListTags().contains(tag));
    }

    @Test
    void testRemoveArtist() {
        TagData tag = new TagData("Cor", "", "#FF5733");
        Artist artist = new Artist();

        tag.addArtist(artist);
        tag.removeArtist(artist);

        Assertions.assertFalse(tag.getArtists().contains(artist));
        Assertions.assertFalse(artist.getListTags().contains(tag));
    }
}
