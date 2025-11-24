package com.editaliza.editaliza.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDataTest {

    @Test
    void testConstructorAndGetters() {
        UserDataFake user = new UserDataFake("12345", "Fellipe", "teste@teste.com", "img.jpg");

        Assertions.assertEquals("Fellipe", user.getName());
        Assertions.assertEquals("teste@teste.com", user.getEmail());
        Assertions.assertEquals("img.jpg", user.getImgUrl());
    }

    @Test
    void testSetters() {
        UserDataFake user = new UserDataFake("12345", "A", "a@a.com", null);

        user.setName("Novo Nome");
        user.setEmail("novo@teste.com");
        user.setPassword("novaSenha");

        Assertions.assertEquals("Novo Nome", user.getName());
        Assertions.assertEquals("novo@teste.com", user.getEmail());
        Assertions.assertEquals("novaSenha", user.getPassword());
    }

    @Test
    void testAddComment() {
        UserDataFake user = new UserDataFake("12345", "Nome", "email@test.com", null);
        CommentData comment = new CommentData();

        user.addComment(comment);

        Assertions.assertEquals(1, user.getListComment().size());
        Assertions.assertEquals(user, comment.getAuthor());
    }

    @Test
    void testRemoveComment() {
        UserDataFake user = new UserDataFake("12345", "Nome", "email@test.com", null);
        CommentData comment = new CommentData();

        user.addComment(comment);
        user.removeComment(comment);

        Assertions.assertEquals(0, user.getListComment().size());
        Assertions.assertNull(comment.getAuthor());
    }

    @Test
    void testUpdateTimestamp() {
        UserDataFake user = new UserDataFake("12345", "Nome", "email@test.com", null);
        
        user.updateTimestamp(); 
        Assertions.assertNotNull(user.getUpdatedAt());
    }

    @Test
    void testUserType() {
        UserData user = new UserDataFake("1234", "X", "y@y.com", null);
        Assertions.assertEquals("FAKE", user.getUserType());
    }
}
