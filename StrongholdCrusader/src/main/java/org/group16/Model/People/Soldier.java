package org.group16.Model.People;

import org.group16.Lib.Pair;
import org.group16.Model.*;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Siege.Siege;

import java.util.ArrayList;

public class Soldier extends Human {
    private final SoldierDetail soldierDetail;
    private WarCommand warCommand;
    private Siege siege;

    public Soldier(ArrayList<Cell> cells, Kingdom kingdom, int hp) {
        super(cells, kingdom, hp);
        soldierDetail = null;
    }

    public Soldier(ArrayList<Cell> cells, Kingdom kingdom, SoldierDetail detail) {
        super(cells, kingdom, detail.getHp());
        this.soldierDetail = detail;
    }


    public SoldierDetail getSoldierDetail() {
        return soldierDetail;
    }

    public WarCommand getWarCommand() {
        return warCommand;
    }

    public void setWarCommand(WarCommand warCommand) {
        this.warCommand = warCommand;
    }

    @Override
    public void onTurnStart() {
        //TODO
    }

    @Override
    public void update(double deltaTime) {
        Cell moveDestination = warCommand.getDestination();
        Human humanTarget = warCommand.getTargetHuman();
        Building buildingTarget = warCommand.getTargetBuilding();
        WarCommand.Status status = warCommand.getStatus();

    }

    public void attackTarget(Alive alive, int damage) {
        alive.dealDamage(damage);
    }

    @Override
    public void onTurnEnd() {
        //TODO
    }

    public Siege getSiege() {
        return siege;
    }

    public void setSiege(Siege siege) {
        this.siege = siege;
    }

}
