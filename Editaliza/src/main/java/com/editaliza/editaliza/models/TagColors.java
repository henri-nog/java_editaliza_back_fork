package com.editaliza.editaliza.models;

public enum TagColors {
    RED("#FF5733"),
    BLUE("#3366FF"),
    GREEN("#33CC33"),
    YELLOW("#FFCC00"),
    PURPLE("#9933CC"),
    ORANGE("#FF8800"),
    PINK("#FF3399"),
    GRAY("#666666");

    private final String hexCode;

    TagColors(String hexCode) {
        this.hexCode = hexCode;
    }

    public String getHexCode() {
        return hexCode;
    }
}
