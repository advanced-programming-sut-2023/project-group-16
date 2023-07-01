package org.group16.Server.Lobby;

import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.google.common.hash.Hashing;
import org.group16.Model.User;
import org.group16.Server.Lobby.Command.Command;
import org.group16.Server.Lobby.Command.CommandHandler;

import java.io.*;
import java.net.Socket;
import java.nio.charset.CoderMalfunctionError;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.regex.Matcher;

public class LobbyConnection extends Thread {
    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    private User currentUser;

    public LobbyConnection(Socket socket) throws IOException {
        this.socket = socket;
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = inputStream.readUTF();
                TreeMap<String, ArrayList<String>> map;
                if ((map = CommandHandler.matches(Command.REGISTER, msg)) != null) register(map);
                else if ((map = CommandHandler.matches(Command.LOGIN, msg)) != null) login(map);
                else if ((map = CommandHandler.matches(Command.FORGOT_PASSWORD, msg)) != null) forgotPassword(map);
                else if ((map = CommandHandler.matches(Command.LOGOUT, msg)) != null) logout();
                else if ((map = CommandHandler.matches(Command.CHANGE_PROFILE, msg)) != null) changeProfile(map);
//                else if((map = CommandHandler.matches(Command.DISPLAY_SCORE,msg)) != null)
//                else if((map = CommandHandler.matches(Command.))
            }
        } catch (Exception ex) {
            System.out.println("User Disconnected");
        }
    }

    private void register(TreeMap<String, ArrayList<String>> matcher) throws IOException {
        String username = matcher.get("u").get(0);
        String password = matcher.get("p").get(0);
        String email = matcher.get("e").get(0);
        String nick = matcher.get("n").get(0);
        String question = matcher.get("q").get(0);
        String answer = matcher.get("q").get(1);
        String slogan = matcher.get("s").get(0);
        String response = LoginMenuController.checkUsername(username);
        if (!response.equals("OK")) {
            outputStream.writeUTF(response);
            return;
        }
        response = LoginMenuController.checkPassword(password);
        if (!response.equals("OK")) {
            outputStream.writeUTF(response);
            return;
        }
        response = LoginMenuController.checkEmail(email);
        if (!response.equals("OK")) {
            outputStream.writeUTF(response);
            return;
        }
        response = LoginMenuController.createUser(username, password, password, email, nick, slogan, question, answer);
        outputStream.writeUTF(response);
    }

    private void login(TreeMap<String, ArrayList<String>> matcher) throws IOException {
        String username = matcher.get("u").get(0);
        String password = matcher.get("p").get(0);

        String response = LoginMenuController.loginUser(username, password);
        outputStream.writeUTF(response);
        if (response.equals("OK")) currentUser = User.getUserByName(username);
    }

    private void forgotPassword(TreeMap<String, ArrayList<String>> map) throws IOException {
        String username = map.get("u").get(0);
        String answer = map.get("a").get(0);
        String newPassword = map.get("p").get(0);
        String response = LoginMenuController.checkRecoveryQuestionAnswer(username, answer);
        if (!response.equals("OK")) {
            outputStream.writeUTF(response);
            return;
        }
        response = LoginMenuController.checkPassword(newPassword);
        if (!response.equals("OK")) {
            outputStream.writeUTF(response);
            return;
        }
        outputStream.writeUTF(response);
        User user = User.getUserByName(username);
        user.setPassword(newPassword);
    }

    private void logout() throws IOException {
        outputStream.writeUTF("OK");
        currentUser = null;
    }

    private void changeProfile(TreeMap<String, ArrayList<String>> matcher) throws IOException {
        String newUsername = matcher.get("u").get(0);
        String newPassword = matcher.get("p").get(0);
        String newEmail = matcher.get("e").get(0);
        String newNick = matcher.get("n").get(0);
        String newSlog = matcher.get("s").get(0);

        String response = LoginMenuController.checkUsername(newUsername);
        if (!response.equals("OK")) {
            outputStream.writeUTF(response);
            return;
        }
        response = LoginMenuController.checkPassword(newPassword);
        if (!response.equals("OK")) {
            outputStream.writeUTF(response);
            return;
        }
        response = LoginMenuController.checkEmail(newEmail);
        if (!response.equals("OK")) {
            outputStream.writeUTF(response);
            return;
        }

        outputStream.writeUTF(response);
        if (!currentUser.getPassword().equals(newPassword))
            currentUser.setPassword(newPassword);
        if (!currentUser.getEmail().equals(newEmail))
            currentUser.setEmail(newEmail);
        if (!currentUser.getNickname().equals(newNick))
            currentUser.setNickname(newNick);
        if (!currentUser.getSlogan().equals(newSlog))
            currentUser.setSlogan(newSlog);
        if (!currentUser.getUsername().equals(newUsername))
            currentUser.setUsername(newUsername);
    }

    private void displayScore() throws IOException {
        outputStream.writeInt(currentUser.getScore());
    }

    private void displayRank() throws IOException {
        ArrayList<User> users = User.getAllUsers();
        users.sort(Comparator.comparingInt(User::getScore));
        int rnk = 1;
        for (int i = users.size() - 1; i >= 0; i--, rnk++)
            if (users.get(i).getUsername().equals(currentUser.getUsername()))
                break;
        outputStream.writeInt(rnk);
    }

    private void getAllUsers() throws IOException {
        ArrayList<User> users = User.getAllUsers();
        objectOutputStream.writeObject(users);
    }
}
