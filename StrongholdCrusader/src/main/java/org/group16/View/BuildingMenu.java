package org.group16.View;

import org.group16.Controller.BuildingMenuController;
import org.group16.Controller.GameMenuController;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Buildings.EconomicBuilding;
import org.group16.Model.Buildings.EconomicBuildingDetail;
import org.group16.Model.Buildings.MilitaryBuilding;
import org.group16.Model.Game;
import org.group16.Model.People.SoldierDetail;
import org.group16.Model.People.SoldierType;
import org.group16.Model.User;
import org.group16.View.Command.Command;
import org.group16.View.Command.CommandHandler;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;

public class BuildingMenu {
    private final Scanner scanner;
    private final Game game;
    private final Building building;
    private final User currentUser;

    public BuildingMenu(Scanner scanner, Game game, Building building, User currentUser) {
        this.scanner = scanner;
        this.game = game;
        this.building = building;
        this.currentUser = currentUser;
    }

    public void run() {
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            TreeMap<String, ArrayList<String>> map;
            if ((map = CommandHandler.matches(Command.CREATE_UNIT, input)) != null) createUnit(map);
            else if ((map = CommandHandler.matches(Command.REPAIR, input)) != null) repair(map);
            else if ((map = CommandHandler.matches(Command.ENTER_SHOP_MENU, input)) != null) enterShopMenu(map);
            else if (CommandHandler.matches(Command.EXIT, input) != null) {
                System.out.println("exit building menu successfully");
                break;
            }
            else
                System.out.println("invalid command");
        }
    }

    private void createUnit(TreeMap<String, ArrayList<String>> map) {
        String type = map.get("t").get(0) ;
        int count = Integer.parseInt(map.get("c").get(0)) ;
        if (!(building instanceof MilitaryBuilding)){
            System.out.println("invalid command! : can not make soldiers here");
            return;
        }
        SoldierDetail soldierDetail = SoldierDetail.getSoldierDetailByName(type) ;
        if (soldierDetail == null) {
            System.out.println("no soldier with this type !");
            return;
        }
        String output = BuildingMenuController.createUnit((MilitaryBuilding) building ,soldierDetail , count) ;
        if (output.equals("OK")){
            return;
        }
        System.out.println(output);
    }

    private void repair(TreeMap<String, ArrayList<String>> map) {
        String output = BuildingMenuController.repair(building) ;
        if (output.equals("OK"))
            return;
        System.out.println(output);
    }
    private void enterShopMenu(TreeMap<String, ArrayList<String>> map){
        if (building instanceof EconomicBuilding && ((EconomicBuilding) building).getDetail().equals(EconomicBuildingDetail.MARKET)){
            ShopMenu shopMenu = new ShopMenu(scanner , game , currentUser) ;
            shopMenu.run();
        }
    }
}
