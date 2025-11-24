package com.editaliza.editaliza.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommentDataTest {

    // Criando stubs simples para UserData e Edital
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

    // ----------------------------------------
    // TESTES DE VALIDAÇÃO
    // ----------------------------------------

    @Test
    void testValidateContentValido() {
        CommentData c = buildValidComment();
        Assertions.assertTrue(c.validateContent());
    }

    @Test
    void testValidateContentVazio() {
        CommentData c = buildValidComment();
        c.setContent("  ");

        Assertions.assertFalse(c.validateContent());
    }

    @Test
    void testValidateAuthorNameInvalido() {
        CommentData c = buildValidComment();
        c.setAuthorName("");

        Assertions.assertFalse(c.validateAuthorName());
    }

    @Test
    void testValidateGeralValida() {
        CommentData c = buildValidComment();
        Assertions.assertDoesNotThrow(c::validate);
    }

    @Test
    void testValidateAutorNulo() {
        CommentData c = buildValidComment();
        c.setAuthor(null);

        Assertions.assertThrows(IllegalArgumentException.class, c::validate);
    }

    @Test
    void testValidateEditalNulo() {
        CommentData c = buildValidComment();
        c.setEdital(null);

        Assertions.assertThrows(IllegalArgumentException.class, c::validate);
    }

    // ----------------------------------------
    // TESTES DE AÇÕES DE STATUS
    // ----------------------------------------

    @Test
    void testApprove() {
        CommentData c = buildValidComment();
        c.approve();

        Assertions.assertTrue(c.isApproved());
        Assertions.assertEquals(CommentStatus.APPROVED, c.getStatus());
    }

    @Test
    void testReject() {
        CommentData c = buildValidComment();
        c.reject();

        Assertions.assertFalse(c.isApproved());
        Assertions.assertEquals(CommentStatus.REJECTED, c.getStatus());
    }

    @Test
    void testHide() {
        CommentData c = buildValidComment();
        c.hide();

        Assertions.assertFalse(c.isApproved());
        Assertions.assertEquals(CommentStatus.HIDDEN, c.getStatus());
    }

    @Test
    void testFlag() {
        CommentData c = buildValidComment();
        c.flag();

        Assertions.assertEquals(CommentStatus.FLAGGED, c.getStatus());
    }

    // ----------------------------------------
    // REGRAS DE VISIBILIDADE
    // ----------------------------------------

    @Test
    void testIsPubliclyVisibleTrue() {
        CommentData c = buildValidComment();
        c.approve();

        Assertions.assertTrue(c.isPubliclyVisible());
    }

    @Test
    void testIsPubliclyVisibleFalse() {
        CommentData c = buildValidComment();
        c.reject();

        Assertions.assertFalse(c.isPubliclyVisible());
    }

    // ----------------------------------------
    // REGRAS DE PERMISSÃO
    // ----------------------------------------

    @Test
    void testCanBeEditedByAutor() {
        CommentData c = buildValidComment();
        Assertions.assertTrue(c.canBeEditedBy(1L));
    }

    @Test
    void testCanBeEditedByOutroUsuario() {
        CommentData c = buildValidComment();
        Assertions.assertFalse(c.canBeEditedBy(99L));
    }

    @Test
    void testCanBeEditedApenasSePendente() {
        CommentData c = buildValidComment();
        c.approve(); // muda status para APPROVED

        Assertions.assertFalse(c.canBeEditedBy(1L));
    }

    @Test
    void testCanBeModerated() {
        CommentData c = buildValidComment();
        Assertions.assertTrue(c.canBeModerated()); // PENDING por padrão
    }

    @Test
    void testCanBeModeratedFlagged() {
        CommentData c = buildValidComment();
        c.flag();

        Assertions.assertTrue(c.canBeModerated());
    }

    @Test
    void testCanBeModeratedFalse() {
        CommentData c = buildValidComment();
        c.approve();

        Assertions.assertFalse(c.canBeModerated());
    }

    // ----------------------------------------
    // RESUMO DO COMENTÁRIO
    // ----------------------------------------

    @Test
    void testGetSummaryMenorQueMax() {
        CommentData c = buildValidComment();
        c.setContent("Pequeno");

        Assertions.assertEquals("Pequeno", c.getSummary(20));
    }

    @Test
    void testGetSummaryMaiorQueMax() {
        CommentData c = buildValidComment();
        c.setContent("Texto muito grande para resumo");

        String summary = c.getSummary(10);
        Assertions.assertTrue(summary.endsWith("..."));
        Assertions.assertTrue(summary.length() <= 13);
    }
}
