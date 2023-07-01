package org.group16.GameGraphics.CommandHandling;

import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Game;
import org.group16.Model.SerializedReference;
import org.group16.Model.User;

public class DeleteBuildingCommand extends UserCommand {
    private final SerializedReference<Building> building;

    public DeleteBuildingCommand(User user, Building building) {
        super(user);
        this.building = new SerializedReference<Building>(building);
    }

    @Override
    public String execute(Game game, GameRenderer gameRenderer) {
        if (building.getValue() == null || !building.getValue().isAlive()) return "building already destroyed";
        building.getValue().destroy();
        return success();
    }

    @Override
    public UserCommand getUndoCommand() {
        if (executed)
            return new CreateBuildingCommand(user, building.getValue().getBuildingType(), building.getValue().getCell().getX(), building.getValue().getCell().getY());
        return null;
    }
}
