package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.TimeUtils;
import org.group16.Controller.GameMenuController;
import org.group16.GameGraphics.*;
import org.group16.GameGraphics.CommandHandling.CreateBuildingCommand;
import org.group16.GameGraphics.CommandHandling.EndTurnCommand;
import org.group16.GameGraphics.CommandHandling.InitResourceCommand;
import org.group16.GameGraphics.CommandHandling.InputProcessor;
import org.group16.Model.*;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.People.Soldier;
import org.group16.StrongholdGame;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Gdx.*;
import static com.badlogic.gdx.Gdx.gl;


public class testingGameScreen extends Menu {
    ////////////////////////////////////// render stuff
    private final List<Renderer> renderers = new ArrayList<>();
    private final float minimapDist = 15;
    public AssetManager assetManager;
    public Window currentRunningWindow;
    Image miniMapImage;
    float time = 0;
    GameRenderer gameRenderer;
    InputProcessor inputProcessor;
    org.group16.Model.Game game;
    ///////////////////////////////////////////////////
    int curUser = 0;
    ArrayList<Cell> selectedCells = new ArrayList<>();
    Cell lastSelectedCell = null;
    Skin skin2 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    Skin skin1 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));
    BuildingSelectWindow buildingSelectWindow;
    CurrentPlayerWindow currentPlayerWindow;
    CellDetailWindow cellDetailWindow;
    MiniWindow miniWindow;
    //////////////////////////////////////////////////
    BuildingWindow buildingWindow;
    StorageWindow storageWindow;
    ShopWindow shopWindow;
    BuyingWindow buyingWindow;
    PopularityWindow popularityWindow;
    ChangeRateWindow changeRateWindow;
    BuyingUnitWindow buyingUnitWindow;

    SoldierControlWindow soldierControlWindow;
    private Camera camera, miniMapCamera;
    private DecalBatch decalBatch, miniMapDecalBatch;
    private FrameBuffer miniMapFrameBuffer;
    private TextureRegion miniMapFrameRegion;
    private long lastFrame = TimeUtils.millis();
    private DetailRenderer testProbe;

    public testingGameScreen(StrongholdGame game1, Game game) {
        super(game1);
        this.game = game;

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


        dropKingdoms();

        renderers.add(gameRenderer);

        currentPlayerWindow = new CurrentPlayerWindow(game.getKingdoms().get(0).getUser(), skin1, game, this);

        buildingSelectWindow = new BuildingSelectWindow(skin2, game, this);

        cellDetailWindow = new CellDetailWindow("", skin1, "");

        miniWindow = new MiniWindow(skin1, 0, 0, 0, 0);

        buildingWindow = new BuildingWindow(skin1, game, this);
        buildingWindow.setVisible(false);

        storageWindow = new StorageWindow(skin1, game, this);
        storageWindow.setVisible(false);

        shopWindow = new ShopWindow(skin1, game, this);
        shopWindow.setVisible(false);

        buyingWindow = new BuyingWindow(skin1, game, this);
        buyingWindow.setVisible(false);

        popularityWindow = new PopularityWindow(skin1, 0, 0, 0, 0, game, this);
        popularityWindow.setVisible(false);

        changeRateWindow = new ChangeRateWindow(skin1, game, this);
        changeRateWindow.setVisible(false);

        buyingUnitWindow = new BuyingUnitWindow(skin1, game, this);
        buyingUnitWindow.setVisible(false);

        soldierControlWindow = new SoldierControlWindow(skin1, game, this);
        soldierControlWindow.setVisible(false);

        miniMapImage = new Image(miniMapFrameRegion);


        currentRunningWindow = buildingSelectWindow;
        uiStage.addActor(buildingSelectWindow);
        uiStage.addActor(currentPlayerWindow);
        uiStage.addActor(cellDetailWindow);
        uiStage.addActor(miniWindow);
        uiStage.addActor(buildingWindow);
        uiStage.addActor(storageWindow);
        uiStage.addActor(shopWindow);
        uiStage.addActor(buyingWindow);
        uiStage.addActor(popularityWindow);
        uiStage.addActor(changeRateWindow);

        uiStage.addActor(buyingUnitWindow);
        uiStage.addActor(soldierControlWindow);

        uiStage.addActor(miniMapImage);

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
//        if (input.isKeyPressed(Input.Keys.N))
//            camera.rotateAround(, Vector3.Y, -dt * 180);
//        if (input.isKeyPressed(Input.Keys.M))
//            camera.rotate(new Vector3(0, 1, 0), dt * 180);
        camera.update();
        miniMapCamera.position.set(camera.position);
        miniMapCamera.position.add(minimapDist, minimapDist, minimapDist);
        miniMapCamera.update();


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
/////////////////////////////////////////////////////////////////////////////////////////
        Cell currentCell = Util.getMouseCell(game);

        if (input.isTouched() && input.getY() < 3 * uiStage.getHeight() / 4) {
            if (!input.isKeyPressed(Input.Keys.SHIFT_LEFT) && !input.isKeyPressed(Input.Keys.SHIFT_RIGHT))
                resetSelection();
            if (currentCell != null && currentCell != lastSelectedCell) {
                for (GameObject gameObject : currentCell.getGameObjects()) {
                    if (gameObject instanceof Building) {
                        if (((Building) gameObject).getBuildingType().equals(BuildingType.TOWN_BUILDING)) {
                            Kingdom kingdom = game.getKingdom(getCurUser());
                            popularityWindow.reset(kingdom.getFoodRate(), kingdom.getFearRate(), kingdom.getTax(), 0);
                            setCurrentRunningWindow(popularityWindow);
                        } else {
                            buildingWindow.changeBuilding((Building) gameObject);
                            setCurrentRunningWindow(buildingWindow);
                        }
                        break;
                    }
                }
            }
            lastSelectedCell = currentCell;
            if (currentCell != null && !selectedCells.contains(currentCell)) {
                selectedCells.add(currentCell);
                CellRenderer cellRenderer = Util.getMouseCellRenderer(currentCell.getX(), currentCell.getY(), gameRenderer);
                cellRenderer.getDecal().setColor(Color.GREEN);
            }
        }
        if (input.isKeyPressed(Input.Keys.Z)) {
            resetSelection();
        }
        if (input.isKeyPressed(Input.Keys.S) && lastSelectedCell != null) {
            ArrayList<Soldier> soldiers = new ArrayList<>();
            for (Cell cell : selectedCells) {
                for (GameObject gameObject : cell.getGameObjects())
                    if (gameObject instanceof Soldier)
                        soldiers.add((Soldier) gameObject);
            }
            soldierControlWindow.makeWindow(game, soldiers);
            setCurrentRunningWindow(soldierControlWindow);
        }


        if (currentCell != null && time - cellDetailWindow.lastRemakeTime >= 1) {
            int x = currentCell.getX();
            int y = currentCell.getY();
            cellDetailWindow.remake(GameMenuController.showMapDetails(game, x, y));
            cellDetailWindow.lastRemakeTime = time;
        }

        if (time - miniWindow.lastChangeTime >= 1) {
            Kingdom curKingdom = game.getKingdom(getCurUser());
            miniWindow.remake(curKingdom.getPopularity(), curKingdom.getPopulation(), curKingdom.getPopulationCapacity(), curKingdom.getGold());
        }


        decalBatch.flush();

        buildingSelectWindow.setWidth(uiStage.getWidth() * 3 / 5);
        buildingSelectWindow.setHeight(uiStage.getHeight() / 4);


        currentPlayerWindow.setPosition(0, uiStage.getHeight() - 100);
        currentPlayerWindow.setWidth(100);
        currentPlayerWindow.setHeight(100);

        cellDetailWindow.setWidth(200);
        cellDetailWindow.setHeight(200);
        cellDetailWindow.setPosition(uiStage.getWidth() - 200, uiStage.getHeight() - 200);

        miniWindow.setHeight(uiStage.getHeight() / 4);
        miniWindow.setWidth(200);
        miniWindow.setPosition(uiStage.getWidth() - miniWindow.getWidth(), 0);

        miniMapImage.setHeight(uiStage.getHeight() / 4);
        miniMapImage.setWidth(1.0f * miniMapFrameRegion.getRegionWidth() / miniMapFrameRegion.getRegionHeight() * miniMapImage.getHeight());
        miniMapImage.setPosition(miniWindow.getX() - miniMapImage.getWidth(), 0);

        buildingWindow.setWidth(uiStage.getWidth() * 3 / 5);
        buildingWindow.setHeight(uiStage.getHeight() / 4);

        storageWindow.setWidth(uiStage.getWidth() * 3 / 5);
        storageWindow.setHeight(uiStage.getHeight() / 4);

        shopWindow.setWidth(uiStage.getWidth() * 3 / 5);
        shopWindow.setHeight(uiStage.getHeight() / 4);

        buyingWindow.setWidth(uiStage.getWidth() * 3 / 5);
        buyingWindow.setHeight(uiStage.getHeight() / 4);

        popularityWindow.setWidth(uiStage.getWidth() * 3 / 5);
        popularityWindow.setHeight(uiStage.getHeight() / 4);

        changeRateWindow.setWidth(uiStage.getWidth() * 3 / 5);
        changeRateWindow.setHeight(uiStage.getHeight() / 4);

        buyingUnitWindow.setWidth(uiStage.getWidth() * 3 / 5);
        buyingUnitWindow.setHeight(uiStage.getHeight() / 4);

        soldierControlWindow.setWidth(uiStage.getWidth() * 3 / 5);
        soldierControlWindow.setHeight(uiStage.getHeight() / 4);

        uiStage.draw();
    }

    private void resetSelection() {
        for (Cell cell : selectedCells) {
            CellRenderer cellRenderer = Util.getMouseCellRenderer(cell.getX(), cell.getY(), gameRenderer);
            cellRenderer.getDecal().setColor(Color.WHITE);
        }
        selectedCells.clear();
        lastSelectedCell = null;
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

    public void dropKingdoms() {
        ArrayList<User> allUsers = new ArrayList<>();
        for (Kingdom kingdom : game.getKingdoms())
            allUsers.add(kingdom.getUser());
        inputProcessor = new InputProcessor(allUsers);

        gameRenderer = new GameRenderer(game, inputProcessor);
        for (int i = 0; i < game.getKingdoms().size(); i++) {
            int x = (i % 4) * 15;
            int y = (i / 2) * 30;
            Kingdom kingdom = game.getKingdoms().get(i);
            User user = kingdom.getUser();

            inputProcessor.submitCommandToServer(new CreateBuildingCommand(user, BuildingType.TOWN_BUILDING, x, y));
            inputProcessor.submitCommandToServer(new CreateBuildingCommand(user, BuildingType.STOCKPILE, x + 1, y));
            inputProcessor.submitCommandToServer(new CreateBuildingCommand(user, BuildingType.UNEMPLOYED_PLACE, x, y + 1));
            inputProcessor.submitCommandToServer(new InitResourceCommand(user));

            inputProcessor.submitCommandToServer(new EndTurnCommand(user));
        }
    }

    public void nextPlayer() {

        inputProcessor.submitCommandToServer(new EndTurnCommand(getCurUser()));
        curUser++;
        curUser = curUser % game.getKingdoms().size();
        currentPlayerWindow.makeWindow(getCurUser());
        System.out.println(getCurUser().getNickname());

        resetSelection();

    }

    public User getCurUser() {
        return game.getKingdoms().get(curUser).getUser();
    }

    public void makeBuilding(BuildingType buildingType) {
        System.out.println(buildingType.GetName());
        if (lastSelectedCell != null)
            inputProcessor.submitCommandToServer(new CreateBuildingCommand(getCurUser(), buildingType, lastSelectedCell.getX(), lastSelectedCell.getY()));
    }

    public void setCurrentRunningWindow(Window currentRunningWindow) {
        this.currentRunningWindow.setVisible(false);
        this.currentRunningWindow = currentRunningWindow;
        this.currentRunningWindow.setVisible(true);
    }
}
