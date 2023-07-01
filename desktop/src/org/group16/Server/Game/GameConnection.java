package org.group16.Server.Game;

import org.group16.GameGraphics.CommandHandling.UserCommand;
import org.group16.Model.GameInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameConnection extends Thread {
    private final GameInfo gameInfo;
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private final GameServer server;

    public GameConnection(GameServer server, Socket socket) throws IOException, ClassNotFoundException {
        this.server = server;
        this.socket = socket;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        gameInfo = (GameInfo) inputStream.readObject();
    }

    @Override
    public void run() {
        try {
            while (true) {
                UserCommand obj = (UserCommand) inputStream.readObject();
                server.shareCommand(gameInfo.gameID(), obj);
            }
        } catch (Exception e) {
            System.out.println("Client Disconnected");
        }
    }

    public void sendCommand(UserCommand obj) throws IOException {
        outputStream.writeObject(obj);
    }
}
