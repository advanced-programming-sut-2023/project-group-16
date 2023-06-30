package org.group16.GameGraphics.CommandHandling;

import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.User;

public abstract class UserCommand {
    protected final User user;
    protected boolean executed;

    public UserCommand(User user) {
        this.user = user;
    }

    public String execute(Game game, GameRenderer gameRenderer) {
        executed = true;
        return "OK";
    }

    public abstract UserCommand getUndoCommand();
}
