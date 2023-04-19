package org.group16.Model.Buildings;

import org.group16.Model.Cell;
import org.group16.Model.GameObject;
import org.group16.Model.Kingdom;
import org.group16.Model.People.Alive;

import java.util.ArrayList;

public abstract class Building extends GameObject implements Alive {

    private int hp;
    private boolean traversable;

    public Building(ArrayList<Cell> cells, Kingdom kingdom, int hp) {
        super(cells, kingdom);
        this.hp = hp;
        kingdom.addBuilding(this);
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
        getKingdom().removeBuilding(this);
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

    @Override
    public void dealDamage(int damage) {
        setHp(getHp() - damage);
    }
}
