package org.group16.Model;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public class User {
    private String username, password, nickname, email, passwordRecoveryQuestion, passwordRecoveryAnswer, slogan;
    private int score;

    private User(String username, String password, String email,
                 String passwordRecoveryQuestion, String passwordRecoveryAnswer) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.passwordRecoveryQuestion = passwordRecoveryQuestion;
        this.passwordRecoveryAnswer = passwordRecoveryAnswer;
        this.score = 0;
    }

    public static void addUser(String username, String password, String email, String passwordRecoveryQuestion,
                               String passwordRecoveryAnswer) throws IOException {
        User user = new User(username, password, email, passwordRecoveryQuestion, passwordRecoveryQuestion);
        Gson gson = new Gson();
        String filePath = "Data/users.json"; //new File("").getAbsolutePath().concat("/Data/users.json");
        ArrayList<User> allUsers = gson.fromJson(new FileReader(filePath), ArrayList.class);
        allUsers.add(user);
        gson.toJson(allUsers, new FileWriter(filePath));
    }

    public static ArrayList<User> getAllUsers() throws FileNotFoundException {
        Gson gson = new Gson();
        String filePath = "Data/users.json";
        return gson.fromJson(new FileReader(filePath), ArrayList.class);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordRecoveryQuestion() {
        return passwordRecoveryQuestion;
    }

    public void setPasswordRecoveryQuestion(String passwordRecoveryQuestion) {
        this.passwordRecoveryQuestion = passwordRecoveryQuestion;
    }

    public String getPasswordRecoveryAnswer() {
        return passwordRecoveryAnswer;
    }

    public void setPasswordRecoveryAnswer(String passwordRecoveryAnswer) {
        this.passwordRecoveryAnswer = passwordRecoveryAnswer;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
}
