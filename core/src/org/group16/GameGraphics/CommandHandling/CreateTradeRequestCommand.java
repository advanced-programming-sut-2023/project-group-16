package org.group16.GameGraphics.CommandHandling;

import org.group16.Controller.TradeMenuController;
import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.Resources.BasicResource;
import org.group16.Model.Resources.Food;
import org.group16.Model.Resources.Resource;
import org.group16.Model.Resources.Weaponry;
import org.group16.Model.User;

public class CreateTradeRequestCommand extends UserCommand{

    final int amount , price ;
    final String message ;
    final transient Resource resource ;
    BasicResource basicResource = null ;
    Weaponry weaponry = null ;
    Food food = null ;


    public CreateTradeRequestCommand(User user, int amount, int price, String message, Resource resource) {
        super(user);
        this.amount = amount;
        this.price = price;
        this.message = message;
        this.resource = resource;
        if (resource instanceof BasicResource){
            basicResource = (BasicResource) resource ;
        }
        if (resource instanceof Weaponry){
            weaponry = (Weaponry) resource ;
        }
        if (resource instanceof Food){
            food = (Food) resource ;
        }
    }

    @Override
    public String execute(Game game, GameRenderer gameRenderer) {
        Resource resource1 = null ;
        if (basicResource!=null)
            resource1 = basicResource ;
        if (food!=null)
            resource1 = food ;
        if (weaponry!=null)
            resource1 = weaponry ;
        String res = TradeMenuController.tradeRequest(game , user , resource1 , amount , price , message) ;
        if (!res.equals("trade request made successfully"))
            return res ;
        return success() ;
    }

    @Override
    public UserCommand getUndoCommand() {
        return null;
    }
}
