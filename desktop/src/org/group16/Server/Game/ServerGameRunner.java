package org.group16.Server.Game;

import org.group16.GameGraphics.CommandHandling.InputProcessor;
import org.group16.GameGraphics.CommandHandling.UserCommand;
import org.group16.Model.Game;
import org.group16.Model.GameInfo;
import org.group16.Model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerGameRunner {
    private final ArrayList<UserCommand> totalUserCommands = new ArrayList<>();
    private final ArrayList<GameConnection> players = new ArrayList<>();
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<GameConnection> spectators = new ArrayList<>();

    private final GameInfo gameInfo;

    public ServerGameRunner(GameInfo info, List<String> usernames) {
        gameInfo = info;
        for (String username : usernames) {
            users.add(User.getUserByName(username));
        }
    }

    public ArrayList<UserCommand> getTotalUserCommands() {
        return totalUserCommands;
    }

    public synchronized void addPlayer(GameConnection connection) {
        players.add(connection);
    }

    public synchronized void removePlayer(GameConnection connection) {
        players.remove(connection);
    }

    public synchronized void addSpectator(GameConnection connection) {
        spectators.add(connection);
    }

    public synchronized void removeSpectator(GameConnection connection) {
        spectators.remove(connection);
    }

    public synchronized void shareCommand(UserCommand command) {
        totalUserCommands.add(command);
        for (int i = players.size() - 1; i >= 0; i--) {
            GameConnection connection = players.get(i);
            try {
                connection.sendCommand(command);
            } catch (IOException e) {
                players.remove(connection);
            }
        }
        for (int i = spectators.size() - 1; i >= 0; i--) {
            GameConnection connection = spectators.get(i);
            try {
                connection.sendCommand(command);
            } catch (IOException e) {
                players.remove(connection);
            }
        }
    }

    public ArrayList<GameConnection> getPLayers() {
        return players;
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }
}
