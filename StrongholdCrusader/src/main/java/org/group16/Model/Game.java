package org.group16.Model;

import java.util.ArrayList;

public class Game {
    private static final int UPDATE_ITERATION_COUNT = 10;
    private final Scene scene;
    private final ArrayList<Kingdom> kingdoms = new ArrayList<>();
    private final ArrayList<Trade> tradeOffers = new ArrayList<>();
    private final ArrayList<Trade> tradeHistory = new ArrayList<>();

    public Game(Scene scene, ArrayList<User> users) {
        this.scene = scene;
        //TODO
    }

    public Game() {
        this.scene = new Scene(new Map());
    }

    public void execute() {
        for (var kingdom : kingdoms)
            kingdom.onTurnStart();
        scene.onTurnStart();
        for (int iteration = 0; iteration < UPDATE_ITERATION_COUNT; iteration++) {
            for (var kingdom : kingdoms)
                kingdom.update(1.0 / UPDATE_ITERATION_COUNT);
            scene.update(1.0 / UPDATE_ITERATION_COUNT);
        }
        scene.onTurnEnd();
        for (var kingdom : kingdoms)
            kingdom.onTurnEnd();
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

    public void completeTrade(Trade trade) {
        tradeHistory.add(trade);
        tradeOffers.remove(trade);
    }

    public ArrayList<Trade> getUserTrades(User user) {
        //TODO
        return null;
    }

    public Kingdom getKingdom(User user) {
        for (Kingdom kingdom : getKingdoms())
            if (kingdom.getUser().equals(user))
                return kingdom;
        return null;
    }
}
