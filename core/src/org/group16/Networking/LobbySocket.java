package org.group16.Networking;

import org.group16.Model.Map;
import org.group16.Model.User;
import org.group16.Model.UserList;
import org.group16.StrongholdGame;

import java.awt.desktop.UserSessionEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

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

    public static User getUser(String username) throws IOException, ClassNotFoundException {
        cmdStream.writeUTF(
                String.format("get user -u \"%s\"", username));
        return (User) inputStream.readObject();
    }

    public static ArrayList<User> getAllUsers() throws IOException, ClassNotFoundException {
        cmdStream.writeUTF("get all users");
        return ((UserList) inputStream.readObject()).users;
    }

    public static String login(String username, String password) throws IOException {
        cmdStream.writeUTF(
                String.format("get user -u \"%s\" -p \"%s\"", username, password));
        return inputStream.readUTF();
    }

    public static String register(String username, String password, String nick, String email, String slogan, String question, String answer) {
        
    }

    public static String forgotPassword(String username, String answer, String newPassword) {

    }

    public static String logout() {

    }

    public static String changeProfile(String username, String password, String nick, String email, String slogan) {

    }

    public static int displayScore() {

    }

    public static int displayRank() {

    }

    public static void uploadMap(Map map) {

    }

    public static Map downloadMap(String mapname) {

    }
}
