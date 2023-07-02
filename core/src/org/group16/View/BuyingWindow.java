package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import org.group16.GameGraphics.CommandHandling.BuyCommand;
import org.group16.GameGraphics.CommandHandling.SellCommand;
import org.group16.Model.Game;
import org.group16.Model.Resources.Resource;
import org.group16.Model.User;


public class BuyingWindow extends Window {


    TextField amount;
    Label price, goodName, error;
    TextButton buyButton, sellButton, back;
    Image soilBackground;
    Skin skin;
    Resource resource;

    testingGameScreen gameScreen;
    Game game;

    public BuyingWindow(Skin skin, Game game, testingGameScreen gameScreen) {
        super("", skin);
        this.skin = skin;

        this.gameScreen = gameScreen;
        this.game = game;

        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));


    }

    public void remake(Resource resource) {
        this.clear();
        this.resource = resource;

        this.setBackground(soilBackground.getDrawable());

        amount = new TextField("1", skin);

        buyButton = new TextButton("buy", skin);
        sellButton = new TextButton("sell", skin);
        price = new com.badlogic.gdx.scenes.scene2d.ui.Label("price : " + resource.getPrice(), skin);
        goodName = new com.badlogic.gdx.scenes.scene2d.ui.Label(resource.GetName(), skin);
        back = new TextButton("back", skin);
        error = new com.badlogic.gdx.scenes.scene2d.ui.Label("", skin);
        error.setColor(Color.RED);
        this.add(goodName).row();
        this.add(amount).row();
        this.add(price).row();
        this.add(buyButton).row();
        this.add(sellButton).row();
        this.add(back).row();
        this.add(error).row();
        buyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                User user = gameScreen.getCurUser();
                try {
                    gameScreen.inputProcessor.submitCommandToServer(new BuyCommand(user, resource, Integer.parseInt(amount.getText())));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        sellButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                User user = gameScreen.getCurUser();
                try {
                    gameScreen.inputProcessor.submitCommandToServer(new SellCommand(user, resource, Integer.parseInt(amount.getText())));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.setCurrentRunningWindow(gameScreen.shopWindow);
            }
        });
    }


}
