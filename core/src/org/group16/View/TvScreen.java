package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.TimeUtils;
import org.group16.Controller.GameMenuController;
import org.group16.GameGraphics.CommandHandling.EndTurnCommand;
import org.group16.GameGraphics.CommandHandling.InputProcessor;
import org.group16.GameGraphics.GameRenderer;
import org.group16.GameGraphics.Renderer;
import org.group16.GameGraphics.Util;
import org.group16.Model.*;
import org.group16.Networking.GameSocket;
import org.group16.StrongholdGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Gdx.*;
import static com.badlogic.gdx.Gdx.graphics;

public class TvScreen extends Menu {
    private final List<Renderer> renderers = new ArrayList<>();
    private final float minimapDist = 15;
    private final GameInfo gameInfo;
    private final User currentUser;
    public AssetManager assetManager;
    public Window currentRunningWindow;
    Image miniMapImage;
    float time = 0;
    GameRenderer gameRenderer;
    InputProcessor inputProcessor;
    org.group16.Model.Game game;
    Skin skin2 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    Skin skin1 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));
    CellDetailWindow cellDetailWindow;
    private Camera camera, miniMapCamera;
    private DecalBatch decalBatch, miniMapDecalBatch;
    private FrameBuffer miniMapFrameBuffer;
    private TextureRegion miniMapFrameRegion;
    private long lastFrame = TimeUtils.millis();

    public TvScreen(StrongholdGame game1, Game game, GameInfo gameInfo, User currentUser) throws IOException {
        super(game1);
        this.game = game;
        this.gameInfo = gameInfo;
        this.currentUser = currentUser;

        camera = new PerspectiveCamera(30, 1f, 1f * graphics.getHeight() / graphics.getWidth());
        miniMapCamera = new PerspectiveCamera(30, 5f, 3);
        miniMapFrameBuffer = new FrameBuffer(Pixmap.Format.RGB565, 500, 300, false);
        miniMapFrameRegion = new TextureRegion(miniMapFrameBuffer.getColorBufferTexture());
        miniMapFrameRegion.flip(false, true);
        camera.position.set(3f, 3f, 3f);
        camera.lookAt(0f, 0f, 0f);
        miniMapCamera.position.set(3f, 3f, 3f);
        miniMapCamera.lookAt(0f, 0f, 0f);

        camera.near = 1f;
        camera.far = 200f;
        camera.update();

        miniMapCamera.near = 5f;
        miniMapCamera.far = 1000f;
        miniMapCamera.update();

        decalBatch = Util.createDecalBatch(camera);
        miniMapDecalBatch = Util.createDecalBatch(miniMapCamera);

        assetManager = new AssetManager();

        Util.load(assetManager);


        cellDetailWindow = new CellDetailWindow("", skin1, "");


        uiStage.addActor(cellDetailWindow);


        ArrayList<User> allUsers = new ArrayList<>();
        for (Kingdom kingdom : game.getKingdoms())
            allUsers.add(kingdom.getUser());
        inputProcessor = new InputProcessor(allUsers);
        gameRenderer = new GameRenderer(game, inputProcessor);
        GameSocket.createSocket(gameInfo, inputProcessor, currentUser, false, false);
        for (var cmd : inputProcessor.initialCommands) {
            inputProcessor.submitCommand(cmd);
            for (int i = 0; i < Time.updateIterationCount; i++)
                gameRenderer.update((float) Time.deltaTime);
        }

        renderers.add(gameRenderer);
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


    @Override
    public void render(float delta) {
        Util.updateMousePosition(camera);
        uiStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        long milis = TimeUtils.timeSinceMillis(lastFrame);
        float dt = milis / 1000f;
        lastFrame += milis;


        for (Renderer renderer : renderers) {
            renderer.update(dt);
        }

        time += dt;
        float camSpeed = 3;

        camera.update();
        miniMapCamera.position.set(camera.position);
        miniMapCamera.position.add(minimapDist, minimapDist, minimapDist);
        miniMapCamera.update();

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


        decalBatch.flush();

        Cell currentCell = Util.getMouseCell(game);

        if (currentCell != null && time - cellDetailWindow.lastRemakeTime >= 1) {
            int x = currentCell.getX();
            int y = currentCell.getY();
            cellDetailWindow.remake(GameMenuController.showMapDetails(game, x, y));
            cellDetailWindow.lastRemakeTime = time;
        }


        cellDetailWindow.setWidth(200);
        cellDetailWindow.setHeight(200);
        cellDetailWindow.setPosition(uiStage.getWidth() - 200, uiStage.getHeight() - 200);


        uiStage.draw();
    }
}
