package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;
import org.group16.Controller.GameMenuController;
import org.group16.GameGraphics.*;
import org.group16.GameGraphics.CommandHandling.CreateBuildingCommand;
import org.group16.GameGraphics.CommandHandling.EndTurnCommand;
import org.group16.GameGraphics.CommandHandling.InitResourceCommand;
import org.group16.GameGraphics.CommandHandling.InputProcessor;
import org.group16.Model.*;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.People.Soldier;
import org.group16.Model.People.SoldierDetail;
import org.group16.StrongholdGame;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Gdx.*;
import static com.badlogic.gdx.Gdx.gl;


public class testingGameScreen extends Menu{
    ////////////////////////////////////// render stuff
    private final List<Renderer> renderers = new ArrayList<>();
    public AssetManager assetManager;
    float time = 0;
    GameRenderer gameRenderer;
    private Camera camera, miniMapCamera;
    private DecalBatch decalBatch, miniMapDecalBatch;
    private FrameBuffer miniMapFrameBuffer;
    private TextureRegion miniMapFrameRegion;
    private long lastFrame = TimeUtils.millis();
    private DetailRenderer testProbe;
    private Renderer miniMapPreview;

    InputProcessor inputProcessor ;
    org.group16.Model.Game game;
    ///////////////////////////////////////////////////
    int curUser = 0 ;

    ArrayList<Cell> selectedCells = new ArrayList<>() ;
    Cell lastSelectedCell = null ;
    //////////////////////////////////////////////////

    Skin skin2 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    Skin skin1 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));

    BuildingSelectWindow buildingSelectWindow ;
    CurrentPlayerWindow currentPlayerWindow ;
    CellDetailWindow cellDetailWindow ;
    MiniWindow miniWindow ;
    public testingGameScreen(StrongholdGame game1 , Game game) {
        super(game1);
        this.game = game ;

        camera = new PerspectiveCamera(30, 1f, 1f * graphics.getHeight() / graphics.getWidth());
        miniMapCamera = new PerspectiveCamera(30, 5f, 3);
        miniMapFrameBuffer = new FrameBuffer(Pixmap.Format.RGB565, 500, 300, false);
        miniMapFrameRegion = new TextureRegion(miniMapFrameBuffer.getColorBufferTexture());
        miniMapFrameRegion.flip(false, true);
//        camera = new OrthographicCamera(1, 1f * graphics.getHeight() / graphics.getWidth());
//        ((OrthographicCamera) camera).zoom = 10;
        camera.position.set(3f, 3f, 3f);
        camera.lookAt(0f, 0f, 0f);
        miniMapCamera.position.set(1f, 1f, 1f);
        miniMapCamera.lookAt(0f, 0f, 0f);
        miniMapCamera.position.set(30f, 18f, 30f);
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


        dropKingdoms();
//        initGameObjects();

        renderers.add(gameRenderer);

//        ArrayList<Soldier> list1 = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            Soldier soldier = new Soldier(List.of(scene.getCellAt(0, 1)), k1, SoldierDetail.MACE_MAN);
//            gameRenderer.createRenderer(soldier);
//            list1.add(soldier);
//        }
//        ArrayList<Soldier> list2 = new ArrayList<>();
//        for (int i = 0; i < 15; i++) {
//            Soldier soldier = new Soldier(List.of(scene.getCellAt(10, 10)), k2, SoldierDetail.ASSASSIN);
//            gameRenderer.createRenderer(soldier);
//            list2.add(soldier);
//        }
//        new WarCommand(list1, k2.getKing());
//        new WarCommand(list2, k1.getKing());
        currentPlayerWindow = new CurrentPlayerWindow(game.getKingdoms().get(0).getUser() , skin1 , game , this) ;
        buildingSelectWindow = new BuildingSelectWindow( skin2 , game , this) ;
        cellDetailWindow = new CellDetailWindow("" , skin1 , "") ;
        miniWindow = new MiniWindow(skin1 ,0 , 0 , 0 , 0 ) ;
        uiStage.addActor(buildingSelectWindow);
        uiStage.addActor(currentPlayerWindow);
        uiStage.addActor(cellDetailWindow);
        uiStage.addActor(miniWindow);
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
            camera.position.add(-5*dt, -5*dt, -5*dt);
        if (input.isKeyPressed(Input.Keys.O))
            camera.position.add(5*dt, 5*dt, 5*dt);
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

        Cell currentCell = Util.getMouseCell(game) ;

        if (input.isTouched()&&input.getY() < 3*uiStage.getHeight()/4){
            lastSelectedCell = currentCell ;
            if (currentCell!=null && !selectedCells.contains(currentCell)) {
                selectedCells.add(currentCell);
                CellRenderer cellRenderer = Util.getMouseCellRenderer(currentCell.getX() , currentCell.getY() , gameRenderer) ;
                cellRenderer.getDecal().setColor(Color.GREEN);
            }
        }
        if (input.isKeyPressed(Input.Keys.Z)){
            for (Cell cell : selectedCells){
                CellRenderer cellRenderer = Util.getMouseCellRenderer(cell.getX() , cell.getY() , gameRenderer) ;
                cellRenderer.getDecal().setColor(Color.WHITE);
            }
            selectedCells.clear();
            lastSelectedCell = null ;
        }

        if (currentCell!=null && time - cellDetailWindow.lastRemakeTime>=1){
            int x = currentCell.getX() ;
            int y = currentCell.getY() ;
            cellDetailWindow.remake(GameMenuController.showMapDetails(game , x , y));
            cellDetailWindow.lastRemakeTime = time ;
        }

        if (time - miniWindow.lastChangeTime >=1){
            Kingdom curKingdom = game.getKingdom(getCurUser()) ;
            miniWindow.remake(curKingdom.getPopularity() , curKingdom.getPopulation() , curKingdom.getPopulationCapacity() , curKingdom.getGold());
        }


        decalBatch.flush();

        buildingSelectWindow.setWidth(uiStage.getWidth()*3/5);
        buildingSelectWindow.setHeight(uiStage.getHeight()/4);

        currentPlayerWindow.setPosition(0 , uiStage.getHeight()-100);
        currentPlayerWindow.setWidth(100);
        currentPlayerWindow.setHeight(100);

        cellDetailWindow.setWidth(200);
        cellDetailWindow.setHeight(200);
        cellDetailWindow.setPosition(uiStage.getWidth()-200 , uiStage.getHeight()-200);

        miniWindow.setHeight(uiStage.getHeight()/4);
        miniWindow.setWidth(200);
        miniWindow.setPosition(uiStage.getWidth() - miniWindow.getWidth() , 0);

        uiStage.draw();
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
    public void dropKingdoms(){
        ArrayList<User> allUsers = new ArrayList<>() ;
        for (Kingdom kingdom : game.getKingdoms())
            allUsers.add(kingdom.getUser());
        inputProcessor = new InputProcessor(allUsers) ;

        gameRenderer = new GameRenderer(game , inputProcessor);
        for (int i = 0 ; i < game.getKingdoms().size() ; i++){
            int x = (i%4)*15 ;
            int y = (i/2)*30 ;
            Kingdom kingdom = game.getKingdoms().get(i) ;
            User user = kingdom.getUser() ;

            inputProcessor.submitCommand(new CreateBuildingCommand(user , BuildingType.TOWN_BUILDING , x , y));
            inputProcessor.submitCommand(new CreateBuildingCommand(user , BuildingType.STOCKPILE , x+1 , y));
            inputProcessor.submitCommand(new CreateBuildingCommand(user , BuildingType.UNEMPLOYED_PLACE , x , y+1));
            inputProcessor.submitCommand(new InitResourceCommand(user));

            inputProcessor.submitCommand(new EndTurnCommand(user));
        }
    }

    public void nextPlayer(){

        inputProcessor.submitCommand(new EndTurnCommand(getCurUser()));
        curUser++ ;
        curUser=curUser%game.getKingdoms().size() ;
        currentPlayerWindow.makeWindow(getCurUser());
        System.out.println(getCurUser().getNickname());

        for (Cell cell : selectedCells){
            CellRenderer cellRenderer = Util.getMouseCellRenderer(cell.getX() , cell.getY() , gameRenderer) ;
            cellRenderer.getDecal().setColor(Color.WHITE);
        }
        selectedCells.clear();
        lastSelectedCell = null ;

    }

    public User getCurUser(){
        return game.getKingdoms().get(curUser).getUser() ;
    }

    public void makeBuilding(BuildingType buildingType){
        System.out.println(buildingType.GetName());
        if (lastSelectedCell!=null)
            inputProcessor.submitCommand(new CreateBuildingCommand(getCurUser(), buildingType,lastSelectedCell.getX() , lastSelectedCell.getY() ));
    }



}
