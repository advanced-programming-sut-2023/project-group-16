package org.group16.GameGraphics.CommandHandling;

import org.group16.Controller.TradeMenuController;
import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.User;

public class TradeAcceptCommand extends UserCommand{

    final int id ;
    final String message ;

    public TradeAcceptCommand(User user, int id, String message) {
        super(user);
        this.id = id;
        this.message = message;
    }

    @Override
    public String execute(Game game, GameRenderer gameRenderer) {
        String res = TradeMenuController.tradeAccept(game , user , id , message) ;
        if (!res.equals("trade made successfully"))
            return res ;
        return success() ;
    }

    @Override
    public UserCommand getUndoCommand() {
        return null;
    }
}
