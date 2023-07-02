package org.group16.Server.Lobby;

import org.group16.Model.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.regex.Pattern;

public class LoginMenuController {
    public static String createUser(String username, String password, String passwordConfirmation,
                                    String email, String nickname, String slogan, String passwordRecoveryQ,
                                    String passwordRecoveryA) {
        User.addUser(username, password, email, passwordRecoveryQ, passwordRecoveryA, nickname, slogan);
        return "OK";
    }

    public static String checkUsername(String username) {
        if (!Pattern.compile("\\w*").matcher(username).matches()) return "invalid username format";
        if (User.getUserByName(username) != null)
            return "this username is already exist, suggested username: " + suggestUsername(username);
        return "OK";
    }

    public static String checkPassword(String password) {
        if (isPasswordWeak(password) != null) return "password is weak: " + isPasswordWeak(password);
        return "OK";
    }

    public static String checkEmail(String email) {
        if (isUserExistByEmail(email)) return "this email is already exist";
        if (!isEmailValid(email)) return "invalid email format";
        return "OK";
    }

    private static String suggestUsername(String username) {
        Random random = new Random();
        int number;
        do {
            number = random.nextInt(1000);
        } while (User.getUserByName(username + number) != null);
        return username + number;
    }

    public static String isPasswordWeak(String password) {
        if (password.equals("random") || password.isEmpty()) return null;
        //if (password.length() < 6) return "length is less than 6 character";
        if (Pattern.compile("[^a-z]").matcher(password).matches()) return "doesn't contain small letter";
        if (Pattern.compile("[^A-Z]").matcher(password).matches()) return "doesn't contain capital letter";
        if (Pattern.compile("\\D+").matcher(password).matches()) return "doesn't contain digit";
        if (Pattern.compile("[a-zA-z\\d]+").matcher(password).matches())
            return "doesn't contain non letter and digit character";
        return null;
    }

    private static boolean isUserExistByEmail(String email) {
        email = email.toLowerCase();
        ArrayList<User> allUsers = User.getAllUsers();
        for (User user : allUsers)
            if (user.getEmail().toLowerCase().equals(email)) return true;
        return false;
    }

    public static String getUserPasswordRecoveryQuestion(String username) {
        User user = User.getUserByName(username);
        return user == null ? null : user.getPasswordRecoveryQuestion();
    }

    private static boolean isEmailValid(String email) {
        return Pattern.compile("[\\w.]+@[\\w.]+\\.[\\w.]+").matcher(email).matches() || email.isEmpty();
    }

    private static String generateSlogan() {
        Random random = new Random();
        int number = random.nextInt(3);
        if (number == 0) return "I shall have my revenge, in this life or the next.";
        if (number == 1) return "That's my secret, Captain.";
        return "Whatever it takes.";
    }

    private static char getChar(int index) {
        String string = "!@#$%^&*()_+-=0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return string.charAt(index);
    }

    public static String generatePassword() {
        Random random = new Random();
        ArrayList<Character> chars = new ArrayList<>();
        chars.add(getChar(random.nextInt(14)));
        chars.add(getChar(random.nextInt(14)));
        chars.add(getChar(14 + random.nextInt(10)));
        chars.add(getChar(14 + random.nextInt(10)));
        chars.add(getChar(24 + random.nextInt(26)));
        chars.add(getChar(24 + random.nextInt(26)));
        chars.add(getChar(50 + random.nextInt(26)));
        chars.add(getChar(50 + random.nextInt(26)));
        Collections.shuffle(chars);
        return chars.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }

    public static String loginUser(String username, String password) {
        User user = User.getUserByName(username);
        if (user == null || !user.getPassword().equals(password)) return "username and password didn't match";
        return "OK";
    }

    public static String checkRecoveryQuestionAnswer(String username, String answer) {
        if (User.getUserByName(username).getPasswordRecoveryAnswer().equals(answer))
            return "please enter your new password:";
        return "incorrect answer";
    }

    public static void setPassword(String username, String password) {
        User.getUserByName(username).setPassword(password);
    }

    public static User getStayLoggedInUser() {
        String folderPath = new File("").getAbsolutePath().concat("/Data");
        try {
            new File(folderPath).mkdirs();
            BufferedReader reader = new BufferedReader(new FileReader(folderPath + "/stayLoggedIn.txt"));
            String username = reader.readLine();
            reader.close();
            return User.getUserByName(username);
        } catch (IOException e) {
            new File(folderPath + "/stayLoggedIn.txt");
            return null;
        }
    }
}
