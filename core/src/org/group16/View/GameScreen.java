package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.group16.StrongholdGame;

public class GameScreen extends Menu{

    Skin skin2 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    Skin skin1 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));

    BuildingSelectWindow buildingSelectWindow = new BuildingSelectWindow("" , skin2) ;
    public GameScreen(StrongholdGame game) {
        super(game);
        uiStage.clear();

        uiStage.addActor(buildingSelectWindow);

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        uiStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        buildingSelectWindow.setWidth(uiStage.getWidth());
        buildingSelectWindow.setHeight(uiStage.getHeight()/4);
        uiStage.draw();
    }
}
