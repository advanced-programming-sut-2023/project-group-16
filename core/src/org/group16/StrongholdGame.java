package org.group16;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.ScreenUtils;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;
import org.group16.View.LoginScreen;


public class StrongholdGame extends Game {
    public static NativeFileChooser fileChooser;
    public static NativeFileChooserConfiguration fileChooserConfiguration;
    public static StrongholdGame singleton;
    public AssetManager assetManager = new AssetManager();

    @Override
    public void create() {
        singleton = this;

        manageAssets();

        fileChooserConfiguration.directory = Gdx.files.absolute(System.getProperty("user.home"));

        setScreen(new LoginScreen(this));
    }

    private void manageAssets() {
        assetManager.finishLoading();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);
        super.render();

    }

    @Override
    public void dispose() {
        if (getScreen() != null) getScreen().dispose();
        assetManager.dispose();
    }
}
