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

    }

    private void nextTurn() {
    }

    private User getCurrentUser() {
        return game.getKingdoms().get(currentPlayer).getUser();
    }


    private void showMap(Matcher matcher) {

    }

    private void moveMap(Matcher matcher) {
    }

    private void showMapDetails(Matcher matcher) {
    }

    private void showPopularityFactors(Matcher matcher) {

    }

    private void showPopularity(Matcher matcher) {

    }

    private void showFoodList(Matcher matcher) {

    }

    private void setFoodRate(Matcher matcher) {

    }

    private void showFoodRate(Matcher matcher) {

    }

    private void setTaxRate(Matcher matcher) {

    }

    private void showTaxRate(Matcher matcher) {

    }

    private void setFearRate(Matcher matcher) {

    }

    private void dropBuilding(Matcher matcher) {

    }

    private void selectBuilding(Matcher matcher) {

    }

    private void selectUnit(Matcher matcher) {

    }
}
