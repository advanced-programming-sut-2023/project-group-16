package org.group16.Server.Lobby;

import org.group16.Model.*;
import org.group16.Model.Map;
import org.group16.Server.Game.GameServer;
import org.group16.Server.Game.ServerGameRunner;
import org.group16.Server.Lobby.Command.Command;
import org.group16.Server.Lobby.Command.CommandHandler;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class LobbyConnection extends Thread {
    private final LobbyServer server;
    private final Socket socket;
    private final DataInputStream utfInputStream;
    private final DataOutputStream utfOutputStream;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    public boolean inGameLobby = false;
    private User currentUser;
    private GameLobby currentLobby;
    private String mapname;
    private long randSeed;

    public LobbyConnection(LobbyServer server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        socket.setTcpNoDelay(true);
        utfOutputStream = new DataOutputStream(socket.getOutputStream());
        utfInputStream = new DataInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        System.out.println("Connected to client");
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public GameLobby getCurrentLobby() {
        return currentLobby;
    }

    public void setCurrentLobby(GameLobby currentLobby) {
        this.currentLobby = currentLobby;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = utfInputStream.readUTF();
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

                else if ((map = CommandHandler.matches(Command.GET_ALL_PUBLIC_GAME_LOBBIES, msg)) != null)
                    getAllPublicGameLobbies();
                else if ((map = CommandHandler.matches(Command.CREATE_GAME_LOBBY, msg)) != null)
                    createGameLobby(map);
                else if ((map = CommandHandler.matches(Command.SET_GAME_LOBBY_PRIVACY, msg)) != null)
                    setGameLobbyPrivacy(map);
                else if ((map = CommandHandler.matches(Command.SET_GAME_CAPACITY, msg)) != null)
                    setGameCapacity(map);
                else if ((map = CommandHandler.matches(Command.GET_ALL_MAPS, msg)) != null)
                    getAllMaps();
                else if ((map = CommandHandler.matches(Command.SELECT_MAP, msg)) != null)
                    selectMap(map);
                else if ((map = CommandHandler.matches(Command.ADD_USER, msg)) != null)
                    addUser(map);
                else if ((map = CommandHandler.matches(Command.REMOVE_USER, msg)) != null)
                    removeUser(map);
                else if ((map = CommandHandler.matches(Command.ENTER_GAME_LOBBY, msg)) != null)
                    enterGameLobby();
                else if ((map = CommandHandler.matches(Command.JOIN_GAME_LOBBY, msg)) != null)
                    joinGameLobby(map);
                else if ((map = CommandHandler.matches(Command.LEAVE_GAME_LOBBY, msg)) != null)
                    leaveGameLobby();
                else if ((map = CommandHandler.matches(Command.EXIT_GAME_LOBBY, msg)) != null)
                    exitGameLobby();
                else if ((map = CommandHandler.matches(Command.START_GAME, msg)) != null)
                    startGame();


                else if ((map = CommandHandler.matches(Command.UPLOAD_MAP, msg)) != null) uploadMap();
                else if ((map = CommandHandler.matches(Command.DOWNLOAD_MAP, msg)) != null) downloadMap(map);
                else if ((map = CommandHandler.matches(Command.GET_RUNNING_GAMES, msg)) != null) getRunningGames();
                else if ((map = CommandHandler.matches(Command.GET_PLAYER_GAME, msg)) != null) getPlayerGame(map);
                else if ((map = CommandHandler.matches(Command.END_GAME, msg)) != null) endGame();
                else if ((map = CommandHandler.matches(Command.ADD_SCORE, msg)) != null) addScore(map);
                else if ((map = CommandHandler.matches(Command.IS_ONLINE, msg)) != null) isOnline(map);


//                else if((map = CommandHandler.matches(Command.))
            }
        } catch (Exception ex) {
            if (currentUser != null)
                server.userLogout(currentUser.getUsername());
            System.out.println("User Disconnected");
        }
    }

    private void startGame() throws IOException {
        server.startLobbyGame(currentLobby.uuid);
    }

    private void exitGameLobby() {
        inGameLobby = false;
    }

    private void setGameCapacity(TreeMap<String, ArrayList<String>> map) throws IOException {
        int n = Integer.parseInt(map.get("n").get(0));
        if (n < 2) return;
        currentLobby.setCapacity(n);
        while (currentLobby.size() > currentLobby.getCapacity())
            server.leaveLobby(currentLobby.getPlayers().get(currentLobby.getPlayers().size() - 1));
    }

    private void setGameLobbyPrivacy(TreeMap<String, ArrayList<String>> map) {
        String t = map.get("t").get(0);
        if (t.equals("private")) server.setAsPrivateGameLobby(currentLobby);
        else server.setAsPublicGameLobby(currentLobby);
    }

    private void enterGameLobby() {
        inGameLobby = true;
    }

    private void createGameLobby(TreeMap<String, ArrayList<String>> map) throws IOException {
        String kingdomType = map.get("t").get(0);
        server.createLobby(this, KingdomType.getKingdomTypeByName(kingdomType));
    }

    private void getAllPublicGameLobbies() throws IOException {
        UUIDList list = new UUIDList();
        for (GameLobby lobby : server.publicLobbies) {
            list.uuids.add(lobby.uuid);
        }
        outputStream.writeObject(list);
    }

    private void isOnline(TreeMap<String, ArrayList<String>> map) throws IOException {
        String username = map.get("u").get(0);
        utfOutputStream.writeBoolean(server.userLoggedIn(username));
    }

    private void addScore(TreeMap<String, ArrayList<String>> map) throws IOException {
        String u = map.get("u").get(0);
        String v = map.get("v").get(0);

        User user = User.getUserByName(u);
        if (user == null) {
            utfOutputStream.writeUTF("invalid user");
            return;
        }

        utfOutputStream.writeUTF("OK");
        user.addScore(Integer.parseInt(v));
    }

    private void endGame() throws IOException, ClassNotFoundException {
        UUID uuid = (UUID) inputStream.readObject();
        GameServer.singleton.endGame(uuid);
        utfOutputStream.writeUTF("OK");
    }

    private void getPlayerGame(TreeMap<String, ArrayList<String>> map) throws IOException {
        String username = map.get("u").get(0);
        outputStream.writeObject(GameServer.singleton.getPlayerGame(username));
    }

    private void getRunningGames() throws IOException {
        ArrayList<ServerGameRunner> games = GameServer.singleton.getGames();
        GameInfoList list = new GameInfoList();
        for (ServerGameRunner gameRunner : games) {
            list.gameInfos.add(gameRunner.getGameInfo());
        }
        outputStream.writeObject(list);
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
            utfOutputStream.writeUTF(response);
            return;
        }
        response = LoginMenuController.checkPassword(password);
        if (!response.equals("OK")) {
            utfOutputStream.writeUTF(response);
            return;
        }
        response = LoginMenuController.checkEmail(email);
        if (!response.equals("OK")) {
            utfOutputStream.writeUTF(response);
            return;
        }
        response = LoginMenuController.createUser(username, password, password, email, nick, slogan, question, answer);
        utfOutputStream.writeUTF(response);
    }

    private void login(TreeMap<String, ArrayList<String>> matcher) throws IOException {
        String username = matcher.get("u").get(0);
        String password = matcher.get("p").get(0);

        String response = LoginMenuController.loginUser(username, password);
        if (server.userLoggedIn(username))
            response = "user already logged in";
        utfOutputStream.writeUTF(response);
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
            utfOutputStream.writeUTF(response);
            return;
        }
        response = LoginMenuController.checkPassword(newPassword);
        if (!response.equals("OK")) {
            utfOutputStream.writeUTF(response);
            return;
        }
        utfOutputStream.writeUTF(response);
        User user = User.getUserByName(username);
        user.setPassword(newPassword);
    }

    private void logout() throws IOException {
        utfOutputStream.writeUTF("OK");
        server.userLogout(currentUser.getUsername());
        currentUser = null;
    }

    private void changeProfile(TreeMap<String, ArrayList<String>> matcher) throws IOException {
        System.out.println(" hello profile ");
        String newUsername = matcher.get("u").get(0);
        String newPassword = matcher.get("p").get(0);
        String newEmail = matcher.get("e").get(0);
        String newNick = matcher.get("n").get(0);
        String newSlog = matcher.get("s").get(0);

        String response = LoginMenuController.checkUsername(newUsername);
        if (!currentUser.getUsername().equals(newUsername))
            if (!response.equals("OK")) {
                utfOutputStream.writeUTF(response);
                return;
            }
        response = LoginMenuController.checkPassword(newPassword);
        if (!currentUser.getPassword().equals(newPassword))
            if (!response.equals("OK")) {
                utfOutputStream.writeUTF(response);
                return;
            }
        response = LoginMenuController.checkEmail(newEmail);
        if (!currentUser.getEmail().equals(newEmail))
            if (!response.equals("OK")) {
                utfOutputStream.writeUTF(response);
                return;
            }
        response = "OK";


        utfOutputStream.writeUTF(response);

        server.userLogout(currentUser.getUsername());

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

        server.userLoggedIn(currentUser.getUsername());
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
        UserList userList = new UserList();
        userList.users.add(User.getUserByName(username));
        outputStream.writeObject(userList);
    }

    private void getAllUsers() throws IOException {
        UserList userList = new UserList();
        userList.users.addAll(User.getAllUsers());
        outputStream.writeObject(userList);
    }

    private void selectMap(TreeMap<String, ArrayList<String>> map) {
        String mapname = map.get("m").get(0);
        currentLobby.setMapName(mapname);
    }

    private void addUser(TreeMap<String, ArrayList<String>> map) {
        String username = map.get("u").get(0);
        KingdomType kingdomType = KingdomType.getKingdomTypeByName(map.get("t").get(0));
        if (kingdomType == null) {
//            utfOutputStream.writeUTF("invalid kingdom type");
            return;
        }
        if (currentLobby.size() >= currentLobby.getCapacity()) {
//            utfOutputStream.writeUTF("game is full");
            return;
        }
        if (server.getConnection(username) == null) {
//            utfOutputStream.writeUTF("user is offline");
            return;
        }
        if (!server.getConnection(username).inGameLobby) {
//            utfOutputStream.writeUTF("user is not ready");
            return;
        }
        if (currentLobby.getPlayers().contains(server.getConnection(username))) {
//            utfOutputStream.writeUTF("this user already exist");
            return;
        }
//        utfOutputStream.writeUTF("OK");
        server.enterLobby(server.getConnection(username), kingdomType, currentLobby.uuid);
    }

    private void removeUser(TreeMap<String, ArrayList<String>> map) throws IOException {
        String username = map.get("u").get(0);
        if (!currentLobby.getPlayers().contains(server.getConnection(username))) {
//            utfOutputStream.writeUTF("this user doesn't exist");
            return;
        }
//        utfOutputStream.writeUTF("OK");
        server.leaveLobby(server.getConnection(username));
    }


    private void joinGameLobby(TreeMap<String, ArrayList<String>> map) throws IOException, ClassNotFoundException {
        UUID uuid = (UUID) inputStream.readObject();
        KingdomType kingdomType = KingdomType.getKingdomTypeByName(map.get("t").get(0));
        server.enterLobby(this, kingdomType, uuid);
    }

    private void leaveGameLobby() throws IOException {
        server.leaveLobby(this);
    }


    public void startGameSuccessful(GameInfo gameInfo) throws IOException {
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

    public void setAsLobbyHost() throws IOException {
        outputStream.writeObject("host");
    }
}

