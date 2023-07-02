package org.group16.Model;

import org.group16.Model.Buildings.Building;
import org.group16.Model.People.Soldier;
import org.group16.Vec2;

import java.io.Serializable;
import java.util.ArrayList;

public class Cell implements Comparable<Cell>, Serializable {
    private final int x, y;
    private transient final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private CellType cellType;
    private TreeType treeType;
    private boolean hasLadder;

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

    public boolean getHasLadder() {
        return hasLadder;
    }

    public void setHasLadder(boolean hasLadder) {
        this.hasLadder = hasLadder;
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

    public Vec2 getPosition() {
        return new Vec2(x, y);
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

    public boolean isTraversable() {
        Building building = getBuilding();
        if (building == null)
            return getTraverseCost() < 100;
        return building.isTraversable();
    }

    public double getTraverseCost() {
        return getCellType().getTraverseCost();
    }

    public Building getBuilding() {
        for (var obj : gameObjects) if (obj instanceof Building) return (Building) obj;
        return null;
    }

    public boolean hasSoldier() {
        for (var obj : gameObjects) if (obj instanceof Soldier) return true;
        return false;
    }

    @Override
    public String toString() {
        return String.format("[%d,%d]", x, y);
    }

}
