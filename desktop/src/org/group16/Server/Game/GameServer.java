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
        try {
            while (true) {
                Socket socket = serverSocket.accept();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
