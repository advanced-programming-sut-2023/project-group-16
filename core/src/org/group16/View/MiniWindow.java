package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MiniWindow extends Window {

    Image soilBackground , face;
    Label popularityLabel , populationLabel , maxPopulationLabel , goldLabel ;
    int popularity , population ,  maxPopulation , gold ;

    public MiniWindow(Skin skin , int popularity , int population , int maxPopulation , int gold) {
        super("", skin);

        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));

        face = new Image() ;
        popularityLabel = new Label("" , skin) ;
        populationLabel = new Label("" , skin) ;
        maxPopulationLabel = new Label("" , skin) ;
        goldLabel = new Label("" , skin) ;
        remake(popularity , population , maxPopulation , gold);
    }

    public void remake(int popularity , int population , int maxPopulation , int gold){
        this.clear();

        this.setBackground(soilBackground.getDrawable());
        this.popularity = popularity ;
        this.population = population ;
        this.maxPopulation = maxPopulation ;
        this.gold = gold ;

        face.setDrawable(popularityToEmoji().getDrawable());

        this.add(face).row();
        popularityLabel.setText("popularity : " + popularity);
        popularityLabel.setColor(popularityToColor());
        this.add(popularityLabel).row(); ;
        populationLabel.setText("population :" + population+"");
        populationLabel.setColor(popularityToColor());
        this.add(populationLabel) ;
        maxPopulationLabel.setText("/" + maxPopulation);
        maxPopulationLabel.setColor(popularityToColor());
        this.add(maxPopulationLabel).row();
        goldLabel.setText("gold : " + gold);
        goldLabel.setColor(popularityToColor());
        this.add(goldLabel) ;
    }


    public Image popularityToEmoji(){
        if (popularity > 60)
            return new Image(new TextureRegionDrawable(picChange.changer(Gdx.files.internal("MenuPictures/FaceEmoji/HAPPY.png").path(), 60 , 60))) ;
        else if (popularity > 40)
            return new Image(new TextureRegionDrawable(picChange.changer(Gdx.files.internal("MenuPictures/FaceEmoji/NORMAL.png").path(), 60 , 60))) ;
        else return new Image(new TextureRegionDrawable(picChange.changer(Gdx.files.internal("MenuPictures/FaceEmoji/SAD.png").path(), 60 , 60))) ;
    }

    public Color popularityToColor(){
        if (popularity > 60)
            return Color.GREEN ;
        else if (popularity > 40)
            return Color.YELLOW ;
        else return Color.RED ;
    }
}
