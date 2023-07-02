package org.group16.Server.Lobby;

import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import org.group16.Model.*;
import org.group16.Model.Map;
import org.group16.Server.Lobby.Command.Command;
import org.group16.Server.Lobby.Command.CommandHandler;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class LobbyConnection extends Thread {
    private final LobbyServer server;
    private final Socket socket;
    private final DataInputStream cmdStream;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private User currentUser;
    private Game currentGame;
    private String mapname;
    private long randSeed;
    private boolean inGameLobby;

    public LobbyConnection(LobbyServer server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        socket.setTcpNoDelay(true);
        cmdStream = new DataInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        System.out.println("Connected to client");
    }

    public boolean isInGameLobby() {
        return inGameLobby;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = cmdStream.readUTF();
                System.out.println(msg);
                TreeMap<String, ArrayList<String>> map;
                if ((map = CommandHandler.matches(Command.REGISTER, msg)) != null) register(map);
                else if ((map = CommandHandler.matches(Command.LOGIN, msg)) != null) login(map);
                else if ((map = CommandHandler.matches(Command.FORGOT_PASSWORD, msg)) != null) forgotPassword(map);
                else if ((map = CommandHandler.matches(Command.LOGOUT, msg)) != null) logout();
                else if ((map = CommandHandler.matches(Command.CHANGE_PROFILE, msg)) != null) changeProfile(map);
                else if ((map = CommandHandler.matches(Command.DISPLAY_SCORE, msg)) != null) displayScore();
                else if ((map = CommandHandler.matches(Command.DISPLAY_RANK, msg)) != null) displayRank();
                else if ((map = CommandHandler.matches(Command.GET_USER, msg)) != null) getUser(map);
                else if ((map = CommandHandler.matches(Command.GET_ALL_USERS, msg)) != null) getAllUsers();
                else if ((map = CommandHandler.matches(Command.CREATE_GAME, msg)) != null) createGame(map);
                else if ((map = CommandHandler.matches(Command.GET_ALL_MAPS, msg)) != null) getAllMaps();
                else if ((map = CommandHandler.matches(Command.SELECT_MAP, msg)) != null) selectMap(map);
                else if ((map = CommandHandler.matches(Command.ADD_USER, msg)) != null) addUser(map);
                else if ((map = CommandHandler.matches(Command.REMOVE_USER, msg)) != null) removeUser(map);
                else if ((map = CommandHandler.matches(Command.JOIN_GAME_LOBBY, msg)) != null) joinGameLobby();
                else if ((map = CommandHandler.matches(Command.LEAVE_GAME_LOBBY, msg)) != null) leaveGameLobby();
                else if ((map = CommandHandler.matches(Command.START_GAME, msg)) != null) startGame();
                else if ((map = CommandHandler.matches(Command.UPLOAD_MAP, msg)) != null) uploadMap();
                else if ((map = CommandHandler.matches(Command.DOWNLOAD_MAP, msg)) != null) downloadMap(map);


//                else if((map = CommandHandler.matches(Command.))
            }
        } catch (Exception ex) {
            System.out.println("User Disconnected");
        }
    }

    private void getAllMaps() throws IOException {
        List<String> res = Map.getAllMapNames();
        StringList data = new StringList();
        data.strings.addAll(res);
        outputStream.writeObject(data);
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
        if (server.userLoggedIn(username))
            response = "user already logged in";
        outputStream.writeUTF(response);
        if (response.equals("OK")) {
            currentUser = User.getUserByName(username);
            server.userLogin(currentUser.getUsername(), this);
        }
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
        server.userLogout(currentUser.getUsername());
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

    private void getUser(TreeMap<String, ArrayList<String>> map) throws IOException {
        String username = map.get("u").get(0);
        outputStream.writeObject(User.getUserByName(username));
    }

    private void getAllUsers() throws IOException {
        UserList userList = new UserList();
        userList.users.addAll(User.getAllUsers());
        outputStream.writeObject(userList);
    }

    private void createGame(TreeMap<String, ArrayList<String>> map) throws IOException {
        KingdomType kingdomType = KingdomType.getKingdomTypeByName(map.get("t").get(0));
        if (kingdomType == null) {
            outputStream.writeUTF("invalid kingdom type");
            return;
        }
        outputStream.writeUTF("OK");
        currentGame = new Game(kingdomType, currentUser);
    }

    private void selectMap(TreeMap<String, ArrayList<String>> map) throws IOException {
        String mapname = map.get("m").get(0);
        Map newMap = Map.getMapByName(mapname);
        if (newMap == null) {
            outputStream.writeUTF("no map with this name exist");
            return;
        }
        outputStream.writeUTF("OK");
        this.mapname = mapname;
        randSeed = new Random().nextLong();
        currentGame.setScene(new Scene(newMap, randSeed));
    }

    private void addUser(TreeMap<String, ArrayList<String>> map) throws IOException {
        User user = User.getUserByName(map.get("u").get(0));
        KingdomType kingdomType = KingdomType.getKingdomTypeByName(map.get("t").get(0));
        if (kingdomType == null) {
            outputStream.writeUTF("invalid kingdom type");
            return;
        }
        if (currentGame.getKingdoms().size() == 8) {
            outputStream.writeUTF("game is full");
            return;
        }
        if (currentGame.getKingdom(user) != null) {
            outputStream.writeUTF("this user already exist");
            return;
        }
        outputStream.writeUTF("OK");
        currentGame.addUser(user, kingdomType);
    }

    private void removeUser(TreeMap<String, ArrayList<String>> map) throws IOException {
        User user = User.getUserByName(map.get("u").get(0));
        if (currentGame.getKingdom(user) == null) {
            outputStream.writeUTF("this user doesn't exist");
            return;
        }
        outputStream.writeUTF("OK");
        currentGame.removeUser(user);
    }

    private void startGame() throws IOException {
        if (currentGame.getKingdoms().size() < 2) {
            outputStream.writeUTF("insufficient user to start game");
            return;
        }
        if (currentGame.getScene() == null) {
            outputStream.writeUTF("no map is selected");
            return;
        }
        PlayerList players = new PlayerList();
        for (Kingdom kingdom : currentGame.getKingdoms()) {
            players.users.add(kingdom.getUser());
            players.kingdomTypes.add(kingdom.getKingdomType());
        }
        GameInfo gameInfo = new GameInfo(UUID.randomUUID()
                , randSeed, mapname, players);
        server.submitGame(gameInfo);
    }

    private void joinGameLobby() throws IOException {
        outputStream.writeObject("OK");
        inGameLobby = true;
    }

    private void leaveGameLobby() throws IOException {
        outputStream.writeObject("OK");
        inGameLobby = false;
    }

    public void startGameFailed() throws IOException {
        outputStream.writeUTF("one of users is offline");
    }

    public void startGameSuccessful(GameInfo gameInfo) throws IOException {
        outputStream.writeUTF("START GAME");
        outputStream.writeObject(gameInfo);
    }

    public void uploadMap() throws IOException, ClassNotFoundException {
        Map mp = (Map) inputStream.readObject();
        Map.saveMap(mp);
    }

    public void downloadMap(TreeMap<String, ArrayList<String>> map) throws IOException {
        String mapname = map.get("m").get(0);
        Map mp = Map.getMapByName(mapname);
        outputStream.writeObject(mp);
    }
}

