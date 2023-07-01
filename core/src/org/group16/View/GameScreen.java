//package org.group16.View;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import org.group16.Model.*;
//import org.group16.Model.Buildings.EconomicBuilding;
//import org.group16.Model.Buildings.EconomicBuildingDetail;
//import org.group16.Model.People.Soldier;
//import org.group16.Model.People.SoldierDetail;
//import org.group16.StrongholdGame;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GameScreen extends Menu{
//
//    Skin skin2 = new Skin(Gdx.files.internal("neon/skin/default.json"));
//    Skin skin1 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));
//
//    BuildingSelectWindow buildingSelectWindow = new BuildingSelectWindow( skin2 , game ) ;
//    ShopWindow shopWindow = new ShopWindow("" , skin1 , uiStage) ;
//
//    PopularityWindow popularityWindow = new PopularityWindow(skin1 , 0 , 0 , 0 , 0) ;
//
//    ChangeRateWindow changeRateWindow = new ChangeRateWindow("" , skin1) ;
//    CellDetailWindow cellDetailWindow = new CellDetailWindow("" , skin1 , "hello \n hi\n oooo") ;
//
//    BuildingWindow buildingWindow ;
//
//    BuyingUnitWindow buyingUnitWindow = new BuyingUnitWindow(skin1 , EconomicBuildingDetail.BARRACKS) ;
//
//    StorageWindow storageWindow ;
//
//    MiniWindow miniWindow = new MiniWindow(skin1 , 63 , 23 , 26 , 2435) ;
//
//
//
//    public GameScreen(StrongholdGame game) {
//        super(game);
//        uiStage.clear();
//
//
//
////        uiStage.addActor(buildingSelectWindow);
//       uiStage.addActor(shopWindow);
//        uiStage.addActor(shopWindow.buyingWindow);
//       shopWindow.buyingWindow.setPosition(0 , 0);
////        uiStage.addActor(popularityWindow);
////        uiStage.addActor(cellDetailWindow);
////        uiStage.addActor(changeRateWindow);
//
//        //uiStage.addActor(buildingWindow);
//
//        {
//            Game game1 ;
//            Scene scene;
//            Kingdom k1, k2;
//            User.addUser("player1", "pass", "email",
//                    "q1", "a1", "playerA", "slog");
//
//            User.addUser("player2", "pass", "email",
//                    "q1", "a1", "playerB", "slog");
//
//            User user = User.getUserByName("player1");
//            User user1 = User.getUserByName("player2");
//
//            game1 = new Game(KingdomType.ARAB, user);
//            game1.addUser(user1, KingdomType.EUROPEAN);
//            Map map = new Map("map0", 5, 5);
//            Map.saveMap(map);
//            scene = new Scene(Map.getMapByName("map0") , 543536);
//            game1.setScene(scene);
//            k1 = game1.getKingdom(user);
//            k2 = game1.getKingdom(user1);
//
//
//            Soldier king1 = new Soldier(new ArrayList<>(List.of(scene.getCellAt(0, 0))), k1, SoldierDetail.KING);
//            k1.setKing(king1);
//            Soldier king2 = new Soldier(new ArrayList<>(List.of(scene.getCellAt(4, 4))), k2, SoldierDetail.KING);
//            k2.setKing(king2);
//
//            EconomicBuilding building = new EconomicBuilding(new ArrayList<>(List.of(scene.getCellAt(0 , 0 ))) , k1 , 0 , EconomicBuildingDetail.ARMOURY) ;
//
//            buildingWindow = new BuildingWindow(skin1 , building) ;
//            storageWindow = new StorageWindow(skin1 , building) ;
//           // uiStage.addActor(buildingWindow);
//        }
//
//       // uiStage.addActor(buyingUnitWindow);
//        uiStage.addActor(storageWindow);
////        uiStage.addActor(miniWindow);
//    }
//
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        uiStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//        buildingSelectWindow.setWidth(uiStage.getWidth()*3/5);
//        buildingSelectWindow.setHeight(uiStage.getHeight()/4);
//
//        shopWindow.setHeight(uiStage.getHeight()/4);
//        shopWindow.setWidth(uiStage.getWidth()*3/5);
//
//        popularityWindow.setHeight(uiStage.getHeight()/4);
//        popularityWindow.setWidth(uiStage.getWidth()*3/5);
//
//        changeRateWindow.setHeight(uiStage.getHeight()/4);
//        changeRateWindow.setWidth(uiStage.getWidth()*3/5);
//
//        cellDetailWindow.setHeight(200);
//        cellDetailWindow.setWidth(200);
//        cellDetailWindow.setPosition(uiStage.getWidth()-cellDetailWindow.getWidth(), uiStage.getHeight()-cellDetailWindow.getHeight() );
//
//        buildingWindow.setHeight(uiStage.getHeight()/4);
//        buildingWindow.setWidth(uiStage.getWidth()*3/5);
//
//        buyingUnitWindow.setHeight(uiStage.getHeight()/4);
//        buyingUnitWindow.setWidth(uiStage.getWidth()*3/5);
//
//        storageWindow.setHeight(uiStage.getHeight()/4);
//        storageWindow.setWidth(uiStage.getWidth()*3/5);
//
//        miniWindow.setHeight(uiStage.getHeight()/4);
//        miniWindow.setWidth(300);
//        miniWindow.setPosition(uiStage.getWidth()-miniWindow.getWidth(), 0);
//
//        uiStage.draw();
//    }
//}
