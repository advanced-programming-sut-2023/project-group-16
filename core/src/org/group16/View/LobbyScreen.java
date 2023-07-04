package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import org.group16.Model.*;
import org.group16.Networking.LobbySocket;
import org.group16.StrongholdGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LobbyScreen extends Menu {


    User user;
    Table table;
    Skin skin2 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    Skin skin1 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));
    TextButton back, add, start;
    TextField usernameField;
    Label usernameStatus, startStatus;
    SelectBox<String> selectBox = new SelectBox<String>(skin1);
    SelectBox<String> mapSelectBox = new SelectBox<String>(skin1);
    ArrayList<User> users = new ArrayList<>();
    HashMap<User, KingdomType> hashMap = new HashMap<>();
    private Image background, white, soilBackground;

    public LobbyScreen(StrongholdGame game, User user) throws IOException, ClassNotFoundException {
        super(game);
        uiStage.clear();


        this.user = user;

        white = new Image(new Texture(Gdx.files.internal("backgrounds/white.jpg")));
        background = new Image(new Texture(Gdx.files.internal("backgrounds/lobbyBackground.jpg")));
        background.setFillParent(true);
        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));

        table = new Table(skin1);
        table.setBackground(soilBackground.getDrawable());
        table.setSize(500, 400);

        selectBox.setItems("ARABIAN", "EUROPEAN");


        Array<String> mapArray = new Array<>();
        for (String mapName : LobbySocket.getAllMaps()) {
            mapArray.add(mapName);
        }

        mapSelectBox.setItems(mapArray);


        uiStage.addActor(background);
        uiStage.addActor(table);
        remake();
    }

    public void remake() {
        table.clear();
        table.background(soilBackground.getDrawable());

        usernameField = new TextField("", skin1);
        usernameStatus = new Label("", skin1);
        usernameStatus.setColor(Color.RED);
        add = new TextButton("add", skin1);
        back = new TextButton("back", skin1);
        start = new TextButton("start", skin1);
        startStatus = new Label("", skin1);
        startStatus.setColor(Color.RED);

        table.add(usernameField).pad(0, 0, 0, 5);
        table.add(add).pad(0, 0, 0, 5);
        table.add(selectBox).pad(0, 0, 0, 5);
        table.add(usernameStatus).row();

        add.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    if (LobbySocket.getUser(usernameField.getText()) == null) {
                        usernameStatus.setText("no user Found");
                    } else if (users.contains(LobbySocket.getUser(usernameField.getText()))) {
                        usernameStatus.setText("user already added");
                    } else {
                        if (selectBox.getSelected().equals("ARABIAN")) {
                            users.add(LobbySocket.getUser(usernameField.getText()));
                            hashMap.put(users.get(users.size() - 1), KingdomType.ARAB);
                        } else {
                            users.add(LobbySocket.getUser(usernameField.getText()));
                            hashMap.put(users.get(users.size() - 1), KingdomType.EUROPEAN);
                        }
                        remake();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        int rowCounter = 1;
        for (User user1 : users) {
            table.add(new Label(rowCounter + " : " + user1.getNickname(), skin1)).pad(0, 0, 0, 5);
            TextButton delButton = new TextButton("delete", skin1);
            table.add(delButton).row();
            delButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    users.remove(user1);
                    remake();
                }
            });
        }

        table.add(mapSelectBox).row();

        table.add(start);
        table.add(startStatus).row();
        table.add(back).row();

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainScreen(game, user));
            }
        });

        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (users.size() < 2) {
                    startStatus.setText("not enough players");
                } else if (users.size() >= 8) {
                    startStatus.setText("game is full");
                } else {
                    boolean canStart = true;
                    Scene scene = null;


                    try {
                        String status = LobbySocket.createGame();
                        if (!status.equals("OK")) {
                            startStatus.setText(status);
                            canStart = false;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    for (User user : users) {
                        if (!canStart)
                            break;
                        try {
                            String status = LobbySocket.addUser(user.getUsername(), hashMap.get(user).toString());
                            if (!status.equals("OK")) {
                                canStart = false;
                                break;
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    try {
                        LobbySocket.selectMap(mapSelectBox.getSelected());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (!canStart) {
                        startStatus.setText("some problem with server!");
                    } else {
                        GameInfo gameInfo = null;
                        try {
                            var res = LobbySocket.startGame();
                            if (res instanceof String) {
                                //TODO : ERROR
                            } else {
                                gameInfo = (GameInfo) res;
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        if (gameInfo != null)
                            try {
                                Map map = LobbySocket.downloadMap(gameInfo.mapname());
                                PlayerList playerList = gameInfo.playerList();
                                long random = gameInfo.randomSeed();
                                scene = new Scene(map, random);
                                Game game1 = new Game();

                                for (int i = 0; i < playerList.users.size(); i++) {
                                    game1.addUser(playerList.users.get(i), playerList.kingdomTypes.get(i));
                                }

                                game1.setScene(scene);
                                testingGameScreen gameScreen = new testingGameScreen(game, game1, gameInfo, user);
                                game.setScreen(gameScreen);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }

                    }
                }
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        uiStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        table.setPosition(uiStage.getWidth() / 2 - table.getWidth() / 2, uiStage.getHeight() / 2 - table.getHeight() / 2);
        uiStage.draw();
    }
}
