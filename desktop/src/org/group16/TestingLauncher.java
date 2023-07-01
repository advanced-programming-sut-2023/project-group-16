package org.group16;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;
import games.spooky.gdx.nativefilechooser.desktop.DesktopFileChooser;

public class TestingLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.useVsync(true);
        config.setTitle("Stronghold Crusader");
        config.setWindowedMode(1600, 1280);
//        StrongholdGame.fileChooser = new DesktopFileChooser();
//        StrongholdGame.fileChooserConfiguration = new NativeFileChooserConfiguration();
        config.setWindowIcon("icons/game_icon.png");
        new Lwjgl3Application(new TestingGDX(), config);
    }
}
