package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sun.tools.javac.Main;
import org.group16.Model.User;
import org.group16.StrongholdGame;

import java.util.ArrayList;

public class LobbyScreen extends Menu{

    ArrayList<User> users = new ArrayList<>() ;
    User user ;
    Table table ;
    private  Image background, white , soilBackground;
    Skin skin2 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    Skin skin1 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));
    TextButton back , add , start;

    TextField usernameField ;
    Label usernameStatus , startStatus;

    public LobbyScreen(StrongholdGame game , User user) {
        super(game);
        uiStage.clear();



        this.user = user ;

        white = new Image(new Texture(Gdx.files.internal("backgrounds/white.jpg")));
        background = new Image(new Texture(Gdx.files.internal("backgrounds/lobbyBackground.jpg")));
        background.setFillParent(true);
        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));

        table = new Table(skin1) ;
        table.setBackground(soilBackground.getDrawable()) ;
        table.setSize(500 , 400);


        users.add(user) ;
        uiStage.addActor(background);
        uiStage.addActor(table);
        remake();
    }

    public void remake(){
        table.clear();
        table.background(soilBackground.getDrawable()) ;

        usernameField = new TextField("" , skin1) ;
        usernameStatus = new Label("" , skin1) ;
        usernameStatus.setColor(Color.RED);
        add = new TextButton("add" , skin1) ;
        back = new TextButton("back" , skin1) ;
        start = new TextButton("start" , skin1) ;
        startStatus = new Label("" , skin1) ;
        startStatus.setColor(Color.RED);

        table.add(usernameField).pad(0 , 0 , 0 , 5) ;
        table.add(add).pad(0 , 0 , 0 , 5) ;
        table.add(usernameStatus).row();

        add.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (User.getUserByName(usernameField.getText())==null){
                    usernameStatus.setText("no user Found");
                }else if(users.contains(User.getUserByName(usernameField.getText()))){
                    usernameStatus.setText("user already added");
                }else{
                    users.add(User.getUserByName(usernameField.getText())) ;
                    remake();
                }
            }
        });

        int rowCounter = 1 ;
        for (User user1 : users){
            table.add(new Label(rowCounter + " : " + user1.getNickname() , skin1)).pad(0 , 0 , 0 , 5) ;
            TextButton delButton = new TextButton("delete" , skin1) ;
            table.add(delButton ).row();
            delButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    users.remove(user1) ;
                    remake();
                }
            });
        }

        table.add(start) ;
        table.add(startStatus).row();
        table.add(back).row();

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainScreen(game , user));
            }
        });

        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (users.size() < 2){
                    startStatus.setText("not enough players");
                }else {
                    //TODO
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
