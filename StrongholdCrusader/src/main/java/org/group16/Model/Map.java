package org.group16.Model;

import jdk.dynalink.beans.StaticClass;
import org.group16.Lib.OrderedPair;

import java.util.*;

public class Map {

    private final int width, height;
    private Cell[][] cells;

    public Map(int mapWidth, int mapHeight) {
        this.width = mapWidth;
        this.height = mapHeight;
        cells = new Cell[mapWidth][mapHeight];
        for (int i = 0; i < mapWidth; i++)
            for (int j = 0; j < mapHeight; j++)
                cells[i][j] = new Cell(i, j, CellType.NORMAL);
    }

    public Map(Map map) {
        this(map.width, map.height);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                cells[i][j] = new Cell(i, j, map.cells[i][j]);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getCellAt(int i, int j) {
        if (i < 0 || i >= width) return null;
        if (j < 0 || j >= height) return null;
        return cells[i][j];
    }

}
