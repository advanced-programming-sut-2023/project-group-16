package org.group16.Model;

import java.util.ArrayList;

public class Cell {
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
}
