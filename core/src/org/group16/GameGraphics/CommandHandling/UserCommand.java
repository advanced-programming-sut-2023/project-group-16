package org.group16.GameGraphics.CommandHandling;

import com.google.gson.Gson;
import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class UserCommand {
    private static final Pattern dataPattern = Pattern.compile("\\[\\[(?<type>\\S+)]](?<json>.+)");
    public User user;
    protected boolean executed;

    public UserCommand(User user) {
        this.user = user;
    }

    public static UserCommand tryDeserialize(String data) {
        Gson gson = new Gson();
        Matcher matcher = dataPattern.matcher(data);
        if (!matcher.matches()) return null;
        String type = matcher.group("type");
        String json = matcher.group("json");
        try {
            return (UserCommand) gson.fromJson(json, Class.forName(type));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    protected String success() {
        executed = true;
        return "OK";
    }

    public void resolveUser(Game game) {
        user = game.getUserByUsername(user.getUsername());
    }

    public abstract String execute(Game game, GameRenderer gameRenderer);

    public abstract UserCommand getUndoCommand();

    public String serialize() {
        Gson gson = new Gson();
        return String.format("[[%s]]%s", getClass().getName(), gson.toJson(this));
    }
}
