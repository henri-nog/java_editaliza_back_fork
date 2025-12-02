package com.editaliza.models;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Slf4j
public class EditalStatusTest {
    private static final Logger logger = LoggerFactory.getLogger(ArtistTest.class);

    @Test
    void testValuesNotNull() {
        logger.debug("Iniciando teste: VERIFICANDO SE TODOS OS ENUMS NÃO SÃO NULOS...");
        try {
            Thread.sleep(5000);

            for (EditalStatus status : EditalStatus.values()) {
                Assertions.assertNotNull(status);
            }

            logger.info("Todos os valores do enum EditalStatus são válidos!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro inesperado ao testar valores do enum!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    void testTotalDeStatus() {
        logger.debug("Iniciando teste: CONTANDO TOTAL DE STATUS...");
        try {
            Thread.sleep(5000);

            Assertions.assertEquals(5, EditalStatus.values().length);

            logger.info("Total de enums verificado corretamente (5)!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao contar quantidade de enums!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    void testEnumNames() {
        logger.debug("Iniciando teste: VALIDANDO NOMES DOS ENUMS...");
        try {
            Thread.sleep(5000);

            Assertions.assertEquals("DRAFT", EditalStatus.DRAFT.name());
            Assertions.assertEquals("PUBLISHED", EditalStatus.PUBLISHED.name());
            Assertions.assertEquals("OPEN", EditalStatus.OPEN.name());
            Assertions.assertEquals("CLOSED", EditalStatus.CLOSED.name());
            Assertions.assertEquals("CANCELLED", EditalStatus.CANCELLED.name());

            logger.info("Todos os nomes dos enums correspondem corretamente!");
        } catch (Exception e) {
            logger.error("Erro ao validar nomes dos enums!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    void testValueOf() {
        logger.debug("Iniciando teste: VALIDANDO MÉTODO valueOf()...");
        try {
            Thread.sleep(5000);

            Assertions.assertEquals(EditalStatus.OPEN, EditalStatus.valueOf("OPEN"));

            logger.info("Enum recuperado corretamente via valueOf()!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao testar valueOf() do enum!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }
}
