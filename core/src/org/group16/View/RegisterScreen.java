package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import org.group16.StrongholdGame;

public class RegisterScreen extends Menu{
    Table table ;

    Image background;
    Image white ;
    Skin skin1 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    Skin skin2 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));

    public RegisterScreen(StrongholdGame game) {
        super(game);
        uiStage.clear();
        table = new Table() ;


        background =new Image(new Texture(Gdx.files.internal("backgrounds/registerMenu.jpg"))) ;
        background.setZIndex(0) ;
        background.setFillParent(true);
        uiStage.addActor(background);
        uiStage.addActor(table);
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
