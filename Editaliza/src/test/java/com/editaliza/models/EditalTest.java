package com.editaliza.editaliza.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class EditalTest {

    private Edital edital;
    private Proposer proposer;

    @BeforeEach
    void setup() {
        proposer = new Proposer();
        edital = new Edital();
        edital.setProposer(proposer);
        edital.setTitle("Título Teste");
        edital.setDescription("Descrição teste");
    }

    @Test
    void testValidateSuccess() {
        edital.setPublishDate(new Date(System.currentTimeMillis() - 1000));
        edital.setEndDate(new Date(System.currentTimeMillis() + 1000));

        Assertions.assertDoesNotThrow(() -> edital.validate());
    }

    @Test
    void testValidateWithoutProposer() {
        edital.setProposer(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> edital.validate());
    }

    @Test
    void testValidateWithoutTitle() {
        edital.setTitle("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> edital.validate());
    }

    @Test
    void testValidateEndDateBeforePublishDate() {
        edital.setPublishDate(new Date());
        edital.setEndDate(new Date(System.currentTimeMillis() - 10000));

        Assertions.assertThrows(IllegalArgumentException.class, () -> edital.validate());
    }

    @Test
    void testIsOpen() {
        edital.setStatus(EditalStatus.OPEN);
        edital.setPublishDate(new Date(System.currentTimeMillis() - 1000));
        edital.setEndDate(new Date(System.currentTimeMillis() + 1000));

        Assertions.assertTrue(edital.isOpen());
    }

    @Test
    void testIsExpired() {
        edital.setEndDate(new Date(System.currentTimeMillis() - 1000));
        Assertions.assertTrue(edital.isExpired());
    }

    @Test
    void testUpdateStatusToOpen() {
        edital.setStatus(EditalStatus.PUBLISHED);
        edital.setPublishDate(new Date(System.currentTimeMillis() - 1000));

        edital.updateStatus();

        Assertions.assertEquals(EditalStatus.OPEN, edital.getStatus());
    }

    @Test
    void testUpdateStatusToClosed() {
        edital.setStatus(EditalStatus.OPEN);
        edital.setEndDate(new Date(System.currentTimeMillis() - 1000));

        edital.updateStatus();

        Assertions.assertEquals(EditalStatus.CLOSED, edital.getStatus());
    }

    @Test
    void testAddTag() {
        TagData tag = new TagData();
        tag.setId(1L);

        edital.addTag(tag);

        Assertions.assertEquals(1, edital.getListTags().size());
    }

    @Test
    void testRemoveTag() {
        TagData tag = new TagData();
        tag.setId(1L);

        edital.addTag(tag);
        edital.removeTag(1L);

        Assertions.assertEquals(0, edital.getListTags().size());
    }

    @Test
    void testAddComment() {
        CommentData comment = new CommentData();
        edital.addComment(comment);

        Assertions.assertEquals(1, edital.getComments().size());
        Assertions.assertEquals(edital, comment.getEdital());
    }

    @Test
    void testRemoveComment() {
        CommentData comment = new CommentData();
        edital.addComment(comment);

        edital.removeComment(comment);

        Assertions.assertEquals(0, edital.getComments().size());
        Assertions.assertNull(comment.getEdital());
    }
}
