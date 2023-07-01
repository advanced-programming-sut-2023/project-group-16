package org.group16.Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameSocket {
    public static Socket socket;
    public static ObjectInputStream inputStream;
    public static ObjectOutputStream outputStream;
    public static String host;
    public static int port;

    public static void createSocket() throws IOException {
        socket = new Socket(host, port);
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
    }
}
