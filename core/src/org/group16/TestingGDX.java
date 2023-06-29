package org.group16;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import org.group16.Controller.GameMenuController;
import org.group16.GameGraphics.*;
import org.group16.Model.*;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.People.Soldier;
import org.group16.Model.People.SoldierDetail;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Gdx.*;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TestingGDX extends Game {
    private final List<Renderer> renderers = new ArrayList<>();
    public AssetManager assetManager;
    float time = 0;
    org.group16.Model.Game game;
    Scene scene;
    Kingdom k1, k2;
    private Camera camera;
    private DecalBatch decalBatch;
    private long lastFrame = TimeUtils.millis();

    @Override
    public void create() {
        camera = new PerspectiveCamera(30, 1f, 1f * graphics.getHeight() / graphics.getWidth());
//        camera = new OrthographicCamera(1, 1f * graphics.getHeight() / graphics.getWidth());
//        ((OrthographicCamera) camera).zoom = 10;
        camera.position.set(3f, 3f, 3f);
        camera.lookAt(0f, 0f, 0f);

        camera.near = 1f;
        camera.far = 50f;
        camera.update();

        decalBatch = Util.createDecalBatch(camera);

        assetManager = new AssetManager();

        Util.load(assetManager);

        initialize();
        GameRenderer gameRenderer = new GameRenderer(game);
        renderers.add(gameRenderer);
    }

    void createMap0() {
        Map map = new Map("map0", 10, 10);
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                map.getCellAt(i, j).setTreeType(TreeType.CHERRY_PALM);
                map.getCellAt(i, j).setCellType(CellType.NORMAL);
            }
        Map.saveMap(map);
    }

    void initialize() {
        User.addUser("player1", "pass", "email",
                "q1", "a1", "playerA", "slog");

        User.addUser("player2", "pass", "email",
                "q1", "a1", "playerB", "slog");

        User user = User.getUserByName("player1");
        User user1 = User.getUserByName("player2");

        game = new org.group16.Model.Game(KingdomType.ARAB, user);
        game.addUser(user1, KingdomType.EUROPEAN);
        createMap0();
        scene = new Scene(Map.getMapByName("map0"));
        game.setScene(scene);
        k1 = game.getKingdom(user);
        k2 = game.getKingdom(user1);

        
        System.out.println(GameMenuController.dropBuilding(game, user, 0, 0, BuildingType.TOWN_BUILDING));
        System.out.println(GameMenuController.dropBuilding(game, user1, 9, 9, BuildingType.TOWN_BUILDING));
        Soldier king1 = new Soldier(new ArrayList<>(List.of(scene.getCellAt(0, 0))), k1, SoldierDetail.KING);
        k1.setKing(king1);
        Soldier king2 = new Soldier(new ArrayList<>(List.of(scene.getCellAt(9, 9))), k2, SoldierDetail.KING);
        k2.setKing(king2);
    }

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
            game.update();
            System.out.printf("[%d,%d] : (%f,%f)", soldier.getCell().getX(), soldier.getCell().getY(),
                    soldier.getRelativeX(), soldier.getRelativeY());

            System.out.printf(" | king=%d\n", k2.getKing().getHp());
        }
        assertSame(k1.getTeam(), GameMenuController.getWinnerTeam(game));
        assertSame(k2.getKing().getCell(), soldier.getCell());
    }

    @Override
    public void render() {
        gl.glClearColor(.3f, .7f, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        long milis = TimeUtils.timeSinceMillis(lastFrame);
        float dt = milis / 1000f;
        lastFrame += milis;
        for (Renderer renderer : renderers) {
            renderer.update(dt);
        }
        time += dt;
        if (input.isKeyPressed(Input.Keys.J))
            camera.position.add(-dt, 0, dt);
        if (input.isKeyPressed(Input.Keys.L))
            camera.position.add(dt, 0, -dt);
        if (input.isKeyPressed(Input.Keys.I))
            camera.position.add(-dt, 0, -dt);
        if (input.isKeyPressed(Input.Keys.K))
            camera.position.add(dt, 0, dt);
        if (input.isKeyPressed(Input.Keys.U))
            camera.position.add(-dt, -dt, -dt);
        if (input.isKeyPressed(Input.Keys.O))
            camera.position.add(dt, dt, dt);
//        if (input.isKeyPressed(Input.Keys.N))
//            camera.rotateAround(, Vector3.Y, -dt * 180);
//        if (input.isKeyPressed(Input.Keys.M))
//            camera.rotate(new Vector3(0, 1, 0), dt * 180);
        camera.update();

        for (Renderer renderer : renderers) {
            renderer.render(decalBatch, new Vector3());
        }
//        gl.glDepthMask(false);
        decalBatch.flush();
//        gl.glDepthMask(true);

//        AnimatedRenderer soldier = (AnimatedRenderer) renderers.get(0);
//        if (input.isKeyPressed(Input.Keys.F)) {
//            soldier.playAnimation("fighting");
//        }
//        if (input.isKeyPressed(Input.Keys.R)) {
//            soldier.playAnimation("running");
//        }
//        if (input.isKeyPressed(Input.Keys.W)) {
//            soldier.playAnimation("walking");
//        }
//        if (input.isKeyPressed(Input.Keys.I)) {
//            soldier.playAnimation("idle");
//        }
//        if (input.isKeyPressed(Input.Keys.RIGHT)) soldier.setDirection(1);
//        if (input.isKeyPressed(Input.Keys.DOWN)) soldier.setDirection(3);
//        if (input.isKeyPressed(Input.Keys.LEFT)) soldier.setDirection(5);
//        if (input.isKeyPressed(Input.Keys.UP)) soldier.setDirection(7);
//        if (input.isKeyPressed(Input.Keys.UP) && input.isKeyPressed(Input.Keys.RIGHT)) soldier.setDirection(0);
//        if (input.isKeyPressed(Input.Keys.DOWN) && input.isKeyPressed(Input.Keys.RIGHT)) soldier.setDirection(2);
//        if (input.isKeyPressed(Input.Keys.DOWN) && input.isKeyPressed(Input.Keys.LEFT)) soldier.setDirection(4);
//        if (input.isKeyPressed(Input.Keys.UP) && input.isKeyPressed(Input.Keys.LEFT)) soldier.setDirection(6);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 1f;
        camera.viewportHeight = 1f * graphics.getHeight() / graphics.getWidth();
        camera.update();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        decalBatch.dispose();
    }
}
