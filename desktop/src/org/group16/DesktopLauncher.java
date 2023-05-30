package org.group16;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;
import games.spooky.gdx.nativefilechooser.desktop.DesktopFileChooser;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.useVsync(true);
        config.setTitle("AP-AA");
        config.setWindowedMode(640, 800);
        StrongholdGame.fileChooser = new DesktopFileChooser();
        StrongholdGame.fileChooserConfiguration = new NativeFileChooserConfiguration();

        new Lwjgl3Application(new StrongholdGame(), config);
    }
}
