package org.group16.Server;

import org.group16.Server.Lobby.LobbyServer;

import java.net.ServerSocket;

public class Server {
    private Server(int lobbyPort, int gamePort, int telegramPort) {
        System.out.println("Starting Server...");
        try {
            LobbyServer lobbyServer = new LobbyServer(new ServerSocket(lobbyPort));
//            GameServer gameServer = new GameServer(new ServerSocket(gamePort));
            ChatServer chatServer = new ChatServer(new ServerSocket(telegramPort)); //Server(new ServerSocket(telegramPort));

//            gameServer.start();
            chatServer.start();
            lobbyServer.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        GameServer gameServer = new GameServer(8081);
//        gameServer.start();
    }

    public static void main(String[] args) {
        new Server(8080, 8081, 8082);
    }
}
