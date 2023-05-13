package org.group16.View;

import org.group16.Controller.GameMenuController;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.Game;
import org.group16.Model.KingdomType;
import org.group16.Model.People.Soldier;
import org.group16.Model.People.SoldierDetail;
import org.group16.Model.Resources.BasicResource;
import org.group16.Model.User;
import org.group16.View.Command.Command;
import org.group16.View.Command.CommandHandler;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class SetKingdomMenu {
    private final Scanner scanner;
    private final Game game;
    boolean back = false;
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
            if (back)
                return;
            String input = scanner.nextLine();
            TreeMap<String, ArrayList<String>> map;
            if ((map = CommandHandler.matches(Command.SET_KINGDOM, input)) != null) selectKingdom(map);
            else if ((map = CommandHandler.matches(Command.SET_UNEMPLOYED, input)) != null) selectUnemployedPlace(map);
            else if ((map = CommandHandler.matches(Command.SET_STOCK_PILE, input)) != null) selectStockPile(map);
            else if ((map = CommandHandler.matches(Command.NEXT_TURN, input)) != null) nextTurn(map);
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
        if (output.equals("OK")) {
            System.out.println("town place dropped successfully");
            Building townBuilding = game.getKingdoms().get(currentPlayer).getEconomicBuildingsByType(BuildingType.TOWN_BUILDING).get(0);
            game.getKingdoms().get(currentPlayer).setKing(new Soldier(townBuilding.getCells(), game.getKingdoms().get(currentPlayer), SoldierDetail.KING));
            game.getKingdoms().get(currentPlayer).addGold(10000);
        } else
            System.out.println(output);
    }

    private void selectStockPile(TreeMap<String, ArrayList<String>> map) {
        int x = Integer.parseInt(map.get("x").get(0));
        int y = Integer.parseInt(map.get("y").get(0));
        String output = GameMenuController.dropBuilding(game, getCurrentUser(), x, y, BuildingType.STOCKPILE);
        if (output.equals("OK")) {
            System.out.println("stock pile dropped successfully");
            game.getKingdoms().get(currentPlayer).addResource(BasicResource.STONE, 50);
            game.getKingdoms().get(currentPlayer).addResource(BasicResource.WOOD, 200);
        } else
            System.out.println(output);
    }

    private void selectUnemployedPlace(TreeMap<String, ArrayList<String>> map) {
        int x = Integer.parseInt(map.get("x").get(0));
        int y = Integer.parseInt(map.get("y").get(0));
        String output = GameMenuController.dropBuilding(game, getCurrentUser(), x, y, BuildingType.UNEMPLOYED_PLACE);
        if (output.equals("OK")) {
            System.out.println("unemployed place dropped successfully");
            game.getKingdoms().get(currentPlayer).addPopulation(10);
        } else
            System.out.println(output);
    }

    private void nextTurn(TreeMap<String, ArrayList<String>> map) {
        if (game.getKingdoms().get(currentPlayer).getEconomicBuildingsByType(BuildingType.TOWN_BUILDING).size() == 0 ||
                game.getKingdoms().get(currentPlayer).getEconomicBuildingsByType(BuildingType.UNEMPLOYED_PLACE).size() == 0 ||
                game.getKingdoms().get(currentPlayer).getEconomicBuildingsByType(BuildingType.STOCKPILE).size() == 0
        ) {
            System.out.println("please set places first");
            return;
        }
        if (currentPlayer != game.getKingdoms().size() - 1) {
            game.getKingdoms().get(currentPlayer).addPopularity(100);
            currentPlayer++;
            System.out.println("now user " + getCurrentUser().getNickname() + " is setting kingdom");
        } else {
            System.out.println("lets Play!!!");
            GameMenu gameMenu = new GameMenu(scanner, game);
            System.out.println("game started successfully");
            gameMenu.run();
            back = true;
        }
    }
}
