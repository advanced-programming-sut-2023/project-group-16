package org.group16.View;

import org.group16.Controller.GameMenuController;
import org.group16.Lib.Pair;
import org.group16.Model.Game;
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
        for (Pair <String , Integer> pair : GameMenuController.showPopularityFactors(game , getCurrentUser()))
            System.out.println(pair.getA() + " : " + pair.getB());
    }

    private void showPopularity(TreeMap<String, ArrayList<String>> map) {
        System.out.println(GameMenuController.showPopularity(game , getCurrentUser()));
    }

    private void showFoodList(TreeMap<String, ArrayList<String>> map) {
        for (String foodName : GameMenuController.showFoodList(game , getCurrentUser()))
            System.out.println(foodName);
    }

    private void setFoodRate(TreeMap<String, ArrayList<String>> map) {
        int rate = Integer.parseInt(map.get("r").get(0)) ;
        String output = GameMenuController.setFoodRate(game , getCurrentUser() , rate) ;
        if (output.equals("OK"))
            return;
        System.out.println(output);
    }

    private void showFoodRate(TreeMap<String, ArrayList<String>> map) {
        String output = GameMenuController.showFoodRate(game , getCurrentUser()) ;
        System.out.println(output);
    }//TODO

    private void setTaxRate(Matcher matcher) {

    }//TODO

    private void showTaxRate(Matcher matcher) {

    }//TODO

    private void setFearRate(Matcher matcher) {

    }//TODO

    private void dropBuilding(Matcher matcher) {

    }//TODO

    private void selectBuilding(Matcher matcher) {

    }//TODO

    private void selectUnit(Matcher matcher) {

    }//TODO
}
