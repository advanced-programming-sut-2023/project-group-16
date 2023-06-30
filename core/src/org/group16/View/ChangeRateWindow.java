package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class ChangeRateWindow extends Window {

    Skin skin;
    SlideBar foodSlideBar, fearSlideBar, taxSlideBar;

    Image soilBackground;

    public ChangeRateWindow(String title, Skin skin) {
        super(title, skin);

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

            slider.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    amount.setText(slider.getValue() + "");
                    //TODO
                }
            });
        }
    }
}
