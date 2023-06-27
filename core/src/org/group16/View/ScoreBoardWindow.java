package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.group16.Model.User;


import java.util.ArrayList;

public class ScoreBoardWindow extends Window {
    Table table;
    TextButton upButton, downButton;
    Skin skin;
    int firstShow = 0;
    final int playersInTable = 5;

    User user ;

    public ScoreBoardWindow(Skin skin, User user) {
        this("ScoreBoard", skin);
        this.user = user ;
        this.skin = skin;
        this.setSize(400, 1200);

        upButton = new TextButton("UP", skin);
        downButton = new TextButton("DOWN", skin);

        ArrayList<User> users = User.getAllUsers();

        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < users.size() - 1; j++) {
                if (users.get(j).getScore() < users.get(j + 1).getScore()) {
                    User tmp = users.get(j + 1);
                    users.set(j + 1, users.get(j));
                    users.set(j, tmp);
                }
            }
        }
        ScoreBoardRow[] scoreBoardRows = new ScoreBoardRow[10];
        for (int i = firstShow; i < playersInTable; i++) {
            scoreBoardRows[i] = new ScoreBoardRow();
            this.add(scoreBoardRows[i].rank).left().pad(0 , 0 , 0 , 5);
            this.add(scoreBoardRows[i].avatar).left().pad(0 , 0 , 0 , 5);
            this.add(scoreBoardRows[i].name).left().pad(0 , 0 , 0 , 5);
            this.add(scoreBoardRows[i].score).left().row();

        }
        this.add(upButton).left().row();
        this.add(downButton).left().row();


        for (int i = 0; i < playersInTable && i < users.size(); i++) {
            scoreBoardRows[i].setUser(users.get(i), i);
        }

        upButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (firstShow != 0)
                    firstShow -= playersInTable;
                for (int i = firstShow; i < playersInTable + firstShow && i < users.size(); i++) {
                    scoreBoardRows[i-firstShow].setUser(users.get(i), i);
                }
            }
        });
        downButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                firstShow += playersInTable;
                for (int i = firstShow; i < playersInTable + firstShow && i < users.size(); i++) {
                    scoreBoardRows[i-firstShow].setUser(users.get(i), i);
                }
            }
        });

        this.add(table) ;
    }

    public ScoreBoardWindow(String title, Skin skin) {
        super(title, skin);
    }

    public ScoreBoardWindow(String title, Skin skin, String styleName) {
        super(title, skin, styleName);
    }

    public ScoreBoardWindow(String title, com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle style) {
        super(title, style);
    }

    public class ScoreBoardRow {
        User user;
        Image avatar;
        Label score, name, rank;

        ScoreBoardRow() {
            name = new Label("***", skin);
            avatar = new Image(picChange.changer(Gdx.files.internal("backgrounds/white.jpg").path(), 60, 60));
            score = new Label("score : " + 0, skin);
            rank = new Label("*. ", skin);
        }

        public void setUser(User user, int rankNum) {
            this.user = user;
            if (user == null) {
                makeNull();
                return;
            }
            name.setText(user.getNickname());
            avatar.setDrawable(new TextureRegionDrawable(picChange.changer(user.getAvatarPicture(), 60, 60)));
            score.setText("score : " + user.getScore());
            rank.setText(rankNum + ". ");
        }

        public void makeNull() {
            name.setText("***");
            avatar.setDrawable(new TextureRegionDrawable(picChange.changer(Gdx.files.internal("backgrounds/white").path(), 60, 60)));
            score.setText("score : " + 0);
            rank.setText("*. ");
        }
    }


}
