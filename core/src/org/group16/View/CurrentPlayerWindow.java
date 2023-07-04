package org.group16.View;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.Game;
import org.group16.Model.User;

public class CurrentPlayerWindow extends Window {
    Image soilBackground;
    testingGameScreen gameScreen ;
    Skin skin ;

    Game game ;
    public CurrentPlayerWindow(User user, Skin skin , Game game , testingGameScreen gameScreen) {
        super("", skin);

        this.game = game ;
        this.gameScreen = gameScreen ;
        this.skin = skin ;


        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));
        this.background(soilBackground.getDrawable());

        makeWindow(user);
    }

    public void makeWindow(User user){
        this.clear();
        Image avatar = new Image(picChange.changer(user.getAvatarPicture(), 60, 60)) ;

        Label name = new Label(user.getNickname() , skin);

        this.add(avatar).row();
        this.add(name) ;
    }

}
