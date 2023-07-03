package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Buildings.EconomicBuilding;
import org.group16.Model.Buildings.EconomicBuildingDetail;
import org.group16.Model.Buildings.WarBuilding;
import org.group16.Model.Game;
import org.w3c.dom.Text;

public class BuildingWindow extends Window {
    Skin skin;
    Label name, status;
    TextButton shop, seeStorage, buildUnit, repair, makeOnOff , trade;

    ImageButton back;
    Image soilBackground;
    Building building;
    Game game ;
    testingGameScreen gameScreen ;

    public BuildingWindow(Skin skin , Game game , testingGameScreen gameScreen) {
        super("", skin);

        this.game = game ;
        this.gameScreen = gameScreen ;

        this.skin = skin;

        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));
        this.background(soilBackground.getDrawable());
    }


    public void makeWindow() {
        System.out.println("hi");
        this.clear();
        this.background(soilBackground.getDrawable());
        shop = new TextButton("shop", skin);
        seeStorage = new TextButton("storage", skin);
        buildUnit = new TextButton("build unit", skin);
        repair = new TextButton("repair", skin);
        trade = new TextButton("trade" , skin) ;
        makeOnOff = new TextButton("off", skin);

        back = new ImageButton(skin);
        ImageButton.ImageButtonStyle imageStyle = new ImageButton.ImageButtonStyle();
        imageStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("ButtonImages/BackButton.png").path(), 30, 30));
        imageStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("ButtonImages/BackButton.png").path(), 27, 27));
        back.setStyle(imageStyle);


        name = new Label("", skin);
        status = new Label("", skin);
        name.setText(building.getBuildingType().GetName().toLowerCase());
        if (building instanceof WarBuilding || ((EconomicBuilding) building).isActive()) {
            status.setText("on");
            makeOnOff.setText("off");
        } else {
            status.setText("off");
            makeOnOff.setText("on");
        }
        this.add(name).row();
        this.add(status).pad(0, 0, 0, 10);
        this.add(makeOnOff).row();
        if (building instanceof EconomicBuilding && ((EconomicBuilding) building).getDetail().equals(EconomicBuildingDetail.MARKET)) {
            this.add(shop).pad(0, 0 , 0 , 5);
            this.add(trade) ;
        }
        if (building instanceof EconomicBuilding && (((EconomicBuilding) building).getDetail().equals(EconomicBuildingDetail.MERCENARY_POST) ||
                ((EconomicBuilding) building).getDetail().equals(EconomicBuildingDetail.BARRACKS) ||
                ((EconomicBuilding) building).getDetail().equals(EconomicBuildingDetail.ENGINEER_GUILD)
        )) {
            this.add(buildUnit);
        }
        if (building instanceof EconomicBuilding && ((EconomicBuilding) building).getDetail().getStorageData().length > 0)
            this.add(seeStorage);
        this.add(repair).row();
        this.add(back);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.setCurrentRunningWindow(gameScreen.buildingSelectWindow) ;
            }
        });

        seeStorage.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.storageWindow.remake((EconomicBuilding) building);
                gameScreen.setCurrentRunningWindow(gameScreen.storageWindow) ;
            }
        });

        shop.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.setCurrentRunningWindow(gameScreen.shopWindow);
            }
        });

        buildUnit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.buyingUnitWindow.remake((EconomicBuilding) building);
                gameScreen.setCurrentRunningWindow(gameScreen.buyingUnitWindow);
            }
        });

        trade.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.setCurrentRunningWindow(gameScreen.tradeWindow);
            }
        }) ;
    }

    public void changeBuilding(Building building) {
        this.building = building;
        makeWindow();
    }

}
