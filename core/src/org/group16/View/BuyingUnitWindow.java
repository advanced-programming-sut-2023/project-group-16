package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.group16.GameGraphics.CommandHandling.CreateSoldierCommand;
import org.group16.Model.Buildings.EconomicBuilding;
import org.group16.Model.Buildings.EconomicBuildingDetail;
import org.group16.Model.Game;
import org.group16.Model.People.SoldierDetail;
import org.group16.Model.Resources.ProductData;

import java.util.ArrayList;

public class BuyingUnitWindow extends Window {
    final int xUp = 120, yUp = 120, xDown = 115, yDown = 115;
    public Skin skin;
    Stage uistage;
    Image soilBackground, grayBackGround;
    int numberOfPics;
    ImageButton back;

    Label status;

    EconomicBuildingDetail economicBuildingDetail;
    ArrayList<SoldierDetail> soldierDetails = new ArrayList<>();
    int startPic = 0;

    Game game;
    testingGameScreen gameScreen;


    public BuyingUnitWindow(Skin skin, Game game, testingGameScreen gameScreen) {
        super("", skin);
        this.skin = skin;
        this.game = game;
        this.gameScreen = gameScreen;

        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));
    }

    public void remake(EconomicBuilding economicBuilding) {
        this.clear();
        this.economicBuildingDetail = economicBuilding.getDetail();
        soldierDetails.clear();
        for (ProductData productData : economicBuildingDetail.getProductsData()) {
            soldierDetails.add((SoldierDetail) productData.resource());
        }
        numberOfPics = soldierDetails.size();

        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));
        this.background(soilBackground.getDrawable());

        status = new Label("", skin);
        this.add(status).row();

        for (int i = 0; i < numberOfPics; i++) {
            try {
                SoldierDetail soldierDetail = soldierDetails.get(i);
                ImageButton imageButton = new ImageButton(skin);
                ImageButton.ImageButtonStyle imageStyle = new ImageButton.ImageButtonStyle();
                imageStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("MenuPictures/Soldiers/" + soldierDetail.getKingdomType() + "/" + soldierDetail.GetName() + ".png").path(), xUp, yUp));
                imageStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("MenuPictures/Soldiers/" + soldierDetail.getKingdomType() + "/" + soldierDetail.GetName() + ".png").path(), xDown, yDown));
                imageButton.setStyle(imageStyle);

                this.add(imageButton);

                imageButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        status.setText(soldierDetail.GetName());
                        gameScreen.inputProcessor.submitCommandToServer(new CreateSoldierCommand(gameScreen.getCurUser(), economicBuilding, soldierDetail, 1));
                    }
                });
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        this.row();

        back = new ImageButton(skin);
        ImageButton.ImageButtonStyle imageStyle = new ImageButton.ImageButtonStyle();
        imageStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("ButtonImages/BackButton.png").path(), 30, 30));
        imageStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("ButtonImages/BackButton.png").path(), 27, 27));
        back.setStyle(imageStyle);
        this.add(back);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.setCurrentRunningWindow(gameScreen.buyingWindow);
            }
        });
    }

}
