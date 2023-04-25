package org.group16.View;

import org.group16.Controller.LoginMenuController;
import org.group16.View.Command.Command;
import org.group16.View.Command.CommandHandler;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;

public class LoginMenu {
    private static Scanner scanner;

    public static String getPasswordConfirmation() {
        System.out.println("Please re-enter your password here:");
        return scanner.nextLine();
    }

    public static void print(String string) {
        System.out.println(string);
    }

    public static TreeMap<String, ArrayList<String>> questionPick() {
        System.out.println("Pick your security question:");
        System.out.println("1. What is my father's name?");
        System.out.println("2. What was my first pet's name?");
        System.out.println("3. What is my mother's last name?");
        String input = scanner.nextLine();
        return CommandHandler.matches(Command.PICK_QUESTION, input);
    }//TODO

    public void run() {
        scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            TreeMap<String, ArrayList<String>> map;
            if ((map = CommandHandler.matches(Command.CREATE_USER, input)) != null) createUser(map);
            else if ((map = CommandHandler.matches(Command.CREATE_USER_WITH_RANDOM_PASSWORD, input)) != null)
                createUser(map);
            else System.out.println("invalid command");
        }
    }//TODO

    private void createUser(TreeMap<String, ArrayList<String>> map) {
        String username = map.get("u").get(0);
        String password = map.containsKey("p") ? map.get("p").get(0) : "random";
        String passwordConfirmation = map.containsKey("p") ? map.get("p").get(1) : "random";
        String email = map.get("e").get(0);
        String nickname = map.get("n").get(0);
        String slogan = map.containsKey("s") ? map.get("s").get(0) : "";
        System.out.println(LoginMenuController.createUser(username, password, passwordConfirmation,
                email, nickname, slogan));
    }

    private void loginUser(Matcher matcher) {
    }//TODO

    private void forgotPassword(Matcher matcher) {
    }//TODO
}