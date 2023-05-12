package org.group16.View;

import org.group16.Controller.GameMenuController;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.Game;
import org.group16.Model.KingdomType;
import org.group16.Model.People.Soldier;
import org.group16.Model.People.SoldierDetail;
import org.group16.Model.User;
import org.group16.View.Command.Command;
import org.group16.View.Command.CommandHandler;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class SetKingdomMenu {
    private final Scanner scanner;
    private final Game game;
    private int currentPlayer = 0;

    public SetKingdomMenu(Scanner scanner, Game game) {
        this.game = game;
        this.scanner = scanner;
    }

    private User getCurrentUser() {
        return game.getKingdoms().get(currentPlayer).getUser();
    }

    public void run() {
        while (true) {
            String input = scanner.nextLine();
            TreeMap<String, ArrayList<String>> map;
            if ((map = CommandHandler.matches(Command.SET_KINGDOM, input)) != null) selectKingdom(map);
            if ((map = CommandHandler.matches(Command.SET_UNEMPLOYED, input)) != null) selectUnemployedPlace(map);
            if ((map = CommandHandler.matches(Command.NEXT_TURN, input)) != null) nextTurn(map);
            else if (CommandHandler.matches(Command.BACK, input) != null) {
                System.out.println("back to CreateGameMenu");
                return;
            } else System.out.println("invalid command");
        }
    }

    private void selectKingdom(TreeMap<String, ArrayList<String>> map) {
        int x = Integer.parseInt(map.get("x").get(0));
        int y = Integer.parseInt(map.get("y").get(0));
        String output = GameMenuController.dropBuilding(game, getCurrentUser(), x, y, BuildingType.TOWN_BUILDING);
        System.out.println(output);
        if (output.equals("OK")) {
            Building townBuilding = game.getKingdoms().get(currentPlayer).getEconomicBuildingsByType(BuildingType.TOWN_BUILDING).get(0);
            new Soldier(townBuilding.getCells(), game.getKingdoms().get(currentPlayer), SoldierDetail.KING);
        }
    }

    private void selectUnemployedPlace(TreeMap<String, ArrayList<String>> map) {
        int x = Integer.parseInt(map.get("x").get(0));
        int y = Integer.parseInt(map.get("y").get(0));
        String output = GameMenuController.dropBuilding(game, getCurrentUser(), x, y, BuildingType.UNEMPLOYED_PLACE);
        System.out.println(output);
        if (output.equals("OK")) {
            game.getKingdoms().get(currentPlayer).addPopulation(10);
        }
    }

    private void nextTurn(TreeMap<String, ArrayList<String>> map) {
        if (game.getKingdoms().get(currentPlayer).getEconomicBuildingsByType(BuildingType.TOWN_BUILDING) == null ||
                game.getKingdoms().get(currentPlayer).getEconomicBuildingsByType(BuildingType.UNEMPLOYED_PLACE) == null) {
            System.out.println("please set places first");
            return;
        }
        if (currentPlayer != game.getKingdoms().size() - 1) {
            currentPlayer++;
            System.out.println("now user " + getCurrentUser().getNickname() + "is setting kingdom");
        } else {
            System.out.println("lets Play!!!");
            GameMenu gameMenu = new GameMenu(scanner, game);
            System.out.println("game started successfully");
            gameMenu.run();
        }
    }
}
