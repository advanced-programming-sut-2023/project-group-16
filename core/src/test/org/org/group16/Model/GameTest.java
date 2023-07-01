package org.group16.Model;

import org.group16.Controller.GameMenuController;
import org.group16.GameGraphics.CommandHandling.CreateBuildingCommand;
import org.group16.GameGraphics.CommandHandling.DeleteBuildingCommand;
import org.group16.GameGraphics.CommandHandling.UserCommand;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.People.Soldier;
import org.group16.Model.People.SoldierDetail;
import org.group16.Model.Resources.BasicResource;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GameTest {
    Game game;
    Scene scene;
    Kingdom k1, k2;

    void createMap0() {
        Map map = new Map("map0", 5, 5);
        Map.saveMap(map);
    }

    @BeforeEach
    void initialize() {
        User.addUser("player1", "pass", "email",
                "q1", "a1", "playerA", "slog");

        User.addUser("player2", "pass", "email",
                "q1", "a1", "playerB", "slog");

        User user = User.getUserByName("player1");
        User user1 = User.getUserByName("player2");

        game = new Game(KingdomType.ARAB, user);
        game.addUser(user1, KingdomType.EUROPEAN);
        createMap0();
        scene = new Scene(Map.getMapByName("map0"), 0);
        game.setScene(scene);
        k1 = game.getKingdom(user);
        k2 = game.getKingdom(user1);

        GameMenuController.dropBuilding(game, k1.getUser(), 0, 0, BuildingType.TOWN_BUILDING);
        k1.addGold(10000);
        GameMenuController.dropBuilding(game, k1.getUser(), 0, 1, BuildingType.STOCKPILE);
        k1.addResource(BasicResource.STONE, 100);
        k1.addResource(BasicResource.WOOD, 100);


        Soldier king1 = new Soldier(new ArrayList<>(List.of(scene.getCellAt(0, 0))), k1, SoldierDetail.KING);
        k1.setKing(king1);
        Soldier king2 = new Soldier(new ArrayList<>(List.of(scene.getCellAt(4, 4))), k2, SoldierDetail.KING);
        k2.setKing(king2);
    }

    @Test
    void commandSerializationTest() {
        CreateBuildingCommand createBuildingCommand = new CreateBuildingCommand(k1.getUser(), BuildingType.TOWN_BUILDING, 0, 1);
        String data = createBuildingCommand.serialize();
        CreateBuildingCommand createBuildingCommand1 = (CreateBuildingCommand) UserCommand.tryDeserialize(data);

        DeleteBuildingCommand deleteBuildingCommand = new DeleteBuildingCommand(k1.getUser(), k1.getBuildings().get(1));
        String data1 = deleteBuildingCommand.serialize();
        DeleteBuildingCommand deleteBuildingCommand1 = (DeleteBuildingCommand) UserCommand.tryDeserialize(data1);

        createBuildingCommand1.resolveUser(game);
        deleteBuildingCommand1.resolveUser(game);
    }

    @Test
    void soldierPathfindingTest() {
        Cell cell = scene.getCellAt(0, 0);
        Soldier soldier = new Soldier(new ArrayList<>(List.of(cell)), k1, SoldierDetail.ARABIAN_SWORDS_MAN);
        Soldier soldier2 = new Soldier(new ArrayList<>(List.of(cell)), k1, SoldierDetail.ARABIAN_SWORDS_MAN);
        Soldier soldier3 = new Soldier(new ArrayList<>(List.of(cell)), k1, SoldierDetail.ARABIAN_SWORDS_MAN);
        Soldier soldier4 = new Soldier(new ArrayList<>(List.of(cell)), k1, SoldierDetail.ARABIAN_SWORDS_MAN);
        WarCommand warCommand = new WarCommand(new ArrayList<>(List.of(soldier, soldier2, soldier3, soldier4)), k2.getKing());
        WarCommand kWarCommand = new WarCommand(new ArrayList<>(List.of(k2.getKing())), scene.getCellAt(0, 4), false);
        assertSame(warCommand, soldier.getWarCommand());
//        assertSame(kWarCommand, k2.getKing().getWarCommand());

        System.out.printf("[%d,%d] : (%f,%f)", soldier.getCell().getX(), soldier.getCell().getY(),
                soldier.getRelativeX(), soldier.getRelativeY());
        System.out.printf(" | king=%d\n", k2.getKing().getHp());
        for (int i = 0; i < 20; i++) {
            game.onTurnStart();
            for (int j = 0; j < Time.updateIterationCount; j++)
                game.update();
            game.onTurnEnd();
            System.out.printf("[%d,%d] : (%f,%f)", soldier.getCell().getX(), soldier.getCell().getY(),
                    soldier.getRelativeX(), soldier.getRelativeY());

            System.out.printf(" | king=%d\n", k2.getKing().getHp());
        }
        assertSame(k1.getTeam(), GameMenuController.getWinnerTeam(game));
        assertSame(k2.getKing().getCell(), soldier.getCell());
    }
}
