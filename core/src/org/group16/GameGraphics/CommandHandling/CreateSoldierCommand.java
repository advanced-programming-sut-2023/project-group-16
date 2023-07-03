package org.group16.GameGraphics.CommandHandling;

import org.group16.Controller.BuildingMenuController;
import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Buildings.EconomicBuilding;
import org.group16.Model.Buildings.MilitaryBuilding;
import org.group16.Model.Game;
import org.group16.Model.People.SoldierDetail;
import org.group16.Model.SerializedReference;
import org.group16.Model.User;

public class CreateSoldierCommand extends UserCommand {

    private final SerializedReference<EconomicBuilding> economicBuilding;
    private final SoldierDetail soldierDetail;
    private final int count;

    public CreateSoldierCommand(User user, EconomicBuilding economicBuilding, SoldierDetail soldierDetail, int count) {
        super(user);
        this.economicBuilding = new SerializedReference<EconomicBuilding>(economicBuilding);
        this.soldierDetail = soldierDetail;
        this.count = count;
    }

    @Override
    public String execute(Game game, GameRenderer gameRenderer) {
        String res = BuildingMenuController.createUnit((MilitaryBuilding) economicBuilding.getValue(), soldierDetail, count);
        if (!res.equals("OK"))
            return res;
        return success();
    }

    @Override
    public UserCommand getUndoCommand() {
        return null;
    }
}
