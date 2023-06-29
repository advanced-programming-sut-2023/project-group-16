package org.group16.Model;

import org.group16.GameGraphics.CellGraphics;
import org.group16.GameGraphics.DetailGraphics;

public enum CellType {
    NORMAL(1, true, "\u001B[43m", CellGraphics.SAND, new DetailGraphics[]{}),
    SMALL_STONE(1, true, "\u001B[40m", CellGraphics.ROCKY_SAND, new DetailGraphics[]{}),
    ROCK(2, false, "\u001B[40m", CellGraphics.ROCK, new DetailGraphics[]{}),
    STONE(Integer.MAX_VALUE, true, "\u001B[40m", CellGraphics.ROCK, new DetailGraphics[]{DetailGraphics.ROCKS}),
    IRON(1, true, "\u001B[41m", CellGraphics.ROCK, new DetailGraphics[]{}),
    GRASS(1, true, "\u001B[42m", CellGraphics.GRASS, new DetailGraphics[]{}),
    LOW_DENSITY_GRASSLAND(1, true, "\u001B[42m", CellGraphics.GRASS, new DetailGraphics[]{}),
    HIGH_DENSITY_GRASSLAND(1, true, "\u001B[42m", CellGraphics.GRASS, new DetailGraphics[]{}),
    OIL(1, true, "\u001B[45m", null, new DetailGraphics[]{}),
    WET_PLAIN(Integer.MAX_VALUE, false, "\u001B[46m", null, new DetailGraphics[]{}),
    SHALLOW_WATER(2, false, "\u001B[46m", null, new DetailGraphics[]{}),
    BEACH(1, true, "\u001B[43m", null, new DetailGraphics[]{}),
    RIVER(Integer.MAX_VALUE, false, "\u001B[44m", null, new DetailGraphics[]{}),
    POND(Integer.MAX_VALUE, false, "\u001B[44m", null, new DetailGraphics[]{}),
    SEA(Integer.MAX_VALUE, false, "\u001B[44m", null, new DetailGraphics[]{}),
    ;// TODO : null , details

    private final double traverseCost;
    private final boolean okToBuildIn;
    private final String colorCode;
    private final CellGraphics graphics;
    private final DetailGraphics[] details;

    private CellType(double traverseCost, boolean okToBuildIn, String colorCode, CellGraphics graphics, DetailGraphics[] details) {
        this.traverseCost = traverseCost;
        this.okToBuildIn = okToBuildIn;
        this.colorCode = colorCode;
        this.graphics = graphics;
        this.details = details;
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

    public CellGraphics getGraphics() {
        return graphics;
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

    public DetailGraphics[] getDetails() {
        return details;
    }
}
