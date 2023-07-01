package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class CellDetailWindow extends Window {
    Label description;
    Image soilBackground;

    float lastRemakeTime = 0 ;

    public CellDetailWindow(String title, Skin skin, String details) {
        super(title, skin);

        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));
        this.background(soilBackground.getDrawable());

        description = new Label(details, skin);
        this.add(description);

    }

    public void remake(String detail) {
        description.setText(detail);
    }
}
