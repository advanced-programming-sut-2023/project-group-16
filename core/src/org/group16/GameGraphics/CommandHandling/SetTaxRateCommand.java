package org.group16.GameGraphics.CommandHandling;

import org.group16.Controller.GameMenuController;
import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.User;

public class SetTaxRateCommand extends UserCommand{

    final private int tax ;

    public SetTaxRateCommand(User user , int tax) {
        super(user);
        this.tax = tax ;
    }

    @Override
    public String execute(Game game, GameRenderer gameRenderer) {
        String res = GameMenuController.setTaxRate(game , user , tax) ;
        if (!res.equals("OK"))
            return res ;
        return success() ;
    }

    @Override
    public UserCommand getUndoCommand() {
        return null;
    }
}
