package org.group16.Server;

import com.google.gson.Gson;
import org.group16.Model.Messenger.Room;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;

public class ChatServer extends Thread {
    private static final int port = 8082;
    private static final TreeMap<String, ArrayList<ChatConnection>> chats = new TreeMap<>();
    private static ServerSocket serverSocket;

    public ChatServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void addConnection(Room room, ChatConnection connection) {
        System.out.println("add connection");
        String name = room.getName();
        ArrayList<ChatConnection> arr = chats.containsKey(name) ? chats.get(name) : new ArrayList<>();
        arr.add(connection);
        chats.put(name, arr);
    }

    public static void notifyMembers(Room room) {
        ChatServer.saveChanges(room);
        String data = new Gson().toJson(room);
        System.out.println("notifying members");
        for (ChatConnection connection : chats.get(room.getName())) {
            try {
                connection.getDataOutputStream().writeUTF(data);
            } catch (IOException e) {

            }
        }
    }

    public static Room getRoomByName(String name) {
        Gson gson = new Gson();
        String folderPath = new File("").getAbsolutePath() + "/Data/Rooms";
        FileReader fileReader;
        try {
            File directory = new File(folderPath);
            directory.mkdirs();
            fileReader = new FileReader(folderPath + "/" + name + ".json");
        } catch (IOException e) {
            return null;
        }
        System.out.println(fileReader);
        Room room = gson.fromJson(fileReader, Room.class);
        try {
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return room;
    }

    public static void saveChanges(Room room) {
        Gson gson = new Gson();
        String folderPath = new File("").getAbsolutePath() + "/Data/Rooms";
        FileWriter fileWriter;
        try {
            File directory = new File(folderPath);
            directory.mkdirs();
            fileWriter = new FileWriter(folderPath + "/" + room.getName() + ".json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gson.toJson(room, fileWriter);
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        System.out.println("Chat Server Starting...");
        try {
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
}
