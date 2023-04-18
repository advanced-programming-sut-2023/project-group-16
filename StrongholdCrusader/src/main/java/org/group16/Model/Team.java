package org.group16.Model;

import java.util.ArrayList;

public class Team {
    private final ArrayList<Kingdom> kingdoms = new ArrayList<>();

    public Team(Kingdom kingdom) {
        kingdoms.add(kingdom);
    }

    public void addKingdom(Kingdom kingdom) {
        kingdoms.add(kingdom);
    }

    public void removeKingdom(Kingdom kingdom) {
        kingdoms.remove(kingdom);
    }
}
