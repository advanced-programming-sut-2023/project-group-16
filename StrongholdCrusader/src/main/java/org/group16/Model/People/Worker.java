package org.group16.Model.People;

import org.group16.Model.Cell;
import org.group16.Model.Kingdom;

import java.util.ArrayList;

public class Worker extends Human {

    public Worker(Kingdom kingdom, int hp) {
        super(kingdom, hp);
    }

    @Override
    public void onTurnStart() {
        //TODO
    }

    @Override
    public void initialize(ArrayList<Cell> spawnCell) {
        super.initialize(spawnCell);
        //TODO
    }

    @Override
    public void update(double deltaTime) {
        //TODO
    }

    @Override
    public void onTurnEnd() {
        //TODO
    }
}
