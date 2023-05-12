package org.group16.Model;

public enum CellType {
    NORMAL(1, true),
    SMALL_STONE(1, true),
    ROCK(2, false),
    STONE(Integer.MAX_VALUE, false),
    IRON(1, true),
    GRASS(1, true),
    LOW_DENSITY_GRASSLAND(1, true),
    HIGH_DENSITY_GRASSLAND(1, true),
    OIL(1, true),
    WET_PLAIN(Integer.MAX_VALUE, false),
    SHALLOW_WATER(2, false),
    BEACH(1, true),
    RIVER(Integer.MAX_VALUE, false),
    POND(Integer.MAX_VALUE, false),
    SEA(Integer.MAX_VALUE, false),
    ;
    //TODO: Fill this enum

    private final double traverseCost;
    private final boolean okToBuildIn;

    private CellType(double traverseCost, boolean okToBuildIn) {
        this.traverseCost = traverseCost;
        this.okToBuildIn = okToBuildIn;
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
}
