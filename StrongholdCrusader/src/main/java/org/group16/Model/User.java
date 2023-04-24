package org.group16.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;

public class User {
    private final String username;
    private String password, nickname, email, passwordRecoveryQuestion, passwordRecoveryAnswer, slogan;
    private int score;

    private User(String username, String password, String email, String passwordRecoveryQuestion,
                 String passwordRecoveryAnswer, String nickname, String slogan) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.passwordRecoveryQuestion = passwordRecoveryQuestion;
        this.passwordRecoveryAnswer = passwordRecoveryAnswer;
        this.nickname = nickname;
        this.slogan = slogan;
        this.score = 0;
    }

    public static void addUser(String username, String password, String email, String passwordRecoveryQuestion,
                               String passwordRecoveryAnswer, String nickname, String slogan) throws IOException {
        User user = new User(username, password, email, passwordRecoveryQuestion,
                passwordRecoveryAnswer, nickname, slogan);
        Gson gson = new Gson();
        String filePath = new File("").getAbsolutePath().concat("/src/main/java/org/group16/Model" +
                "/Data/users.json");
        FileReader fileReader = new FileReader(filePath);
        ArrayList<User> allUsers = gson.fromJson(fileReader, new TypeToken<ArrayList<User>>() {
        }.getType());
        if (allUsers == null) allUsers = new ArrayList<>();
        allUsers.add(user);
        fileReader.close();
        FileWriter fileWriter = new FileWriter(filePath);
        gson.toJson(allUsers, fileWriter);
        fileWriter.close();
    }

    public static ArrayList<User> getAllUsers() throws IOException {
        Gson gson = new Gson();
        String filePath = new File("").getAbsolutePath().concat("/src/main/java/org/group16/Model" +
                "/Data/users.json");
        FileReader fileReader = new FileReader(filePath);
        ArrayList<User> allUsers = gson.fromJson(fileReader, new TypeToken<ArrayList<User>>() {
        }.getType());
        fileReader.close();
        if (allUsers == null) allUsers = new ArrayList<>();
        return allUsers;
    }

    public static User getUserByName(String name) throws IOException {
        ArrayList<User> allUsers = getAllUsers();
        for (User user : allUsers)
            if (user.username.equals(name)) return user;
        return null;
    }

    private static void saveChanges(User user) throws IOException {
        ArrayList<User> allUsers = getAllUsers();
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).username.equals(user.username)) {
                allUsers.set(i, user);
                break;
            }
        }
        Gson gson = new Gson();
        String filePath = new File("").getAbsolutePath().concat("/src/main/java/org/group16/Model" +
                "/Data/users.json");
        FileWriter fileWriter = new FileWriter(filePath);
        gson.toJson(allUsers, fileWriter);
        fileWriter.close();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) throws IOException {
        this.score = score;
        saveChanges(this);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws IOException {
        this.password = password;
        saveChanges(this);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) throws IOException {
        this.nickname = nickname;
        saveChanges(this);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IOException {
        this.email = email;
        saveChanges(this);
    }

    public String getPasswordRecoveryQuestion() {
        return passwordRecoveryQuestion;
    }

    public void setPasswordRecoveryQuestion(String passwordRecoveryQuestion) throws IOException {
        this.passwordRecoveryQuestion = passwordRecoveryQuestion;
        saveChanges(this);
    }

    public String getPasswordRecoveryAnswer() {
        return passwordRecoveryAnswer;
    }

    public void setPasswordRecoveryAnswer(String passwordRecoveryAnswer) throws IOException {
        this.passwordRecoveryAnswer = passwordRecoveryAnswer;
        saveChanges(this);
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) throws IOException {
        this.slogan = slogan;
        saveChanges(this);
    }
}
