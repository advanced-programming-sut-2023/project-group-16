package org.group16.Server.Lobby;

import org.group16.Model.GameInfo;
import org.group16.Model.Kingdom;
import org.group16.Model.KingdomType;
import org.group16.Model.PlayerList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class LobbyServer extends Thread {
    public static LobbyServer singleton;
    public final HashSet<GameLobby> publicLobbies = new HashSet<>();
    private final ServerSocket serverSocket;
    private final HashMap<String, LobbyConnection> userConnections = new HashMap<>();
    private final HashMap<UUID, GameLobby> lobbies = new HashMap<>();

    public LobbyServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        singleton = this;
    }

    public LobbyConnection getConnection(String username) {
        return userConnections.get(username);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                LobbyConnection connection = new LobbyConnection(this, socket);

                connection.start();
            } catch (Exception e) {
                System.out.println("Client Disconnected");
            }
        }
    }


    public void userLogin(String username, LobbyConnection connection) {
        userConnections.put(username, connection);
    }

    public boolean userLoggedIn(String username) {
        return userConnections.containsKey(username);
    }

    public void userLogout(String username) {
        userConnections.remove(username);
    }

    public synchronized void createLobby(LobbyConnection user, KingdomType kingdomType) throws IOException {
        GameLobby lobby = new GameLobby();
        lobbies.put(lobby.uuid, lobby);
        lobby.addPlayer(user, kingdomType);
        user.setCurrentLobby(lobby);
        user.setAsLobbyHost();
    }

    public synchronized void enterLobby(LobbyConnection user, KingdomType kingdomType, UUID lobbyId) {
        GameLobby lobby = lobbies.get(lobbyId);
        if (lobby == null) return;
        lobby.addPlayer(user, kingdomType);
        user.setCurrentLobby(lobby);
    }

    public synchronized void leaveLobby(LobbyConnection user) throws IOException {
        GameLobby lobby = user.getCurrentLobby();
        if (lobby == null) return;
        lobby.removePlayer(user);
        user.setCurrentLobby(null);
        lobby.getPlayers().get(0).setAsLobbyHost();
        if (user.getCurrentLobby().size() == 1) lobbies.remove(user.getCurrentLobby().uuid);
    }

    public synchronized void startLobbyGame(UUID lobbyId) throws IOException {
        GameLobby lobby = lobbies.get(lobbyId);
        if (lobby == null) return;
        lobbies.remove(lobbyId);

        PlayerList players = new PlayerList();
        for (LobbyConnection connection : lobby.getPlayers()) {
            KingdomType kingdomType = lobby.getKingdomType(connection);
            players.users.add(connection.getCurrentUser());
            players.kingdomTypes.add(kingdomType);
        }
        GameInfo gameInfo = new GameInfo(UUID.randomUUID()
                , new Random().nextLong(), lobby.getMapName(), players);

        for (LobbyConnection lobbyConnection : lobby.getPlayers()) {
            lobbyConnection.startGameSuccessful(gameInfo);
        }
    }

    public synchronized void setAsPublicGameLobby(GameLobby gameLobby) {
        publicLobbies.add(gameLobby);
    }

    public synchronized void setAsPrivateGameLobby(GameLobby gameLobby) {
        publicLobbies.remove(gameLobby);
    }
}
