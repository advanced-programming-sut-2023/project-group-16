package org.group16.GameGraphics.CommandHandling;

import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.User;
import org.group16.Networking.GameSocket;

import java.util.*;

public class InputProcessor {
    private final HashMap<String, List<UserCommand>> commands = new HashMap<>();
    private final HashMap<String, Integer> commandIter = new HashMap<>();
    private final HashSet<String> finalizedUsers = new HashSet<>();
    private final List<User> allUsers = new ArrayList<>();

    public InputProcessor(List<User> allUsers) {
        for (User user : allUsers)
            addUser(user);
    }

    public synchronized void addUser(User user) {
        allUsers.add(user);
        List<UserCommand> cmd = new ArrayList<>();
        cmd.add(new UserJoinCommand(user, 0));
        commands.put(user.getUsername(), cmd);
        commandIter.put(user.getUsername(), 0);
    }

    public synchronized void submitCommandToServer(UserCommand command) {
        GameSocket.submitCommand(command);
    }

    public synchronized void submitCommand(UserCommand command) {
        if (!finalizedUsers.contains(command.user.getUsername())) {
            commands.get(command.user.getUsername()).add(command);
            if (command instanceof EndTurnCommand)
                finalizedUsers.add(command.user.getUsername());
        }
    }

    public synchronized boolean process(Game game, GameRenderer gameRenderer) {
        for (User user : allUsers) if (!finalizedUsers.contains(user.getUsername())) return false;
        System.out.println("PRC");
        for (User user : allUsers) {
            int i = commandIter.get(user.getUsername());
            List<UserCommand> userCommands = commands.get(user.getUsername());
            for (; i < userCommands.size(); i++) {
                userCommands.get(i).resolveUser(game);
                userCommands.get(i).execute(game, gameRenderer);
            }
            commandIter.put(user.getUsername(), i);
        }
        finalizedUsers.clear();
        return true;
    }

}
