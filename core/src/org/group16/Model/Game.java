package org.group16.Model;

import java.util.ArrayList;

public class Game {
    private final ArrayList<Kingdom> kingdoms = new ArrayList<>();
    private final ArrayList<Trade> tradeOffers = new ArrayList<>();
    private final ArrayList<Trade> tradeHistory = new ArrayList<>();
    private final ArrayList<TeamUp> teamUpOffers = new ArrayList<>();
    private Scene scene;
    private double currentTime = 0.0;

    public Game(KingdomType kingdomType, User user) {
        kingdoms.add(new Kingdom(kingdomType, user));
    }

    public void update() {
        for (var kingdom : kingdoms)
            kingdom.update(currentTime);
        scene.update(currentTime);
        currentTime += Time.deltaTime;
    }

    public void onTurnStart() {
        for (var kingdom : kingdoms)
            kingdom.onTurnStart();
        scene.onTurnStart();
    }

    public void onTurnEnd() {
        scene.onTurnEnd();
        for (var kingdom : kingdoms)
            kingdom.onTurnEnd();
    }


    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public ArrayList<Kingdom> getKingdoms() {
        return kingdoms;
    }

    public ArrayList<Trade> getTradeOffers() {
        return tradeOffers;
    }

    public ArrayList<Trade> getTradeHistory() {
        return tradeHistory;
    }

    public void addTrade(Trade trade) {
        tradeOffers.add(trade);
    }

    public void addTeamUp(TeamUp teamUp) {
        teamUpOffers.add(teamUp);
    }

    public String completeTrade(Trade trade, String buyerMessage) {
        if (!trade.getBuyer().addResource(trade.getResource(), trade.getAmount()))
            return "insufficient storage capacity";
        trade.getSeller().useResource(trade.getResource(), trade.getAmount());
        trade.setBuyerMessage(buyerMessage);
        tradeHistory.add(trade);
        tradeOffers.remove(trade);
        return "trade made successfully";
    }

    public TeamUp getTeamUpById(int id) {
        for (TeamUp teamUp : teamUpOffers)
            if (teamUp.getId() == id) return teamUp;
        return null;
    }

    public void completeTeamUp(TeamUp teamUp) {
        teamUp.getTo().setTeam(teamUp.getFrom().getTeam());
        teamUpOffers.remove(teamUp);
    }

    public ArrayList<TeamUp> getUserTeamUpOffers(User user) {
        ArrayList<TeamUp> teamUps = new ArrayList<>();
        for (TeamUp teamUp : teamUpOffers) if (teamUp.getTo().getUser() == user) teamUps.add(teamUp);
        return teamUps;
    }

    public ArrayList<Trade> getUserTrades(User user) {
        ArrayList<Trade> trades = new ArrayList<>();
        for (Trade trade : getTradeHistory())
            if (trade.getSeller().equals(user) || trade.getBuyer().equals(user)) trades.add(trade);
        for (Trade trade : getTradeOffers())
            if (trade.getSeller().equals(user)) trades.add(trade);
        return trades;
    }

    public Trade getTradeById(int id) {
        for (Trade trade : tradeOffers)
            if (trade.getId() == id) return trade;
        return null;
    }

    public Kingdom getKingdom(User user) {
        for (Kingdom kingdom : kingdoms)
            if (kingdom.getUser().equals(user)) return kingdom;
        return null;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void addUser(User user, KingdomType kingdomType) {
        kingdoms.add(new Kingdom(kingdomType, user));
    }

    public void removeUser(User user) {
        kingdoms.remove(getKingdom(user));
    }

    public User getUserByUsername(String username) {
        for (Kingdom kingdom : kingdoms) {
            if (kingdom.getUser().getUsername().equals(username)) return kingdom.getUser();
        }
        return null;
    }
}
