package org.group16.Model.People;

import org.group16.Model.Siege.Siege;
import org.group16.Model.WarCommand;

public class Soldier extends Human {
    private final SoldierDetail soldierDetail;
    private WarCommand warCommand;
    private Siege siege;

    public Soldier(int hp) {
        super(hp);
        soldierDetail = null;
    }

    public Soldier(SoldierDetail detail) {
        super(detail.getHp());
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
        //TODO
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
