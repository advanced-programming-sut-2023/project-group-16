package org.group16.Model.People;

import org.group16.Model.Cell;
import org.group16.Model.Kingdom;
import org.group16.Model.Time;

import java.util.ArrayList;

public class Worker extends Human {

    public Worker(ArrayList<Cell> cells, Kingdom kingdom, int hp) {
        super(cells, kingdom, hp);
    }

    @Override
    public void onTurnStart() {
        //TODO : on turn start
    }

    @Override
    public void update(double currentTime) {
        super.update(currentTime);
        double deltaTime = Time.deltaTime;
        //TODO : update
    }

    @Override
    public void onTurnEnd() {
        //TODO : on turn end
    }

}
