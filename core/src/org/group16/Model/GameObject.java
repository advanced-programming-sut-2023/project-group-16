package org.group16.Model;

import org.group16.GameGraphics.Renderer;

import java.util.ArrayList;
import java.util.UUID;

public abstract class GameObject {
    private final Kingdom kingdom;
    private final UUID uuid = UUID.randomUUID();
    protected float relativeX;
    protected float relativeY;
    DestroyCallback destroyCallback;
    private ArrayList<Cell> cells;
    private boolean isAlive = true;

    public GameObject(ArrayList<Cell> cells, Kingdom kingdom) {
        this.cells = new ArrayList<>(cells);
        Scene.getCurrent().addGameObject(this);
        for (Cell cell : cells)
            cell.addGameObject(this);
        this.kingdom = kingdom;
    }

    public static boolean nullOrDead(GameObject gameObject) {
        return gameObject == null || !gameObject.isAlive;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void addCell(Cell cell) {
        cell.addGameObject(this);
        this.cells.add(cell);
    }

    public void removeCell(Cell cell) {
        cell.removeGameObject(this);
        this.cells.remove(cell);
    }

    public Cell getCell() {
        return cells.get(0);
    }

    public void setCell(Cell cell) {
        removeCell(cells.get(0));
        addCell(cell);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void destroy() {
        Scene.getCurrent().removeGameObject(this);
        for (Cell cell : cells)
            cell.removeGameObject(this);
        isAlive = false;
        destroyCallback.onDestroy(this);
    }

    public UUID getUuid() {
        return uuid;
    }

    public float getRelativeX() {
        return relativeX;
    }

    public void setRelativeX(float relativeX) {
        this.relativeX = relativeX;
    }

    public float getRelativeY() {
        return relativeY;
    }

    public void setRelativeY(float relativeY) {
        this.relativeY = relativeY;
    }

    public abstract void onTurnStart();

    public abstract void update(double currentTime);

    public abstract void onTurnEnd();

    public abstract Renderer createRenderer();

    public abstract void updateRenderer(Renderer renderer);

    public DestroyCallback getDestroyCallback() {
        return destroyCallback;
    }

    public void setDestroyCallback(DestroyCallback destroyCallback) {
        this.destroyCallback = destroyCallback;
    }

    public interface DestroyCallback {
        public void onDestroy(GameObject go);
    }
}
