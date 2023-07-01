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

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Gdx.*;
import static com.badlogic.gdx.Gdx.graphics;

public class TestBuildingGraphics extends Game {
    private final List<Renderer> renderers = new ArrayList<>();
    public AssetManager assetManager;
    float time = 0;
    org.group16.Model.Game game;
    Scene scene;
    Kingdom k1, k2;
    GameRenderer gameRenderer;
    BuildingGraphics buildingGraphics;
    int roofHumanCnt;
    private InputProcessor inputProcessor;
    private Camera camera, miniMapCamera;
    private DecalBatch decalBatch, miniMapDecalBatch;
    private FrameBuffer miniMapFrameBuffer;
    private TextureRegion miniMapFrameRegion;
    private long lastFrame = TimeUtils.millis();
    private DetailRenderer testProbe;
    private Renderer miniMapPreview;

    public TestBuildingGraphics(BuildingGraphics graphics, int roofHumanCnt) {
        buildingGraphics = graphics;
        this.roofHumanCnt = roofHumanCnt;
    }

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

        BuildingRenderer buildingRenderer = new BuildingRenderer(buildingGraphics, 0, 0);
        for (int i = 0; i < roofHumanCnt; i++)
            for (int j = 0; j < roofHumanCnt; j++) {
                HumanRenderer humanRenderer = new HumanRenderer(HumanGraphics.EUROPEAN_ARCHER);
                humanRenderer.setLocalPosition(buildingRenderer.getRoofPosition(1.0f * i / (roofHumanCnt - 1), 1.0f * j / (roofHumanCnt - 1)));
                renderers.add(humanRenderer);
            }
        renderers.add(buildingRenderer);
        CellRenderer cellRenderer = new CellRenderer(CellGraphics.SAND, 0, 0);
        renderers.add(cellRenderer);
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
            camera.position.add(-5 * dt, -5 * dt, -5 * dt);
        if (input.isKeyPressed(Input.Keys.O))
            camera.position.add(5 * dt, 5 * dt, 5 * dt);
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
