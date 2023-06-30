package org.group16.GameGraphics.CommandHandling;

import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.User;

public class EndTurnCommand extends UserCommand {
    public EndTurnCommand(User user) {
        super(user);
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
