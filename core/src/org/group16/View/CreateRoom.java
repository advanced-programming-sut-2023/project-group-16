package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import org.group16.Model.Messenger.Room;
import org.group16.Model.User;

import java.util.ArrayList;

public class CreateRoom extends Window {
    private final Skin skin1 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    private final Skin skin2 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));
    private final Image white = new Image(new Texture(Gdx.files.internal("backgrounds/white.jpg")));
    private final Stage stage;
    private final User owner;
    private final boolean isPV;
    private final ArrayList<User> users = new ArrayList<>();
    private final CreateRoom tmp = this;
    private TextField text, roomName;
    private Label error, roomNameLabel, roomNameError;
    private TextButton add, remove, ok, back;
    private Table table, container;

    public CreateRoom(String title, Skin skin, boolean isPV, User owner, Stage stage) {
        super(title, skin);
        this.isPV = isPV;
        this.owner = owner;
        users.add(owner);
        this.stage = stage;

        text = new TextField("", skin1);

        container = new Table();

        handleBack();
        handleRoomName();
        container.add(text);
        addButton();
        removeButton();
        container.add(error).row();
        container.add(table).row();
        okButton();

        error = new Label("", skin1);

        table = new Table(skin1);
        table.setBackground(white.getDrawable());
        table.setColor(Color.BLACK);
        table.add(owner.getUsername()).row();

        this.add(container);
    }

    private void handleBack() {
        back = new TextButton("Back", skin1);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.getActors().removeValue(tmp, true);
            }
        });
        container.add(back).row();
    }

    private void handleRoomName() {
        if (isPV) return;
        roomNameLabel = new Label("Room Name:", skin1);
        roomName = new TextField("", skin1);
        roomNameError = new Label("", skin1);
        roomNameError.setColor(Color.RED);

        container.add(roomNameLabel);
        container.add(roomName).row();
        container.add(roomNameError).row();
    }

    private void addButton() {
        add = new TextButton("Add", skin1);
        add.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String name = text.getText();
                if (name.isEmpty()) return;
                User user = User.getUserByName(name);
                if (user == null) {
                    error.setColor(Color.RED);
                    error.setText("no user with such name exist");
                } else if (users.contains(user)) {
                    error.setColor(Color.RED);
                    error.setText("user already added");
                } else if (isPV && users.size() == 2) {
                    error.setColor(Color.RED);
                    error.setText("you can have private chat with one user");
                } else {
                    users.add(user);
                    table.add(user.getUsername()).row();
                    error.setColor(Color.GREEN);
                    error.setText("user added");
                }
            }
        });
        container.add(add);
    }

    private void removeButton() {
        remove = new TextButton("Remove", skin1);
        remove.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String name = text.getText();
                if (name.isEmpty()) return;
                User user = find(name);
                if (User.getUserByName(name) == null || user == null) {
                    error.setColor(Color.RED);
                    error.setText("this user didn't add");
                } else if (owner.getUsername().equals(name)) {
                    error.setColor(Color.RED);
                    error.setText("can't remove owner");
                } else {
                    users.remove(user);

                    table = new Table(skin1);
                    table.setBackground(white.getDrawable());
                    table.setColor(Color.BLACK);
                    for (User usr : users)
                        table.add(usr.getUsername()).row();

                    error.setColor(Color.GREEN);
                    error.setText("user removed");
                }
            }
        });
        container.add(remove).row();
    }

    private void okButton() {
        ok = new TextButton("OK", skin1);
        ok.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String name = roomName.getText();
                if (!isPV && !name.matches("\\w+")) {
                    roomNameError.setText("invalid room name format");
                } else if (Room.getRoomByName(name) != null) {
                    roomNameError.setText("room with this name already exist");
                } else if (users.size() == 1) {
                    error.setColor(Color.RED);
                    error.setText("add a person");
                } else {
                    Room room = new Room(users, new ArrayList<>(), name);
                    Chat chat = new Chat("", skin1, room, owner);
                    chat.setSize(stage.getWidth() / 5.0F, stage.getHeight());
                    stage.getActors().removeValue(tmp, true);
                    stage.addActor(chat);
                }
            }
        });
        container.add(ok).row();
    }

    private User find(String name) {
        for (User user : users)
            if (user.getUsername().equals(name)) return user;
        return null;
    }
}
