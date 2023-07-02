package org.group16.Networking;

import org.group16.Model.Map;
import org.group16.Model.StringList;
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
import java.util.List;

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
                String.format("login -u \"%s\" -p \"%s\"", username, password));
        return inputStream.readUTF();
    }

    public static String register(String username, String password, String nick, String email, String slogan, String question, String answer) throws IOException {
        cmdStream.writeUTF(
                String.format("register -u \"%s\" -p \"%s\" -s \"%s\" -n \"%s\" -e \"%s\" -q \"%s\" \"%s\"",
                        username, password, slogan, nick, email, question, answer));
        return inputStream.readUTF();
    }

    public static String forgotPassword(String username, String answer, String newPassword) throws IOException {
        cmdStream.writeUTF(
                String.format("forgot password -u \"%s\" -p \"%s\" -a \"%s\"",
                        username, newPassword, answer));
        return inputStream.readUTF();
    }

    public static String logout() throws IOException {
        cmdStream.writeUTF("logout");
        return inputStream.readUTF();
    }

    public static String changeProfile(String username, String password, String nick, String email, String slogan) throws IOException {
        cmdStream.writeUTF(
                String.format("change profile -u \"%s\" -p \"%s\" -s \"%s\" -n \"%s\" -e \"%s\"",
                        username, password, slogan, nick, email));
        return inputStream.readUTF();
    }

    public static int displayScore() throws IOException {
        cmdStream.writeUTF("display score");
        return inputStream.readInt();
    }

    public static int displayRank() throws IOException {
        cmdStream.writeUTF("display rank");
        return inputStream.readInt();
    }

    public static void uploadMap(Map map) throws IOException {
        cmdStream.writeUTF("upload map");
        outputStream.writeObject(map);
    }

    public static Map downloadMap(String mapname) throws IOException, ClassNotFoundException {
        cmdStream.writeUTF("download map");
        return (Map) inputStream.readObject();
    }

    public static ArrayList<String> getAllMaps() throws IOException, ClassNotFoundException {
        cmdStream.writeUTF("get all maps");
        return ((StringList) inputStream.readObject()).strings;
    }

    public static String createGame(String kingdomType) throws IOException {
        cmdStream.writeUTF(
                String.format("create game -t \"%s\"", kingdomType));
        return inputStream.readUTF();
    }

    public static String addUser(String username, String kingdomType) throws IOException {
        cmdStream.writeUTF(
                String.format("add user -u \"%s\" -t \"%s\"", username, kingdomType));
        return inputStream.readUTF();
    }

    public static String removeUser(String username, String kingdomType) throws IOException {
        cmdStream.writeUTF(
                String.format("remove user -u \"%s\"", username, kingdomType));
        return inputStream.readUTF();
    }

    public static String joinGameLobby() throws IOException {
        cmdStream.writeUTF("join game lobby");
        return inputStream.readUTF();
    }

    public static String leaveGameLobby() throws IOException {
        cmdStream.writeUTF("leave game lobby");
        return inputStream.readUTF();
    }
}
