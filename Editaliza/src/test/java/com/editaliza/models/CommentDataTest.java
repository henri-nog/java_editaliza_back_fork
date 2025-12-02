package com.editaliza.models;

import com.editaliza.editaliza.models.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class CommentDataTest {
    private static final Logger logger = LoggerFactory.getLogger(CommentDataTest.class);

    //Objeto falsas para simular um usuário e edital

    static class FakeUser extends UserData {
        public FakeUser(Long id, String name) {
            this.setId(id);
            this.setName(name);
            this.setEmail("teste@email.com");
            this.setPassword("123");
        }
    }

    static class FakeEdital extends Edital {
        public FakeEdital(Long id, String title) {
            this.setId(id);
            this.setTitle(title);
        }
    }

    //Cria um comentário válido
    private CommentData buildValidComment() {
        UserData user = new FakeUser(1L, "João");
        Edital edital = new FakeEdital(10L, "Edital Teste");

        return new CommentData(
                "João",
                "Comentário válido",
                user,
                edital,
                false,
                CommentStatus.PENDING
        );
    }

    @Test
    @DisplayName("Valida o conteúdo de um texto dentro das regras")
    void testValidateContentValido() {
        logger.debug("Iniciando teste de \"VALIDAÇÃO DO CONTEÚDO VÁLIDO\"...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            Assertions.assertTrue(comentario.validateContent());

            logger.info("Conteúdo validado corretamente!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar conteúdo!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Invalida o conteúdo de um texto fora das regras")
    void testValidateContentVazio() {
        logger.debug("Iniciando teste de \"VALIDAÇÃO DE CONTEÚDO VAZIO\"...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            comentario.setContent("  ");

            Assertions.assertFalse(comentario.validateContent());
            logger.info("Conteúdo vazio rejeitado corretamente!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar conteúdo vazio!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa um nome inválido")
    void testValidateAuthorNameInvalido() {
        logger.debug("Iniciando teste de \"VALIDAÇÃO DE NOME INVÁLIDO\"...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            comentario.setAuthorName("");

            Assertions.assertFalse(comentario.validateAuthorName());
            logger.info("Nome inválido corretamente rejeitado!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao validar nome do autor!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa a validação geral de CommentData")
    void testValidateGeralValida() {
        logger.debug("Iniciando teste de \"VALIDAÇÃO GERAL\"...");

            CommentData comentario = buildValidComment();
            Assertions.assertDoesNotThrow(IllegalArgumentException.class, () -> comentario.validate());

            logger.info("Validação geral passou sem exceções!");

        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Impede que um autor seja nulo")
    void testValidateAutorNulo() {
        logger.debug("Iniciando teste de \"VALIDAÇÃO COM AUTOR NULO\"...");

            CommentData comentario = buildValidComment();
            comentario.setAuthor(null);

            Assertions.assertThrows(IllegalArgumentException.class, () -> comentario.validate());

            logger.info("Exceção lançada como esperado! Autor nulo inválido.");
            logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Lança uma exceção contra um edital nulo")
    void testValidateEditalNulo() {
        logger.debug("Iniciando teste de \"INVALIDAÇÃO COM EDITAL NULO\"...");

        CommentData comentario = buildValidComment();
        comentario.setEdital(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> comentario.validate());

        logger.info("Exceção lançada como esperado! Edital nulo inválido.");
        logger.info("Finalizou o programa!");
    }


    @Test
    @DisplayName("Teste para APROVAR um comentário")
    void testApprove() {
        logger.debug("Iniciando teste de \"APROVAR COMENTÁRIO\"...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            comentario.approve();

            Assertions.assertTrue(comentario.isApproved());
            Assertions.assertEquals(CommentStatus.APPROVED, comentario.getStatus());

            logger.info("Comentário aprovado corretamente!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao aprovar comentário!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Teste para REJEITAR um comentário")
    void testReject() {
        logger.debug("Iniciando teste de \"REJEITAR COMENTÁRIO\"...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            comentario.reject();

            Assertions.assertFalse(comentario.isApproved());
            Assertions.assertEquals(CommentStatus.REJECTED, comentario.getStatus());

            logger.info("Comentário rejeitado corretamente!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao rejeitar comentário!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Teste para OCULTAR um comentário")
    void testHide() {
        logger.debug("Iniciando teste de \"OCULTAR COMENTÁRIO\"...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            comentario.hide();

            Assertions.assertFalse(comentario.isApproved());
            Assertions.assertEquals(CommentStatus.HIDDEN, comentario.getStatus());

            logger.info("Comentário ocultado corretamente!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao ocultar comentário!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Teste para MARCAR (suspeito ou impróprio) um comentário")
    void testFlag() {
        logger.debug("Iniciando teste de \"MARCAR COMENTÁRIO COMO FLAGGED\"...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            comentario.flag();

            Assertions.assertEquals(CommentStatus.FLAGGED, comentario.getStatus());
            logger.info("Comentário marcado como FLAGGED corretamente!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao marcar FLAG!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Torna um comentário público")
    void testIsPubliclyVisibleTrue() {
        logger.debug("Iniciando teste de \"VISIBILIDADE PÚBLICA VERDADEIRA\"...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            comentario.approve();

            Assertions.assertTrue(comentario.isPubliclyVisible());
            logger.info("Comentário corretamente classificado como público!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao testar visibilidade pública TRUE!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Não torna um comentário público")
    void testIsPubliclyVisibleFalse() {
        logger.debug("Iniciando teste de \"VISIBILIDADE PÚBLICA FALSA\"...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            comentario.reject();

            Assertions.assertFalse(comentario.isPubliclyVisible());
            logger.info("Comentário corretamente identificado como não público!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao testar visibilidade pública FALSE!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testa se somente o autor pode editar")
    void testCanBeEditedByAutor() {
        logger.debug("Iniciando teste de \"EDIÇÃO PELO AUTOR\"...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            Assertions.assertTrue(comentario.canBeEditedBy(1L));

            logger.info("Comentário corretamente identificado como editável pelo autor!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao testar edição pelo autor!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Barra a edição por outro autor")
    void testCanBeEditedByOutroUsuario() {
        logger.debug("Iniciando teste de \"EDIÇÃO POR OUTRO USUÁRIO\"...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            Assertions.assertFalse(comentario.canBeEditedBy(99L));

            logger.info("Outro usuário não pode editar. Teste correto!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao testar edição por outro usuário!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Testando se ao mudar o status bloqueia a edição")
    void testCanBeEditedApenasSePendente() {
        logger.debug("Iniciando teste de \"EDIÇÃO APENAS SE PENDENTE\"...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            comentario.approve();

            Assertions.assertFalse(comentario.canBeEditedBy(1L));
            logger.info("Comentário não editável após aprovação. Correto!");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao testar edição condicional!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Teste para saber se o comentário PENDENTE pode ser moderado")
    void testCanBeModerated() {
        logger.debug("Iniciando teste de \"MODERAÇÃO PENDING\"...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            Assertions.assertTrue(comentario.canBeModerated());

            logger.info("Comentário PENDING pode ser moderado. Sucesso!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao testar moderação!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Teste para saber se o comentário FLAGGED pode ser moderado")
    void testCanBeModeratedFlagged() {
        logger.debug("Iniciando teste de \"MODERAÇÃO FLAGGED\"...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            comentario.flag();

            Assertions.assertTrue(comentario.canBeModerated());
            logger.info("FLAGGED moderável. Sucesso!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao testar moderação FLAGGED!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Barra a moderação para comentários aprovados")
    void testCanBeModeratedFalse() {
        logger.debug("Iniciando teste de \"MODERAÇÃO NEGADA\"...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            comentario.approve();

            Assertions.assertFalse(comentario.canBeModerated());
            logger.info("APROVADO não é moderável. Comportamento correto!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao testar moderação NEGADA!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Retorna o textos dentro do limite de tamanho")
    void testGetSummaryMenorQueMax() {
        logger.debug("Iniciando teste de \"MENOR\" que max...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            comentario.setContent("Pequeno");

            Assertions.assertEquals("Pequeno", comentario.getSummary(20));
            logger.info("Resumo retornado corretamente!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao gerar resumo!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

    @Test
    @DisplayName("Formata textos que ultrapassam o limite de tamanho")
    void testGetSummaryMaiorQueMax() {
        logger.debug("Iniciando teste de \"MAIOR\" que max...");
        try {
            Thread.sleep(5000);

            CommentData comentario = buildValidComment();
            comentario.setContent("Texto muito grande para resumo");

            String summary = comentario.getSummary(10);

            Assertions.assertTrue(summary.endsWith("..."));
            Assertions.assertTrue(summary.length() <= 13);

            logger.info("Resumo truncado corretamente!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao gerar resumo truncado!", e);
        } catch (InterruptedException e) {
            logger.error("Error de thread", e);
        }
        logger.info("Finalizou o programa!");
    }

}