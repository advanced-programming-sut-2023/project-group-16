package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Scaling;
import org.group16.Model.Buildings.EconomicBuildingDetail;
import org.group16.Model.Buildings.WarBuildingDetail;
import org.group16.StrongholdGame;


import java.util.ArrayList;
import java.util.Objects;

public class BuildingSelectWindow extends Window {

    Image soilBackground;

    final int numberOfPics = 5, xUp = 150, yUp = 150 , xDown=145 , yDown = 145;
    int startPic = 0;
    Skin skin;

    ImageButton economicButton, warButton , next , back;
    String currentType = "Economic";
    ImageButtonElement[] imageButtonElements;
    Label status ;
    Image image;

    public BuildingSelectWindow(String title, Skin skin) {
        super(title, skin);
        this.skin = skin;
        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));
        this.setBackground(soilBackground.getDrawable());

        imageButtonElements = new ImageButtonElement[numberOfPics];
        for (int i = 0; i < numberOfPics; i++)
            imageButtonElements[i] = new ImageButtonElement();

        economicButton = new ImageButton(skin) ;
        makeButtons(economicButton , "ButtonImages/EconomicButton.png" , "ButtonImages/EconomicButton.png");
        warButton = new ImageButton(skin) ;
        makeButtons(warButton , "ButtonImages/WarButton.png" , "ButtonImages/WarButton.png");
        next = new ImageButton(skin) ;
        makeButtons(next, "ButtonImages/NextButton.png", "ButtonImages/NextButton.png");
        back = new ImageButton(skin) ;
        makeButtons(back, "ButtonImages/BackButton.png", "ButtonImages/BackButton.png");
        status = new Label("" , skin) ;
        status.setColor(Color.BLACK);
        remake();

        warButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startPic = 0;
                currentType = "War";
                remake();
            }
        });

        economicButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startPic = 0;
                currentType = "Economic";
                remake();
            }
        });
        next.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startPic+=numberOfPics ;
                remake();
            }
        });
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startPic-=numberOfPics ;
                if(startPic < 0)
                    startPic = 0 ;
                remake();
            }
        });
        this.add(status).row();
        this.add(back).pad(0 , 0 , 0 , 5) ;
        for (int i = 0; i < numberOfPics; i++)
            this.add(imageButtonElements[i].imageButton);
        this.add(next).pad(0 , 0 , 5 , 0) ;
        this.add().row();
        this.add(economicButton).pad(0, 0, 0, 5);
        this.add(warButton);

    }

    public void makeButtons(ImageButton imageButton, String up, String down) {
        try {
            ImageButton.ImageButtonStyle imageStyle = new ImageButton.ImageButtonStyle();
            imageStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal(up).path(), xUp/3, yUp/3));
            imageStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal(down).path(), xDown/3, yDown/3));
            imageButton.setStyle(imageStyle);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public class ImageButtonElement {

        ImageButton imageButton;
        ImageButton.ImageButtonStyle imageButtonStyle ;
        String name;

        public ImageButtonElement() {
            imageButtonStyle = new ImageButton.ImageButtonStyle() ;
            imageButtonStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("backgrounds/soilBackground.jpg").path(), xUp, yUp)) ;
            imageButtonStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("backgrounds/soilBackground.jpg").path(), xDown, yDown)) ;
            imageButton = new ImageButton(imageButtonStyle) ;

            name = "null";

            imageButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    status.setText(name);
                }
            }) ;
        }

        public void setNewBuilding(String name, String kind) {
            this.name = name;
            if (kind.equals("null")) {
                imageButtonStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("backgrounds/soilBackground.jpg").path(), xUp, yUp)) ;
                imageButtonStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("backgrounds/soilBackground.jpg").path(), xDown, yDown)) ;
                name = "null";
            } else {
                try {
                    imageButtonStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("MenuPictures/Buildings/" + kind + "/" + name + ".png").path(), xUp, yUp)) ;
                    imageButtonStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("MenuPictures/Buildings/" + kind + "/" + name + ".png").path(), xDown, yDown)) ;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void remake() {
        int i2 = numberOfPics;
        if (currentType.equals("Economic")) {
            if (startPic > EconomicBuildingDetail.values().length)
                startPic-=numberOfPics ;
            for (int i = startPic; i < startPic + numberOfPics && i < EconomicBuildingDetail.values().length; i++) {
                imageButtonElements[i - startPic].setNewBuilding(EconomicBuildingDetail.values()[i].name(), "Economic");
                i2 = i - startPic ;
            }
        } else {
            if (startPic > WarBuildingDetail.values().length)
                startPic-=numberOfPics ;
            for (int i = startPic; i < startPic + numberOfPics && i < WarBuildingDetail.values().length; i++) {
                imageButtonElements[i - startPic].setNewBuilding(WarBuildingDetail.values()[i].name(), "War");
                i2 = i - startPic ;
            }
        }
        i2++ ;
        for (;i2 < numberOfPics ; i2++)
            imageButtonElements[i2].setNewBuilding("null" , "null");
    }

}
