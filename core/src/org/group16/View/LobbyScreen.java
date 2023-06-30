package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.sun.tools.javac.Main;
import org.group16.Controller.GameMenuController;
import org.group16.Model.*;
import org.group16.Model.Buildings.BuildingType;
import org.group16.StrongholdGame;

import java.util.ArrayList;

public class LobbyScreen extends Menu {


    User user;
    Table table;
    private Image background, white, soilBackground;
    Skin skin2 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    Skin skin1 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));
    TextButton back, add, start;

    TextField usernameField;
    Label usernameStatus, startStatus;

    SelectBox<String> selectBox = new SelectBox<String>(skin1);
    SelectBox<String> mapSelectBox = new SelectBox<String>(skin1) ;

    Game realGame = new Game();

    public LobbyScreen(StrongholdGame game, User user) {
        super(game);
        uiStage.clear();


        this.user = user;

        white = new Image(new Texture(Gdx.files.internal("backgrounds/white.jpg")));
        background = new Image(new Texture(Gdx.files.internal("backgrounds/lobbyBackground.jpg")));
        background.setFillParent(true);
        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));

        table = new Table(skin1);
        table.setBackground(soilBackground.getDrawable());
        table.setSize(500, 400);

        selectBox.setItems("ARABIAN", "EUROPEAN");


        Array<String> mapArray = new Array<>() ;
        for (Map map : Map.getAllMaps()){
            mapArray.add(map.getName());
        }
        mapSelectBox.setItems(mapArray); ;

        uiStage.addActor(background);
        uiStage.addActor(table);
        remake();
    }

    public void remake() {
        table.clear();
        table.background(soilBackground.getDrawable());

        usernameField = new TextField("", skin1);
        usernameStatus = new Label("", skin1);
        usernameStatus.setColor(Color.RED);
        add = new TextButton("add", skin1);
        back = new TextButton("back", skin1);
        start = new TextButton("start", skin1);
        startStatus = new Label("", skin1);
        startStatus.setColor(Color.RED);

        table.add(usernameField).pad(0, 0, 0, 5);
        table.add(add).pad(0, 0, 0, 5);
        table.add(selectBox).pad(0, 0, 0, 5);
        table.add(usernameStatus).row();

        add.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (User.getUserByName(usernameField.getText()) == null) {
                    usernameStatus.setText("no user Found");
                } else if (realGame.getKingdom(User.getUserByName(usernameField.getText()))!=null) {
                    usernameStatus.setText("user already added");
                } else {
                    if (selectBox.getSelected().equals("ARABIAN"))
                        realGame.addUser(User.getUserByName(usernameField.getText()), KingdomType.ARAB);
                    else
                        realGame.addUser(User.getUserByName(usernameField.getText()), KingdomType.EUROPEAN);
                    remake();
                }
            }
        });

        int rowCounter = 1;
        for (Kingdom kingdom : realGame.getKingdoms()) {
            User user1 = kingdom.getUser();
            table.add(new Label(rowCounter + " : " + user1.getNickname(), skin1)).pad(0, 0, 0, 5);
            TextButton delButton = new TextButton("delete", skin1);
            table.add(delButton).row();
            delButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    realGame.removeUser(user1);
                    remake();
                }
            });
        }

        table.add(mapSelectBox).row();

        table.add(start);
        table.add(startStatus).row();
        table.add(back).row();

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainScreen(game, user));
            }
        });

        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (realGame.getKingdoms().size() < 2) {
                    startStatus.setText("not enough players");
                } else if (realGame.getKingdoms().size() >= 8) {
                    startStatus.setText("game is full");
                } else {
                    try {
                        realGame.setScene(new Scene(Map.getMapByName(mapSelectBox.getName()), 0));
                        GameMenuController.dropBuilding(realGame, realGame.getKingdoms().get(0).getUser(), 0, 0, BuildingType.TOWN_BUILDING);
                        GameMenuController.dropBuilding(realGame, realGame.getKingdoms().get(0).getUser(), 0, 1, BuildingType.UNEMPLOYED_PLACE);
                        GameMenuController.dropBuilding(realGame, realGame.getKingdoms().get(0).getUser(), 0, 2, BuildingType.STOCKPILE);
                        GameMenuController.dropBuilding(realGame, realGame.getKingdoms().get(0).getUser(), 10, 0, BuildingType.TOWN_BUILDING);
                        GameMenuController.dropBuilding(realGame, realGame.getKingdoms().get(0).getUser(), 10, 1, BuildingType.UNEMPLOYED_PLACE);
                        GameMenuController.dropBuilding(realGame, realGame.getKingdoms().get(0).getUser(), 10, 2, BuildingType.STOCKPILE);

                        // game.setScreen(new testingGameScreen(game , realGame, Map.getMapByName(mapSelectBox.getName())));
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        uiStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        table.setPosition(uiStage.getWidth() / 2 - table.getWidth() / 2, uiStage.getHeight() / 2 - table.getHeight() / 2);
        uiStage.draw();
    }
}
