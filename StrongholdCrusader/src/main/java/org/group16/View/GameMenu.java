package org.group16.View;

import org.group16.Controller.GameMenuController;
import org.group16.Lib.Pair;
import org.group16.Model.*;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.People.Soldier;
import org.group16.View.Command.Command;
import org.group16.View.Command.CommandHandler;
import org.ietf.jgss.GSSManager;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class GameMenu {
    private final Scanner scanner;
    private Game game;
    private int currentPlayer = 0;
    private int x, y;

    public GameMenu(Scanner scanner, Game game) {
        this.scanner = scanner;
        this.game = game;
    }

    public void run() {
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            TreeMap<String, ArrayList<String>> map;
            if ((map = CommandHandler.matches(Command.SHOW_MAP, input)) != null) showMap(map);
            else if ((map = CommandHandler.matches(Command.MOVE_MAP, input)) != null) moveMap(map);
            else if ((map = CommandHandler.matches(Command.SHOW_DETAILS, input)) != null) showMapDetails(map);
            else if ((map = CommandHandler.matches(Command.SHOW_POPULARITY, input)) != null) showPopularity(map);
            else if ((map = CommandHandler.matches(Command.SHOW_FACTORS, input)) != null) showPopularityFactors(map);
            else if ((map = CommandHandler.matches(Command.FOOD_LIST, input)) != null) showFoodList(map);
            else if ((map = CommandHandler.matches(Command.SET_FOOD_RATE, input)) != null) setFoodRate(map);
            else if ((map = CommandHandler.matches(Command.SHOW_FOOD_RATE, input)) != null) showFoodRate(map);
            else if ((map = CommandHandler.matches(Command.SET_TAX_RATE, input)) != null) setTaxRate(map);
            else if ((map = CommandHandler.matches(Command.SHOW_TAX_RATE, input)) != null) showTaxRate(map);
            else if ((map = CommandHandler.matches(Command.SET_FEAR_RATE, input)) != null) setFearRate(map);
            else if ((map = CommandHandler.matches(Command.DROP_BUILDING, input)) != null) dropBuilding(map);
            else if ((map = CommandHandler.matches(Command.SELECT_UNIT, input)) != null) selectUnit(map);
            else if ((map = CommandHandler.matches(Command.SELECT_BUILDING, input)) != null) selectBuilding(map);
            else if ((map = CommandHandler.matches(Command.TEAM_UP_REQUEST, input)) != null) teamUpRequest(map);
            else if ((map = CommandHandler.matches(Command.TEAM_UP_ACCEPT, input)) != null) teamUpAccept(map);
            else if ((map = CommandHandler.matches(Command.TEAM_UP_ACCEPT, input)) != null) teamUpAccept(map);
            else if ((map = CommandHandler.matches(Command.SHOW_TEAM_UP_LIST, input)) != null) showTeamUpList(map);
            else if ((map = CommandHandler.matches(Command.LEAVE_TEAM, input)) != null) leaveTeam(map);
            else if (CommandHandler.matches(Command.NEXT_TURN, input) != null) nextTurn();
            else if (CommandHandler.matches(Command.SHOW_GOLD, input) != null) showMoney();
            else if (CommandHandler.matches(Command.EXIT, input) != null) break;
            else System.out.println("invalid command");
        }
    }

    private void nextTurn() {
        //TODO : game end ?
        if (currentPlayer != game.getKingdoms().size() - 1) {
            currentPlayer++;
            if (game.getKingdoms().get(currentPlayer).getKing().getHp() <= 0)
                nextTurn();
            System.out.println("now user " + getCurrentUser().getNickname() + "is playing");
        } else {
            game.execute();
            if (GameMenuController.checkEndGame(game)){
                System.out.println("Game ended!!!");
                System.out.println("Winners : ");
                Team winnerTeam = GameMenuController.getWinnerTeam(game)  ;
                for (int i = 0 ; i < game.getKingdoms().size() ; i++){
                    if (game.getKingdoms().get(i).getTeam().equals(winnerTeam)) {
                        System.out.println(game.getKingdoms().get(i).getUser().getNickname() +" : " + game.getKingdoms().get(i).getUser().getSlogan());
                        game.getKingdoms().get(i).getUser().addHighScore(1);
                    }
                }
                System.out.println("Losers : ");
                for (int i = 0 ; i < game.getKingdoms().size() ; i++){
                    if (!game.getKingdoms().get(i).getTeam().equals(winnerTeam)) {
                        System.out.println(game.getKingdoms().get(i).getUser().getNickname() +" : " + game.getKingdoms().get(i).getUser().getSlogan());
                        game.getKingdoms().get(i).getUser().addHighScore(-1);
                    }
                }
            }
            System.out.println("game updated");
            currentPlayer = 0;
            System.out.println("now user " + getCurrentUser().getNickname() + "is playing");
        }
    }

    private User getCurrentUser() {
        return game.getKingdoms().get(currentPlayer).getUser();
    }


    private void showMap(TreeMap<String, ArrayList<String>> map) {
        int x, y;
        try {
            x = Integer.parseInt(map.get("x").get(0));
            y = Integer.parseInt(map.get("y").get(0));
        } catch (NumberFormatException exception) {
            System.out.println("invalid position format");
            return;
        }
        System.out.print(GameMenuController.showMap(game, x, y));
    }

    private void moveMap(TreeMap<String, ArrayList<String>> map) {
        if (map.isEmpty()) {
            System.out.println("invalid command");
            return;
        }
        int l, r, u, d;
        try {
            l = map.containsKey("l") ? Integer.parseInt(map.get("l").get(0)) : 0;
            r = map.containsKey("r") ? Integer.parseInt(map.get("r").get(0)) : 0;
            u = map.containsKey("u") ? Integer.parseInt(map.get("u").get(0)) : 0;
            d = map.containsKey("d") ? Integer.parseInt(map.get("d").get(0)) : 0;
        } catch (NumberFormatException exception) {
            System.out.println("invalid movement format");
            return;
        }
        System.out.print(GameMenuController.moveMap(game, d - u, r - l));
    }

    private void showMapDetails(TreeMap<String, ArrayList<String>> map) {
        int x, y;
        try {
            x = Integer.parseInt(map.get("x").get(0));
            y = Integer.parseInt(map.get("y").get(0));
        } catch (NumberFormatException exception) {
            System.out.println("invalid position format");
            return;
        }
        System.out.print(GameMenuController.showMapDetails(game, x, y));
    }

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
        int rate = Integer.parseInt(map.get("r").get(0));
        String output = GameMenuController.setTaxRate(game, getCurrentUser(), rate);
        if (output.equals("OK"))
            return;
        System.out.println(output);
    }

    private void showTaxRate(TreeMap<String, ArrayList<String>> map) {
        String output = GameMenuController.showTaxRate(game, getCurrentUser());
        System.out.println(output);
    }

    private void setFearRate(TreeMap<String, ArrayList<String>> map) {
        int rate = Integer.parseInt(map.get("r").get(0));
        String output = GameMenuController.setFearRate(game, getCurrentUser(), rate);
        if (output.equals("OK"))
            return;
        System.out.println(output);
    }

    private void dropBuilding(TreeMap<String, ArrayList<String>> map) {
        int x = Integer.parseInt(map.get("x").get(0));
        int y = Integer.parseInt(map.get("y").get(0));
        String type = map.get("t").get(0);
        BuildingType buildingType = BuildingType.getBuildingTypeByName(type);
        if (buildingType == null) {
            System.out.println("no building called " + type + "!");
            return;
        }
        String output = GameMenuController.dropBuilding(game, getCurrentUser(), x, y, buildingType);
        if (output.equals("OK")) {
            System.out.println(type + " dropped successfully");
            return;
        }
        System.out.println(output);
    }

    private void selectBuilding(TreeMap<String, ArrayList<String>> map) {
        int x = Integer.parseInt(map.get("x").get(0));
        int y = Integer.parseInt(map.get("y").get(0));
        Building building = null;
        for (GameObject gameObject : game.getScene().getMap().getCellAt(x, y).getGameObjects()) {
            if (gameObject instanceof Building)
                building = (Building) gameObject;
        }
        if (building == null) {
            System.out.println("no Building here");
            return;
        }
        BuildingMenu buildingMenu = new BuildingMenu(scanner, game, building, getCurrentUser());
        System.out.println("entered BuildingMenu");
        buildingMenu.run();
    }

    private void selectUnit(TreeMap<String, ArrayList<String>> map) {
        int x = Integer.parseInt(map.get("x").get(0));
        int y = Integer.parseInt(map.get("y").get(0));
        if (x > game.getScene().getMap().getHeight() || x < 0 || y > game.getScene().getMap().getWidth() || y < 0)
            System.out.println("not valid cell!");
        ArrayList<Soldier> unit = new ArrayList<>();
        Kingdom kingdom = game.getKingdom(getCurrentUser());
        for (GameObject gameObject : game.getScene().getCellAt(x, y).getGameObjects()) {
            if (gameObject instanceof Soldier && gameObject.getKingdom().equals(kingdom))
                unit.add((Soldier) gameObject);
        }
        if (unit.size() == 0)
            System.out.println("no Soldier here!");
        UnitMenu unitMenu = new UnitMenu(scanner, game, unit, getCurrentUser());
        System.out.println("entered UnitMenu");
        unitMenu.run();
    }

    private void teamUpRequest(TreeMap<String, ArrayList<String>> map){
        User user = User.getUserByName(map.get("i").get(0));
        String output = GameMenuController.teamUpRequest(game , getCurrentUser() , user) ;
        System.out.println(output);
    }
    private void teamUpAccept(TreeMap<String, ArrayList<String>> map){
        int id = Integer.parseInt(map.get("i").get(0)) ;
        String output = GameMenuController.teamUpAccept(game , getCurrentUser() , id) ;
        System.out.println(output);
    }

    private void showTeamUpList(TreeMap<String, ArrayList<String>> map) {
        System.out.print(GameMenuController.showTeamUpList(game, getCurrentUser()));
    }

    private void leaveTeam(TreeMap<String, ArrayList<String>> map) {
        System.out.println(GameMenuController.leaveTeam(game, getCurrentUser()));
    }
    private void showMoney(){
        System.out.println("GOLD : " + game.getKingdoms().get(currentPlayer).getGold());
    }
}
