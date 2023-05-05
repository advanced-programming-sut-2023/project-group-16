package org.group16.Controller;


import org.checkerframework.checker.units.qual.K;
import org.group16.Lib.Pair;
import org.group16.Model.*;
import org.group16.Model.Buildings.*;
import org.group16.Model.People.SoldierType;
import org.group16.Model.Resources.Food;
import org.group16.Model.Resources.Resource;

import java.util.ArrayList;

public class GameMenuController {
    public static String showMap(Game game, User currentUser, int x, int y) {
        return null;
    }//TODO

    public static String moveMap(Game game, User currentUser, int deltaX, int deltaY) {
        return null;
    }//TODO

    public static String showMapDetails(Game game, User currentUser, int x, int y) {
        return null;
    }//TODO

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
        for (Pair<Resource, Integer> pair : buildingType.getDependencies())
            kingdom.useResource(pair.getA(), pair.getB());
        for (EconomicBuildingDetail economicBuildingDetail : EconomicBuildingDetail.values()){
            if (economicBuildingDetail.getBuildingType().equals(buildingType)) {
                //TODO : time should be added correctly
                new EconomicBuilding(cells, kingdom, 0.0 ,economicBuildingDetail) ;
            }
        }
        for (WarBuildingDetail warBuildingDetail : WarBuildingDetail.values()){
            if (warBuildingDetail.getBuildingType().equals(buildingType)){
                //TODO : time should be added correctly
                new WarBuilding(cells , kingdom , 0.0 , warBuildingDetail) ;
            }
        }
        return "OK";
    }

    public static String selectUnit(Game game, User currentUser, int x, int y) {
        return null;
    }//TODO
}
