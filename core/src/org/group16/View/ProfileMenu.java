package org.group16.View;

import org.group16.Controller.ProfileMenuController;
import org.group16.Model.User;
import org.group16.View.Command.Command;
import org.group16.View.Command.CommandHandler;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class ProfileMenu {
    private static Scanner scanner;
    private final User currentUser;

    public ProfileMenu(Scanner scanner, User currentUser) {
        ProfileMenu.scanner = scanner;
        this.currentUser = currentUser;
    }

    public static String getPasswordConfirmation() {
        System.out.println("please enter your new password here:");
        return scanner.nextLine();
    }

    public void run() {
        while (true) {
            String input = scanner.nextLine();
            TreeMap<String, ArrayList<String>> map;
            if ((map = CommandHandler.matches(Command.CHANGE_USERNAME, input)) != null) changeUsername(map);
            else if ((map = CommandHandler.matches(Command.CHANGE_NICKNAME, input)) != null) changeNickname(map);
            else if ((map = CommandHandler.matches(Command.CHANGE_PASSWORD, input)) != null) changePassword(map);
            else if ((map = CommandHandler.matches(Command.CHANGE_EMAIL, input)) != null) changeEmail(map);
            else if ((map = CommandHandler.matches(Command.CHANGE_SLOGAN, input)) != null) changeSlogan(map);
            else if (CommandHandler.matches(Command.REMOVE_SLOGAN, input) != null) removeSlogan();
            else if (CommandHandler.matches(Command.DISPLAY_HIGHSCORE, input) != null) displayHighScore();
            else if (CommandHandler.matches(Command.DISPLAY_RANK, input) != null) displayRank();
            else if (CommandHandler.matches(Command.DISPLAY_SLOGAN, input) != null) displaySlogan();
            else if (CommandHandler.matches(Command.DISPLAY_PROFILE, input) != null) displayProfile();
            else if (CommandHandler.matches(Command.BACK, input) != null) {
                System.out.println("exit profile menu successfully");
                break;
            } else System.out.println("invalid command");
        }
    }

    private void changeUsername(TreeMap<String, ArrayList<String>> map) {
        String username = map.get("u").get(0);
        System.out.println(ProfileMenuController.changeUsername(currentUser, username));
    }

    private void changeNickname(TreeMap<String, ArrayList<String>> map) {
        String nickname = map.get("n").get(0);
        System.out.println(ProfileMenuController.changeNickname(currentUser, nickname));
    }

    private void changePassword(TreeMap<String, ArrayList<String>> map) {
        String oldPassword = map.get("o").get(0);
        String newPassword = map.get("n").get(0);
        System.out.println(ProfileMenuController.changePassword(currentUser, oldPassword, newPassword));
    }

    private void changeEmail(TreeMap<String, ArrayList<String>> map) {
        String email = map.get("e").get(0);
        System.out.println(ProfileMenuController.changeEmail(currentUser, email));
    }

    private void changeSlogan(TreeMap<String, ArrayList<String>> map) {
        String slogan = map.get("s").get(0);
        System.out.println(ProfileMenuController.changeSlogan(currentUser, slogan));
    }

    private void removeSlogan() {
        System.out.println(ProfileMenuController.removeSlogan(currentUser));
    }

    private void displayHighScore() {
        System.out.println(ProfileMenuController.displayHighScore(currentUser));
    }

    private void displayRank() {
        System.out.println(ProfileMenuController.displayRank(currentUser));
    }

    private void displaySlogan() {
        System.out.println(ProfileMenuController.displaySlogan(currentUser));
    }

    private void displayProfile() {
        System.out.println(ProfileMenuController.displayProfile(currentUser));
    }
}
