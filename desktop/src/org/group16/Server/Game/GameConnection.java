package org.group16.Server.Game;

import org.group16.GameGraphics.CommandHandling.EndTurnCommand;
import org.group16.GameGraphics.CommandHandling.UserCommand;
import org.group16.Model.GameInfo;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class GameConnection extends Thread {
    private final String username;
    private final GameInfo gameInfo;
    private final UUID gameId;
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
        inputStream = new ObjectInputStream(socket.getInputStream());

        username = dataInputStream.readUTF();
        String type = dataInputStream.readUTF();
        if (type.equals("p")) {
            gameInfo = (GameInfo) inputStream.readObject();
            gameId = gameInfo.gameID();
            type = "Player";
            server.subscribePlayer(gameInfo, this);
        } else {
            gameInfo = null;
            gameId = (UUID) inputStream.readObject();
            type = "Spectator";
            server.subscribeSpectator(gameId, this);
        }

        System.out.printf("%s Connected as %s to Game(%s)", username, type, gameId);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String stream = dataInputStream.readUTF();
                UserCommand obj = UserCommand.tryDeserialize(stream);
                if (!(obj instanceof EndTurnCommand))
                    System.out.printf(" Received Command %s\n", obj.getClass().getSimpleName());
                server.shareCommand(gameInfo.gameID(), obj);
            }
        } catch (Exception e) {
            System.out.println("Client Disconnected");
        }
    }

    public void sendCommand(UserCommand command) throws IOException {
        dataOutputStream.writeUTF(command.serialize());
    }
}
