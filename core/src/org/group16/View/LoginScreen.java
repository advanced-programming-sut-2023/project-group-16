package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import org.group16.StrongholdGame;

public class LoginScreen extends Menu {
    Table table;
    TextField username, password;

    TextButton login, register, forgotPassword;
    CheckBox stayLogIn , passwordHide;

    Label usernameLabel, passwordLabel, usernameStatus, passwordStatus;


    Image background;
    Image white ;
    Skin skin1 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    Skin skin2 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));



    public LoginScreen(StrongholdGame game) {
        super(game);
        uiStage.clear();
        white = new Image(new Texture(Gdx.files.internal("backgrounds/white.jpg"))) ;
        table = new Table(skin1);
        table.setBackground(white.getDrawable());
        table.setColor(Color.BLACK);
        table.setSize(410, 200);
        table.setPosition(uiStage.getWidth()/2 - table.getWidth()/2, uiStage.getHeight()/2-table.getHeight()/2);



        username = new TextField("", skin1);
        usernameLabel = new Label("username", skin1);
        usernameStatus = new Label("", skin1);

        password = new TextField("", skin1);
        password.setPasswordMode(true);
        password.setPasswordCharacter('*');
        passwordHide = new CheckBox("hide" , skin1) ;
        passwordHide.setChecked(true);
        passwordLabel = new Label("password", skin1);
        passwordStatus = new Label("", skin1);

        login = new TextButton("Login", skin1);
        register = new TextButton("register", skin1);
        forgotPassword = new TextButton("forgot password", skin1);
        stayLogIn = new CheckBox("stay log in", skin1);

        table.add(usernameLabel).pad(0, 0, 0, 5);
        table.add(username).pad(0, 0, 0, 5);
        table.add(usernameStatus).row();

        table.add(passwordLabel).pad(0, 0, 0, 5);
        table.add(password).pad(0, 0, 0, 5);
        table.add(passwordHide).pad(0 , 0 , 0 , 5);
        table.add(passwordStatus).row();

        table.add(forgotPassword).center();
        table.add(login).pad(0, 0, 0, 5);
        table.add(stayLogIn).row();
        table.add(register);


        background =new Image(new Texture(Gdx.files.internal("backgrounds/loginMenu.jpg"))) ;
        background.setZIndex(0) ;
        background.setFillParent(true);
        uiStage.addActor(background);
        uiStage.addActor(table);

        register.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new RegisterScreen(game));
            }
        });

        passwordHide.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                password.setPasswordMode(passwordHide.isChecked());
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        uiStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        table.setPosition(uiStage.getWidth()/2 - table.getWidth()/2, uiStage.getHeight()/2-table.getHeight()/2);
        uiStage.draw();
    }
}
