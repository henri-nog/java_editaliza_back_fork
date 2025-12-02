package com.editaliza.models;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@Slf4j
public class EditalTest {
    private static final Logger logger = LoggerFactory.getLogger(EditalTest.class);

    private Edital edital;
    private Proposer proposer;

    @BeforeEach
    void setup() {
        logger.debug("Iniciando configuração do objeto Edital para os testes...");
        proposer = new Proposer();
        edital = new Edital();
        edital.setProposer(proposer);
        edital.setTitle("Título Teste");
        edital.setDescription("Descrição teste");
        logger.info("Setup concluído!");
    }

    @Test
    @DisplayName("Não deve lançar uma exceção já que os dados foram preenchidos corretamente")
    void testValidateSuccess() {
        logger.debug("Iniciando teste de \"VALIDAR EDITAL COM DADOS CORRETOS\"...");

            edital.setPublishDate(new Date(System.currentTimeMillis() - 1000));
            edital.setEndDate(new Date(System.currentTimeMillis() + 1000));

            Assertions.assertDoesNotThrow(() -> edital.validate());

            logger.info("Validação bem-sucedida!");
            logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Teste para garantir que PROPONENTE do edital é obrigatório")
    void testValidateWithoutProposer() {
        logger.debug("Iniciando teste de \"INVALIDAR EDITAL SEM PROPONENTE\"...");

            edital.setProposer(null);

            Assertions.assertThrows(IllegalArgumentException.class, () -> edital.validate());

            logger.info("Erro esperado lançado corretamente!");
            logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Teste para garantir que TÍTULO do edital é obrigatório")
    void testValidateWithoutTitle() {
        logger.debug("Iniciando teste de \"INVALIDAR EDITAL SEM TÍTULO\"...");

            edital.setTitle("");

            Assertions.assertThrows(IllegalArgumentException.class, () -> edital.validate());

            logger.info("Erro esperado detectado: título vazio!");
            logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Teste para assegurar que a data de encerramento seja posterior a de publicação")
    void testValidateEndDateBeforePublishDate() {
        logger.debug("Iniciando teste de \"INVALIDAR EndDate ANTERIOR PublishDate\"...");

            edital.setPublishDate(new Date());
            edital.setEndDate(new Date(System.currentTimeMillis() - 10000));

            Assertions.assertThrows(IllegalArgumentException.class, () -> edital.validate());

            logger.info("Erro detectado corretamente: endDate anterior à publishDate!");
            logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Teste para verificar se o edital está aberto")
    void testIsOpen() {
        logger.debug("Iniciando teste de \"VERIFICAR SE EDITAL ESTÁ ABERTO\"...");
        try {

            edital.setStatus(EditalStatus.OPEN);
            edital.setPublishDate(new Date(System.currentTimeMillis() - 1000));
            edital.setEndDate(new Date(System.currentTimeMillis() + 1000));

            Assertions.assertTrue(edital.isOpen());

            logger.info("Edital reconhecido corretamente como aberto!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao verificar se edital está aberto!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa se o edital já expirou")
    void testIsExpired() {
        logger.debug("Iniciando teste de \"VERIFICAR EXPIRAÇÃO DO EDITAL\"...");
        try {

            edital.setEndDate(new Date(System.currentTimeMillis() - 1000));

            Assertions.assertTrue(edital.isExpired());

            logger.info("Expiração detectada corretamente!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao verificar expiração do edital!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Teste para saber se o edital foi atualizado para ABERTO")
    void testUpdateStatusToOpen() {
        logger.debug("Iniciando teste de \"ATUALIZAR STATUS PARA OPEN\"...");
        try {

            edital.setStatus(EditalStatus.PUBLISHED);
            edital.setPublishDate(new Date(System.currentTimeMillis() - 1000));

            edital.updateStatus();

            Assertions.assertEquals(EditalStatus.OPEN, edital.getStatus());

            logger.info("Status atualizado corretamente para OPEN!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao atualizar status para OPEN!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Teste para saber se o edital foi atualizado para FECHADO")
    void testUpdateStatusToClosed() {
        logger.debug("Iniciando teste de \"ATUALIZAR STATUS PARA CLOSED\"...");
        try {

            edital.setStatus(EditalStatus.OPEN);
            edital.setEndDate(new Date(System.currentTimeMillis() - 1000));

            edital.updateStatus();

            Assertions.assertEquals(EditalStatus.CLOSED, edital.getStatus());

            logger.info("Status atualizado corretamente para CLOSED!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao atualizar status para CLOSED!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa se uma tag foi ADICIONADA na lista")
    void testAddTag() {
        logger.debug("Iniciando teste de \"ADICIONAR TAG\"...");
        try {

            TagData tag = new TagData();
            tag.setId(1L);

            edital.addTag(tag);

            Assertions.assertEquals(1, edital.getListTags().size());

            logger.info("Tag adicionada com sucesso!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao adicionar tag!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa se uma tag foi REMOVIDA na lista")
    void testRemoveTag() {
        logger.debug("Iniciando teste de \"REMOVER TAG\"...");
        try {

            TagData tag = new TagData();
            tag.setId(1L);

            edital.addTag(tag);
            edital.removeTag(1L);

            Assertions.assertEquals(0, edital.getListTags().size());

            logger.info("Tag removida com sucesso!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao remover tag!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa se o comentário foi ADICIONADO")
    void testAddComment() {
        logger.debug("Iniciando teste de \"ADICIONAR COMENTÁRIO\"...");
        try {

            CommentData comment = new CommentData();
            edital.addComment(comment);

            Assertions.assertEquals(1, edital.getComments().size());
            Assertions.assertEquals(edital, comment.getEdital());

            logger.info("Comentário adicionado e associado corretamente!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao adicionar comentário!", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa se o comentário foi REMOVIDO")
    void testRemoveComment() {
        logger.debug("Iniciando teste de \"REMOVER COMENTÁRIO\"...");
        try {

            CommentData comment = new CommentData();
            edital.addComment(comment);

            edital.removeComment(comment);

            Assertions.assertEquals(0, edital.getComments().size());
            Assertions.assertNull(comment.getEdital());

            logger.info("Comentário removido corretamente!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao remover comentário!", e);
        }
        logger.info("Finalizou o programa!");
    }
}

