package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.group16.Model.Buildings.EconomicBuilding;
import org.group16.Model.Buildings.EconomicBuildingDetail;
import org.group16.Model.Resources.*;

import java.util.ArrayList;

public class StorageWindow extends Window {
    Stage uistage;
    Image soilBackground, grayBackGround;
    public Skin skin;
    final int numberOfPicsInRow = 10, xUp = 120, yUp = 120, xDown = 115, yDown = 115;
    int numberOfPics;
    ImageButton back;
    EconomicBuilding economicBuilding;
    EconomicBuildingDetail economicBuildingDetail;

    ArrayList<Resource> resources = new ArrayList<>();

    public StorageWindow(Skin skin, EconomicBuilding economicBuilding) {
        super("", skin);
        this.skin = skin;
        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));
        this.background(soilBackground.getDrawable());
        remake(economicBuilding);
    }


    public void remake(EconomicBuilding economicBuilding) {
       this.clear();


        this.economicBuilding = economicBuilding;
        this.economicBuildingDetail = economicBuilding.getDetail();

        resources.clear();
        for (StorageData storageData : economicBuildingDetail.getStorageData())
            resources.add(storageData.resource());

        numberOfPics = resources.size();

        int cnt = 0;
        for (int i = 0; i < numberOfPics; i++) {
            if ((cnt + 1) % numberOfPicsInRow == 0)
                this.row();
            try {
                Resource resource = resources.get(i);
                Image image = new Image(picChange.changer("MenuPictures/Resources/" + getPathByType(resource) + "/" + resource.GetName() + ".png", 60, 60));
                Label number = new Label(" : " + economicBuilding.getKingdom().getResourceCount(resource), skin);
                this.add(image);
                this.add(number).pad(0, 0, 0, 5);
                cnt++;
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


    }

    public String getPathByType(Resource resource) {
        if (resource instanceof BasicResource)
            return "BASIC";
        else if (resource instanceof Food)
            return "FOOD";
        else
            return "WEAPONRY";
    }

}
