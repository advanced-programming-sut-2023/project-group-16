package org.group16.Model.Siege;

import org.group16.Model.Cell;
import org.group16.Model.Kingdom;
import org.group16.Model.People.Soldier;

import java.util.ArrayList;

public class SiegeTower extends Siege {
    private final ArrayList<Soldier> soldiers = new ArrayList<>();
    private boolean isFixed;

    public SiegeTower(ArrayList<Cell> cells, Kingdom kingdom, SiegeDetail detail) {
        super(cells, kingdom, detail);
    }

    public void addSoldier(Soldier soldier) {
        soldiers.add(soldier);
        soldier.setSiege(this);
    }

    public void removeSoldier(Soldier soldier) {
        soldiers.remove(soldier);
        soldier.setSiege(null);
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }


    @Override
    public void destroy() {
        while (soldiers.size() > 0)
            soldiers.get(0).destroy();
        super.destroy();
    }
}
