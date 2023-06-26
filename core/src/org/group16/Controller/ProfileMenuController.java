package org.group16.Controller;

import com.google.common.hash.Hashing;
import org.group16.Model.User;
import org.group16.ViewTerminal.ProfileMenu;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

public class ProfileMenuController {
    public static String checkChangeUsername(User user, String newUsername) {
        if (newUsername.equals(user.getUsername())) return "choose a new username";
        if (newUsername.isEmpty()) return "username can't be empty";
        if (!Pattern.compile("\\w+").matcher(newUsername).matches()) return "invalid username format";
        if (User.getUserByName(newUsername) != null)
            return "this username is already exist, suggested username: " + suggestUsername(newUsername);
        return "";
    }

    public static void changeUsername(User user, String newUsername) {
        user.setUsername(newUsername);
    }

    private static String suggestUsername(String username) {
        Random random = new Random();
        int number;
        do {
            number = random.nextInt(1000);
        } while (User.getUserByName(username + number) != null);
        return username + number;
    }

    public static String checkChangeNickname(User user, String newNickname) {
        if (newNickname.equals(user.getNickname())) return "choose a new nickname";
        if (newNickname.isEmpty()) return "nickname can't be empty";
        return "";
    }

    public static void changeNickname(User user, String newNickname) {
        user.setNickname(newNickname);
    }

    public static String checkChangePassword(User user, String oldPassword) {
        oldPassword = Hashing.sha256().hashString(oldPassword, StandardCharsets.UTF_8).toString();
        if (!user.getPassword().equals(oldPassword)) return "password is incorrect";
        return "";
    }

    public static void changePassword(User user, String newPassword) {
        String hashedNewPassword = Hashing.sha256().hashString(newPassword, StandardCharsets.UTF_8).toString();
        user.setPassword(hashedNewPassword);
    }

    public static String isPasswordWeak(String password) {
        if (password.isEmpty()) return "password can't be empty";
        if (password.length() < 6) return "length is less than 6 character";
        if (Pattern.compile("[^a-z]").matcher(password).matches()) return "doesn't contain small letter";
        if (Pattern.compile("[^A-Z]").matcher(password).matches()) return "doesn't contain capital letter";
        if (Pattern.compile("\\D+").matcher(password).matches()) return "doesn't contain digit";
        if (Pattern.compile("[a-zA-z\\d]+").matcher(password).matches())
            return "doesn't contain non letter and digit character";
        return "";
    }

    public static String checkChangeEmail(User user, String newEmail) {
        if (newEmail.equals(user.getEmail())) return "choose a new email";
        if (newEmail.isEmpty()) return "email can't be empty";
        if (!Pattern.compile("[\\w.]+@[\\w.]+\\.[\\w.]+").matcher(newEmail).matches())
            return "invalid email format";
        if (isUserExistByEmail(newEmail)) return "this email is already exist";
        return "";
    }

    public static void changeEmail(User user, String newEmail) {
        user.setEmail(newEmail);
    }

    private static boolean isUserExistByEmail(String email) {
        email = email.toLowerCase();
        ArrayList<User> allUsers = User.getAllUsers();
        for (User user : allUsers)
            if (user.getEmail().toLowerCase().equals(email)) return true;
        return false;
    }

    public static String checkChangeSlogan(User user, String newSlogan) {
        if (newSlogan.isEmpty()) return "slogan can't be empty";
        if (newSlogan.equals(user.getSlogan())) return "choose a new slogan";
        return "";
    }

    public static void changeSlogan(User user, String newSlogan) {
        user.setSlogan(newSlogan);
    }

    public static String removeSlogan(User user) {
        user.setSlogan("");
        return "slogan removed successfully";
    }

    public static String displayHighScore(User user) {
        return "high score: " + user.getScore();
    }

    public static String displayRank(User user) {
        ArrayList<User> allUsers = User.getAllUsers();
        allUsers.sort((o1, o2) -> {
            if (o1.getScore() != o2.getScore()) return o2.getScore() - o1.getScore();
            return o1.getUsername().compareTo(o2.getUsername());
        });
        for (int i = 0; i < allUsers.size(); i++)
            if (allUsers.get(i).getUsername().equals(user.getUsername())) return "rank: " + (i + 1);
        return null;
    }

    public static String displaySlogan(User user) {
        return user.getSlogan().isEmpty() ? "slogan is empty" : user.getSlogan();
    }

    public static String displayProfile(User user) {
        String output = "username: " + user.getUsername() + "\n";
        output += "nickname: " + user.getNickname() + "\n";
        output += "email: " + user.getEmail() + "\n";
        output += "slogan: " + user.getSlogan() + "\n";
        output += "highscore: " + user.getScore() + "\n";
        output += displayRank(user);
        return output;
    }
}
