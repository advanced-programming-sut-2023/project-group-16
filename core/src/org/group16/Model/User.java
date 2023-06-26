package org.group16.Model;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String passwordRecoveryQuestion;
    private String passwordRecoveryAnswer;
    private String slogan;


    public void setScore(int score) {
        this.score = score;
    }

    private String avatarPictureP;
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
        user.setAvatarPicture("mainPfp/LionHeart.jpg");
        saveChanges(user);
    }
    public static void removeUser(User user) {
        String folderPath = new File("").getAbsolutePath() + "/Data/Users";
        File file = new File(folderPath + "/" + user.username + ".json");
        file.delete();
    }

    public static ArrayList<User> getAllUsers() {
        Gson gson = new Gson();
        String folderPath = new File("").getAbsolutePath().concat("/Data/Users");
        File[] files = new File(folderPath).listFiles();
        ArrayList<User> users = new ArrayList<>();

        if (files != null) for (File file : files) {
            if (file.isDirectory()) continue;
            FileReader fileReader;
            try {
                fileReader = new FileReader(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            User user = null;
            try {
                user = gson.fromJson(fileReader, User.class);
            } catch (Exception ignored) {
            }
            try {
                fileReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (user != null)
                users.add(user);
        }
        return users;
    }

    public static User getUserByName(String name) {
        Gson gson = new Gson();
        String folderPath = new File("").getAbsolutePath() + "/Data/Users";
        FileReader fileReader;
        try {
            File directory = new File(folderPath);
            directory.mkdirs();
            fileReader = new FileReader(folderPath + "/" + name + ".json");
        } catch (IOException e) {
            return null;
        }
        User user = gson.fromJson(fileReader, User.class);
        try {
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    private static void saveChanges(User user) {
        Gson gson = new Gson();
        String folderPath = new File("").getAbsolutePath() + "/Data/Users";
        FileWriter fileWriter;
        try {
            File directory = new File(folderPath);
            directory.mkdirs();
            fileWriter = new FileWriter(folderPath + "/" + user.username + ".json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gson.toJson(user, fileWriter);
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
        saveChanges(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        removeUser(this);
        this.username = username;
        saveChanges(this);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
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
    public String getAvatarPicture() {
        return avatarPictureP;
    }

    public void setAvatarPicture(String avatarPictureP) {
        this.avatarPictureP = avatarPictureP;
        saveChanges(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }
}
