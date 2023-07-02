package org.group16.Server.Chat;

import java.net.ServerSocket;

public class ChatServer extends Thread {
    private final ServerSocket serverSocket;

    public ChatServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
    }
}
