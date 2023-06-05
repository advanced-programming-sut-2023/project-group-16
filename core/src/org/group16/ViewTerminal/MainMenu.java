package org.group16.ViewTerminal;

import org.group16.Model.KingdomType;
import org.group16.Model.User;
import org.group16.ViewTerminal.Command.Command;
import org.group16.ViewTerminal.Command.CommandHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

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
            TreeMap<String, ArrayList<String>> map;
            if ((map = CommandHandler.matches(Command.CREATE_GAME, input)) != null) createGame(map);
            else if (CommandHandler.matches(Command.ENTER_PROFILE_MENU, input) != null) enterProfileMenu();
            else if (CommandHandler.matches(Command.ENTER_EDITOR_MENU, input) != null) enterEditorMenu();
            else if (CommandHandler.matches(Command.LOGOUT, input) != null) {
                logout();
                break;
            } else System.out.println("invalid command");
        }
    }

    private void createGame(TreeMap<String, ArrayList<String>> map) {
        KingdomType kingdomType = KingdomType.getKingdomTypeByName(map.get("t").get(0));
        if (kingdomType == null) {
            System.out.println("invalid kingdom type");
            return;
        }
        System.out.println("entered to create map menu successfully");
        CreateGameMenu createGameMenu = new CreateGameMenu(scanner, currentUser, kingdomType);
        createGameMenu.run();
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
        String filePath = new File("").getAbsolutePath().concat("/Data/stayLoggedInUser.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
