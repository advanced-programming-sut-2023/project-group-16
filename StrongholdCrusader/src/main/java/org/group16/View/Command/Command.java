package org.group16.View.Command;

import java.util.ArrayList;
import java.util.Collections;

public enum Command {
    ;
    private final String commandRegex;
    private final ArrayList<Option> options = new ArrayList<>();

    Command(String commandRegex, ArrayList<Option> options) {
        this.commandRegex = commandRegex;
        Collections.copy(this.options, options);
    }

    public String getCommandRegex() {
        return commandRegex;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }
}
