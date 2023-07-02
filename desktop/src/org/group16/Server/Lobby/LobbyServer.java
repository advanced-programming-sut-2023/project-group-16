package org.group16.Server.Lobby;

import org.group16.Model.GameInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LobbyServer extends Thread {
    private final ServerSocket serverSocket;
    private final List<GameInfo> runningGames = new ArrayList<>();
    private final HashMap<String, LobbyConnection> userConnections = new HashMap<>();

    public LobbyServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                LobbyConnection connection = new LobbyConnection(this, socket);

                connection.start();
            } catch (Exception e) {
                System.out.println("Client Disconnected");
            }
        }
    }

    public synchronized void submitGame(GameInfo gameInfo) throws IOException {
        ArrayList<LobbyConnection> connections = new ArrayList<>();
        for (int i = 0; i < gameInfo.playerList().users.size(); i++) {
            String username = gameInfo.playerList().users.get(i).getUsername();
            LobbyConnection connection = userConnections.get(username);
            connections.add(connection);
            if (connection == null || !connection.isInGameLobby()) {
                connections.get(0).startGameFailed();
                return;
            }
        }

        for (LobbyConnection connection : connections)
            connection.startGameSuccessful(gameInfo);
    }

    public void userLogin(String username, LobbyConnection connection) {
        userConnections.put(username, connection);
    }

    public boolean userLoggedIn(String username) {
        return userConnections.containsKey(username);
    }

    public void userLogout(String username) {
        userConnections.remove(username);
    }
}
