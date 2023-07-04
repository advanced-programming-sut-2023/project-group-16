package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import org.group16.Controller.LoginMenuController;
import org.group16.Model.Messenger.Room;
import org.group16.Model.User;
import org.group16.Networking.LobbySocket;
import org.group16.Server.ChatServer;
import org.group16.StrongholdGame;

import java.io.IOException;
import java.util.ArrayList;

public class MainScreen extends Menu {
    private final User user;
    private final Table table;
    private final Image background, white;
    private final Skin skin1 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    private final Skin skin2 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));

    private TextButton profileMenu, gameMenu, chatButton, chatBack, publicChat, privateChat,
            createRoomButton, openRoom, roomNameSend, joinGame, createGame;

    private TextButton logoutButton;

    private Dialog chat, roomNameDialog;
    private TextField roomName;
    private Label roomNameError;

    public MainScreen(StrongholdGame game, User user) {
        super(game);
        this.user = user;
        uiStage.clear();
        white = new Image(new Texture(Gdx.files.internal("backgrounds/white.jpg")));

        background = new Image(new Texture(Gdx.files.internal("backgrounds/loginMenu.jpg")));

        profileMenu = new TextButton("Profile", skin1);
        profileMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    game.setScreen(new ProfileScreen(game, user));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        gameMenu = new TextButton("Game", skin1);
        gameMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //  game.setScreen(new GameScreen(game));
            }
        });

        createGame = new TextButton("createGame", skin1);
        createGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    game.setScreen(new LobbyScreen(game, user));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        joinGame = new TextButton("joinGame", skin1);
        joinGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    game.setScreen(new JoinRoomScreen(game, user));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        logoutButton = new TextButton("Logout", skin1);
        logoutButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    LobbySocket.logout();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                LoginMenuController.setStayLoggedInUser("", "");
                setScreen(new LoginScreen(game));
            }
        });

        uiStage.addActor(background);

        table = new Table(skin1);
        table.setBackground(white.getDrawable());
        table.setColor(Color.BLACK);
        table.setSize(600, 300);
        table.setPosition(uiStage.getWidth() / 2 - table.getWidth() / 2,
                uiStage.getHeight() / 2 - table.getHeight() / 2);
        handleChat();

        uiStage.addActor(background);
        table.add(profileMenu).row();
        table.add(gameMenu).row();

        table.add(createGame).row();
        table.add(joinGame).row();

//        table.add(lobbyMenu).row();
        table.add(logoutButton).row();

        uiStage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        uiStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        table.setPosition(uiStage.getWidth() / 2 - table.getWidth() / 2, uiStage.getHeight() / 2 - table.getHeight() / 2);
        uiStage.draw();
    }

    private void handleChat() {
        chatBack = new TextButton("Back", skin1);
        chatBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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
                chat.show(uiStage);
            }
        });
        table.add(chatButton).row();
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
