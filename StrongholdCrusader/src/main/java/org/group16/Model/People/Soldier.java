package org.group16.Model.People;

import org.group16.Lib.Pair;
import org.group16.Model.Resources.Resource;
import org.group16.Model.Resources.SoldierDetail;
import org.group16.Model.WarCommand;

import java.util.ArrayList;

public class Soldier extends Human {
    private final SoldierDetail detail;
    private WarCommand warCommand;

    public Soldier(SoldierDetail detail) {
        super(detail.getHp());
        this.detail = detail;
    }

    public SoldierDetail getDetail() {
        return detail;
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
}
