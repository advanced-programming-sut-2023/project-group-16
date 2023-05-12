package org.group16.View;

import org.group16.Model.*;
import org.group16.View.Command.Command;
import org.group16.View.Command.CommandHandler;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class CreateGameMenu {

    private final Scanner scanner;
    private final Game game;
    private boolean back = false;

    public CreateGameMenu(Scanner scanner, User firstPlayer, KingdomType kingdomType) {
        this.scanner = scanner;
        this.game = new Game(kingdomType, firstPlayer);
    }

    public void run() {
        while (true) {
            if (back)
                return;
            String input = scanner.nextLine();
            TreeMap<String, ArrayList<String>> map;
            if ((map = CommandHandler.matches(Command.SELECT_MAP, input)) != null) selectMap(map);
            else if ((map = CommandHandler.matches(Command.ADD_USER, input)) != null) addUser(map);
            else if ((map = CommandHandler.matches(Command.REMOVE_USER, input)) != null) removeUser(map);
            else if (CommandHandler.matches(Command.START_GAME, input) != null) startGame();
            else if (CommandHandler.matches(Command.BACK, input) != null) {
                System.out.println("exit create game menu successfully");
                break;
            } else System.out.println("invalid command");
        }
    }

    private void selectMap(TreeMap<String, ArrayList<String>> map) {
        Map newMap = Map.getMapByName(map.get("n").get(0));
        if (newMap == null) {
            System.out.println("no map with this name exist");
            return;
        }
        game.setScene(new Scene(newMap));
        System.out.println("map selected successfully");
    }

    private void addUser(TreeMap<String, ArrayList<String>> map) {
        User user = User.getUserByName(map.get("u").get(0));
        KingdomType kingdomType = KingdomType.getKingdomTypeByName(map.get("t").get(0));
        if (kingdomType == null) {
            System.out.println("invalid kingdom type");
            return;
        }
        if (game.getKingdoms().size() == 8) {
            System.out.println("game is full");
            return;
        }
        if (game.getKingdom(user) != null) {
            System.out.println("this user already exist");
            return;
        }
        game.addUser(user, kingdomType);
        System.out.println("user added successfully");
    }

    private void removeUser(TreeMap<String, ArrayList<String>> map) {
        User user = User.getUserByName(map.get("u").get(0));
        if (game.getKingdom(user) == null) {
            System.out.println("this user doesn't exist");
            return;
        }
        game.removeUser(user);
        System.out.println("user removed successfully");
    }

    private void startGame() {
        if (game.getKingdoms().size() < 2) {
            System.out.println("insufficient user to start game");
            return;
        }
        if (game.getScene().getMap() == null) {
            System.out.println("no map is selected");
            return;
        }
        SetKingdomMenu setKingdomMenu = new SetKingdomMenu(scanner, game);
        System.out.println("now please select places of primary buildings");
        setKingdomMenu.run();
        back = true;
    }
}
