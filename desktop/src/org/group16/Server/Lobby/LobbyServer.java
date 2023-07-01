package org.group16.Server.Lobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LobbyServer extends Thread {
    private final ServerSocket serverSocket;

    public LobbyServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                LobbyConnection connection = new LobbyConnection(socket);
                connection.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
