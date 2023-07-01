package org.group16.Server;

import com.google.gson.Gson;
import org.group16.Model.Messenger.Room;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;

public class ChatServer {
    private static final int port = 8080;
    private static final TreeMap<String, ArrayList<ChatConnection>> chats = new TreeMap<>();
    private static ServerSocket serverSocket;

    public ChatServer() {
        System.out.println("Chat Server Starting...");
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                ChatConnection chatConnection = new ChatConnection(socket);
                chatConnection.start();
            }
        } catch (IOException ignored) {
            System.out.println("Error");
        }
        System.out.println("Server Died");
    }

    public static void addConnection(Room room, ChatConnection connection) {
        System.out.println("add connection");
        String name = room.getName();
        ArrayList<ChatConnection> arr = chats.containsKey(name) ? chats.get(name) : new ArrayList<>();
        arr.add(connection);
        chats.put(name, arr);
    }

    public static void notifyMembers(Room room) {
        String data = new Gson().toJson(room);
        System.out.println("notifying members");
        for (ChatConnection connection : chats.get(room.getName())) {
            try {
                connection.getDataOutputStream().writeUTF(data);
            } catch (IOException e) {

            }
        }
    }
}
