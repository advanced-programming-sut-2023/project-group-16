package org.group16.Model.People;

import org.group16.Model.*;
import org.group16.Model.Buildings.Building;

import java.util.ArrayList;
import java.util.Random;

public abstract class Human extends GameObject implements Alive {
    private int hp;
    private Building building;
    private double relativeX;
    private double relativeY;

    public Human(ArrayList<Cell> cells, Kingdom kingdom, int hp) {
        super(cells);
        this.kingdom = kingdom;
        this.hp = hp;
    }

    public double getRelativeX() {
        return relativeX;
    }

    public void setRelativeX(double relativeX) {
        this.relativeX = relativeX;
    }

    public double getRelativeY() {
        return relativeY;
    }

    public void setRelativeY(double relativeY) {
        this.relativeY = relativeY;
    }

    public void moveToward(Cell destination, double distance, double randomness, Random random) {
        Cell currentCell = getCells().get(0);
        PathFindingQuery pathFindingQuery = new PathFindingQuery(Scene.getCurrent().getMap(), currentCell, destination, randomness, random);
        pathFindingQuery.findShortestPath();
        Cell nextCell = pathFindingQuery.getNextCell(currentCell);
        double dx = nextCell.getX() - currentCell.getX();
        double dy = nextCell.getY() - currentCell.getY();
        if (nextCell.getX() != currentCell.getX() && nextCell.getY() != currentCell.getY()) {
            dx /= PathFindingQuery.DIAGONAL_COST_MULTIPLIER;
            dy /= PathFindingQuery.DIAGONAL_COST_MULTIPLIER;
        }
        dx *= distance;
        dy *= distance;
        relativeX += dx;
        relativeY += dy;
        int cellDx = 0, cellDy = 0;
        if (relativeX >= 1) cellDx = 1;
        else if (relativeX <= -1) cellDx = -1;
        if (relativeY >= 1) cellDy = 1;
        else if (relativeY <= -1) cellDy = -1;
        removeCell(currentCell);
        currentCell = Scene.getCurrent().getCellAt(currentCell.getX() + cellDx, currentCell.getY() + cellDy);
        relativeX -= cellDx;
        relativeY -= cellDy;
        addCell(currentCell);
    }//TODO: move on buildings

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @Override
    public void dealDamage(int damage) {
        setHp(getHp() - damage);
    }
}
