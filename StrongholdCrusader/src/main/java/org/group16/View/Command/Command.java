package org.group16.View.Command;

import java.util.ArrayList;
import java.util.Arrays;

public enum Command {
    //LoginMenu
    CREATE_USER("user\\s+create", new ArrayList<Option>(Arrays.asList(
            new Option("u", 1, true), new Option("p", 2, true),
            new Option("e", 1, true), new Option("n", 1, true),
            new Option("s", 1, false)))),
    CREATE_USER_WITH_RANDOM_PASSWORD("user\\s+create", new ArrayList<Option>(Arrays.asList(
            new Option("u", 1, true), new Option("e", 1, true),
            new Option("n", 1, true), new Option("s", 1, false),
            new Option("p\\s+((random)|(\"random\"))", 0, true)))),
    PICK_QUESTION("question\\s+pick", new ArrayList<Option>(Arrays.asList(
            new Option("q", 1, true), new Option("a", 1, true),
            new Option("c", 1, true))));

    private final String commandRegex;
    private final ArrayList<Option> options;

    Command(String commandRegex, ArrayList<Option> options) {
        this.commandRegex = commandRegex;
        this.options = options;
    }

    public String getCommandRegex() {
        return commandRegex;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }
}
