package org.group16.Controller;

import org.group16.Lib.Pair;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Buildings.MilitaryBuilding;
import org.group16.Model.Game;
import org.group16.Model.Kingdom;
import org.group16.Model.People.SoldierDetail;
import org.group16.Model.People.SoldierType;
import org.group16.Model.Resources.Resource;
import org.group16.Model.User;
import org.group16.View.BuildingMenu;

public class BuildingMenuController {

    public static String createUnit(MilitaryBuilding militaryBuilding, SoldierDetail soldierDetail, int count) {
        if (!militaryBuilding.makeResource(soldierDetail, count)) return "not enough Resource/Human !";
        return "OK";
    }

    public static String repair(Building building) {
        Kingdom kingdom = building.getKingdom();
        for (Pair<Resource, Integer> pair : building.getBuildingType().getDependencies()) {
            if (kingdom.getResourceCount(pair.getA()) < pair.getB() / 2)
                return "can not repair : not enough " + pair.getA();
        }
        for (Pair<Resource, Integer> pair : building.getBuildingType().getDependencies())
            kingdom.useResource(pair.getA(), pair.getB() / 2);
        building.repair();
        return "OK";
    }
}
