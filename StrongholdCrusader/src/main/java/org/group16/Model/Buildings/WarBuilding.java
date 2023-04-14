package org.group16.Model.Buildings;

import org.group16.Model.People.Human;
import org.group16.Model.People.Soldier;

import java.util.ArrayList;

public class WarBuilding extends Building {
    private final ArrayList<Human> soldiers = new ArrayList<>();
    private final WarBuildingDetail detail;

    public WarBuilding(WarBuildingDetail detail) {
        super(detail.getHp());
        this.detail = detail;
    }

    public WarBuildingDetail getDetail() {
        return detail;
    }

    @Override
    public void onTurnStart() {
        //TODO
    }

    public ArrayList<Human> getSoldiers() {
        return soldiers;
    }

    public void addSoldier(Human soldier) {
        if (soldier instanceof Soldier) {
            soldiers.add(soldier);
            soldier.setBuilding(this);
        }
    }

    public void removeSoldier(Human soldier) {
        soldiers.remove(soldier);
        soldier.setBuilding(null);
    }

    @Override
    public void update(double deltaTime) {
        //TODO
    }
}
