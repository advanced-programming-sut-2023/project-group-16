package org.group16.Controller;

import org.group16.Model.Game;
import org.group16.Model.Kingdom;
import org.group16.Model.Resources.BasicResource;
import org.group16.Model.Resources.Resource;
import org.group16.Model.Trade;
import org.group16.Model.User;

import java.util.ArrayList;

public class TradeMenuController {
    public static String tradeRequest(Game game, User currentUser, Resource resource, int amount, int price, String massage) {
        if (price < 0) return "price can't be negative";
        if (amount < 0) return "amount should be positive";
        if (resource == null) return "invalid resource type";
        Kingdom kingdom = game.getKingdom(currentUser);
        if (kingdom.getResourceCount(BasicResource.GOLD) < amount*price) return "not enough money";

        Trade trade = new Trade(resource, kingdom, price, amount, massage);
        game.addTrade(trade);
        return "trade request made successfully";
    }

    public static String showTradeList(Game game, User currentUser) {
        ArrayList<Trade> trades = game.getTradeOffers();
        StringBuilder output = new StringBuilder();
        for (Trade trade : trades)
            output.append(trade);
        if (output.length() == 0) return "no trades available\n";
        return output.toString();
    }

    public static String tradeAccept(Game game, User currentUser, int id, String massage) {
        Trade trade = game.getTradeById(id);
        trade.setSeller(game.getKingdom(currentUser));
        if (trade == null) return "no trade with this id exist";
        return game.completeTrade(trade, massage);
    }

    public static String tradeHistory(Game game, User currentUser) {
        ArrayList<Trade> trades = game.getUserTrades(currentUser);
        String output = "";
        for (Trade trade : trades)
            output += trade;
        if (output.isEmpty()) return "no trade history available";
        return output;
    }
}
