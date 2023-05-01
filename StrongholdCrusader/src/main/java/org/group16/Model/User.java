package org.group16.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;

public class User {
    private String username, password, nickname, email, passwordRecoveryQuestion, passwordRecoveryAnswer, slogan;
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
                               String passwordRecoveryAnswer, String nickname, String slogan) {
        User user = new User(username, password, email, passwordRecoveryQuestion,
                passwordRecoveryAnswer, nickname, slogan);
        Gson gson = new Gson();
        String filePath = new File("").getAbsolutePath().concat("/StrongholdCrusader/src/main/java/org/" +
                "group16/Model/Data/users.json");
        FileReader fileReader;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ArrayList<User> allUsers = gson.fromJson(fileReader, new TypeToken<ArrayList<User>>() {
        }.getType());
        if (allUsers == null) allUsers = new ArrayList<>();
        allUsers.add(user);
        try {
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gson.toJson(allUsers, fileWriter);
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<User> getAllUsers() {
        Gson gson = new Gson();
        String filePath = new File("").getAbsolutePath().concat("/StrongholdCrusader/src/main/java/org/" +
                "group16/Model/Data/users.json");
        FileReader fileReader;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ArrayList<User> allUsers = gson.fromJson(fileReader, new TypeToken<ArrayList<User>>() {
        }.getType());
        try {
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (allUsers == null) allUsers = new ArrayList<>();
        return allUsers;
    }

    public static User getUserByName(String name) {
        ArrayList<User> allUsers = getAllUsers();
        for (User user : allUsers)
            if (user.username.equals(name)) return user;
        return null;
    }

    private static void saveChanges(User user) {
        ArrayList<User> allUsers = getAllUsers();
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).username.equals(user.username)) {
                allUsers.set(i, user);
                break;
            }
        }
        Gson gson = new Gson();
        String filePath = new File("").getAbsolutePath().concat("/StrongholdCrusader/src/main/java/org/" +
                "group16/Model/Data/users.json");
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gson.toJson(allUsers, fileWriter);
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        saveChanges(this);
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
        saveChanges(this);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        saveChanges(this);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        saveChanges(this);
    }

    public String getPasswordRecoveryQuestion() {
        return passwordRecoveryQuestion;
    }

    public void setPasswordRecoveryQuestion(String passwordRecoveryQuestion) {
        this.passwordRecoveryQuestion = passwordRecoveryQuestion;
        saveChanges(this);
    }

    public String getPasswordRecoveryAnswer() {
        return passwordRecoveryAnswer;
    }

    public void setPasswordRecoveryAnswer(String passwordRecoveryAnswer) {
        this.passwordRecoveryAnswer = passwordRecoveryAnswer;
        saveChanges(this);
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
        saveChanges(this);
    }
}
