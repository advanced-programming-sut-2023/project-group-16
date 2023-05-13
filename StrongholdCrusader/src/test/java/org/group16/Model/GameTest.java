package org.group16.Model;

import org.group16.Controller.GameMenuController;
import org.group16.Model.People.Soldier;
import org.group16.Model.People.SoldierDetail;
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
        scene = new Scene(Map.getMapByName("map0"));
        game.setScene(scene);
        k1 = game.getKingdom(user);
        k2 = game.getKingdom(user1);


        Soldier king1 = new Soldier(new ArrayList<>(List.of(scene.getCellAt(0, 0))), k1, SoldierDetail.KING);
        k1.setKing(king1);
        Soldier king2 = new Soldier(new ArrayList<>(List.of(scene.getCellAt(4, 4))), k2, SoldierDetail.KING);
        k2.setKing(king2);
    }

    @Test
    void soldierPathfindingTest() {
        Cell cell = scene.getCellAt(0, 0);
        Soldier soldier = new Soldier(new ArrayList<>(List.of(cell)), k1, SoldierDetail.ARCHER);
        WarCommand warCommand = new WarCommand(new ArrayList<>(List.of(soldier)), scene.getCellAt(4, 4), false);
        assertSame(warCommand, soldier.getWarCommand());

        System.out.printf("[%d,%d] : (%f,%f)", soldier.getCell().getX(), soldier.getCell().getY(),
                soldier.getRelativeX(), soldier.getRelativeY());
        for (int i = 0; i < 5; i++) {
            game.execute();
            System.out.printf("[%d,%d] : (%f,%f)", soldier.getCell().getX(), soldier.getCell().getY(),
                    soldier.getRelativeX(), soldier.getRelativeY());
        }
        k2.getKing().destroy();
        assertSame(k1.getTeam(), GameMenuController.getWinnerTeam(game));
        assertSame(scene.getCellAt(4, 4), soldier.getCell());
    }
}
