package com.editaliza.models;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class UserDataTest {
    private static final Logger logger = LoggerFactory.getLogger(ArtistTest.class);

    @Test
    void testConstructorAndGetters() {
        logger.debug("Iniciando teste: CONSTRUTOR E GETTERS...");
        try {
            Thread.sleep(5000);

            UserDataFake user = new UserDataFake("12345", "Fellipe", "teste@teste.com", "img.jpg");

            Assertions.assertEquals("Fellipe", user.getName());
            Assertions.assertEquals("teste@teste.com", user.getEmail());
            Assertions.assertEquals("img.jpg", user.getImgUrl());

            logger.info("Construtor e getters validados com sucesso!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao testar construtor e getters!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    void testSetters() {
        logger.debug("Iniciando teste: SETTERS...");
        try {
            Thread.sleep(5000);

            UserDataFake user = new UserDataFake("12345", "A", "a@a.com", null);

            user.setName("Novo Nome");
            user.setEmail("novo@teste.com");
            user.setPassword("novaSenha");

            Assertions.assertEquals("Novo Nome", user.getName());
            Assertions.assertEquals("novo@teste.com", user.getEmail());
            Assertions.assertEquals("novaSenha", user.getPassword());

            logger.info("Setters validados com sucesso!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao testar setters!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    void testAddComment() {
        logger.debug("Iniciando teste: ADICIONAR COMENTÁRIO AO USUÁRIO...");
        try {
            Thread.sleep(5000);

            UserDataFake user = new UserDataFake("12345", "Nome", "email@test.com", null);
            CommentData comment = new CommentData();

            user.addComment(comment);

            Assertions.assertEquals(1, user.getListComment().size());
            Assertions.assertEquals(user, comment.getAuthor());

            logger.info("Comentário adicionado com sucesso!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao adicionar comentário!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    void testRemoveComment() {
        logger.debug("Iniciando teste: REMOVER COMENTÁRIO DO USUÁRIO...");
        try {
            Thread.sleep(5000);

            UserDataFake user = new UserDataFake("12345", "Nome", "email@test.com", null);
            CommentData comment = new CommentData();

            user.addComment(comment);
            user.removeComment(comment);

            Assertions.assertEquals(0, user.getListComment().size());
            Assertions.assertNull(comment.getAuthor());

            logger.info("Comentário removido com sucesso!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao remover comentário!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    void testUpdateTimestamp() {
        logger.debug("Iniciando teste: UPDATE TIMESTAMP...");
        try {
            Thread.sleep(5000);

            UserDataFake user = new UserDataFake("12345", "Nome", "email@test.com", null);

            user.updateTimestamp();

            Assertions.assertNotNull(user.getUpdatedAt());

            logger.info("Timestamp atualizado com sucesso!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao atualizar timestamp!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    void testUserType() {
        logger.debug("Iniciando teste: TIPO DE USUÁRIO (DISCRIMINATOR)...");
        try {
            Thread.sleep(5000);

            UserData user = new UserDataFake("1234", "X", "y@y.com", null);

            Assertions.assertEquals("FAKE", user.getUserType());
            logger.info("Tipo de usuário retornado corretamente!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar tipo de usuário!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }
}