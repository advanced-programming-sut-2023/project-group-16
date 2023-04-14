package org.group16.Model;

import java.util.ArrayList;

public class Game {
    private final Scene scene;
    private final ArrayList<Kingdom> kingdoms = new ArrayList<>();
    private final ArrayList<Trade> tradeOffers = new ArrayList<>();
    private final ArrayList<Trade> tradeHistory = new ArrayList<>();

    public Game(Scene scene, ArrayList<User> users) {
        this.scene = scene;
        //TODO
    }

    public void execute() {
        scene.onTurnStart();
        for (int iteration = 0; iteration < 5; iteration++) {
            scene.update(1);
        }
        scene.onTurnEnd();
    }

    public Scene getScene() {
        return scene;
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

    public void solveTrade(Trade trade) {
        tradeHistory.add(trade);
        tradeOffers.remove(trade);
    }

    public Kingdom getKingdom(User user) {
        //TODO
        return null;
    }
}
