package net.openbyte.enums;

/**
 * Represents minecraft versions.
 */
public enum MinecraftVersion {
    COMBAT_UPDATE("1.9"),
    BOUNTIFUL_UPDATE("1.8.9"),
    THE_UPDATE_THAT_CHANGED_THE_WORLD("1.7.10");

    private String string;

    MinecraftVersion(String minecraftVersionString){
        this.string = minecraftVersionString;
    }

    public String getString() {
        return string;
    }
}
