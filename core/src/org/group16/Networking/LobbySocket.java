package org.group16.Networking;

import org.group16.Model.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

public class LobbySocket {
    public static Socket socket;
    public static DataOutputStream utfOutputStream;
    public static DataInputStream utfInputStream;
    public static ObjectInputStream inputStream;
    public static ObjectOutputStream outputStream;

    public static void createSocket(String host, int port) throws IOException {
        socket = new Socket(host, port);
        utfOutputStream = new DataOutputStream(socket.getOutputStream());
        utfInputStream = new DataInputStream(socket.getInputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public static User getUser(String username) throws IOException, ClassNotFoundException {
        if (username.length() == 0) return null;

        utfOutputStream.writeUTF(
                String.format("get user -u \"%s\"", username));
        return ((UserList) inputStream.readObject()).users.get(0);
    }

    public static ArrayList<User> getAllUsers() throws IOException, ClassNotFoundException {
        utfOutputStream.writeUTF("get all users");
        return ((UserList) inputStream.readObject()).users;
    }

    public static String login(String username, String password) throws IOException {
        utfOutputStream.writeUTF(
                String.format("login -u \"%s\" -p \"%s\"", username, password));
        return utfInputStream.readUTF();
    }

    public static String register(String username, String password, String nick, String email, String slogan, String question, String answer) throws IOException {
        utfOutputStream.writeUTF(
                String.format("register -u \"%s\" -p \"%s\" -s \"%s\" -n \"%s\" -e \"%s\" -q \"%s\" \"%s\"",
                        username, password, slogan, nick, email, question, answer));
        return utfInputStream.readUTF();
    }

    public static String forgotPassword(String username, String answer, String newPassword) throws IOException {
        utfOutputStream.writeUTF(
                String.format("forgot password -u \"%s\" -p \"%s\" -a \"%s\"",
                        username, newPassword, answer));
        return utfInputStream.readUTF();
    }

    public static String logout() throws IOException {
        utfOutputStream.writeUTF("logout");
        return utfInputStream.readUTF();
    }

    public static String changeProfile(User user, String username, String password, String nick, String email, String slogan) throws IOException {
        utfOutputStream.writeUTF(
                String.format("change profile -u \"%s\" -p \"%s\" -s \"%s\" -n \"%s\" -e \"%s\"",
                        username, password, slogan, nick, email));
        String response = utfInputStream.readUTF();
        if (response.equals("OK"))
            user.updateLocal(username, password, email, slogan, nick);
        return response;
    }

    public static int displayScore() throws IOException {
        utfOutputStream.writeUTF("display score");
        return inputStream.readInt();
    }

    public static int displayRank() throws IOException {
        utfOutputStream.writeUTF("display rank");
        return inputStream.readInt();
    }

    public static void uploadMap(Map map) throws IOException {
        utfOutputStream.writeUTF("upload map");
        outputStream.writeObject(map);
    }

    public static Map downloadMap(String mapname) throws IOException, ClassNotFoundException {
        utfOutputStream.writeUTF(
                String.format("download map -m \"%s\"", mapname));
        return (Map) inputStream.readObject();
    }

    public static ArrayList<String> getAllMaps() throws IOException, ClassNotFoundException {
        utfOutputStream.writeUTF("get all maps");
        return ((StringList) inputStream.readObject()).strings;
    }

    public static String createGame() throws IOException {
        utfOutputStream.writeUTF("create game");
        return utfInputStream.readUTF();
    }

    public static String selectMap(String mapname) throws IOException {
        utfOutputStream.writeUTF(
                String.format("select map -m \"%s\"", mapname));
        return utfInputStream.readUTF();
    }

    public static String addUser(String username, String kingdomType) throws IOException {
        utfOutputStream.writeUTF(
                String.format("add user -u \"%s\" -t \"%s\"", username, kingdomType));
        return utfInputStream.readUTF();
    }

    public static String removeUser(String username, String kingdomType) throws IOException {
        utfOutputStream.writeUTF(
                String.format("remove user -u \"%s\"", username, kingdomType));
        return utfInputStream.readUTF();
    }

    public static GameInfo joinGameLobby() throws IOException, ClassNotFoundException {
        System.out.println("JOIN");
        utfOutputStream.writeUTF("join game lobby");
        System.out.println(utfInputStream.readUTF()); //OK
        System.out.println(utfInputStream.readUTF()); //START GAME
        return (GameInfo) inputStream.readObject();
    }

    //    public static String leaveGameLobby() throws IOException {
//        cmdStream.writeUTF("leave game lobby");
//        return utfInputStream.readUTF()();
//    }
    public static Object startGame() throws IOException, ClassNotFoundException {
        utfOutputStream.writeUTF("start game");
        String response = utfInputStream.readUTF();
        if (!response.equals("START GAME")) return response;
        return (GameInfo) inputStream.readObject();
    }

    public static GameInfoList getRunningGames() throws IOException, ClassNotFoundException {
        utfOutputStream.writeUTF("get running games");
        return (GameInfoList) inputStream.readObject();
    }

    public static UUID getPlayerGame(String username) throws IOException, ClassNotFoundException {
        utfOutputStream.writeUTF(
                String.format("get player game -u \"%s\"", username));
        return (UUID) inputStream.readObject();
    }

    public static String endGame(UUID gameId) throws IOException {
        utfOutputStream.writeUTF("end game");
        outputStream.writeObject(gameId);
        return utfInputStream.readUTF();
    }

    public static String addScore(String username, int value) throws IOException {
        utfOutputStream.writeUTF(
                String.format("add score -u \"%s\" -v \"%d\"", username, value));
        return utfInputStream.readUTF();
    }

    public static boolean isOnline(String username) throws IOException {
        utfOutputStream.writeUTF(
                String.format("is online -u \"%s\"", username));
        return utfInputStream.readBoolean();
    }
}
