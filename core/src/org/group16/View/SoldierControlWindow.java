package org.group16.View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.group16.Model.People.Soldier;
import org.group16.Model.People.SoldierDetail;
import org.w3c.dom.ls.LSException;

import java.util.ArrayList;


public class SoldierControlWindow extends Window {
    Image soilBackground, grayBackGround;

    ImageButton attackButton, disbandButton, patrolButton, defensiveButton, offensiveButton, standingButton , back;

    public Skin skin;

    public SoldierControlWindow(Skin skin, Game game, ArrayList<Soldier> unit) {
        super("", skin);

        this.skin = skin;

        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));
        this.background(soilBackground.getDrawable());


        back = new ImageButton(skin);
        ImageButton.ImageButtonStyle imageStyle = new ImageButton.ImageButtonStyle();
        imageStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("ButtonImages/BackButton.png").path(), 30, 30));
        imageStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("ButtonImages/BackButton.png").path(), 27, 27));
        back.setStyle(imageStyle);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //TODO
            }
        });
    }


    public void makeWindow(Game game, ArrayList<Soldier> unit) {
        this.clear();
        ArrayList<SoldierDetail> soldierDetails = new ArrayList<>();
        for (Soldier soldier : unit) {
            if (!soldierDetails.contains(soldier.getSoldierDetail()))
                soldierDetails.add(soldier.getSoldierDetail());
        }

        for (SoldierDetail soldierDetail : soldierDetails) {
            Image soldierImage = new Image(picChange.changer("MenuPictures/Soldiers/" + soldierDetail.getKingdomType() + "/" + soldierDetail.GetName() + ".png", 100, 100));
            Label erase = new Label("-", skin);
            this.add(soldierImage);
            this.add(erase);

            erase.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    //TODO

                    //makeWindow();
                }
            });
        }

        attackButton = new ImageButton(skin);
        makeButtons(attackButton, "ButtonImages/Attack.png", "ButtonImages/Attack.png");
        disbandButton = new ImageButton(skin);
        makeButtons(disbandButton, "ButtonImages/Disband.png", "ButtonImages/Disband.png");
        patrolButton = new ImageButton(skin);
        makeButtons(patrolButton, "ButtonImages/Patrol.png", "ButtonImages/Patrol.png");
        defensiveButton = new ImageButton(skin);
        makeButtons(defensiveButton, "ButtonImages/Defensive.png", "ButtonImages/Defensive.png");
        offensiveButton = new ImageButton(skin);
        makeButtons(offensiveButton, "ButtonImages/Offensive.png", "ButtonImages/Offensive.png");
        standingButton = new ImageButton(skin);
        makeButtons(standingButton, "ButtonImages/Standing.png", "ButtonImages/Standing.png");

        this.row();
        this.add(standingButton).pad(0, 0, 0, 5);
        this.add(defensiveButton).pad(0, 0, 0, 5);
        this.add(offensiveButton).row();
        this.add(attackButton).pad(0, 0, 0, 5);
        this.add(disbandButton).pad(0, 0, 0, 5);
        this.add(patrolButton).row();

        this.add(back).row();


        standingButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        defensiveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        offensiveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        attackButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        disbandButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        patrolButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

    }


    public void makeButtons(ImageButton imageButton, String up, String down) {
        try {
            ImageButton.ImageButtonStyle imageStyle = new ImageButton.ImageButtonStyle();
            imageStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal(up).path(), 30, 30));
            imageStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal(down).path(), 27, 27));
            imageButton.setStyle(imageStyle);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
