package com.editaliza.models;

import com.editaliza.editaliza.models.CommentStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class CommentStatusTest {
    private static final Logger logger = LoggerFactory.getLogger(CommentStatus.class);

    @Test
    void testEnumValuesNotNull() {
        logger.debug("Iniciando teste de \"VERIFICAÇÃO DE STATUS NÃO NULOS\"...");
        try {

            for (CommentStatus status : CommentStatus.values()) {
                Assertions.assertNotNull(status);
            }

            logger.info("Todos os valores do ENUM são não nulos. Sucesso!");
        } catch (AssertionError e) {
            logger.error("Erro ao verificar valores do enum!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    void testTotalDeStatus() {
        logger.debug("Iniciando teste de \"CONTAGEM DOS STATUS\"...");
        try {

            Assertions.assertEquals(5, CommentStatus.values().length);

            logger.info("Total de status confere: 5 encontrados!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro durante contagem de valores do enum!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    void testEnumNames() {
        logger.debug("Iniciando teste de \"NOMES DOS STATUS\"...");
        try {

            Assertions.assertEquals("PENDING", CommentStatus.PENDING.name());
            Assertions.assertEquals("APPROVED", CommentStatus.APPROVED.name());
            Assertions.assertEquals("REJECTED", CommentStatus.REJECTED.name());
            Assertions.assertEquals("HIDDEN", CommentStatus.HIDDEN.name());
            Assertions.assertEquals("FLAGGED", CommentStatus.FLAGGED.name());

            logger.info("Todos os nomes dos status foram validados com sucesso!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar nomes do enum!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    void testValueOf() {
        logger.debug("Iniciando teste de \"VALUE OF\" do enum...");
        try {

            Assertions.assertEquals(CommentStatus.PENDING, CommentStatus.valueOf("PENDING"));

            logger.info("Método valueOf funcionando corretamente!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao testar valueOf!", e);
        }
        logger.info("Finalizou o programa!");
    }
}