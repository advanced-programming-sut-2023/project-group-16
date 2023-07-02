package org.group16.Networking;

import org.group16.GameGraphics.CommandHandling.InputProcessor;
import org.group16.GameGraphics.CommandHandling.UserCommand;
import org.group16.Model.GameInfo;

import java.io.*;
import java.net.Socket;

public class GameSocket {
    public static Socket socket;
    public static DataOutputStream dataOutputStream;
    public static DataInputStream dataInputStream;
    public static ObjectInputStream inputStream;
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
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public static void initSocket(String host, int port) {
        GameSocket.host = host;
        GameSocket.port = port;
    }

    public static void createSocket(GameInfo gameInfo, InputProcessor inputProcessor) throws IOException {
        createSocket();
        connection = new Thread(() -> {
            try {
                while (true) {
                    String cmd = dataInputStream.readUTF();
                    UserCommand userCommand = (UserCommand) inputStream.readObject();
                    System.out.printf("Received %s\n", userCommand.getClass().getSimpleName());
                    synchronized (inputProcessor) {
                        inputProcessor.submitCommand(userCommand);
                    }
                }
            } catch (Exception e) {
                System.out.println("Client Disconnected");
                alive = false;
            }
        });
        connection.start();
        outputStream.writeObject(gameInfo);
    }

    public static void submitCommand(UserCommand command) {
        try {
            dataOutputStream.writeUTF("cmd");
            outputStream.writeObject(command);
        } catch (IOException e) {
            alive = false;
            System.out.println("Client Disconnected");
            connection.interrupt();
        }
    }

    public static boolean isAlive() {
        return alive;
    }
}
