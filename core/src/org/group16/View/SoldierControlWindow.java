package org.group16.View;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.group16.GameGraphics.CommandHandling.*;
import org.group16.Model.Cell;
import org.group16.Model.Game;
import org.group16.Model.People.Soldier;
import org.group16.Model.People.SoldierDetail;
import org.group16.Model.WarCommand;
import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.Iterator;


public class SoldierControlWindow extends Window {
    Image soilBackground, grayBackGround;

    ImageButton attackButton, disbandButton, patrolButton, defensiveButton, offensiveButton, standingButton , back;

    public Skin skin;

    Game game ;
    testingGameScreen gameScreen ;

    public SoldierControlWindow(Skin skin, Game game,testingGameScreen gameScreen ) {
        super("", skin);

        this.skin = skin;

        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));

        this.game = game ;
        this.gameScreen = gameScreen ;
    }


    public void makeWindow(Game game, ArrayList<Soldier> unit) {
        this.clear();
        this.background(soilBackground.getDrawable());


        back = new ImageButton(skin);
        ImageButton.ImageButtonStyle imageStyle = new ImageButton.ImageButtonStyle();
        imageStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("ButtonImages/BackButton.png").path(), 30, 30));
        imageStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("ButtonImages/BackButton.png").path(), 27, 27));
        back.setStyle(imageStyle);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.setCurrentRunningWindow(gameScreen.buildingSelectWindow);
            }
        });
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
                    unit.removeIf(soldier -> soldier.getSoldierDetail().equals(soldierDetail));
                    makeWindow(game , unit);
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
                gameScreen.inputProcessor.submitCommand(new unitSetStatsCommand(gameScreen.getCurUser() , unit , WarCommand.Status.STAND_STILL));
            }
        });

        defensiveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.inputProcessor.submitCommand(new unitSetStatsCommand(gameScreen.getCurUser() , unit , WarCommand.Status.DEFENSIVE));
            }
        });

        offensiveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.inputProcessor.submitCommand(new unitSetStatsCommand(gameScreen.getCurUser() , unit , WarCommand.Status.OFFENSIVE));
            }
        });

        attackButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Cell cell = gameScreen.lastSelectedCell ;
                try {
                    gameScreen.inputProcessor.submitCommand(new unitAttackCommand(gameScreen.getCurUser() , unit , cell.getX() , cell.getY()));
                }catch (Exception e){
                    e.getMessage() ;
                }
            }
        });

        disbandButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.inputProcessor.submitCommand(new unitDisbandCommand(gameScreen.getCurUser() , unit));
            }
        });

        patrolButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    Cell cell1 = gameScreen.selectedCells.get(0) ;
                    Cell cell2 = gameScreen.selectedCells.get(1) ;
                    gameScreen.inputProcessor.submitCommand(new unitPatrolCommand(gameScreen.getCurUser() , unit , cell1.getX() , cell1.getY() , cell2.getX() , cell2.getY()));
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
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
