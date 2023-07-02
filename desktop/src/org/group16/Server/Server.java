package org.group16.Server;

import org.group16.Server.Game.GameServer;
import org.group16.Server.Lobby.LobbyServer;

import java.net.ServerSocket;

public class Server {
    public static final int lobbyPort = 8080;
    public static final int gamePort = 8081;
    public static final int chatPort = 8082;
    public static final String host = "localhost";

    private Server(int lobbyPort, int gamePort, int chatPort) {
        System.out.println("Starting Server...");
        try {
            LobbyServer lobbyServer = new LobbyServer(new ServerSocket(lobbyPort));
            GameServer gameServer = new GameServer(new ServerSocket(gamePort));
            ChatServer chatServer = new ChatServer(new ServerSocket(chatPort)); //Server(new ServerSocket(telegramPort));

            gameServer.start();
            chatServer.start();
            lobbyServer.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        GameServer gameServer = new GameServer(8081);
//        gameServer.start();
    }

    public static void main(String[] args) {
        new Server(lobbyPort, gamePort, chatPort);
    }
}
