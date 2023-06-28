package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Messenger extends Window {
    private final Skin skin1 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    private final Skin skin2 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));
    private final Image white = new Image(new Texture(Gdx.files.internal("backgrounds/white.jpg")));
    private Table table, container;
    private ScrollPane scrollPane;

    public Messenger(String title, Skin skin) {
        super(title, skin);

        table = new Table(skin1);
        table.setBackground(white.getDrawable());
        table.setColor(Color.BLACK);
        table.setSize(600, 300);
        
        for (int i = 0; i < 100; i++)
            table.add("" + i).row();

        scrollPane = new ScrollPane(table, skin1);

        container = new Table();
        container.setFillParent(true);
        container.add(scrollPane).fill().expand();
        container.row();

        this.add(container);
    }
}
