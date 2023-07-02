package org.group16.GameGraphics.CommandHandling;

import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.User;
import org.group16.Networking.GameSocket;

import java.util.*;

public class InputProcessor {
    private final HashMap<User, List<UserCommand>> commands = new HashMap<>();
    private final HashMap<User, Integer> commandIter = new HashMap<>();
    private final HashSet<User> finalizedUsers = new HashSet<>();
    private final List<User> allUsers = new ArrayList<>();

    public InputProcessor(List<User> allUsers) {
        for (User user : allUsers)
            addUser(user);
    }

    public void addUser(User user) {
        allUsers.add(user);
        List<UserCommand> cmd = new ArrayList<>();
        cmd.add(new UserJoinCommand(user, 0));
        commands.put(user, cmd);
        commandIter.put(user, 0);
    }

    public void submitCommandToServer(UserCommand command) {
        GameSocket.submitCommand(command);
    }

    public void submitCommand(UserCommand command) {
        if (!finalizedUsers.contains(command.user)) {
            commands.get(command.user).add(command);
            if (command instanceof EndTurnCommand)
                finalizedUsers.add(command.user);
        }
    }

    public boolean process(Game game, GameRenderer gameRenderer) {
        for (User user : allUsers) if (!finalizedUsers.contains(user)) return false;
        for (User user : allUsers) {
            int i = commandIter.get(user);
            List<UserCommand> userCommands = commands.get(user);
            for (; i < userCommands.size(); i++) {
                userCommands.get(i).resolveUser(game);
                userCommands.get(i).execute(game, gameRenderer);
            }
            commandIter.put(user, i);
        }
        finalizedUsers.clear();
        return true;
    }

}
