package org.group16;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import org.group16.Controller.GameMenuController;
import org.group16.GameGraphics.*;
import org.group16.GameGraphics.CommandHandling.CreateBuildingCommand;
import org.group16.GameGraphics.CommandHandling.DeleteBuildingCommand;
import org.group16.GameGraphics.CommandHandling.EndTurnCommand;
import org.group16.GameGraphics.CommandHandling.InputProcessor;
import org.group16.Model.*;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.People.Soldier;
import org.group16.Model.People.SoldierDetail;
import org.group16.Model.Resources.BasicResource;
import org.group16.Model.Resources.Resource;

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
    GameRenderer gameRenderer;
    private InputProcessor inputProcessor;
    private Camera camera, miniMapCamera;
    private DecalBatch decalBatch, miniMapDecalBatch;
    private FrameBuffer miniMapFrameBuffer;
    private TextureRegion miniMapFrameRegion;
    private long lastFrame = TimeUtils.millis();
    private DetailRenderer testProbe;
    private Renderer miniMapPreview;

    @Override
    public void create() {
        camera = new PerspectiveCamera(30, 1f, 1f * graphics.getHeight() / graphics.getWidth());
        miniMapCamera = new OrthographicCamera(5f, 3);
        float resolutionMultiplier = 200;
        miniMapFrameBuffer = new FrameBuffer(Pixmap.Format.RGB565, (int) (5 * resolutionMultiplier), (int) (3 * resolutionMultiplier), false);
        miniMapFrameRegion = new TextureRegion(miniMapFrameBuffer.getColorBufferTexture());
        miniMapFrameRegion.flip(false, true);
//        camera = new OrthographicCamera(1, 1f * graphics.getHeight() / graphics.getWidth());
//        ((OrthographicCamera) camera).zoom = 10;
        camera.position.set(3f, 3f, 3f);
        camera.lookAt(0f, 0f, 0f);
        miniMapCamera.position.set(1f, 1f, 1f);
        miniMapCamera.lookAt(0f, 0f, 0f);
        miniMapCamera.position.set(20, 10, 20);
        ((OrthographicCamera) miniMapCamera).zoom = 6;
        miniMapPreview = new Renderer(miniMapFrameRegion, false, 1, Util.forward, Util.up);
        miniMapPreview.setLocalPosition(0, 2, 0);

        camera.near = 1f;
        camera.far = 50f;
        camera.update();

        miniMapCamera.near = 2f;
        miniMapCamera.far = 100f;
        miniMapCamera.update();

        decalBatch = Util.createDecalBatch(camera);
        miniMapDecalBatch = Util.createDecalBatch(miniMapCamera);

        assetManager = new AssetManager();

        Util.load(assetManager);

        initialize();

        inputProcessor = new InputProcessor(List.of(k1.getUser(), k2.getUser()));
        gameRenderer = new GameRenderer(game, inputProcessor);
        initGameObjects();
        new WarCommand(List.of(k1.getKing()), k2.getKing());
        new WarCommand(List.of(k2.getKing()), k1.getKing());
        renderers.add(gameRenderer);

        testProbe = new DetailRenderer(DetailGraphics.CACTII, 0, 0);
        renderers.add(testProbe);

        ArrayList<Soldier> list1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Soldier soldier = new Soldier(List.of(scene.getCellAt(0, 1)), k1, SoldierDetail.MACE_MAN);
            gameRenderer.createRenderer(soldier);
            list1.add(soldier);
        }
        ArrayList<Soldier> list2 = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Soldier soldier = new Soldier(List.of(scene.getCellAt(10, 10)), k2, SoldierDetail.ASSASSIN);
            gameRenderer.createRenderer(soldier);
            list2.add(soldier);
        }
        new WarCommand(list1, scene.getCellAt(9, 9), false);
        new WarCommand(list2, scene.getCellAt(9, 9), false);
    }

    void createMap0() {
        Map map = new Map("map0", 20, 20);
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
        scene = new Scene(Map.getMapByName("map0"), 0);
        game.setScene(scene);
        k1 = game.getKingdom(user);
        k2 = game.getKingdom(user1);
    }

    private void initGameObjects() {
        GameMenuController.dropBuilding(game, k1.getUser(), 0, 0, BuildingType.TOWN_BUILDING);
        GameMenuController.dropBuilding(game , k1.getUser() , 16 , 17 , BuildingType.GRANARY) ;
        GameMenuController.dropBuilding(game, k2.getUser(), 19, 19, BuildingType.TOWN_BUILDING);
        gameRenderer.createRenderer(k1.getEconomicBuildingsByType(BuildingType.TOWN_BUILDING).get(0));
        gameRenderer.createRenderer(k2.getEconomicBuildingsByType(BuildingType.TOWN_BUILDING).get(0));

        GameMenuController.dropBuilding(game, k1.getUser(), 0, 1, BuildingType.STOCKPILE);
        GameMenuController.dropBuilding(game, k2.getUser(), 18, 19, BuildingType.STOCKPILE);
        gameRenderer.createRenderer(k1.getEconomicBuildingsByType(BuildingType.STOCKPILE).get(0));
        gameRenderer.createRenderer(k2.getEconomicBuildingsByType(BuildingType.STOCKPILE).get(0));

        k1.addResource(BasicResource.STONE, 50);
        k1.addResource(BasicResource.WOOD, 200);
        k2.addResource(BasicResource.STONE, 50);
        k2.addResource(BasicResource.WOOD, 200);

        Soldier king1 = new Soldier(new ArrayList<>(List.of(scene.getCellAt(0, 0))), k1, SoldierDetail.KING);
        k1.setKing(king1);
        k1.addGold(10000);
        gameRenderer.createRenderer(king1);
        Soldier king2 = new Soldier(new ArrayList<>(List.of(scene.getCellAt(19, 19))), k2, SoldierDetail.KING);
        k2.setKing(king2);
        k2.addGold(10000);
        gameRenderer.createRenderer(king2);
    }

//    void soldierPathfindingTest() {
//        Cell cell = scene.getCellAt(0, 0);
//        Soldier soldier = new Soldier(new ArrayList<>(List.of(cell)), k1, SoldierDetail.ARABIAN_SWORDS_MAN);
//        Soldier soldier2 = new Soldier(new ArrayList<>(List.of(cell)), k1, SoldierDetail.ARABIAN_SWORDS_MAN);
//        Soldier soldier3 = new Soldier(new ArrayList<>(List.of(cell)), k1, SoldierDetail.ARABIAN_SWORDS_MAN);
//        Soldier soldier4 = new Soldier(new ArrayList<>(List.of(cell)), k1, SoldierDetail.ARABIAN_SWORDS_MAN);
//        WarCommand warCommand = new WarCommand(new ArrayList<>(List.of(soldier, soldier2, soldier3, soldier4)), k2.getKing());
//        WarCommand kWarCommand = new WarCommand(new ArrayList<>(List.of(k2.getKing())), scene.getCellAt(0, 4), false);
//        assertSame(warCommand, soldier.getWarCommand());
////        assertSame(kWarCommand, k2.getKing().getWarCommand());
//
//        System.out.printf("[%d,%d] : (%f,%f)", soldier.getCell().getX(), soldier.getCell().getY(),
//                soldier.getRelativeX(), soldier.getRelativeY());
//        System.out.printf(" | king=%d\n", k2.getKing().getHp());
//        for (int i = 0; i < 20; i++) {
//            game.update();
//            System.out.printf("[%d,%d] : (%f,%f)", soldier.getCell().getX(), soldier.getCell().getY(),
//                    soldier.getRelativeX(), soldier.getRelativeY());
//
//            System.out.printf(" | king=%d\n", k2.getKing().getHp());
//        }
//        assertSame(k1.getTeam(), GameMenuController.getWinnerTeam(game));
//        assertSame(k2.getKing().getCell(), soldier.getCell());
//    }

    @Override
    public void render() {
        Util.updateMousePosition(camera);

        long milis = TimeUtils.timeSinceMillis(lastFrame);
        float dt = milis / 1000f;
        lastFrame += milis;
        for (Renderer renderer : renderers) {
            renderer.update(dt);
        }
        time += dt;
        float camSpeed = 3;
        if (input.isKeyPressed(Input.Keys.J))
            camera.position.add(-dt * camSpeed, 0, dt * camSpeed);
        if (input.isKeyPressed(Input.Keys.L))
            camera.position.add(dt * camSpeed, 0, -dt * camSpeed);
        if (input.isKeyPressed(Input.Keys.I))
            camera.position.add(-dt * camSpeed, 0, -dt * camSpeed);
        if (input.isKeyPressed(Input.Keys.K))
            camera.position.add(dt * camSpeed, 0, dt * camSpeed);
        if (input.isKeyPressed(Input.Keys.U))
            camera.position.add(-dt, -dt, -dt);
        if (input.isKeyPressed(Input.Keys.O))
            camera.position.add(dt, dt, dt);

        if (input.isKeyJustPressed(Input.Keys.B))
            inputProcessor.submitCommand(new CreateBuildingCommand(k1.getUser(), BuildingType.LOOKOUT_TOWER, 4, 2));
        if (input.isKeyJustPressed(Input.Keys.V))
            inputProcessor.submitCommand(new DeleteBuildingCommand(k1.getUser(), k1.getBuildings().get(2)));
        if (input.isKeyPressed(Input.Keys.N)) inputProcessor.submitCommand(new EndTurnCommand(k1.getUser()));
        if (input.isKeyPressed(Input.Keys.M)) inputProcessor.submitCommand(new EndTurnCommand(k2.getUser()));
//        if (input.isKeyPressed(Input.Keys.N))
//            camera.rotateAround(, Vector3.Y, -dt * 180);
//        if (input.isKeyPressed(Input.Keys.M))
//            camera.rotate(new Vector3(0, 1, 0), dt * 180);
        camera.update();


        for (Renderer renderer : renderers) {
            renderer.render(decalBatch, new Vector3());
            renderer.render(miniMapDecalBatch, new Vector3());
        }
//        gl.glDepthMask(false);
        miniMapFrameBuffer.begin();
        gl.glClearColor(.2f, .6f, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        miniMapDecalBatch.flush();
        miniMapFrameBuffer.end();

        gl.glClearColor(.3f, .7f, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        miniMapPreview.render(decalBatch, new Vector3());
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
