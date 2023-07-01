package org.group16.GameGraphics.CommandHandling;

import org.group16.Controller.ShopMenuController;
import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.Resources.Resource;
import org.group16.Model.User;

public class SellCommand extends UserCommand{
    private final Resource resource ;
    private final int amount ;

    public SellCommand(User user , Resource resource, int amount) {
        super(user);
        this.resource = resource;
        this.amount = amount;
    }

    @Override
    public String execute(Game game, GameRenderer gameRenderer) {
        String res = ShopMenuController.sellItem(game.getKingdom(user) , resource.GetName() , amount) ;
        if (!res.equals("item sold successfully"))
            return res ;
        return success() ;
    }

    @Override
    public UserCommand getUndoCommand() {
        return null;
    }
}
