package org.group16.Model.Buildings;

import org.group16.Model.Cell;
import org.group16.Model.GameObject;
import org.group16.Model.Kingdom;

import java.util.ArrayList;

public abstract class Building extends GameObject {

    private final Kingdom kingdom;
    private int hp;
    private boolean traversable;
    private BuildingType buildingType;

    public Kingdom getKingdom() {
        return kingdom;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }


    public Building(ArrayList<Cell> cells, Kingdom kingdom, int hp , BuildingType buildingType) {
        super(cells);
        this.kingdom = kingdom;
        this.hp = hp;
        this.buildingType = buildingType ;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isTraversable() {
        return traversable;
    }

    public void setTraversable(boolean traversable) {
        this.traversable = traversable;
    }

    public void repair() {
        //TODO
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    public void delete() {
        super.destroy();
    }


    @Override
    public void onTurnEnd() {
        if (hp <= 0) {
            destroy();
            hp = 0;
        }
    }
}
