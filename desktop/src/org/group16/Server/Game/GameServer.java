package org.group16.Server.Game;

import org.group16.GameGraphics.CommandHandling.UserCommand;
import org.group16.Model.*;
import org.group16.Networking.LobbySocket;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GameServer extends Thread {
    public static GameServer singleton;
    private final ServerSocket serverSocket;
    private final HashMap<UUID, ServerGameRunner> games = new HashMap<>();
    private final HashMap<String, UUID> playerGames = new HashMap<>();

    public GameServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        singleton = this;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                GameConnection connection = new GameConnection(this, socket);
                connection.start();
            } catch (Exception ex) {
                System.out.println("User Disconnected");
            }
        }
    }

    public boolean gameExists(UUID gameID) {
        return games.containsKey(gameID);
    }

    private synchronized void createGame(GameInfo gameInfo) {
        PlayerList playerList = gameInfo.playerList();
        ArrayList<String> usernames = new ArrayList<>();
        for (int i = 0; i < playerList.users.size(); i++) {
            usernames.add(playerList.users.get(i).getUsername());
        }
        for (String username : usernames) {
            playerGames.put(username, gameInfo.gameID());
        }
        ServerGameRunner gameRunner = new ServerGameRunner(gameInfo, usernames);
        games.put(gameInfo.gameID(), gameRunner);
    }

    public synchronized void endGame(UUID gameId) {
        for (GameConnection connection : games.get(gameId).getPLayers()) {
            playerGames.remove(connection.getUsername());
        }
        games.remove(gameId);
    }

    public synchronized void shareCommand(UUID gameID, UserCommand command) throws IOException {
        if (!gameExists(gameID)) return;
        games.get(gameID).shareCommand(command);
    }

    public synchronized void subscribePlayer(GameInfo gameInfo, GameConnection connection) {
        if (!gameExists(gameInfo.gameID())) createGame(gameInfo);
        games.get(gameInfo.gameID()).addPlayer(connection);
    }

    public synchronized void subscribeSpectator(UUID gameId, GameConnection connection) {
        while (!games.containsKey(gameId)) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        games.get(gameId).addSpectator(connection);
    }

    public void sendAllCommands(UUID gameId, GameConnection connection) throws IOException {
        connection.sendAllCommands(games.get(gameId).getTotalUserCommands());
    }

    public UUID getPlayerGame(String username) {
        return playerGames.getOrDefault(username, null);
    }

    public ArrayList<ServerGameRunner> getGames() {
        return new ArrayList<>(games.values());
    }
}
