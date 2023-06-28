package org.group16.Model.Messenger;

import org.group16.Model.User;

import java.util.ArrayList;

public class Message {
    private final User sender;
    private final String time;
    private String text;
    private boolean seen;
    private ArrayList<Emoji> reactions = new ArrayList<>();

    public Message(User sender, String text) {
        this.sender = sender;
        this.time = java.time.LocalTime.now().toString();
        this.text = text;
    }

    public User getSender() {
        return sender;
    }

    public String getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public boolean isSeen() {
        return seen;
    }

    public ArrayList<Emoji> getReactions() {
        return reactions;
    }

    enum Emoji {
        NONE, LIKE, DISLIKE, LOVE
    }
}
