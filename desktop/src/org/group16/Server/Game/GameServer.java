package org.group16.Server.Game;

import org.group16.GameGraphics.CommandHandling.UserCommand;

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

    private final HashMap<UUID, ArrayList<GameConnection>> games = new HashMap<>();

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

    public void subscribeConnection(UUID gameID, GameConnection gameConnection) {
        if (!gameExists(gameID)) games.put(gameID, new ArrayList<>());
        games.get(gameID).add(gameConnection);
    }

    public void shareCommand(UUID gameID, UserCommand obj) throws IOException {
        System.out.println("sharing?");
        if (!gameExists(gameID)) return;
        ArrayList<GameConnection> connections = games.get(gameID);
        System.out.printf("Sending %s to %d connections\n", obj.getClass().toString(), connections.size());
        for (GameConnection connection : connections) {
            connection.sendCommand(obj);
        }
    }
}
