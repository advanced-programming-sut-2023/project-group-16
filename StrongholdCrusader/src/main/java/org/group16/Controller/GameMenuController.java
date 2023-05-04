package org.group16.Controller;


import org.group16.Lib.Pair;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.Game;
import org.group16.Model.Kingdom;
import org.group16.Model.People.SoldierType;
import org.group16.Model.User;

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

    public static ArrayList<Pair<String , Integer>> showPopularityFactors(Game game, User currentUser) {
        Kingdom kingdom = game.getKingdom(currentUser) ;
        ArrayList<Pair<String , Integer>> factors = new ArrayList<>();
        factors.add(new Pair<>("Food" , kingdom.getFoodEffectOnPopularity()));
        factors.add(new Pair<>("Tax" , kingdom.getTaxEffectOnPopularity())) ;
        factors.add(new Pair<>("Fear" , -kingdom.getFearRate())) ;
        //not sure about this field
        factors.add(new Pair<>("Religion" , 0)) ;
        return factors;
    }

    public static String showPopularity(Game game, User currentUser) {
        return null;
    }//TODO

    public static String showFoodList(Game game, User currentUser) {
        return null;
    }//TODO

    public static String setFoodRate(Game game, User currentUser, int rate) {
        return null;
    }//TODO

    public static String showFoodRate(Game game, User currentUser) {
        return null;
    }//TODO

    public static String setTaxRate(Game game, User currentUser, int rate) {
        return null;
    }//TODO

    public static String showTaxRate(Game game, User currentUser) {
        return null;
    }//TODO

    public static String setFearRate(Game game, User currentUser, int rate) {
        return null;
    }//TODO

    public static String dropBuilding(Game game, User currentUser, int x, int y, BuildingType buildingType) {
        return null;
    }//TODO

    public static String selectBuilding(Game game, User currentUser, int x, int y) {
        return null;
    }//TODO

    public static String selectUnit(Game game, User currentUser, int x, int y) {
        return null;
    }//TODO
}
