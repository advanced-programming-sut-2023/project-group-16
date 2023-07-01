package org.group16.Server;

import java.io.*;
import java.net.Socket;

public class ClientTest {
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    public ClientTest(Socket socket) throws IOException {
        this.socket = socket;
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        dataOutputStream.writeUTF("get all users");
    }

    public static void main(String[] args) {
        try {
            new ClientTest(new Socket("localhost", 8080));
        } catch (IOException e) {
            System.out.println("Disconnected");
        }
    }
}
