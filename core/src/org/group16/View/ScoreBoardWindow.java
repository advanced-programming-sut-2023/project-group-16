package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.group16.Model.User;
import org.group16.Networking.LobbySocket;


import java.io.IOException;
import java.util.ArrayList;

public class ScoreBoardWindow extends Window {
    Table table;
    TextButton upButton, downButton , refresh;
    Skin skin;
    int firstShow = 0;
    final int playersInTable = 5;
    Image soilBackground;
    User user;
    ArrayList<User> users ;
    ScoreBoardRow[] scoreBoardRows ;
    public ScoreBoardWindow(Skin skin, User user , ArrayList<User> users) throws IOException {
        this("ScoreBoard", skin);
        this.user = user;
        this.skin = skin;
        this.setSize(400, 1200);

        upButton = new TextButton("UP", skin);
        downButton = new TextButton("DOWN", skin);

        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));
        this.setBackground(soilBackground.getDrawable());
        this.users = users ;

        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < users.size() - 1; j++) {
                if (users.get(j).getScore() < users.get(j + 1).getScore()) {
                    User tmp = users.get(j + 1);
                    users.set(j + 1, users.get(j));
                    users.set(j, tmp);
                }
            }
        }

        refresh = new TextButton("refresh" , skin) ;
        refresh.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    remake();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        scoreBoardRows = new ScoreBoardRow[playersInTable];
        for (int i = firstShow; i < playersInTable; i++) {
            scoreBoardRows[i] = new ScoreBoardRow();
            this.add(scoreBoardRows[i].rank).left().pad(0, 0, 0, 5);
            this.add(scoreBoardRows[i].avatar).left().pad(0, 0, 0, 5);
            this.add(scoreBoardRows[i].name).left().pad(0, 0, 0, 5);
            this.add(scoreBoardRows[i].score).left().pad(0 ,  0 , 0 , 5) ;
            this.add(scoreBoardRows[i].isOnline).row();

        }
        this.add(upButton).left().row();
        this.add(downButton).left().row();
        this.add(refresh);

        for (int i = 0; i < playersInTable && i < users.size(); i++) {
            scoreBoardRows[i].setUser(users.get(i), i);
        }

        upButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (firstShow != 0)
                    firstShow -= playersInTable;
                try {
                    remake();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        downButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (firstShow + playersInTable<users.size())
                    firstShow += playersInTable;
                try {
                    remake();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }

    public ScoreBoardWindow(String title, Skin skin) {
        super(title, skin);
    }

    public void remake() throws IOException, ClassNotFoundException {
        this.users = LobbySocket.getAllUsers() ;
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < users.size() - 1; j++) {
                if (users.get(j).getScore() < users.get(j + 1).getScore()) {
                    User tmp = users.get(j + 1);
                    users.set(j + 1, users.get(j));
                    users.set(j, tmp);
                }
            }
        }
        int cnt = 0 ;
        for (int i = firstShow; i < playersInTable + firstShow && i < users.size(); i++) {
            cnt++ ;
            scoreBoardRows[i - firstShow].setUser(users.get(i), i);
        }
        for (int i = cnt+firstShow ; i < playersInTable+firstShow ; i++)
            scoreBoardRows[i-firstShow].setUser(null , 0) ;
    }


    public class ScoreBoardRow {
        User user;
        Image avatar;
        Label score, name, rank , isOnline;

        ScoreBoardRow() {
            name = new Label("***", skin);
            avatar = new Image(picChange.changer(Gdx.files.internal("backgrounds/white.jpg").path(), 60, 60));
            score = new Label("score : " + 0, skin);
            rank = new Label("*. ", skin);
            isOnline = new Label("" , skin);
        }

        public void setUser(User user, int rankNum) throws IOException {
            this.user = user;
            if (user == null) {
                makeNull();
                return;
            }
            name.setText(user.getNickname());
            avatar.setDrawable(new TextureRegionDrawable(picChange.changer(user.getAvatarPicture(), 60, 60)));
            score.setText("score : " + user.getScore());
            rank.setText(rankNum + ". ");
            if (LobbySocket.isOnline(user.getUsername()))
                isOnline.setText("online");
            else
                isOnline.setText("offline");

        }

        public void makeNull() {
            name.setText("***");
            avatar.setDrawable(new TextureRegionDrawable(picChange.changer(Gdx.files.internal("backgrounds/white.jpg").path(), 60, 60)));
            score.setText("score : " + 0);
            rank.setText("*. ");
        }
    }


}
