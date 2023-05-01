package org.group16.Controller;

import com.google.common.hash.Hashing;
import org.group16.Model.User;
import org.group16.View.ProfileMenu;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

public class ProfileMenuController {
    public static String changeUsername(User user, String newUsername) {
        if (newUsername.isEmpty()) return "username can't be empty";
        if (!Pattern.compile("\\w+").matcher(newUsername).matches()) return "invalid username format";
        if (User.getUserByName(newUsername) != null)
            return "this username is already exist, suggested username: " + suggestUsername(newUsername);
        user.setUsername(newUsername);
        return "username changed successfully";
    }

    private static String suggestUsername(String username) {
        Random random = new Random();
        int number;
        do {
            number = random.nextInt(1000);
        } while (User.getUserByName(username + number) != null);
        return username + number;
    }

    public static String changeNickname(User user, String newNickname) {
        if (newNickname.isEmpty()) return "nickname can't be empty";
        user.setNickname(newNickname);
        return "nickname changed successfully";
    }

    public static String changePassword(User user, String oldPassword, String newPassword) {
        oldPassword = Hashing.sha256().hashString(oldPassword, StandardCharsets.UTF_8).toString();
        String hashedNewPassword = Hashing.sha256().hashString(newPassword, StandardCharsets.UTF_8).toString();
        if (!user.getPassword().equals(oldPassword)) return "current password is incorrect";
        if (user.getPassword().equals(hashedNewPassword)) return "please enter a new password";
        if (newPassword.isEmpty()) return "password can't be empty";
        if (isPasswordWeak(newPassword) != null) return "password is weak: " + isPasswordWeak(newPassword);
        if (!ProfileMenu.getPasswordConfirmation().equals(newPassword))
            return "password and password confirmation are not same";
        user.setPassword(hashedNewPassword);
        return "password changed successfully";
    }

    private static String isPasswordWeak(String password) {
        if (password.equals("random")) return null;
        if (password.length() < 6) return "length is less than 6 character";
        if (Pattern.compile("[^a-z]").matcher(password).matches()) return "doesn't contain small letter";
        if (Pattern.compile("[^A-Z]").matcher(password).matches()) return "doesn't contain capital letter";
        if (Pattern.compile("\\D+").matcher(password).matches()) return "doesn't contain digit";
        if (Pattern.compile("[a-zA-z\\d]+").matcher(password).matches())
            return "doesn't contain non letter and digit character";
        return null;
    }

    public static String changeEmail(User user, String newEmail) {
        if (newEmail.isEmpty()) return "email can't be empty";
        if (!Pattern.compile("[\\w.]+@[\\w.]+\\.[\\w.]+").matcher(newEmail).matches())
            return "invalid email format";
        if (isUserExistByEmail(newEmail)) return "this email is already exist";
        user.setEmail(newEmail);
        return "email changed successfully";
    }

    private static boolean isUserExistByEmail(String email) {
        email = email.toLowerCase();
        ArrayList<User> allUsers = User.getAllUsers();
        for (User user : allUsers)
            if (user.getEmail().toLowerCase().equals(email)) return true;
        return false;
    }

    public static String changeSlogan(User user, String newSlogan) {
        if (newSlogan.isEmpty()) return "slogan can't be empty";
        user.setSlogan(newSlogan);
        return "slogan changed successfully";
    }

    public static String removeSlogan(User user) {
        user.setSlogan("");
        return "slogan removed successfully";
    }

    public static String displayHighScore(User user) {
        return "high score: " + user.getHighScore();
    }

    public static String displayRank(User user) {
        ArrayList<User> allUsers = User.getAllUsers();
        allUsers.sort((o1, o2) -> {
            if (o1.getHighScore() != o2.getHighScore()) return o2.getHighScore() - o1.getHighScore();
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
        output += "highscore: " + user.getHighScore() + "\n";
        output += displayRank(user);
        return output;
    }
}
