package org.group16.Networking;

import org.group16.StrongholdGame;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LobbySocket {
    public static Socket socket;
    public static DataOutputStream cmdStream;
    public static ObjectInputStream inputStream;
    public static ObjectOutputStream outputStream;

    public static void createSocket(String host, int port) throws IOException {
        socket = new Socket(host, port);
        cmdStream = new DataOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
    }
}
