package org.group16.Client;

import com.google.gson.Gson;
import org.group16.Model.Messenger.Room;
import org.group16.View.Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClient {
    private static final String host = "localhost";
    private static final int port = 8080;

    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    public ChatClient(Chat chat) throws IOException {
        this.socket = new Socket(host, port);
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());

        notifyServer(chat.getRoom());

        new Thread(() -> {
            while (true) {
                try {
                    String data = dataInputStream.readUTF();
                    Room newRoom = new Gson().fromJson(data, Room.class);
                    chat.setRoom(newRoom);
                } catch (IOException e) {

                }
            }
        }).start();
    }

    public void notifyServer(Room room) throws IOException {
        System.out.println("notify server");
        dataOutputStream.writeUTF(new Gson().toJson(room));
    }
}
