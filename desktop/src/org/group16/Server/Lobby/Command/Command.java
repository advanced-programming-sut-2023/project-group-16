package org.group16.Server.Lobby.Command;

import java.util.ArrayList;
import java.util.List;

public enum Command {
    //LOGIN MENU
    REGISTER("register", new ArrayList<>(List.of(
            new Option("u", 1, true), new Option("p", 1, true),
            new Option("e", 1, true), new Option("n", 1, true),
            new Option("s", 1, true), new Option("q", 2, true)))),
    LOGIN("login", new ArrayList<>(List.of(
            new Option("u", 1, true), new Option("p", 1, true)))),
    FORGOT_PASSWORD("forgot\\s+password", new ArrayList<>(List.of(
            new Option("u", 1, true), new Option("a", 1, true),
            new Option("p", 1, true)))),
    LOGOUT("logout", new ArrayList<>()),
    // PROFILE MENU
    CHANGE_PROFILE("change\\s+profile", new ArrayList<>(List.of(
            new Option("u", 1, true), new Option("n", 1, true),
            new Option("e", 1, true), new Option("s", 1, true),
            new Option("p", 1, true)))),
    DISPLAY_SCORE("display\\s+score", new ArrayList<>()),
    DISPLAY_RANK("display\\s+rank", new ArrayList<>()),
    GET_USER("get\\s+user", new ArrayList<>(List.of(
            new Option("u", 1, true)
    ))),
    GET_ALL_USERS("get\\s+all\\s+users", new ArrayList<>()),
    // GAME LOBBY
    CREATE_GAME("create\\s+game", new ArrayList<>(List.of(
            new Option("t", 1, true)))),
    GET_ALL_MAPS("get\\s+all\\s+maps", new ArrayList<>()),
    SELECT_MAP("select\\s+map", new ArrayList<>(List.of(
            new Option("m", 1, true)
    ))),
    ADD_USER("add\\s+user", new ArrayList<>(List.of(
            new Option("u", 1, true), new Option("t", 1, true)))),
    REMOVE_USER("remove\\s+user", new ArrayList<>(List.of(
            new Option("u", 1, true)))),
    START_GAME("start\\s+game", new ArrayList<>()),


    // MISC
    UPLOAD_PFP("upload\\s+pfp", new ArrayList<>(List.of())),
    DOWNLOAD_PFP("download\\s+pfp", new ArrayList<>(List.of())),
    UPLOAD_MAP("upload\\s+map", new ArrayList<>()),
    DOWNLOAD_MAP("download\\s+map", new ArrayList<>(List.of(
            new Option("m", 1, true)
    )));

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
