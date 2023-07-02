package org.group16;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;
import games.spooky.gdx.nativefilechooser.desktop.DesktopFileChooser;
import org.group16.Networking.GameSocket;
import org.group16.Networking.LobbySocket;
import org.group16.Server.Server;

import java.io.IOException;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    public static void main(String[] arg) {
        try {
            LobbySocket.createSocket(Server.host, Server.lobbyPort);
        } catch (IOException e) {
            System.out.println("Didnt Connect To Server");
            return;
        }
        GameSocket.initSocket(Server.host, Server.lobbyPort);

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.useVsync(true);
        config.setTitle("Stronghold Crusader");
        config.setWindowedMode(1600, 1280);
        StrongholdGame.fileChooser = new DesktopFileChooser();
        StrongholdGame.fileChooserConfiguration = new NativeFileChooserConfiguration();

        config.setWindowIcon("icons/game_icon.png");
        new Lwjgl3Application(new StrongholdGame(), config);
    }
}
