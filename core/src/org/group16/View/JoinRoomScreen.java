package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import org.group16.Model.*;
import org.group16.Networking.LobbySocket;
import org.group16.StrongholdGame;

import java.io.IOException;

public class JoinRoomScreen extends Menu {
    private final Skin skin1 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    private final Skin skin2 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));
    User user;
    private Image soilBackground;
    private Table table;


    public JoinRoomScreen(StrongholdGame game, User user) throws IOException, ClassNotFoundException {
        super(game);
        this.user = user;

        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));

        table = new Table();
        table.background(soilBackground.getDrawable());
        table.setFillParent(true);
        Label waiting = new Label("waiting...", skin1);

        table.add(waiting);


        GameInfo gameInfo = LobbySocket.joinGameLobby();


        Scene scene = null;
        Map map = LobbySocket.downloadMap(gameInfo.mapname());
        PlayerList playerList = gameInfo.playerList();
        Long random = gameInfo.randomSeed();
        scene = new Scene(map, random);
        Game game1 = new Game();

        for (int i = 0; i < playerList.users.size(); i++) {
            game1.addUser(playerList.users.get(i), playerList.kingdomTypes.get(i));
        }

        game1.setScene(scene);
        testingGameScreen gameScreen = new testingGameScreen(game, game1, gameInfo, user);
        game.setScreen(gameScreen);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        uiStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        table.setPosition(uiStage.getWidth() / 2 - table.getWidth() / 2,
                uiStage.getHeight() / 2 - table.getHeight() / 2);

        uiStage.draw();
    }


}
