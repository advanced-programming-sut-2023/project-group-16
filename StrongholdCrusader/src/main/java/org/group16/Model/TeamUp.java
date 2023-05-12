package org.group16.Model;

import java.util.Objects;

public final class TeamUp {
    private static int totalTeamUps = 0;
    private final Kingdom from;
    private final Kingdom to;
    private final int id;

    public TeamUp(Kingdom from, Kingdom to) {
        id = totalTeamUps++;
        this.from = from;
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public Kingdom getFrom() {
        return from;
    }

    public Kingdom getTo() {
        return to;
    }

    @Override
    public String toString() {
        return String.format("%d) from %s to %s",
                from.getUser().getUsername(), to.getUser().getUsername());
    }

}
