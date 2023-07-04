package org.group16.Server.Lobby;

import org.group16.Model.Kingdom;
import org.group16.Model.KingdomType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GameLobby {
    public final UUID uuid = UUID.randomUUID();
    private final ArrayList<LobbyConnection> players = new ArrayList<>(); // host: players[0]
    private final HashMap<LobbyConnection, KingdomType> kingdomTypes = new HashMap<>();
    private String mapName;
    private int capacity;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public void addPlayer(LobbyConnection connection, KingdomType kingdomType) {
        players.add(connection);
        kingdomTypes.put(connection, kingdomType);
    }

    public void removePlayer(LobbyConnection connection) {
        players.remove(connection);
        kingdomTypes.remove(connection);
    }

    public int size() {
        return players.size();
    }

    public ArrayList<LobbyConnection> getPlayers() {
        return players;
    }

    public KingdomType getKingdomType(LobbyConnection connection) {
        return kingdomTypes.get(connection);
    }
}
