package org.group16.Model;

import jdk.dynalink.beans.StaticClass;
import org.group16.Lib.OrderedPair;
import org.group16.Model.People.Human;

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

    public static double getCellDistance(Cell a, Cell b) {
        int dx = b.getX() - a.getX();
        int dy = b.getY() - a.getY();
        return Math.sqrt(dx * dx + dy * dy);
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

    public ArrayList<Cell> getCellsInRange(Cell origin, double range) {
        ArrayList<Cell> result = new ArrayList<>();
        int dxBound = (int) range;
        for (int dx = -dxBound; dx <= dxBound; dx++) {
            int dyBound = (int) Math.sqrt(range * range - dx * dx);
            for (int dy = -dyBound; dy <= dyBound; dy++) {
                Cell cell = Scene.getCurrent().getCellAt(origin.getX() + dx, origin.getY() + dy);
                result.add(cell);
            }
        }
        return result;
    }

}
