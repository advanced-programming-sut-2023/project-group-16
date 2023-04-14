package org.group16.Model;

public enum CellType {
    NORMAL(1),
    SMALL_STONE(1),
    ROCK(2),
    STONE(100000),
    IRON(1),
    GRASS(1),
    LOW_DENSITY_GRASSLAND(1),
    HIGH_DENSITY_GRASSLAND(1),
    OIL(1),
    WET_PLAIN(100000),
    SHALLOW_WATER(2),
    BEACH(1),
    RIVER(100000),
    POND(100000),
    SEA(100000),
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
