package org.group16.Server.Game;

import java.net.ServerSocket;
import java.net.Socket;

public class GameServer extends Thread {
    private final ServerSocket serverSocket;

    public GameServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
            } catch (Exception ex) {
                System.out.println("User Disconnected");
            }
        }
    }
}
