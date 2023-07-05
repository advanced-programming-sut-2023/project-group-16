package org.group16.GameGraphics.CommandHandling;

import org.group16.Controller.ShopMenuController;
import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.Resources.BasicResource;
import org.group16.Model.Resources.Food;
import org.group16.Model.Resources.Resource;
import org.group16.Model.Resources.Weaponry;
import org.group16.Model.User;

public class SellCommand extends UserCommand{
    private final transient Resource resource ;
    private final int amount ;

    BasicResource basicResource = null ;
    Weaponry weaponry = null ;
    Food food = null ;


    public SellCommand(User user, Resource resource , int amount) {
        super(user);
        this.resource = resource;
        this.amount = amount ;

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
        String res = ShopMenuController.sellItem(game.getKingdom(user) , resource1.GetName() , amount) ;
        if (!res.equals("item sold successfully"))
            return res ;
        return success() ;
    }

    @Override
    public UserCommand getUndoCommand() {
        return null;
    }
}
