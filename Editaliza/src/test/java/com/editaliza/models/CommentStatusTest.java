package com.editaliza.editaliza.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommentStatusTest {

    @Test
    void testEnumValuesNotNull() {
        for (CommentStatus status : CommentStatus.values()) {
            Assertions.assertNotNull(status);
        }
    }

    @Test
    void testTotalDeStatus() {
        Assertions.assertEquals(5, CommentStatus.values().length);
    }

    @Test
    void testEnumNames() {
        Assertions.assertEquals("PENDING", CommentStatus.PENDING.name());
        Assertions.assertEquals("APPROVED", CommentStatus.APPROVED.name());
        Assertions.assertEquals("REJECTED", CommentStatus.REJECTED.name());
        Assertions.assertEquals("HIDDEN", CommentStatus.HIDDEN.name());
        Assertions.assertEquals("FLAGGED", CommentStatus.FLAGGED.name());
    }

    @Test
    void testValueOf() {
        Assertions.assertEquals(CommentStatus.PENDING, CommentStatus.valueOf("PENDING"));
    }
}
