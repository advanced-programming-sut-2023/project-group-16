package org.group16.Model.People;

import org.group16.Model.Buildings.Building;
import org.group16.Model.GameObject;
import org.group16.Model.Kingdom;

public abstract class Human extends GameObject {
    private final Kingdom kingdom;
    private int hp;
    private Building building;

    public Human(Kingdom kingdom, int hp) {
        this.kingdom = kingdom;
        this.hp = hp;
    }

    public Kingdom getKingdom() {
        return kingdom;
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
}
