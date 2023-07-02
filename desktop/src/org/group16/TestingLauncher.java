package org.group16;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;
import games.spooky.gdx.nativefilechooser.desktop.DesktopFileChooser;
import org.group16.GameGraphics.BuildingGraphics;
import org.group16.Networking.GameSocket;
import org.group16.Networking.LobbySocket;
import org.group16.Server.Server;

import java.io.IOException;

public class TestingLauncher {
    public static void main(String[] args) throws IOException {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.useVsync(true);
        config.setTitle("Stronghold Crusader");
        config.setWindowedMode(1600, 1280);
//        StrongholdGame.fileChooser = new DesktopFileChooser();
//        StrongholdGame.fileChooserConfiguration = new NativeFileChooserConfiguration();
//        LobbySocket.createSocket(Server.host, Server.lobbyPort);
//        GameSocket.initSocket(Server.host, Server.gamePort);
        config.setWindowIcon("icons/game_icon.png");
        new Lwjgl3Application(new TestBuildingGraphics(
                BuildingGraphics.UNEMPLOYED_PLACE,
                0)
                , config);
    }
}
