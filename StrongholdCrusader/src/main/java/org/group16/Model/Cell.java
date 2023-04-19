package org.group16.Model;

import org.group16.Model.Buildings.Building;
import org.group16.Model.People.Soldier;

import java.util.ArrayList;

public class Cell implements Comparable<Cell> {
    private final int x, y;
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private CellType cellType;
    private TreeType treeType;

    public Cell(int x, int y, CellType cellType, TreeType treeType) {
        this.x = x;
        this.y = y;
        this.cellType = cellType;
        this.treeType = treeType;
    }

    public Cell(int x, int y, CellType cellType) {
        this(x, y, cellType, TreeType.NONE);
    }

    public Cell(int x, int y, Cell cell) {
        this(x, y, cell.cellType, cell.treeType);
    }

    public TreeType getTreeType() {
        return treeType;
    }

    public void setTreeType(TreeType treeType) {
        this.treeType = treeType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    @Override
    public int compareTo(Cell other) {
        if (x == other.x) return Integer.compare(y, other.y);
        return Integer.compare(x, other.x);
    }

    public boolean traversable() {
        return true;
    }//TODO

    public double getTraverseCost() {
        return 1;
    }//TODO

    public Building getBuilding() {
        for (var obj : gameObjects) if (obj instanceof Building) return (Building) obj;
        return null;
    }

    @Override
    public String toString() {
        return String.format("[%d,%d]", x, y);
    }

}
