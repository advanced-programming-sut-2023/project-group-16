package org.group16.View;

import org.group16.Model.Game;
import org.group16.Model.User;
import org.group16.View.Command.Command;
import org.group16.View.Command.CommandHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner;
    private final User currentUser;

    public MainMenu(Scanner scanner, User currentUser) {
        this.scanner = scanner;
        this.currentUser = currentUser;
    }

    public void run() {
        while (true) {
            String input = scanner.nextLine();
            if (CommandHandler.matches(Command.ENTER_GAME_MENU, input) != null) enterGameMenu();
            else if (CommandHandler.matches(Command.ENTER_PROFILE_MENU, input) != null) enterProfileMenu();
            else if (CommandHandler.matches(Command.ENTER_EDITOR_MENU, input) != null) enterEditorMenu();
            else if (CommandHandler.matches(Command.LOGOUT, input) != null) {
                logout();
                break;
            } else System.out.println("invalid command");
        }
    }

    private void enterGameMenu() {
        System.out.println("entered game menu successfully");
        GameMenu gameMenu = new GameMenu(scanner, new Game());
        gameMenu.run();
    }

    private void enterProfileMenu() {
        System.out.println("entered profile menu successfully");
        ProfileMenu profileMenu = new ProfileMenu(scanner, currentUser);
        profileMenu.run();

    }

    private void enterEditorMenu() {
        System.out.println("entered editor menu successfully");
        EditorMenu editorMenu = new EditorMenu(scanner, null, currentUser);
        editorMenu.run();
    }

    private void logout() {
        System.out.println("logged out successfully");
        String filePath = new File("").getAbsolutePath().concat("/StrongholdCrusader/src/main/java/" +
                "org/group16/Model/Data/stayLoggedInUser.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
