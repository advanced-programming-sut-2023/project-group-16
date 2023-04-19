package org.group16.Model;

import java.util.ArrayList;
import java.util.UUID;

public abstract class GameObject {
    public final Kingdom kingdom;
    private final UUID uuid = UUID.randomUUID();
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
        this.cells.add(cell);
    }

    public void removeCell(Cell cell) {
        this.cells.remove(cell);
    }

    public void destroy() {
        Scene.getCurrent().removeGameObject(this);
        for (Cell cell : cells)
            cell.removeGameObject(this);
        isAlive = false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public abstract void onTurnStart();

    public abstract void update(double deltaTime);

    public abstract void onTurnEnd();
}
