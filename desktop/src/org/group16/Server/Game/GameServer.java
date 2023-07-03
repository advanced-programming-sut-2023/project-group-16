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
    private final ServerSocket serverSocket;

    private final HashMap<UUID, ServerGameRunner> games = new HashMap<>();

    public GameServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
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
        Map map = Map.getMapByName(gameInfo.mapname());
        PlayerList playerList = gameInfo.playerList();
        long random = gameInfo.randomSeed();
        Scene scene = new Scene(map, random);
        Game game = new Game();
        ArrayList<String> usernames = new ArrayList<>();

        for (int i = 0; i < playerList.users.size(); i++) {
            usernames.add(playerList.users.get(i).getUsername());
            game.addUser(playerList.users.get(i), playerList.kingdomTypes.get(i));
        }

        game.setScene(scene);


        ServerGameRunner gameRunner = new ServerGameRunner(game, usernames);
        games.put(gameInfo.gameID(), gameRunner);
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
}
