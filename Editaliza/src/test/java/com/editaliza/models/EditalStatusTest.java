package com.editaliza.editaliza.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EditalStatusTest {

    @Test
    void testValuesNotNull() {
        for (EditalStatus status : EditalStatus.values()) {
            Assertions.assertNotNull(status);
        }
    }

    @Test
    void testTotalDeStatus() {
        Assertions.assertEquals(5, EditalStatus.values().length);
    }

    @Test
    void testEnumNames() {
        Assertions.assertEquals("DRAFT", EditalStatus.DRAFT.name());
        Assertions.assertEquals("PUBLISHED", EditalStatus.PUBLISHED.name());
        Assertions.assertEquals("OPEN", EditalStatus.OPEN.name());
        Assertions.assertEquals("CLOSED", EditalStatus.CLOSED.name());
        Assertions.assertEquals("CANCELLED", EditalStatus.CANCELLED.name());
    }

    @Test
    void testValueOf() {
        Assertions.assertEquals(EditalStatus.OPEN, EditalStatus.valueOf("OPEN"));
    }
}
