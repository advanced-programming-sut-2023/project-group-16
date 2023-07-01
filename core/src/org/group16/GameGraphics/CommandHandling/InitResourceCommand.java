package org.group16.GameGraphics.CommandHandling;

import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.Game;
import org.group16.Model.People.Human;
import org.group16.Model.People.Soldier;
import org.group16.Model.People.SoldierDetail;
import org.group16.Model.Resources.BasicResource;
import org.group16.Model.User;

public class InitResourceCommand extends UserCommand{
    public InitResourceCommand(User user) {
        super(user);
    }

    @Override
    public String execute(Game game, GameRenderer gameRenderer) {
        Building townBuilding = game.getKingdom(user).getEconomicBuildingsByType(BuildingType.TOWN_BUILDING).get(0);
        game.getKingdom(user).setKing(new Soldier(townBuilding.getCells(), game.getKingdom(user), SoldierDetail.KING));
        game.getKingdom(user).addGold(10000);

        game.getKingdom(user).addResource(BasicResource.STONE, 50);
        game.getKingdom(user).addResource(BasicResource.WOOD, 200);

        game.getKingdom(user).addPopulation(10);
        game.getKingdom(user).addPopularity(100);

        gameRenderer.createRenderer(game.getKingdom(user).getKing());
        for (Human human : game.getKingdom(user).getHumans()){
            gameRenderer.createRenderer(human);
        }
        return success() ;
    }

    @Override
    public UserCommand getUndoCommand() {
        return null;
    }
}
