package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.group16.StrongholdGame;

import static com.badlogic.gdx.Gdx.graphics;
import static com.badlogic.gdx.Gdx.input;

public abstract class Menu implements Screen {
    protected final StrongholdGame game;
    protected final Stage uiStage;

    public Menu(StrongholdGame game) {
        this.game = game;
        uiStage = new Stage(new ScreenViewport());
        input.setInputProcessor(uiStage);
        //uiStage.setViewport(new FitViewport(graphics.getWidth(), graphics.getHeight()));
    }

    protected Menu getScreen() {
        return (Menu) game.getScreen();
    }

    protected void setScreen(Menu menu) {
        game.setScreen(menu);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        uiStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        uiStage.dispose();
    }
}
