package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import org.group16.Model.User;
import org.group16.StrongholdGame;

public class MainScreen extends Menu {
    private final User user;
    private final Table table;
    private final Image background, white;
    private final Skin skin1 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    private final Skin skin2 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));
    private TextButton profileMenu , gameMenu;


    public MainScreen(StrongholdGame game, User user) {
        super(game);
        this.user = user;
        uiStage.clear();
        white = new Image(new Texture(Gdx.files.internal("backgrounds/white.jpg")));

        background = new Image(new Texture(Gdx.files.internal("backgrounds/loginMenu.jpg")));

        profileMenu = new TextButton("Profile", skin1);
        profileMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new ProfileScreen(game, user));
            }
        });
        gameMenu = new TextButton("Game" , skin1) ;
        gameMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
            }
        });

        uiStage.addActor(background);

        table = new Table(skin1);
        table.setBackground(white.getDrawable());
        table.setColor(Color.BLACK);
        table.setSize(600, 300);
        table.setPosition(uiStage.getWidth() / 2 - table.getWidth() / 2,
                uiStage.getHeight() / 2 - table.getHeight() / 2);
        table.add(profileMenu).center().row();
        table.add(gameMenu).row();
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
