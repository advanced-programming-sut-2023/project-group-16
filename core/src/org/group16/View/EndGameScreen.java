package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.group16.Controller.GameMenuController;
import org.group16.Model.Game;
import org.group16.Model.Kingdom;
import org.group16.Model.Team;
import org.group16.Model.User;
import org.group16.Networking.LobbySocket;
import org.group16.StrongholdGame;

import java.util.ArrayList;

public class EndGameScreen extends Menu{
    Game realGame ;
    Table table ;
    Skin skin2 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    Skin skin1 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));
    User curUser ;
    TextButton backToMainButton = new TextButton("back to main menu" , skin1) ;
    private Image background, white, soilBackground;
    public EndGameScreen(StrongholdGame game , Game realGame , User curUser) {
        super(game);
        this.realGame = realGame ;
        this.curUser = curUser ;
        this.table = new Table(skin1) ;
        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));
        table.setBackground(soilBackground.getDrawable()) ;
        table.setSize(600, 300);

        background = new Image(new Texture(Gdx.files.internal("backgrounds/endGame.jpg")));
        background.setFillParent(true);


        Team winnerTeam = GameMenuController.getWinnerTeam(realGame);
        ArrayList<Kingdom> winnerTeamKingdoms = winnerTeam.getKingdoms() ;
        ArrayList<Kingdom> loserTeamKingdoms = new ArrayList<>() ;
        for (Kingdom kingdom : realGame.getKingdoms()){
            if (!winnerTeamKingdoms.contains(kingdom))
                loserTeamKingdoms.add(kingdom);
        }
        Label winners = new Label("Winners : " , skin1) ;
        winners.setColor(Color.GREEN);
        table.add(winners).row();
        for (Kingdom kingdom : winnerTeamKingdoms){
            User user = kingdom.getUser() ;
            Label name = new Label(user.getUsername() , skin1) ;
            Image image = new Image() ;
            image.setDrawable(new TextureRegionDrawable(picChange.changer(user.getAvatarPicture(), 60, 60)));
            name.setColor(Color.GREEN);
            user.addScore(3);
            table.add(name) ;
            table.add(image).row();
        }
        Label losers = new Label("Losers : " , skin1) ;
        losers.setColor(Color.RED);
        table.add(losers).row();
        for (Kingdom kingdom : loserTeamKingdoms){
            User user = kingdom.getUser() ;
            Label name = new Label(user.getUsername() , skin1) ;
            Image image = new Image() ;
            image.setDrawable(new TextureRegionDrawable(picChange.changer(user.getAvatarPicture(), 60, 60)));
            name.setColor(Color.RED);
            user.addScore(-3);
            table.add(name) ;
            table.add(image).row();
        }
        table.add(backToMainButton) ;
        backToMainButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainScreen(game , curUser));
            }
        });

        uiStage.addActor(background);
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
