package com.editaliza.models;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class TagDataTest {
    private static final Logger logger = LoggerFactory.getLogger(TagColorsTest.class);

    @Test
    @DisplayName("Testa um nome VÁLIDO")
    void testValidateNameOk() {
        logger.debug("Iniciando teste de \"VALIDAR NOME DA TAG (válido)\"...");
        try {

            TagData tag = new TagData("Fotografia", "descrição", "#FF5733");
            Assertions.assertTrue(tag.validateName());
            logger.info("Nome validado com sucesso!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar nome da tag!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa um nome INVÁLIDO")
    void testValidateNameFail() {
        logger.debug("Iniciando teste de \"VALIDAR NOME DA TAG (inválido)\"...");
        try {

            TagData tag = new TagData("", "descrição", "#FF5733");
            Assertions.assertFalse(tag.validateName());
            logger.info("Nome inválido detectado corretamente!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar nome inválido!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("VALIDA o formato da cor hexadeximal")
    void testValidHexColor() {
        logger.debug("Iniciando teste de \"VALIDAR COR HEXADECIMAL\"...");
        try {

            TagData tag = new TagData("Arte", "descrição", "#A1B2C3");
            Assertions.assertTrue(tag.isValidColor());
            logger.info("Cor hexadecimal válida reconhecida!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar cor hexadecimal válida!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("INVALIDA o formato da cor hexadeximal")
    void testInvalidHexColor() {
        logger.debug("Iniciando teste de \"INVALIDAR COR HEXADECIMAL"|...");
        try {

            TagData tag = new TagData("Arte", "descrição", "ABC123");
            Assertions.assertFalse(tag.isValidColor());
            logger.info("Cor hexadecimal inválida identificada!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar cor inválida!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Teste para converter cor hex em RGB VÁLIDO")
    void testGetRGBColor() {
        logger.debug("Iniciando teste de \"CONVERTER HEX PARA RGB VÁLIDO\"...");
        try {

            TagData tag = new TagData("Cor", null, "#FF8800");
            int[] rgb = tag.getRGBColor();

            Assertions.assertArrayEquals(new int[]{255, 136, 0}, rgb);
            logger.info("Conversão RGB realizada com sucesso!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao converter HEX para RGB!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Teste para converter cor hex em RGB INVÁLIDO")
    void testGetRGBColorInvalid() {
        logger.debug("Iniciando teste de \"CONVERTER HEX PARA RGB INVÁLIDO\"...");
        try {

            TagData tag = new TagData("Cor", null, "INVALIDO");
            int[] rgb = tag.getRGBColor();

            Assertions.assertArrayEquals(new int[]{0, 0, 0}, rgb);
            logger.info("HEX inválido corretamente convertido para RGB preto!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar RGB para HEX inválido!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Normaliza o nome")
    void testNormalizedName() {
        logger.debug("Iniciando teste de \"NORMALIZAR NOME\"...");
        try {

            TagData tag = new TagData("  FOTOGRAFIA  ", "", "#FF5733");
            Assertions.assertEquals("fotografia", tag.getNormalizedName());
            logger.info("Nome normalizado corretamente!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao normalizar nome!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Confere se o edital foi adicionado na tag")
    void testAddEdital() {
        logger.debug("Iniciando teste de \"ADICIONAR EDITAL NA TAG\"...");
        try {

            TagData tag = new TagData("Foto", "", "#FF5733");
            Edital edital = new Edital();

            tag.addEdital(edital);

            Assertions.assertEquals(1, tag.getEditais().size());
            Assertions.assertTrue(edital.getListTags().contains(tag));

            logger.info("Edital adicionado à Tag com sucesso!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao adicionar edital!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Confere se o edital foi REMOVIDO da tag")
    void testRemoveEdital() {
        logger.debug("Iniciando teste de \"REMOVER EDITAL DA TAG\"...");
        try {

            TagData tag = new TagData("Foto", "", "#FF5733");
            Edital edital = new Edital();

            tag.addEdital(edital);
            tag.removeEdital(edital);

            Assertions.assertFalse(tag.getEditais().contains(edital));
            Assertions.assertFalse(edital.getListTags().contains(tag));

            logger.info("Edital removido com sucesso!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao remover edital!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @Display
    void testAddArtist() {
        logger.debug("Iniciando teste: ADICIONAR ARTISTA NA TAG...");
        try {

            TagData tag = new TagData("Cor", "", "#FF5733");
            Artist artist = new Artist();

            tag.addArtist(artist);

            Assertions.assertEquals(1, tag.getArtists().size());
            Assertions.assertTrue(artist.getListTags().contains(tag));

            logger.info("Artista adicionado com sucesso!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao adicionar artista!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    void testRemoveArtist() {
        logger.debug("Iniciando teste: REMOVER ARTISTA DA TAG...");
        try {

            TagData tag = new TagData("Cor", "", "#FF5733");
            Artist artist = new Artist();

            tag.addArtist(artist);
            tag.removeArtist(artist);

            Assertions.assertFalse(tag.getArtists().contains(artist));
            Assertions.assertFalse(artist.getListTags().contains(tag));

            logger.info("Artista removido com sucesso!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao remover artista!", e);
        }
        logger.info("Finalizou o programa!");
    }
}