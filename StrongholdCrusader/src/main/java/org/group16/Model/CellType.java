package org.group16.Model;

public enum CellType {
    NORMAL(1),
    SMALL_STONE(1),
    ROCK(2),
    STONE(Integer.MAX_VALUE),
    IRON(1),
    GRASS(1),
    LOW_DENSITY_GRASSLAND(1),
    HIGH_DENSITY_GRASSLAND(1),
    OIL(1),
    WET_PLAIN(Integer.MAX_VALUE),
    SHALLOW_WATER(2),
    BEACH(1),
    RIVER(Integer.MAX_VALUE),
    POND(Integer.MAX_VALUE),
    SEA(Integer.MAX_VALUE),
    ;
    //TODO: Fill this enum

    private final double traverseCost;

    private CellType(double traverseCost) {
        this.traverseCost = traverseCost;
    }

    public double getTraverseCost() {
        return traverseCost;
    }
}
