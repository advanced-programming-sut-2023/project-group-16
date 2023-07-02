package org.group16.View;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.group16.GameGraphics.CommandHandling.SetFearRateCommand;
import org.group16.GameGraphics.CommandHandling.SetFoodRateCommand;
import org.group16.GameGraphics.CommandHandling.SetTaxRateCommand;
import org.group16.Model.Game;
import org.group16.Model.Kingdom;
import org.group16.Model.User;

public class ChangeRateWindow extends Window {

    Skin skin;
    SlideBar foodSlideBar, fearSlideBar, taxSlideBar;

    Image soilBackground;

    ImageButton back;
    TextButton change ;

    Game game ;
    testingGameScreen gameScreen;

    public ChangeRateWindow(Skin skin , Game game , testingGameScreen gameScreen) {
        super("" , skin);

        this.game = game ;
        this.gameScreen = gameScreen ;

        this.skin = skin;

        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));
        this.setBackground(soilBackground.getDrawable());

        foodSlideBar = new SlideBar(-2, 2, "Food");
        fearSlideBar = new SlideBar(-5, 5, "FearRate");
        taxSlideBar = new SlideBar(-3, 8, "Tax");

        this.add(foodSlideBar.name).pad(0, 0, 0, 10);
        this.add(foodSlideBar.slider).pad(0, 0, 0, 10);
        this.add(foodSlideBar.amount).row();
        this.add(fearSlideBar.name).pad(0, 0, 0, 10);
        this.add(fearSlideBar.slider).pad(0, 0, 0, 10);
        this.add(fearSlideBar.amount).row();
        this.add(taxSlideBar.name).pad(0, 0, 0, 10);
        this.add(taxSlideBar.slider).pad(0, 0, 0, 10);
        this.add(taxSlideBar.amount).row();

        back = new ImageButton(skin);
        ImageButton.ImageButtonStyle imageStyle = new ImageButton.ImageButtonStyle();
        imageStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("ButtonImages/BackButton.png").path(), 30, 30));
        imageStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("ButtonImages/BackButton.png").path(), 27, 27));
        back.setStyle(imageStyle);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.setCurrentRunningWindow(gameScreen.popularityWindow);
            }
        }) ;

        change = new TextButton("change" , skin) ;



        change.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                User user = gameScreen.getCurUser() ;
                gameScreen.inputProcessor.submitCommand(new SetFoodRateCommand(user , (int) foodSlideBar.slider.getValue()));
                gameScreen.inputProcessor.submitCommand(new SetTaxRateCommand(user , (int) taxSlideBar.slider.getValue()));
                gameScreen.inputProcessor.submitCommand(new SetFearRateCommand(user , (int) fearSlideBar.slider.getValue()));
            }
        }) ;

        this.add(change).pad(0 , 0 , 0 , 5) ;
        this.add(back) ;

        foodSlideBar.slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                foodSlideBar.amount.setText(foodSlideBar.slider.getValue()+"");
            }
        });
        taxSlideBar.slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                taxSlideBar.amount.setText(taxSlideBar.slider.getValue()+"");
            }
        });
        fearSlideBar.slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fearSlideBar.amount.setText(fearSlideBar.slider.getValue()+"");
            }
        });

    }


    public class SlideBar {
        Slider.SliderStyle sliderStyle;
        Slider slider;
        Label name, amount;

        public SlideBar(int mn, int mx, String name) {
            this.name = new Label(name, skin);
            sliderStyle = new Slider.SliderStyle();
            sliderStyle.background = new Image(new Texture(Gdx.files.internal("ButtonImages/Slider.png"))).getDrawable();
            sliderStyle.knob = new Image(new Texture(Gdx.files.internal("ButtonImages/KnobSlider.png"))).getDrawable();
            slider = new Slider(mn, mx, 1, false, sliderStyle);

            slider.setWidth(100);
            amount = new Label(slider.getValue() + "", skin);
        }
    }

    public void remake(int food , int fear , int tax){
        foodSlideBar.slider.setValue(food) ;
        fearSlideBar.slider.setValue(fear) ;
        taxSlideBar.slider.setValue(tax) ;
    }
}
