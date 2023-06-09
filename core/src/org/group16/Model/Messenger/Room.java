package org.group16.Model.Messenger;

import com.google.gson.Gson;
import org.group16.Model.User;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Room {
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Message> messages = new ArrayList<>();
    private String name;

    public Room(ArrayList<User> users, ArrayList<Message> messages, String name) {
        this.users.addAll(users);
        this.messages.addAll(messages);
        this.name = name;
    }

    public User getUser(User user) {
        for (User usr : users)
            if (usr.getUsername().equals(user.getUsername())) return usr;
        return null;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }
}
