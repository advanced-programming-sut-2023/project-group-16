package org.group16.GameGraphics.CommandHandling;

import com.google.gson.Gson;
import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.User;

import java.io.Serializable;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class UserCommand implements Serializable {
    private static final Pattern dataPattern = Pattern.compile("\\[\\[(?<type>\\S+)]](?<json>.+)");
    public final UUID uuid = UUID.randomUUID();
    public final String username;
    public transient User user;
    protected transient boolean executed;

    public UserCommand(User user) {
        username = user.getUsername();
    }

    //    @Deprecated
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
        user = game.getUserByUsername(username);
    }

    public abstract String execute(Game game, GameRenderer gameRenderer);

    public abstract UserCommand getUndoCommand();

    //    @Deprecated
    public String serialize() {
        Gson gson = new Gson();
        return String.format("[[%s]]%s", getClass().getName(), gson.toJson(this));
    }
}
