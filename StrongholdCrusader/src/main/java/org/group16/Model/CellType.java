package org.group16.Model;

public enum CellType {
    NORMAL(1, true, "\u001B[43m"),
    SMALL_STONE(1, true, "\u001B[40m"),
    ROCK(2, false, "\u001B[40m"),
    STONE(Integer.MAX_VALUE, false, "\u001B[40m"),
    IRON(1, true, "\u001B[41m"),
    GRASS(1, true, "\u001B[42m"),
    LOW_DENSITY_GRASSLAND(1, true, "\u001B[42m"),
    HIGH_DENSITY_GRASSLAND(1, true, "\u001B[42m"),
    OIL(1, true, "\u001B[45m"),
    WET_PLAIN(Integer.MAX_VALUE, false, "\u001B[46m"),
    SHALLOW_WATER(2, false, "\u001B[46m"),
    BEACH(1, true, "\u001B[43m"),
    RIVER(Integer.MAX_VALUE, false, "\u001B[44m"),
    POND(Integer.MAX_VALUE, false, "\u001B[44m"),
    SEA(Integer.MAX_VALUE, false, "\u001B[44m"),
    ;

    private final double traverseCost;
    private final boolean okToBuildIn;
    private final String colorCode;

    private CellType(double traverseCost, boolean okToBuildIn, String colorCode) {
        this.traverseCost = traverseCost;
        this.okToBuildIn = okToBuildIn;
        this.colorCode = colorCode;
    }

    public static CellType getCellTypeByName(String name) {
        name = name.toUpperCase();
        CellType cellType;
        try {
            cellType = CellType.valueOf(name);
        } catch (IllegalArgumentException exception) {
            cellType = null;
        }
        return cellType;
    }

    public double getTraverseCost() {
        return traverseCost;
    }

    public boolean isOkToBuildIn() {
        return okToBuildIn;
    }

    public String getColorCode() {
        return colorCode;
    }
}
