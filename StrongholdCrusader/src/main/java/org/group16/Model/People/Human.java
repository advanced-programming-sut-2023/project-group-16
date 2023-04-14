package org.group16.Model.People;

import org.group16.Model.Buildings.Building;
import org.group16.Model.GameObject;

public abstract class Human extends GameObject {
    private int hp;
    private Building building;

    public Human(int hp) {
        this.hp = hp;
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
