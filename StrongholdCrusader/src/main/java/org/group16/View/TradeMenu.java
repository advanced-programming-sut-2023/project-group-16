package org.group16.View;

import org.group16.Controller.TradeMenuController;
import org.group16.Model.Game;
import org.group16.Model.Resources.BasicResource;
import org.group16.Model.Resources.Food;
import org.group16.Model.Resources.Resource;
import org.group16.Model.Resources.Weaponry;
import org.group16.Model.User;
import org.group16.View.Command.Command;
import org.group16.View.Command.CommandHandler;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class TradeMenu {

    private final Scanner scanner;
    private final Game game;
    private final User currentUser;

    public TradeMenu(Scanner scanner, Game game, User currentUser) {
        this.scanner = scanner;
        this.game = game;
        this.currentUser = currentUser;
    }

    private static Resource getResourceByName(String resourceType) {
        resourceType = resourceType.toUpperCase();
        Resource resource;
        try {
            resource = BasicResource.valueOf(resourceType);
        } catch (IllegalArgumentException e1) {
            try {
                resource = Food.valueOf(resourceType);
            } catch (IllegalArgumentException e2) {
                try {
                    resource = Weaponry.valueOf(resourceType);
                } catch (IllegalArgumentException e3) {
                    return null;
                }
            }
        }
        return resource;
    }

    public void run() {
        while (true) {
            String input = scanner.nextLine();
            TreeMap<String, ArrayList<String>> map;
            if (CommandHandler.matches(Command.SHOW_TRADE_LIST, input) != null) showTradeList();
            else if (CommandHandler.matches(Command.SHOW_TRADE_HISTORY, input) != null) tradeHistory();
            else if ((map = CommandHandler.matches(Command.TRADE_REQUEST, input)) != null) tradeRequest(map);
            else if ((map = CommandHandler.matches(Command.TRADE_ACCEPT, input)) != null) tradeAccept(map);
            else if (CommandHandler.matches(Command.BACK, input) != null) {
                System.out.println("exit trade menu successfully");
                break;
            } else System.out.println("invalid command");
        }
    }

    private void tradeRequest(TreeMap<String, ArrayList<String>> map) {
        String resourceType = map.get("t").get(0);
        Resource resource = getResourceByName(resourceType);
        int amount;
        try {
            amount = Integer.parseInt(map.get("a").get(0));
        } catch (NumberFormatException exception) {
            System.out.println("invalid amount");
            return;
        }
        int price;
        try {
            price = Integer.parseInt(map.get("p").get(0));
        } catch (NumberFormatException exception) {
            System.out.println("invalid price");
            return;
        }
        String message = map.get("m").get(0);
        System.out.println(TradeMenuController.tradeRequest(game, currentUser, resource, amount, price, message));
    }

    private void showTradeList() {
        System.out.print(TradeMenuController.showTradeList(game, currentUser));
    }

    private void tradeAccept(TreeMap<String, ArrayList<String>> map) {
        int id;
        try {
            id = Integer.parseInt(map.get("i").get(0));
        } catch (NumberFormatException exception) {
            System.out.println("invalid id");
            return;
        }
        String message = map.get("m").get(0);
        System.out.println(TradeMenuController.tradeAccept(game, currentUser, id, message));
    }//TODO

    private void tradeHistory() {
        System.out.print(TradeMenuController.tradeHistory(game, currentUser));
    }
}
