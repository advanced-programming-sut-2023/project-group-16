package org.group16.View;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.group16.GameGraphics.CommandHandling.CreateTradeRequestCommand;
import org.group16.GameGraphics.CommandHandling.TradeAcceptCommand;
import org.group16.Model.Game;
import org.group16.Model.Kingdom;
import org.group16.Model.Resources.BasicResource;
import org.group16.Model.Resources.Food;
import org.group16.Model.Resources.Resource;
import org.group16.Model.Resources.Weaponry;
import org.group16.Model.Trade;
import org.group16.Model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TradeWindow extends Window {
    Image soilBackground, grayBackGround;
    public Skin skin;

    Game game;
    User user;
    ImageButton back;
    TextButton newTrade, receivedTrades, submittedTrades;
    testingGameScreen gameScreen;

    public TradeWindow(Skin skin, Game game, User user, testingGameScreen gameScreen) {
        super("", skin);

        this.skin = skin;
        this.gameScreen = gameScreen;
        this.game = game;
        this.user = user;

        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));

        makeNewTradeWindow();
    }

    public void makeNewTradeWindow() {
        this.clear();
        initWindow();
        Label seeResourcesOf = new Label("see resources of : ", skin);
        this.add(seeResourcesOf).row();
        for (Kingdom kingdom : game.getKingdoms()) {
            User curUser = kingdom.getUser();

            Image userProfile = new Image(picChange.changer(curUser.getAvatarPicture(), 60, 60));
            TextButton usernameButton = new TextButton(curUser.getUsername(), skin);

            this.add(userProfile);
            this.add(usernameButton).pad(0, 0, 0, 10);

            usernameButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    showResourcesWindow(curUser);
                }
            });
        }
        this.row();


        this.add(back).pad(0, 0, 0, 5);
        this.add(newTrade).pad(0, 0, 0, 5);
        this.add(receivedTrades).pad(0, 0, 0, 5);
        this.add(submittedTrades).pad(0, 0, 0, 5);
    }

    public void showResourcesWindow(User curUser) {
        this.clear();
        initWindow();
        for (Resource resource : BasicResource.values())
            addImageButtonOfResource(resource, curUser);

        this.row();

        for (Resource resource : Food.values())
            addImageButtonOfResource(resource, curUser);

        this.row();

        for (Resource resource : Weaponry.values())
            addImageButtonOfResource(resource, curUser);

        this.row();

        this.add(back).pad(0, 0, 0, 5);
        this.add(newTrade).pad(0, 0, 0, 5);
        this.add(receivedTrades).pad(0, 0, 0, 5);
        this.add(submittedTrades).pad(0, 0, 0, 5);
    }

    public String getPathByType(Resource resource) {
        if (resource instanceof BasicResource)
            return "BASIC";
        else if (resource instanceof Food)
            return "FOOD";
        else
            return "WEAPONRY";
    }

    public void addImageButtonOfResource(Resource resource, User curUser) {
        if (resource.getPrice() == Integer.MAX_VALUE)
            return;
        try {
            ImageButton resourceImageButton = new ImageButton(skin);
            ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
            imageButtonStyle.imageUp = new TextureRegionDrawable(picChange.changer(
                    "MenuPictures/Resources/" + getPathByType(resource) + "/" + resource.GetName() + ".png", 30, 30));
            imageButtonStyle.imageDown = new TextureRegionDrawable(picChange.changer(
                    "MenuPictures/Resources/" + getPathByType(resource) + "/" + resource.GetName() + ".png", 27, 27));
            resourceImageButton.setStyle(imageButtonStyle);

            Label resourceAmount = new Label(":" + game.getKingdom(curUser).getResourceCount(resource), skin);

            this.add(resourceImageButton);
            this.add(resourceAmount).pad(0, 0, 0, 5);

            resourceImageButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    makeRequestWindow(resource, curUser);
                }
            });
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void makeRequestWindow(Resource resource, User curUser) {
        this.clear();
        initWindow();
        Image ResourceImage = new Image(picChange.changer("MenuPictures/Resources/" + getPathByType(resource) + "/" + resource.GetName() + ".png", 100, 100));

        TextField price = new TextField("enter price", skin);
        TextField amount = new TextField("enter amount", skin);
        TextField message = new TextField("enter message", skin);

        TextButton submitButton = new TextButton("submit offer", skin);

        this.add(ResourceImage).row();
        this.add(price).pad(0, 0, 0, 5);
        this.add(amount).row();

        this.add(message).row();

        this.add(submitButton).row();
        submitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    int amountNum = Integer.parseInt(amount.getText());
                    int priceNum = Integer.parseInt(price.getText());
                    gameScreen.inputProcessor.submitCommandToServer(new CreateTradeRequestCommand(user, amountNum, priceNum, message.getText(), resource));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        this.add(back).pad(0, 0, 0, 5);
        this.add(newTrade).pad(0, 0, 0, 5);
        this.add(receivedTrades).pad(0, 0, 0, 5);
        this.add(submittedTrades).pad(0, 0, 0, 5);
    }

    public void makeSubmittedWindow() {
        this.clear();
        initWindow();
        int rowCounter = 1;
        for (Trade trade : game.getUserTrades(user)) {
            Label tradeLabel;
            if (trade.getSeller() != null) {
                tradeLabel = new Label(rowCounter + "." + trade.getId() + " : " + trade.getBuyer() + " , " + trade.getSellerMessage(), skin);
            } else {
                tradeLabel = new Label(rowCounter + "." + trade.getId() + " : " + "not accepted yet" + " , " + trade.getBuyerMessage(), skin);
            }
            this.add(tradeLabel).row();
            rowCounter++;
        }

        this.add(back).pad(0, 0, 0, 5);
        this.add(newTrade).pad(0, 0, 0, 5);
        this.add(receivedTrades).pad(0, 0, 0, 5);
        this.add(submittedTrades).pad(0, 0, 0, 5);
    }

    public void makeReceivedWindow() {
        this.clear();
        initWindow();
        int rowCounter = 1;
        for (Trade trade : game.getTradeOffers()) {
            if (trade.getBuyer().getUser().equals(user))
                continue;
            Label tradeLabel = new Label(rowCounter + "." + trade.getId() + " : " + trade.getAmount() + " " + trade.getResource().GetName() + "each for" + trade.getPrice(), skin);
            Label tradeMessage = new Label(trade.getBuyer().getUser().getUsername() + " : " + trade.getBuyerMessage(), skin);
            TextButton accept = new TextButton("accept", skin);

            this.add(tradeLabel).row();
            this.add(tradeMessage).pad(0, 0, 0, 5);
            this.add(accept).row();

            accept.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    try {
                        gameScreen.inputProcessor.submitCommandToServer(new TradeAcceptCommand(user, trade.getId(), "trade accepted by" + user.getUsername()));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
        }

        this.add(back).pad(0, 0, 0, 5);
        this.add(newTrade).pad(0, 0, 0, 5);
        this.add(receivedTrades).pad(0, 0, 0, 5);
        this.add(submittedTrades).pad(0, 0, 0, 5);
    }

    public void initWindow() {
        this.setBackground(soilBackground.getDrawable());

        newTrade = new TextButton("new trade", skin);
        receivedTrades = new TextButton("received trades", skin);
        submittedTrades = new TextButton("submitted trades", skin);

        back = new ImageButton(skin);
        ImageButton.ImageButtonStyle imageStyle = new ImageButton.ImageButtonStyle();
        imageStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("ButtonImages/BackButton.png").path(), 30, 30));
        imageStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("ButtonImages/BackButton.png").path(), 27, 27));
        back.setStyle(imageStyle);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.setCurrentRunningWindow(gameScreen.buildingWindow);
            }
        });

        newTrade.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                makeNewTradeWindow();
            }
        });
        receivedTrades.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                makeReceivedWindow();
            }
        });
        submittedTrades.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                makeSubmittedWindow();
            }
        });
    }

}
