package org.group16.View;

import org.group16.Controller.UnitMenuController;
import org.group16.Model.Game;
import org.group16.Model.People.Soldier;
import org.group16.Model.People.SoldierDetail;
import org.group16.Model.Siege.SiegeDetail;
import org.group16.Model.User;
import org.group16.Model.WarCommand;
import org.group16.View.Command.Command;
import org.group16.View.Command.CommandHandler;
import org.ietf.jgss.GSSManager;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;

public class UnitMenu {
    private final Scanner scanner;
    private final Game game;
    private final ArrayList<Soldier> unit;
    private final User currentUser;

    public UnitMenu(Scanner scanner, Game game, ArrayList<Soldier> unit, User currentUser) {
        this.scanner = scanner;
        this.game = game;
        this.unit = unit;
        this.currentUser = currentUser;
    }

    public void run() {
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            TreeMap<String, ArrayList<String>> map;
            if ((map = CommandHandler.matches(Command.PATROL_UNIT, input)) != null) patrolUnit(map);
            else if ((map = CommandHandler.matches(Command.MOVE_UNIT, input)) != null) moveUnit(map);
            else if ((map = CommandHandler.matches(Command.SET_STATE, input)) != null) setStats(map);
            else if ((map = CommandHandler.matches(Command.ATTACK, input)) != null) attackEnemy(map);
            else if ((map = CommandHandler.matches(Command.DIG_TUNNEL, input)) != null) digTunnel(map);
            else if ((map = CommandHandler.matches(Command.BUILD, input)) != null) buildEquipment(map);
            else if ((map = CommandHandler.matches(Command.DISBAND_UNIT, input)) != null) disbandUnit(map);
            else if ((map = CommandHandler.matches(Command.FILTER_UNIT, input)) != null) filterUnit(map);
            else if ((map = CommandHandler.matches(Command.FILTER_SUBTRACTIVE_UNIT, input)) != null)
                filterSubtractiveUnit(map);
            else if ((map = CommandHandler.matches(Command.EXIT, input)) != null) {
                System.out.println("closed unit menu successfully");
                return;
            } else System.out.println("invalid command");

            if (unit.size() == 0) {
                System.out.println("unit size is 0 !");
                return;
            }
        }
    }

    public void moveUnit(TreeMap<String, ArrayList<String>> map) {
        int x = Integer.parseInt(map.get("x").get(0));
        int y = Integer.parseInt(map.get("y").get(0));
        String output = UnitMenuController.moveUnit(game, unit, x, y);
        System.out.println(output);
    }

    private void patrolUnit(TreeMap<String, ArrayList<String>> map) {
        int x1 = Integer.parseInt(map.get("x1").get(0));
        int y1 = Integer.parseInt(map.get("y1").get(0));
        int x2 = Integer.parseInt(map.get("x2").get(0));
        int y2 = Integer.parseInt(map.get("y2").get(0));
        String output = UnitMenuController.patrolUnit(game, unit, x1, y1, x2, y2);
        System.out.println(output);
    }

    private void setStats(TreeMap<String, ArrayList<String>> map) {
        int x = Integer.parseInt(map.get("x").get(0));
        int y = Integer.parseInt(map.get("y").get(0));
        String stats = map.get("s").get(0);
        WarCommand.Status status;
        switch (stats) {
            case "standing" -> {
                status = WarCommand.Status.STAND_STILL;
            }
            case "defensive" -> {
                status = WarCommand.Status.DEFENSIVE;
            }
            case "offensive" -> {
                status = WarCommand.Status.OFFENSIVE;
            }
            default -> {
                System.out.println("not valid state");
                return;
            }
        }
        String output = UnitMenuController.setStats(game, unit, status);
        System.out.println(output);
    }

    private void attackEnemy(TreeMap<String, ArrayList<String>> map) {
        int x = Integer.parseInt(map.get("x").get(0));
        int y = Integer.parseInt(map.get("y").get(0));
        String output = UnitMenuController.attackEnemy(game, unit, x, y);
        System.out.println(output);
    }

    private void digTunnel(TreeMap<String, ArrayList<String>> map) {
    }

    private void filterUnit(TreeMap<String, ArrayList<String>> map) {
        String type = map.get("t").get(0);
        SoldierDetail soldierDetail = SoldierDetail.getSoldierDetailByName(type);
        SiegeDetail siegeDetail = SiegeDetail.getSiegeDetailByName(type);
        String output = null;
        if (soldierDetail != null)
            output = UnitMenuController.filter(game, unit, soldierDetail);
        else if (siegeDetail != null)
            output = UnitMenuController.filter(game, unit, siegeDetail);
        else
            System.out.println("unit name is not valid");
        System.out.println(output);
    }

    private void filterSubtractiveUnit(TreeMap<String, ArrayList<String>> map) {
        String type = map.get("t").get(0);
        SoldierDetail soldierDetail = SoldierDetail.getSoldierDetailByName(type);
        SiegeDetail siegeDetail = SiegeDetail.getSiegeDetailByName(type);
        String output = null;
        if (soldierDetail != null)
            output = UnitMenuController.filterSubtractive(game, unit, soldierDetail);
        else if (siegeDetail != null)
            output = UnitMenuController.filterSubtractive(game, unit, siegeDetail);
        else
            System.out.println("unit name is not valid");
        System.out.println(output);
    }

    private void buildEquipment(TreeMap<String, ArrayList<String>> map) {
        int x = Integer.parseInt(map.get("x").get(0));
        int y = Integer.parseInt(map.get("y").get(0));
        String type = map.get("q").get(0);
        SiegeDetail siegeDetail = SiegeDetail.getSiegeDetailByName(type);
        if (siegeDetail == null) {
            System.out.println("not valid siege name");
            return;
        }
        String output = UnitMenuController.buildEquipment(game, unit, x, y, siegeDetail);
        System.out.println(output);
    }

    private void disbandUnit(TreeMap<String, ArrayList<String>> map) {
        String output = UnitMenuController.disbandUnit(game, unit);
        System.out.println(output);
    }
}
