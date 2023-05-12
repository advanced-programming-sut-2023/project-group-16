package org.group16.View.Command;

import java.util.ArrayList;
import java.util.List;

public enum Command {
    CREATE_USER("user\\s+create", new ArrayList<>(List.of(
            new Option("u", 1, true), new Option("p", 2, true),
            new Option("e", 1, true), new Option("n", 1, true),
            new Option("s", 1, false)))),
    CREATE_USER_WITH_RANDOM_PASSWORD("user\\s+create", new ArrayList<>(List.of(
            new Option("u", 1, true), new Option("e", 1, true),
            new Option("n", 1, true), new Option("s", 1, false),
            new Option("p\\s+((random)|(\"random\"))", 0, true)))),
    PICK_QUESTION("question\\s+pick", new ArrayList<>(List.of(
            new Option("q", 1, true), new Option("a", 1, true),
            new Option("c", 1, true)))),
    LOGIN("user\\s+login", new ArrayList<>(List.of(
            new Option("u", 1, true), new Option("p", 1, true),
            new Option("-stay-logged-in", 0, false)))),
    FORGOT_PASSWORD("forgot\\s+my\\s+password", new ArrayList<>(List.of(
            new Option("u", 1, true)))),
    EXIT("exit", new ArrayList<>()),
    LOGOUT("user\\s+logout", new ArrayList<>()),
    CREATE_GAME("create\\s+game", new ArrayList<>(List.of(
            new Option("t", 1, true)))),
    ADD_USER("add\\s+user", new ArrayList<>(List.of(
            new Option("u", 1, true), new Option("t", 1, true)))),
    REMOVE_USER("remove\\s+user", new ArrayList<>(List.of(
            new Option("u", 1, true)))),
    START_GAME("start\\s+game", new ArrayList<>()),
    ENTER_PROFILE_MENU("enter\\s+profile\\s+menu", new ArrayList<>()),
    ENTER_EDITOR_MENU("enter\\s+editor\\s+menu", new ArrayList<>()),
    CHANGE_USERNAME("profile\\s+change", new ArrayList<>(List.of(
            new Option("u", 1, true)))),
    CHANGE_NICKNAME("profile\\s+change", new ArrayList<>(List.of(
            new Option("n", 1, true)))),
    CHANGE_EMAIL("profile\\s+change", new ArrayList<>(List.of(
            new Option("e", 1, true)))),
    CHANGE_SLOGAN("profile\\s+change", new ArrayList<>(List.of(
            new Option("s", 1, true)))),
    CHANGE_PASSWORD("profile\\s+change\\s+password", new ArrayList<>(List.of(
            new Option("o", 1, true), new Option("n", 1, true)))),
    REMOVE_SLOGAN("profile\\s+remove\\s+slogan", new ArrayList<>()),
    DISPLAY_HIGHSCORE("profile\\s+display\\s+highscore", new ArrayList<>()),
    DISPLAY_RANK("profile\\s+display\\s+rank", new ArrayList<>()),
    DISPLAY_SLOGAN("profile\\s+display\\s+slogan", new ArrayList<>()),
    DISPLAY_PROFILE("profile\\s+display", new ArrayList<>()),
    BACK("back", new ArrayList<>()),
    SHOW_MAP("show\\s+map", new ArrayList<>(List.of(
            new Option("x", 1, true), new Option("y", 1, true)))),
    SHOW_DETAILS("show\\s+details", new ArrayList<>(List.of(
            new Option("x", 1, true), new Option("y", 1, true)))),
    SHOW_FACTORS("show\\s+popularity\\s+factors", new ArrayList<>()),
    SHOW_POPULARITY("show\\s+popularity", new ArrayList<>()),
    FOOD_LIST("show\\s+food\\s+list", new ArrayList<>()),
    SET_FOOD_RATE("food\\s+rate", new ArrayList<>(List.of(
            new Option("r", 1, true)))),
    SHOW_FOOD_RATE("food\\s+rate\\s+show", new ArrayList<>()),
    SET_TAX_RATE("tax\\s+rate", new ArrayList<>(List.of(
            new Option("r", 1, true)))),
    SHOW_TAX_RATE("tax\\s+rate\\s+show", new ArrayList<>()),
    SET_FEAR_RATE("fear\\s+rate", new ArrayList<>(List.of(
            new Option("r", 1, true)))),
    DROP_BUILDING("drop\\s+building", new ArrayList<>(List.of(
            new Option("x", 1, true), new Option("y", 1, true),
            new Option("t", 1, true)))),
    SELECT_BUILDING("select\\s+building", new ArrayList<>(List.of(
            new Option("x", 1, true), new Option("y", 1, true)))),
    CREATE_UNIT("create\\s+unit", new ArrayList<>(List.of(
            new Option("t", 1, true), new Option("c", 1, true)))),
    REPAIR("repair", new ArrayList<>()),
    SELECT_UNIT("select\\s+unit", new ArrayList<>(List.of(
            new Option("x", 1, true), new Option("y", 1, true)))),
    MOVE_UNIT("move\\s+unit\\s+to", new ArrayList<>(List.of(
            new Option("x", 1, true), new Option("y", 1, true)))),
    PATROL_UNIT("patrol\\s+unit", new ArrayList<>(List.of(
            new Option("x1", 1, true), new Option("y1", 1, true),
            new Option("x2", 1, true), new Option("y2", 1, true)))),
    ABANDON_UNIT("abandon\\s+unit", new ArrayList<>(List.of(
            new Option("x", 1, true), new Option("y", 1, true)))),
    SET_STATE("set", new ArrayList<>(List.of(
            new Option("x", 1, true), new Option("y", 1, true),
            new Option("s", 1, true)))),
    ATTACK("attack", new ArrayList<>(List.of(new Option("e", 2, true)))),
    ARCHER_ATTACK("attack", new ArrayList<>(List.of(
            new Option("x", 1, true), new Option("y", 1, true)))),
    POUR_OIL("pour\\s+oil", new ArrayList<>(List.of(new Option("d", 1, true)))),
    DIG_TUNNEL("dig\\s+tunnel", new ArrayList<>(List.of(
            new Option("x", 1, true), new Option("y", 1, true)))),
    BUILD("build", new ArrayList<>(List.of(new Option("q", 1, true),
            new Option("x", 1 , true) , new Option("y" , 1 , true)
    ))),
    DISBAND_UNIT("disband\\s+unit", new ArrayList<>()),
    FILTER_UNIT("filter\\s+unit" , new ArrayList<>(List.of(new Option("t", 1, true)))),
    FILTER_SUBTRACTIVE_UNIT("filter\\s+subtractive\\s+unit" , new ArrayList<>(List.of(new Option("t", 1, true)))),
    SET_TEXTURE("set\\s+texture", new ArrayList<>(List.of(
            new Option("x", 1, true), new Option("y", 1, true),
            new Option("t", 1, true)))),
    SET_RECTANGULAR_TEXTURE("set\\s+texture", new ArrayList<>(List.of(
            new Option("x1", 1, true), new Option("y1", 1, true),
            new Option("x2", 1, true), new Option("y2", 1, true),
            new Option("t", 1, true)))),
    CLEAR("clear", new ArrayList<>(List.of(
            new Option("x", 1, true), new Option("y", 1, true)))),
    DROP_ROCK("drop\\s+rock", new ArrayList<>(List.of(
            new Option("x", 1, true), new Option("y", 1, true),
            new Option("d", 1, true)))),
    DROP_TREE("drop\\s+tree", new ArrayList<>(List.of(
            new Option("x", 1, true), new Option("y", 1, true),
            new Option("t", 1, true)))),
    DROP_UNIT("drop\\s+unit", new ArrayList<>(List.of(
            new Option("x", 1, true), new Option("y", 1, true),
            new Option("t", 1, true), new Option("c", 1, true)))),
    CREATE_MAP("create\\s+map", new ArrayList<>(List.of(
            new Option("n", 1, true), new Option("h", 1, true),
            new Option("w", 1, true)))),
    SELECT_MAP("select\\s+map", new ArrayList<>(List.of(
            new Option("n", 1, true)))),
    SAVE_MAP("save\\s+map", new ArrayList<>()),
    DELETE_MAP("delete\\s+map", new ArrayList<>(List.of(
            new Option("n", 1, true)))),
    TRADE_REQUEST("trade", new ArrayList<>(List.of(
            new Option("t", 1, true), new Option("a", 1, true),
            new Option("p", 1, true), new Option("m", 1, true)))),
    SHOW_TRADE_LIST("trade\\s+list", new ArrayList<>()),
    TRADE_ACCEPT("trade\\s+accept", new ArrayList<>(List.of(
            new Option("i", 1, true), new Option("m", 1, true)))),
    SHOW_TRADE_HISTORY("trade\\s+history", new ArrayList<>()),
    SHOW_PRICE_LIST("show\\s+price\\s+list", new ArrayList<>()),
    BUY("buy", new ArrayList<>(List.of(new Option("i", 1, true),
            new Option("a", 1, true)))),
    SELL("sell", new ArrayList<>(List.of(new Option("i", 1, true),
            new Option("a", 1, true)))),
    ;

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
