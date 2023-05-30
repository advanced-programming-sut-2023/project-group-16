package org.group16.Model.Buildings;

import org.group16.Model.Cell;
import org.group16.Model.GameObject;
import org.group16.Model.Kingdom;
import org.group16.Model.People.Human;
import org.group16.Model.People.Soldier;
import org.group16.Model.Time;

import java.util.ArrayList;
import java.util.Objects;

public class WarBuilding extends Building {
    private final ArrayList<Human> soldiers = new ArrayList<>();
    private final WarBuildingDetail detail;

    public WarBuilding(ArrayList<Cell> cells, Kingdom kingdom, double BuildTime, WarBuildingDetail detail) {
        super(cells, kingdom, detail.getHp(), BuildTime, detail.getBuildingType());
        this.detail = detail;
    }

    public WarBuildingDetail getDetail() {
        return detail;
    }

    @Override
    public void onTurnStart() {
        //TODO : on turn start
    }

    @Override
    public void onTurnEnd() {
        super.onTurnEnd();
        //TODO : on turn end
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
    public void update(double currentTime) {
        //TODO : Time needed may change -
        if (Time.isItTurned(currentTime, Time.deltaTime)) {
            for (Cell cell : getCells()) {
                for (GameObject human : cell.getGameObjects()) {
                    if (human instanceof Human && !getKingdom().getTeam().getKingdoms().contains(human.getKingdom())) {
                        ((Human) human).dealDamage(detail.getCurrentDamage());
                    }
                }
            }
        }
    }
}
