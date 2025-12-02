package com.editaliza.models;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class TagColorsTest {
    private static final Logger logger = LoggerFactory.getLogger(TagColorsTest.class);

    @Test
    @DisplayName("Testa se todas as cores do enum possuem um código hexadecimal associado")
    void testHexCodesNotNull() {
        logger.debug("Iniciando teste de \"VERIFICAR HEX CODES NÃO NULOS\"...");
        try {

            for (TagColors color : TagColors.values()) {
                Assertions.assertNotNull(color.getHexCode());
                logger.info("Hex code verificado para " + color.name());
            }

            logger.info("Todos os hex codes foram validados com sucesso!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar hex codes!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Verifica se o quantidade de cores no enum é a esperada")
    void testTotalColors() {
        logger.debug("Iniciando teste de \"VERIFICAR QUANTIDADE TOTAL DE CORES\"...");
        try {

            Assertions.assertEquals(8, TagColors.values().length);
            logger.info("Quantidade correta de cores detectada: 8");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar quantidade de cores!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Compara os CÓDIGOS HEXADECIMAIS com o que foi passado")
    void testSpecificHexCodes() {
        logger.debug("Iniciando teste de \"VALIDAR HEX CODES ESPECÍFICOS\"...");
        try {

            Assertions.assertEquals("#FF5733", TagColors.RED.getHexCode());
            Assertions.assertEquals("#3366FF", TagColors.BLUE.getHexCode());
            Assertions.assertEquals("#33CC33", TagColors.GREEN.getHexCode());
            Assertions.assertEquals("#FFCC00", TagColors.YELLOW.getHexCode());
            Assertions.assertEquals("#9933CC", TagColors.PURPLE.getHexCode());
            Assertions.assertEquals("#FF8800", TagColors.ORANGE.getHexCode());
            Assertions.assertEquals("#FF3399", TagColors.PINK.getHexCode());
            Assertions.assertEquals("#666666", TagColors.GRAY.getHexCode());

            logger.info("Todos os hex codes específicos foram validados com sucesso!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar hex codes específicos!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Compara os NOMES com o que foi passado")
    void testEnumName() {
        logger.debug("Iniciando teste de \"VALIDAR NOMES DOS ENUMS\"...");
        try {

            Assertions.assertEquals("RED", TagColors.RED.name());
            Assertions.assertEquals("BLUE", TagColors.BLUE.name());

            logger.info("Nomes dos enums validados corretamente!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar nomes dos enums!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    void testValueOf() {
        logger.debug("Iniciando teste de \"VALIDAR MÉTODO valueOf() DO ENUM\"...");
        try {

            Assertions.assertEquals(TagColors.GREEN, TagColors.valueOf("GREEN"));
            logger.info("valueOf() reconheceu corretamente o enum GREEN!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar valueOf()!", e);
        }
        logger.info("Finalizou o programa!");
    }
}