package org.group16.Model;

public enum KingdomType {
    ARAB,
    EUROPEAN;

    public static KingdomType getKingdomTypeByName(String name) {
        KingdomType kingdomType;
        try {
            kingdomType = KingdomType.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException exception) {
            kingdomType = null;
        }
        return kingdomType;
    }
}
