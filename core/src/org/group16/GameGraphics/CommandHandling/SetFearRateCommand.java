package org.group16.GameGraphics.CommandHandling;

import org.group16.Controller.GameMenuController;
import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.User;

public class SetFearRateCommand extends UserCommand{

    final private int fear ;

    public SetFearRateCommand(User user , int fear) {
        super(user);
        this.fear = fear ;
    }

    @Override
    public String execute(Game game, GameRenderer gameRenderer) {
        String res = GameMenuController.setFearRate(game , user , fear) ;
        if (!res.equals("OK"))
            return res ;
        return success() ;
    }

    @Override
    public UserCommand getUndoCommand() {
        return null;
    }
}
