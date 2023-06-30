package org.group16.GameGraphics.CommandHandling;

import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.User;

public abstract class UserCommand {
    public final User user;
    protected boolean executed;

    public UserCommand(User user) {
        this.user = user;
    }

    protected String success() {
        executed = true;
        return "OK";
    }

    public abstract String execute(Game game, GameRenderer gameRenderer);

    public abstract UserCommand getUndoCommand();
}
