package org.group16.Model.People;

import org.group16.Model.*;
import org.group16.Model.Buildings.Building;

import java.util.ArrayList;

public class LadderMan extends Soldier {
    public LadderMan(ArrayList<Cell> cells, Kingdom kingdom, SoldierDetail detail) {
        super(cells, kingdom, detail);
    }

    @Override
    public void update(double currentTime) {
        double deltaTime = Time.deltaTime;
        Cell destination = warCommand.getDestination();
        Building target = warCommand.getTargetBuilding();
        if (target != null)
            destination = target.getCell();
        moveToward(destination, false, deltaTime * getSoldierDetail().getSpeed(), PATH_FINDING_RANDOMNESS, Scene.getCurrent().getRandom());

        if (target != null)
            if (Map.getCellDistance(getCell(), destination) <= 1.9)
                deployLadder(destination);
    }

    private void deployLadder(Cell cell) {
        cell.setHasLadder(true);
        // TODO : what happens to him?
        destroy();
    }
}
