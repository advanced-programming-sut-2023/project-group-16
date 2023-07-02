package org.group16.Server.Game;

import org.group16.GameGraphics.CommandHandling.UserCommand;
import org.group16.Model.GameInfo;

import java.io.*;
import java.net.Socket;

public class GameConnection extends Thread {
    private final GameInfo gameInfo;
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private final ObjectInputStream inputStream;
    //    private final ObjectOutputStream outputStream;
    private final GameServer server;

    public GameConnection(GameServer server, Socket socket) throws IOException, ClassNotFoundException {
        this.server = server;
        this.socket = socket;
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataInputStream = new DataInputStream(socket.getInputStream());
//        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        gameInfo = (GameInfo) inputStream.readObject();
        System.out.printf("gameId : %s", gameInfo.gameID().toString());
        server.subscribeConnection(gameInfo.gameID(), this);
    }

    @Override
    public void run() {
        try {
            while (true) {
                UserCommand obj = UserCommand.tryDeserialize(dataInputStream.readUTF());
                System.out.printf(" Received Command %s\n", obj);
                server.shareCommand(gameInfo.gameID(), obj);
            }
        } catch (Exception e) {
            System.out.println("Client Disconnected");
        }
    }

    public void sendCommand(UserCommand obj) throws IOException {
        dataOutputStream.writeUTF(obj.serialize());
    }
}
