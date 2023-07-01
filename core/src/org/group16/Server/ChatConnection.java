package org.group16.Server;

import com.google.gson.Gson;
import org.group16.Model.Messenger.Room;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatConnection extends Thread {
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    public ChatConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public synchronized void run() {
        try {
            ChatServer.addConnection(getRoom(), this);
            while (true) {
                Room room = getRoom();
                ChatServer.notifyMembers(room);
            }
        } catch (IOException e) {

        }
    }

    private Room getRoom() throws IOException {
        String data = dataInputStream.readUTF();
        System.out.println("read");
        Room room = new Gson().fromJson(data, Room.class);
        return room;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }
}
