package org.group16.View;

import org.group16.Controller.GameMenuController;
import org.group16.Lib.Pair;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.Game;
import org.group16.Model.GameObject;
import org.group16.Model.User;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;

public class GameMenu {
    private final Scanner scanner;
    private final Game game;
    private int currentPlayer;
    private int x, y;

    public GameMenu(Scanner scanner, Game game) {
        this.scanner = scanner;
        this.game = game;
    }

    public void run() {

    }//TODO

    private void nextTurn() {
    }//TODO

    private User getCurrentUser() {
        return game.getKingdoms().get(currentPlayer).getUser();
    }


    private void showMap(TreeMap<String, ArrayList<String>> map) {

    }//TODO

    private void moveMap(TreeMap<String, ArrayList<String>> map) {
    }//TODO

    private void showMapDetails(TreeMap<String, ArrayList<String>> map) {
    }//TODO

    private void showPopularityFactors(TreeMap<String, ArrayList<String>> map) {
        for (Pair<String, Integer> pair : GameMenuController.showPopularityFactors(game, getCurrentUser()))
            System.out.println(pair.getA() + " : " + pair.getB());
    }

    private void showPopularity(TreeMap<String, ArrayList<String>> map) {
        System.out.println(GameMenuController.showPopularity(game, getCurrentUser()));
    }

    private void showFoodList(TreeMap<String, ArrayList<String>> map) {
        for (String foodName : GameMenuController.showFoodList(game, getCurrentUser()))
            System.out.println(foodName);
    }

    private void setFoodRate(TreeMap<String, ArrayList<String>> map) {
        int rate = Integer.parseInt(map.get("r").get(0));
        String output = GameMenuController.setFoodRate(game, getCurrentUser(), rate);
        if (output.equals("OK"))
            return;
        System.out.println(output);
    }

    private void showFoodRate(TreeMap<String, ArrayList<String>> map) {
        String output = GameMenuController.showFoodRate(game, getCurrentUser());
        System.out.println(output);
    }

    private void setTaxRate(TreeMap<String, ArrayList<String>> map) {
        int rate = Integer.parseInt(map.get("r").get(0)) ;
        String output = GameMenuController.setTaxRate(game , getCurrentUser() , rate) ;
        if (output.equals("OK"))
            return;
        System.out.println(output);
    }

    private void showTaxRate(TreeMap<String, ArrayList<String>> map) {
        String output = GameMenuController.showTaxRate(game , getCurrentUser()) ;
        System.out.println(output);
    }

    private void setFearRate(TreeMap<String, ArrayList<String>> map) {
        int rate = Integer.parseInt(map.get("r").get(0)) ;
        String output = GameMenuController.setFearRate(game , getCurrentUser() , rate) ;
        if (output.equals("OK"))
            return;
        System.out.println(output);
    }

    private void dropBuilding(TreeMap<String, ArrayList<String>> map) {
        int x = Integer.parseInt(map.get("x").get(0)) ;
        int y = Integer.parseInt(map.get("y").get(0)) ;
        String type = map.get("t").get(0) ;
        BuildingType buildingType = BuildingType.getBuildingTypeByName(type) ;
        if (buildingType==null){
            System.out.println("no building called " + type + "!");
            return;
        }
        String output = GameMenuController.dropBuilding(game , getCurrentUser() , x , y , buildingType) ;
        if (output.equals("OK"))
            return;
        System.out.println(output);
    }

    private void selectBuilding(TreeMap<String, ArrayList<String>> map) {
        int x = Integer.parseInt(map.get("x").get(0)) ;
        int y = Integer.parseInt(map.get("y").get(0)) ;
        Building building = null;
        for (GameObject gameObject : game.getScene().getMap().getCellAt(x , y).getGameObjects()){
            if (gameObject instanceof Building)
                building = (Building) gameObject ;
        }
        if (building==null){
            System.out.println("no Building here");
            return;
        }
        //TODO : going to Building menu
    }

    private void selectUnit(TreeMap<String, ArrayList<String>> map) {

    }//TODO
}
