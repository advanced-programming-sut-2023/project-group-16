package org.group16.Server;

import org.group16.GameGraphics.CommandHandling.CreateBuildingCommand;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.Map;
import org.group16.Model.StringList;
import org.group16.Model.User;
import org.group16.Model.UserList;

import java.io.*;
import java.net.Socket;

public class ClientTest {
    private final Socket socket;
    private final DataOutputStream cmdStream;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public ClientTest(Socket socket) throws IOException, ClassNotFoundException {
        this.socket = socket;
        cmdStream = new DataOutputStream(socket.getOutputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());

        cmdStream.writeUTF("get all maps");
        StringList list = (StringList) inputStream.readObject();
        cmdStream.writeUTF(
                String.format("download map -m \"%s\"", list.strings.get(0)));
        Map mp = (Map) inputStream.readObject();
        System.out.println(mp.getName());
    }

    public static void main(String[] args) {
        try {
            new ClientTest(new Socket("localhost", 8080));
        } catch (IOException e) {
            System.out.println("Disconnected");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

