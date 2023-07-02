package org.group16.Model.Messenger;

import org.group16.Model.User;

public class Reaction {
    private User owner;
    private Emoji emoji;
    Reaction(User owner, Emoji emoji) {
        this.owner = owner;
        this.emoji = emoji;
    }

    enum Emoji {
        NONE, LIKE, DISLIKE, LOVE;
    }
}
