package com.editaliza.Editaliza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.editaliza.editaliza.models.CommentData;
import com.editaliza.editaliza.models.Edital;
import com.editaliza.editaliza.models.UserData;

public class RemoveCommentTeste {

    private Edital edital;
    private CommentData comment;

    @BeforeEach
    void setUp() {
        edital = new Edital();
        edital.setListComment(new ArrayList<>());

        comment = new CommentData();

        // Subclasse anônima implementando o método abstrato
        UserData user = new UserData() {
            @Override
            public String getUserType() {
                return "test";  // valor fictício
            }
        };
        comment.setAuthor(user);

        edital.getListComment().add(comment);
    }

    @Test
    void testeRemoveComment() {
        // Verifica que o comentário está na lista antes de remover
        assertEquals(1, edital.getListComment().size());

        // Remove o comentário
        edital.removeComment(comment);

        // Verifica que a lista ficou vazia
        assertEquals(0, edital.getListComment().size());

        // Verifica que o link do autor foi removido
        assertEquals(null, comment.getAuthor());
    }
}
