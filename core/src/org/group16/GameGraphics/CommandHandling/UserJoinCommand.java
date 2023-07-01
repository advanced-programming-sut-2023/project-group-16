package org.group16.GameGraphics.CommandHandling;

import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.User;

public class UserJoinCommand extends UserCommand {
    private int turnId;

    public UserJoinCommand(User user, int turnId) {
        super(user);
        this.turnId = turnId;
    }

    @Override
    public String execute(Game game, GameRenderer gameRenderer) {
        return success();
    }

    @Override
    public UserCommand getUndoCommand() {
        return null;
    }
}
