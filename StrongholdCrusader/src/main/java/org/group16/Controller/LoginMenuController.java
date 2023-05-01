package org.group16.Controller;

import com.google.common.hash.Hashing;
import org.group16.Model.User;
import org.group16.View.LoginMenu;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class LoginMenuController {
    public static String createUser(String username, String password, String passwordConfirmation,
                                    String email, String nickname, String slogan) {
        if (username.isEmpty() || password.isEmpty() ||
                passwordConfirmation.isEmpty() || email.isEmpty() || nickname.isEmpty()) return "field can't be empty";
        if (!Pattern.compile("\\w+").matcher(username).matches()) return "invalid username format";
        if (User.getUserByName(username) != null)
            return "this username is already exist, suggested username: " + suggestUsername(username);
        if (isPasswordWeak(password) != null) return "password is weak: " + isPasswordWeak(password);
        if (!password.equals(passwordConfirmation)) return "password and password confirmation are not same";
        if (isUserExistByEmail(email)) return "this email is already exist";
        if (!isEmailValid(email)) return "invalid email format";
        if (slogan.equals("random")) {
            slogan = generateSlogan();
            LoginMenu.print("Your slogan is: " + slogan);
        }
        if (password.equals("random")) {
            password = generatePassword();
            LoginMenu.print("Your random password is: " + password);
            passwordConfirmation = LoginMenu.getPasswordConfirmation();
            if (!password.equals(passwordConfirmation)) return "password and password confirmation are not same";
        }
        password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        TreeMap<String, ArrayList<String>> map = LoginMenu.questionPick();
        if (map == null) return "invalid command";
        String passwordRecoveryQuestion = null;
        if (map.get("q").get(0).equals("1")) passwordRecoveryQuestion = "What is my father's name?";
        if (map.get("q").get(0).equals("2")) passwordRecoveryQuestion = "What was my first pet's name?";
        if (map.get("q").get(0).equals("3")) passwordRecoveryQuestion = "What is my mother's last name?";
        if (passwordRecoveryQuestion == null) return "invalid command";
        if (!map.get("a").get(0).equals(map.get("c").get(0)))
            return "password recovery answer and it's confirmation are not same";
        String passwordRecoveryAnswer = map.get("a").get(0);
        User.addUser(username, password, email, passwordRecoveryQuestion, passwordRecoveryAnswer, nickname, slogan);
        return "user created successfully";
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
        if (password.equals("random")) return null;
        if (password.length() < 6) return "length is less than 6 character";
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
        return Pattern.compile("[\\w.]+@[\\w.]+\\.[\\w.]+").matcher(email).matches();
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

    private static String generatePassword() {
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

    public static String loginUser(String username, String password, boolean stayLoggedIn) {
        User user = User.getUserByName(username);
        password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        if (user == null || !user.getPassword().equals(password)) return "username and password didn't match";
        if (stayLoggedIn) {
            String filePath = new File("").getAbsolutePath().concat("/StrongholdCrusader/src/main/java/" +
                    "org/group16/Model/Data/stayLoggedInUser.txt");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                writer.write(username);
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "user " + username + " logged in successfully";
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
        String filePath = new File("").getAbsolutePath().concat("/StrongholdCrusader/src/main/java/org/" +
                "group16/Model/Data/stayLoggedInUser.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String username = reader.readLine();
            reader.close();
            return User.getUserByName(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
