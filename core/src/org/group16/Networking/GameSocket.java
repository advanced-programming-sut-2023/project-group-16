package org.group16.Networking;

import org.group16.GameGraphics.CommandHandling.InputProcessor;
import org.group16.GameGraphics.CommandHandling.UserCommand;
import org.group16.Model.GameInfo;
import org.group16.Model.User;

import java.io.*;
import java.net.Socket;

public class GameSocket {
    public static Socket socket;
    public static DataOutputStream dataOutputStream;
    public static DataInputStream dataInputStream;
    //    public static ObjectInputStream inputStream;
    public static ObjectOutputStream outputStream;
    public static String host;
    public static int port;
    private static Thread connection;
    private static boolean alive;

    private static void createSocket() throws IOException {
        alive = true;
        socket = new Socket(host, port);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public static void initSocket(String host, int port) {
        GameSocket.host = host;
        GameSocket.port = port;
    }

    public static void createSocket(GameInfo gameInfo, InputProcessor inputProcessor, User user, boolean isPlayer, boolean isHost) throws IOException {
        createSocket();
        dataOutputStream.writeUTF(user.getUsername());
        if (isPlayer) {
            dataOutputStream.writeUTF("p");
            outputStream.writeObject(gameInfo);
        } else {
            dataOutputStream.writeUTF("s");
            outputStream.writeObject(gameInfo.gameID());
            dataOutputStream.writeUTF("g");
        }

        connection = new Thread(() -> {
            try {
                while (true) {
                    String cmd = dataInputStream.readUTF();
                    if (cmd.equals("c")) {
                        String stream = dataInputStream.readUTF();
                        UserCommand userCommand = UserCommand.tryDeserialize(stream);
                        System.out.printf(" Received Command %s\n", stream);
                        synchronized (inputProcessor) {
                            inputProcessor.submitCommand(userCommand);
                        }
                    } else if (cmd.equals("g")) {
                        String stream = dataInputStream.readUTF();
                        String[] cmds = stream.split("\\|");
                        for (String cd : cmds) {
                            UserCommand userCommand = UserCommand.tryDeserialize(cd);
                            System.out.printf(" Loading Command %s\n", stream);
                            synchronized (inputProcessor) {
                                inputProcessor.submitCommand(userCommand);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Client Disconnected");
                alive = false;
            }
        });
        connection.setDaemon(true);
        connection.start();
    }

    public static void submitCommand(UserCommand command) {
        try {
            Thread.sleep(20, 0);
            dataOutputStream.writeUTF("c");
            dataOutputStream.writeUTF(command.serialize());
        } catch (Exception e) {
            alive = false;
            System.out.println("Client Disconnected");
            connection.interrupt();
        }
    }

    public static boolean isAlive() {
        return alive;
    }
}
