package org.group16.GameGraphics.CommandHandling;

import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Game;
import org.group16.Model.User;

public class DeleteBuildingCommand extends UserCommand {
    private final Building building;

    public DeleteBuildingCommand(User user, Building building) {
        super(user);
        this.building = building;
    }

    @Override
    public String execute(Game game, GameRenderer gameRenderer) {
        if (building == null || !building.isAlive()) return "building already destroyed";
        building.destroy();
        return success();
    }

    @Override
    public UserCommand getUndoCommand() {
        if (executed)
            return new CreateBuildingCommand(user, building.getBuildingType(), building.getCell().getX(), building.getCell().getY());
        return null;
    }
}
