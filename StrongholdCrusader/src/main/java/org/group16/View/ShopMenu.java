package org.group16.View;

import org.group16.Controller.ShopMenuController;
import org.group16.Model.Game;
import org.group16.Model.User;
import org.group16.View.Command.Command;
import org.group16.View.Command.CommandHandler;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class ShopMenu {

    private final Scanner scanner;
    private final Game game;
    private final User currentUser;

    public ShopMenu(Scanner scanner, Game game, User currentUser) {
        this.scanner = scanner;
        this.game = game;
        this.currentUser = currentUser;
    }

    public void run() {
        while (true) {
            String input = scanner.nextLine();
            TreeMap<String, ArrayList<String>> map;
            if (CommandHandler.matches(Command.SHOW_PRICE_LIST, input) != null) showPriceList();
            else if ((map = CommandHandler.matches(Command.BUY, input)) != null) buyItem(map);
            else if ((map = CommandHandler.matches(Command.SELL, input)) != null) sellItem(map);
            else if (CommandHandler.matches(Command.BACK, input) != null) {
                System.out.println("exit shop menu successfully");
                break;
            } else System.out.println("invalid command");
        }
    }


    private void showPriceList() {
        System.out.print(ShopMenuController.showPriceList());
    }

    private void buyItem(TreeMap<String, ArrayList<String>> map) {
        String itemName = map.get("i").get(0);
        int amount;
        try {
            amount = Integer.parseInt(map.get("a").get(0));
        } catch (NumberFormatException exception) {
            System.out.println("invalid amount");
            return;
        }
        System.out.println(ShopMenuController.buyItem(game.getKingdom(currentUser), itemName, amount));
    }

    private void sellItem(TreeMap<String, ArrayList<String>> map) {
        String itemName = map.get("i").get(0);
        int amount;
        try {
            amount = Integer.parseInt(map.get("a").get(0));
        } catch (NumberFormatException exception) {
            System.out.println("invalid amount");
            return;
        }
        System.out.println(ShopMenuController.sellItem(game.getKingdom(currentUser), itemName, amount));
    }

}
