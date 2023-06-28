package org.group16.Model.Messenger;

import org.group16.Model.User;

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
}
