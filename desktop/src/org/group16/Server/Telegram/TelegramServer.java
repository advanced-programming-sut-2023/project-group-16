package org.group16.Server.Telegram;

import java.net.ServerSocket;

public class TelegramServer extends Thread {
    private final ServerSocket serverSocket;

    public TelegramServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
    }
}
