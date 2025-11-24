package com.editaliza.editaliza.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TagColorsTest {

    @Test
    void testHexCodesNotNull() {
        for (TagColors color : TagColors.values()) {
            Assertions.assertNotNull(color.getHexCode());
        }
    }

    @Test
    void testTotalColors() {
        Assertions.assertEquals(8, TagColors.values().length);
    }

    @Test
    void testSpecificHexCodes() {
        Assertions.assertEquals("#FF5733", TagColors.RED.getHexCode());
        Assertions.assertEquals("#3366FF", TagColors.BLUE.getHexCode());
        Assertions.assertEquals("#33CC33", TagColors.GREEN.getHexCode());
        Assertions.assertEquals("#FFCC00", TagColors.YELLOW.getHexCode());
        Assertions.assertEquals("#9933CC", TagColors.PURPLE.getHexCode());
        Assertions.assertEquals("#FF8800", TagColors.ORANGE.getHexCode());
        Assertions.assertEquals("#FF3399", TagColors.PINK.getHexCode());
        Assertions.assertEquals("#666666", TagColors.GRAY.getHexCode());
    }

    @Test
    void testEnumName() {
        Assertions.assertEquals("RED", TagColors.RED.name());
        Assertions.assertEquals("BLUE", TagColors.BLUE.name());
    }

    @Test
    void testValueOf() {
        Assertions.assertEquals(TagColors.GREEN, TagColors.valueOf("GREEN"));
    }
}
