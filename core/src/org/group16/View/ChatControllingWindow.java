package org.group16.View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import org.group16.Model.Messenger.Room;
import org.group16.Model.User;
import org.group16.Server.ChatServer;

import java.util.ArrayList;

public class ChatControllingWindow extends Window {
    Image soilBackground;
    Stage uiStage ;
    Game game ;
    testingGameScreen gameScreen ;
    private final Skin skin1 = new Skin(Gdx.files.internal("neon/skin/default.json"));

    private TextButton chatButton, chatBack, publicChat, privateChat,
            createRoomButton, openRoom, roomNameSend ;

    private Dialog chat, roomNameDialog;
    private TextField roomName;
    private Label roomNameError;

    User user ;


    public ChatControllingWindow(Skin skin , Stage uiStage , Game game , testingGameScreen gameScreen , User user) {
        super("", skin);

        this.game = game ;
        this.gameScreen = gameScreen ;
        this.user = user ;
        this.uiStage = uiStage ;


        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));
        this.setBackground(soilBackground.getDrawable());

        handleChat();

    }



    private void handleChat() {
        chatBack = new TextButton("Back", skin1);
        chatBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.inputControlling = true ;
                chat.hide();
            }
        });

        chat = new Dialog("", skin1);
        chat.hide();
        chat.add(chatBack);

        handlePublicChat();
        handlePrivateChat();
        handleCreateRoom();
        handleOpenRoom();

        chatButton = new TextButton("Chat", skin1);
        chatButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.inputControlling = false ;
                chat.show(uiStage);
            }
        });
        this.add(chatButton).row();
    }

    private void handlePublicChat() {
        publicChat = new TextButton("Public Chat", skin1);
        publicChat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Room room = ChatServer.getRoomByName("public-chat");
                if (room == null) room = new Room(User.getAllUsers(), new ArrayList<>(), "public-chat");
                Chat chat = new Chat("", skin1, room, user);
                chat.setSize(uiStage.getWidth() / 5.0F, uiStage.getHeight());
                uiStage.addActor(chat);
            }
        });

        chat.add(publicChat);
    }

    private void handlePrivateChat() {
        privateChat = new TextButton("Private Chat", skin1);
        privateChat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CreateRoom createRoom = new CreateRoom("", skin1, true, user, uiStage);
                createRoom.setSize(uiStage.getWidth() / 5.0F, uiStage.getHeight());
                uiStage.addActor(createRoom);
            }
        });

        chat.add(privateChat);
    }

    private void handleCreateRoom() {
        createRoomButton = new TextButton("Create Room", skin1);
        createRoomButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CreateRoom createRoom = new CreateRoom("", skin1, false, user, uiStage);
                createRoom.setSize(uiStage.getWidth() / 5.0F, uiStage.getHeight());
                uiStage.addActor(createRoom);
            }
        });

        chat.add(createRoomButton);
    }

    private void handleOpenRoom() {
        openRoom = new TextButton("Open Room", skin1);
        roomNameDialog = new Dialog("", skin1);
        roomName = new TextField("", skin1);
        roomNameError = new Label("", skin1);
        roomNameError.setColor(Color.RED);

        roomNameSend = new TextButton("OK", skin1);
        roomNameSend.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String name = roomName.getText();
                if (name.isEmpty()) return;
                Room room = ChatServer.getRoomByName(name);
                if (room == null) {
                    roomNameError.setText("no room with such name exist");
                } else if (room.getUser(user) == null) {
                    roomNameError.setText("you aren't member of this room");
                } else {
                    roomNameDialog.hide();
                    Chat chat = new Chat("", skin1, room, user);
                    chat.setSize(uiStage.getWidth() / 5.0F, uiStage.getHeight());
                    uiStage.addActor(chat);
                }
            }
        });

        roomNameDialog.button("Cancel").row();
        roomNameDialog.add(roomName);
        roomNameDialog.add(roomNameSend).row();
        roomNameDialog.add(roomNameError);
        openRoom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                roomNameDialog.show(uiStage);
            }
        });

        chat.add(openRoom);
    }
}
