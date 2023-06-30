package org.group16.GameGraphics.CommandHandling;

import org.group16.Controller.GameMenuController;
import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.Game;
import org.group16.Model.User;

public class CreateBuildingCommand extends UserCommand {
    private final BuildingType buildingType;
    private final int x, y;
    private Building building;

    public CreateBuildingCommand(User user, BuildingType buildingType, int x, int y) {
        super(user);
        this.buildingType = buildingType;
        this.x = x;
        this.y = y;
    }

    @Override
    public String execute(Game game, GameRenderer gameRenderer) {
        String res = GameMenuController.dropBuilding(game, user, x, y, buildingType);
        if (!res.equals("OK"))
            return res;
        building = game.getScene().getCellAt(x, y).getBuilding();
        gameRenderer.createRenderer(building);
        return super.execute(game, gameRenderer);
    }

    @Override
    public UserCommand getUndoCommand() {
        if (executed)
            return new DeleteBuildingCommand(user, building);
        return null;
    }
}
