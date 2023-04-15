package org.group16.View;

import org.group16.Model.Game;
import org.group16.Model.User;

import java.util.Scanner;
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


    private void showMap(Matcher matcher) {

    }//TODO

    private void moveMap(Matcher matcher) {
    }//TODO

    private void showMapDetails(Matcher matcher) {
    }//TODO

    private void showPopularityFactors(Matcher matcher) {

    }//TODO

    private void showPopularity(Matcher matcher) {

    }//TODO

    private void showFoodList(Matcher matcher) {

    }//TODO

    private void setFoodRate(Matcher matcher) {

    }//TODO

    private void showFoodRate(Matcher matcher) {

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
