package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.group16.Client.ChatClient;
import org.group16.Model.Messenger.Message;
import org.group16.Model.Messenger.Room;
import org.group16.Model.User;

import java.io.IOException;

public class Chat extends Window {
    private final Skin skin1 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    private final Skin skin2 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));
    private final Image white = new Image(new Texture(Gdx.files.internal("backgrounds/white.jpg")));
    private final Image like = new Image(new Texture(Gdx.files.internal("emoji/like.png")));
    private final Image dislike = new Image(new Texture(Gdx.files.internal("emoji/dislike.png")));
    private final Image heart = new Image(new Texture(Gdx.files.internal("emoji/heart.png")));
    private final Table table, container;
    private final TextButton back, send, save, delete, cancel;
    private final TextField text, editText;
    private final User user;
    private ScrollPane scrollPane;
    private Dialog dialog;
    private Room room;
    private Message selectedMessage;
    private Chat tmp = this;
    private ChatClient client;

    public Chat(String title, Skin skin, Room room, User user) {
        super(title, skin);
        this.room = room;
        this.user = user;

        try {
            this.client = new ChatClient(this);
        } catch (IOException e) {
            this.client = null;
        }

        back = new TextButton("Back", skin1);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tmp.getStage().getActors().removeValue(tmp, true);
            }
        });

        text = new TextField("", skin1);

        send = new TextButton("Send", skin1);
        send.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sendMessage();
            }
        });

        table = new Table(skin1);
        table.setBackground(white.getDrawable());
        table.setColor(Color.BLACK);
        table.setSize(600, 300);

        scrollPane = new ScrollPane(table, skin1);

        editText = new TextField("", skin1);
        save = new TextButton("Save", skin1);
        save.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editMessage(editText.getText());
                dialog.hide();
            }
        });
        delete = new TextButton("Delete", skin1);
        delete.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                deleteMessage();
                dialog.hide();
            }
        });
        cancel = new TextButton("Cancel", skin1);
        cancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
            }
        });

        dialog = new Dialog("", skin1);
        dialog.setColor(Color.WHITE);
        dialog.getTitleLabel().setColor(Color.BLACK);
        dialog.hide();
        dialog.getContentTable().add(editText);
        dialog.getContentTable().add(save).row();
        dialog.getContentTable().add(like);
        dialog.getContentTable().add(dislike);
        dialog.getContentTable().add(heart).row();
        dialog.getContentTable().add(delete);
        dialog.getContentTable().add(cancel).row();

        container = new Table();
        container.add(back).top().row();
        container.add(scrollPane);
        container.row();
        container.add(text).bottom();
        container.add(send).bottom().row();

        this.add(container);

        setRoom(room);
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room newRoom) {
        room = newRoom;
        table.clear();
        for (Message message : room.getMessages())
            table.add(renderMessage(message)).row();
    }

    private Table renderMessage(Message message) {
        Table table = new Table(skin1);
        table.add(message.getSender().getUsername() + ":").row();
        table.add(message.getText()).row();
        table.add(message.getTime()).row();

        table.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedMessage = message;
                editText.setText(message.getText());
                dialog.show(tmp.getStage());
            }
        });
        return table;
    }

    private void sendMessage() {
        String txt = text.getText();
        if (txt.isEmpty()) return;
        room.addMessage(new Message(user, txt));
        text.setText("");
        try {
            client.notifyServer(room);
        } catch (IOException e) {

        }
    }

    private void editMessage(String txt) {
        if (txt.isEmpty()) {
            deleteMessage();
            return;
        }
        selectedMessage.setText(txt);
        try {
            client.notifyServer(room);
        } catch (IOException e) {

        }
    }

    private void deleteMessage() {
        room.removeMessage(selectedMessage);
        try {
            client.notifyServer(room);
        } catch (IOException e) {

        }
    }
}
