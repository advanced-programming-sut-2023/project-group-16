package org.group16.View;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.group16.Model.Game;

public class PopularityWindow extends Window {

    int food = 0, fear = 0, tax = 0, religion = 0;

    ImageButton back;
    TextButton changeRates ;
    Image soilBackground;
    Label foodLabel, fearLabel, taxLabel, religionLabel;

    Skin skin;

    Game game ;
    testingGameScreen gameScreen ;

    public PopularityWindow(Skin skin, int food, int fear, int tax, int religion , Game game, testingGameScreen gameScreen) {
        super("", skin);

        this.gameScreen = gameScreen ;
        this.game = game ;

        this.skin = skin;
        this.food = food;
        this.fear = fear;
        this.tax = tax;
        this.religion = religion;

        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));

        makeWindow();
    }

    private void makeWindow() {
        this.clear();
        this.setBackground(soilBackground.getDrawable());

        back = new ImageButton(skin);
        ImageButton.ImageButtonStyle imageStyle = new ImageButton.ImageButtonStyle();
        imageStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("ButtonImages/BackButton.png").path(), 30, 30));
        imageStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("ButtonImages/BackButton.png").path(), 27, 27));
        back.setStyle(imageStyle);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.setCurrentRunningWindow(gameScreen.shopWindow);
            }
        });

        changeRates = new TextButton("change" , skin) ;

        changeRates.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.changeRateWindow.remake(food , fear , tax);
                gameScreen.setCurrentRunningWindow(gameScreen.changeRateWindow) ;
            }
        });

        foodLabel = new Label("food : " + food, skin);
        fearLabel = new Label("fear : " + fear, skin);
        taxLabel = new Label("tax : " + tax, skin);
        religionLabel = new Label("religion : " + religion, skin);

        this.add(foodLabel);
        this.add(rateToEmoji(food)).pad(0, 0, 0, 60);
        this.add(fearLabel);
        this.add(rateToEmoji(-fear)).row();

        this.add(taxLabel);
        this.add(rateToEmoji(-tax)).pad(0, 0, 0, 60);
        this.add(religionLabel);
        this.add(rateToEmoji(religion)).row();
        this.add(changeRates).row();
        this.add(back);
    }

    public Image rateToEmoji(int rate) {
        if (rate > 0)
            return new Image(new TextureRegionDrawable(picChange.changer(Gdx.files.internal("MenuPictures/FaceEmoji/HAPPY.png").path(), 30, 30)));
        else if (rate == 0)
            return new Image(new TextureRegionDrawable(picChange.changer(Gdx.files.internal("MenuPictures/FaceEmoji/NORMAL.png").path(), 30, 30)));
        else
            return new Image(new TextureRegionDrawable(picChange.changer(Gdx.files.internal("MenuPictures/FaceEmoji/SAD.png").path(), 30, 30)));
    }


    public void reset(int food, int fear, int tax, int religion) {
        this.food = food;
        this.fear = fear;
        this.tax = tax;
        this.religion = religion;
        makeWindow();
    }


}
