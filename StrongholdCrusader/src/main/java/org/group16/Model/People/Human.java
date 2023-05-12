package org.group16.Model.People;

import org.group16.Model.*;
import org.group16.Model.Buildings.Building;

import java.util.ArrayList;
import java.util.Random;

public class Human extends GameObject implements Alive {
    private int hp;
    private Building building;
    private double relativeX;
    private double relativeY;

    public Human(ArrayList<Cell> cells, Kingdom kingdom, int hp) {
        super(cells, kingdom);
        if (this instanceof Soldier)
            kingdom.addSoldier((Soldier) this);
        else
            kingdom.addHuman(this);
        this.hp = hp;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (this instanceof Soldier)
            getKingdom().removeSoldier((Soldier) this);
        else
            getKingdom().removeHuman(this);
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

    protected void moveToward(Cell destination, boolean canUseLadder, double distance, double randomness, Random random) {
        Cell currentCell = getCell();
        PathFindingQuery pathFindingQuery = new PathFindingQuery(Scene.getCurrent().getMap(), currentCell, destination, getKingdom().getTeam(), canUseLadder, randomness, random);
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
        currentCell = Scene.getCurrent().getCellAt(currentCell.getX() + cellDx, currentCell.getY() + cellDy);
        relativeX -= cellDx;
        relativeY -= cellDy;
        setCell(currentCell);
    }


    @Override
    public void dealDamage(int damage) {
        hp -= damage;
        if (hp <= 0) destroy();
    }

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

    public void onTurnStart() {
        //TODO
    }

    @Override
    public void update(double currentTime) {
        double deltaTime = Time.deltaTime;
        setBuilding(getCell().getBuilding());
        // TODO?
    }

    public void onTurnEnd() {
        //TODO
    }
}
