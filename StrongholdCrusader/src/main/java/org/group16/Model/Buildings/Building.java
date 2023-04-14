package org.group16.Model.Buildings;

import org.group16.Model.GameObject;
import org.group16.Model.People.Engineer;
import org.group16.Model.People.Human;
import org.group16.Model.People.Worker;

import java.util.ArrayList;

public abstract class Building extends GameObject {
    private int hp;
    private boolean traversable;

    public Building(int hp) {
        this.hp = hp;
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
