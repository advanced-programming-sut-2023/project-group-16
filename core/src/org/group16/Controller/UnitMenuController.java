package org.group16.Controller;

import org.group16.Model.*;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.Buildings.EconomicBuilding;
import org.group16.Model.People.Engineer;
import org.group16.Model.People.Human;
import org.group16.Model.People.Soldier;
import org.group16.Model.People.SoldierDetail;
import org.group16.Model.Siege.Siege;
import org.group16.Model.Siege.SiegeDetail;

import java.util.ArrayList;

public class UnitMenuController {
    public static String moveUnit(Game game, ArrayList<Soldier> unit, int destinationX, int destinationY) {
        if (unit == null || unit.isEmpty()) return "invalid unit";
        Cell cell = game.getScene().getCellAt(destinationX, destinationY);
        if (cell == null) return "invalid coordinates";
        new WarCommand(unit, cell, false);
        return "move command successful";
    }

    public static String patrolUnit(Game game, ArrayList<Soldier> unit, int x1, int y1, int x2, int y2) {
        if (unit == null || unit.isEmpty()) return "invalid unit";
        Cell from = game.getScene().getCellAt(x1, y1);
        Cell to = game.getScene().getCellAt(x2, y2);
        if (from == null) return "invalid first coordinate";
        if (to == null) return "invalid second coordinate";

        new WarCommand(unit, from, to);
        return "patrol command successful";
    }

    public static String setStats(Game game, ArrayList<Soldier> unit, WarCommand.Status status) {
        if (status == null) return "invalid status";
        if (unit == null || unit.isEmpty()) return "invalid unit";
        if (unit.get(0).getWarCommand().getUnit().equals(unit))
            unit.get(0).getWarCommand().setStatus(status);
        new WarCommand(unit).setStatus(status);
        return "set status successful";
    }

    public static String filterSubtractive(Game game, ArrayList<Soldier> unit, SoldierDetail type) {
        unit.removeIf(soldier -> soldier.getSoldierDetail() == type);
        return "filter successful";
    }

    public static String filterSubtractive(Game game, ArrayList<Soldier> unit, SiegeDetail type) {
        unit.removeIf(soldier -> soldier instanceof Siege siege && siege.getSiegeDetail() == type);
        return "filter successful";
    }

    public static String filter(Game game, ArrayList<Soldier> unit, SoldierDetail type) {
        unit.removeIf(soldier -> soldier.getSoldierDetail() != type);
        return "filter successful";
    }

    public static String filter(Game game, ArrayList<Soldier> unit, SiegeDetail type) {
        unit.removeIf(soldier -> !(soldier instanceof Siege siege && siege.getSiegeDetail() == type));
        return "filter successful";
    }

    public static String attackEnemy(Game game, ArrayList<Soldier> unit, int enemyX, int enemyY) {
        Cell cell = game.getScene().getCellAt(enemyX, enemyY);
        if (cell == null) return "invalid enemy cell";
        if (unit == null || unit.isEmpty()) return "invalid unit";

        new WarCommand(unit, cell, true);
        return "attack command successful";
    }

    public static String digTunnel(Game game, ArrayList<Soldier> unit, int x, int y) {
        return null;
    }//TODO : what?

    public static String buildEquipment(Game game, ArrayList<Soldier> unit, int x, int y, SiegeDetail type) {
        Cell cell = game.getScene().getCellAt(x, y);
        if (cell == null) return "invalid cell";
        if (type == null) return "invalid type";
        if (unit == null || unit.isEmpty()) return "invalid unit";
        for (Soldier soldier : unit) if (soldier instanceof Engineer engineer) engineer.setCreationCommand(type);

        new WarCommand(unit, cell, false);
        return "build command successful";
    }

    public static String disbandUnit(Game game, ArrayList<Soldier> unit) {
        Kingdom kingdom = unit.get(0).getKingdom();
        EconomicBuilding building = kingdom.getEconomicBuildingsByType(BuildingType.UNEMPLOYED_PLACE).get(0);
        for (Soldier soldier : unit) {
            soldier.destroy();
            new Human(building.getCells(), kingdom, 100).setBuilding(building);
        }
        unit.clear();
        return "disband successful";
    }
}
