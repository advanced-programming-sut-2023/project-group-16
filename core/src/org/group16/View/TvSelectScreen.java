package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import org.group16.Model.*;
import org.group16.Networking.LobbySocket;
import org.group16.StrongholdGame;

import java.io.IOException;
import java.util.ArrayList;

public class TvSelectScreen extends Menu {

    Table table;
    Skin skin2 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    Skin skin1 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));
    User user;

    private Image background, white, soilBackground;

    public TvSelectScreen(StrongholdGame game, User user) throws IOException, ClassNotFoundException {
        super(game);

        this.user = user;

        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));
        makeScreen();
    }

    public void makeScreen() throws IOException, ClassNotFoundException {
        uiStage.clear();

        table = new Table(skin1);
        table.setBackground(soilBackground.getDrawable());
        table.setSize(600, 400);

        GameInfoList gameInfoList = LobbySocket.getRunningGames();

        ArrayList<GameInfo> gameInfos = gameInfoList.gameInfos;

        int rowCounter = 0;
        for (GameInfo gameInfo : gameInfos) {
            rowCounter++;
            Label gameId = new Label(rowCounter + ".game" + gameInfo.gameID().toString().substring(0, 4), skin1);
            TextButton watchThisGame = new TextButton("watch", skin1);
            table.add(gameId).pad(0, 0, 0, 5);
            table.add(watchThisGame).row();
            watchThisGame.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    try {
                        Game gameData = new Game();
                        Scene scene = new Scene(LobbySocket.downloadMap(gameInfo.mapname()), gameInfo.randomSeed());
                        gameData.setScene(scene);
                        for (int i = 0; i < gameInfo.playerList().users.size(); i++) {
                            gameData.addUser(gameInfo.playerList().users.get(i), gameInfo.playerList().kingdomTypes.get(i));
                        }
                        TvScreen screen = new TvScreen(game, gameData, gameInfo, user);
                        setScreen(screen);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        TextButton back = new TextButton("back", skin1);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainScreen(game, user));
            }
        });

        TextButton refreshPage = new TextButton("refresh", skin1);
        refreshPage.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    makeScreen();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        table.add(refreshPage).row();
        table.add(back).row();

        uiStage.addActor(table);
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
