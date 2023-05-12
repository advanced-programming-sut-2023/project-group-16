package org.group16.Controller;


import org.checkerframework.checker.units.qual.K;
import org.group16.Lib.Pair;
import org.group16.Model.*;
import org.group16.Model.Buildings.*;
import org.group16.Model.People.*;
import org.group16.Model.Resources.Food;
import org.group16.Model.Resources.Resource;

import java.util.ArrayList;

public class GameMenuController {
    public static String showMap(Game game, User currentUser, int x, int y) {
        return null;
    }//TODO : show map

    public static String moveMap(Game game, User currentUser, int deltaX, int deltaY) {
        return null;
    }//TODO : move map

    public static String showMapDetails(Game game, User currentUser, int x, int y) {
        return null;
    }//TODO : show map Details

    public static ArrayList<Pair<String, Integer>> showPopularityFactors(Game game, User currentUser) {
        Kingdom kingdom = game.getKingdom(currentUser);
        ArrayList<Pair<String, Integer>> factors = new ArrayList<>();
        factors.add(new Pair<>("Food", kingdom.getFoodEffectOnPopularity()));
        factors.add(new Pair<>("Tax", kingdom.getTaxEffectOnPopularity()));
        factors.add(new Pair<>("Fear", -kingdom.getFearRate()));
        //not sure about this field
        factors.add(new Pair<>("Religion", 0));
        return factors;
    }

    public static String showPopularity(Game game, User currentUser) {
        Kingdom kingdom = game.getKingdom(currentUser);
        return "Popularity : " + kingdom.getPopularity();
    }

    public static ArrayList<String> showFoodList(Game game, User currentUser) {
        Kingdom kingdom = game.getKingdom(currentUser);
        ArrayList<String> foods = new ArrayList<>();
        for (Food food : kingdom.getFoodList())
            foods.add(food.name());
        return foods;
    }

    public static String setFoodRate(Game game, User currentUser, int rate) {
        Kingdom kingdom = game.getKingdom(currentUser);
        if (rate < -2 || rate > 2)
            return "food rate should be between -2 and 2 !";
        kingdom.setFoodRate(rate);
        return "OK";
    }

    public static String showFoodRate(Game game, User currentUser) {
        Kingdom kingdom = game.getKingdom(currentUser);
        return "Food rate : " + kingdom.getFoodRate();
    }

    public static String setTaxRate(Game game, User currentUser, int rate) {
        Kingdom kingdom = game.getKingdom(currentUser);
        if (rate < -3 || rate > 8)
            return "tax rate should be between -3 and 8 !";
        kingdom.setTax(rate);
        return "OK";
    }

    public static String showTaxRate(Game game, User currentUser) {
        Kingdom kingdom = game.getKingdom(currentUser);
        return "Tax rate : " + kingdom.getTax();
    }

    public static String setFearRate(Game game, User currentUser, int rate) {
        Kingdom kingdom = game.getKingdom(currentUser);
        if (rate < -5 || rate > 5)
            return "fear rate should be between -5 and 5 !";
        kingdom.setFearRate(rate);
        return "OK";
    }

    public static String dropBuilding(Game game, User currentUser, int x, int y, BuildingType buildingType) {
        Kingdom kingdom = game.getKingdom(currentUser);
        int cellSize = buildingType.getCellSize() - 1;
        boolean isCellsAppropriate = false;
        ArrayList<Cell> cells = new ArrayList<>();
        for (int xx = x - cellSize; xx < x + cellSize; xx++) {
            for (int yy = y - cellSize; yy < y + cellSize; yy++) {
                if (xx > game.getScene().getMap().getHeight() || xx < 0 || yy > game.getScene().getMap().getWidth() || yy < 0)
                    return "can not build here : not in map";
                Cell cell = game.getScene().getCellAt(xx, yy);
                if (!cell.getCellType().isOkToBuildIn())
                    return "can not build here : inappropriate land";
                if (cell.getCellType().equals(buildingType.getCellTypeNeeded()))
                    isCellsAppropriate = true;
                if (cell.getGameObjects().size() > 0)
                    return "can not build here : cell is full !";
                cells.add(cell);
            }
        }
        if (!isCellsAppropriate)
            return "can not build here : this building should be built on " + buildingType.getCellTypeNeeded();
        for (Pair<Resource, Integer> pair : buildingType.getDependencies()) {
            if (kingdom.getResourceCount(pair.getA()) < pair.getB())
                return "can not build now : not enough " + pair.getA();
        }

        for (EconomicBuildingDetail economicBuildingDetail : EconomicBuildingDetail.values()) {
            if (economicBuildingDetail.getBuildingType().equals(buildingType)) {
                if (kingdom.availableHumans() < economicBuildingDetail.getNeededWorkers() + economicBuildingDetail.getNeededEngineers())
                    return "can not build now : not enough Human";
                kingdom.useHuman(economicBuildingDetail.getNeededEngineers() + economicBuildingDetail.getNeededWorkers());
                for (Pair<Resource, Integer> pair : buildingType.getDependencies())
                    kingdom.useResource(pair.getA(), pair.getB());
                EconomicBuilding economicBuilding = new EconomicBuilding(cells, kingdom, game.getCurrentTime(), economicBuildingDetail);
                for (int i = 0; i < economicBuildingDetail.getNeededWorkers(); i++)
                    economicBuilding.addWorker(new Worker(cells, kingdom, 100));
                for (int i = 0; i < economicBuildingDetail.getNeededEngineers(); i++)
                    economicBuilding.addWorker(new Soldier(cells, kingdom, SoldierDetail.ENGINEER));
            }
        }
        for (WarBuildingDetail warBuildingDetail : WarBuildingDetail.values()) {
            if (warBuildingDetail.getBuildingType().equals(buildingType)) {
                for (Pair<Resource, Integer> pair : buildingType.getDependencies())
                    kingdom.useResource(pair.getA(), pair.getB());
                new WarBuilding(cells, kingdom, game.getCurrentTime(), warBuildingDetail);
            }
        }
        return "OK";
    }

    public static String teamUpRequest(Game game, User currentUser, User to) {
        Kingdom kingdom1 = game.getKingdom(currentUser);

        if (to == null) return "invalid user";
        Kingdom kingdom2 = game.getKingdom(to);
        if (kingdom2 == null) return "user is not playing";

        if (kingdom1.getTeam() == kingdom2.getTeam()) return "already team members";

        game.addTeamUp(new TeamUp(kingdom1, kingdom2));
        return "team up sent successfully";
    }

    public static String teamUpAccept(Game game, User currentUser, int id) {
        TeamUp teamUp = game.getTeamUpById(id);
        if (teamUp == null || currentUser != teamUp.getTo().getUser()) return "invalid team up id";
        game.completeTeamUp(teamUp);
        return "team up successful";
    }

    public static String showTeamUpList(Game game, User currentUser) {
        ArrayList<TeamUp> teamUps = game.getUserTeamUpOffers(currentUser);
        StringBuilder output = new StringBuilder();
        for (TeamUp teamUp : teamUps)
            output.append(teamUp);
        if (output.length() == 0) return "no team up available\n";
        return output.toString();
    }

    public static String leaveTeam(Game game, User currenUser) {
        Kingdom kingdom = game.getKingdom(currenUser);
        kingdom.setTeam(new Team(kingdom));
        return "leaved team successfully";
    }
}
