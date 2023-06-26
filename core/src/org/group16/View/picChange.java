package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class picChange {
    public static Texture changer(String path , int x , int y){
        Pixmap pixmap2 = new Pixmap (Gdx.files.internal (path));
        Pixmap pixmap = new Pixmap(x, y, pixmap2.getFormat ()) ;
        pixmap.drawPixmap(pixmap2 , 0 , 0 , pixmap2.getWidth() , pixmap2.getHeight() , 0 , 0 , pixmap.getWidth() , pixmap.getHeight());
        Texture texture = new Texture (pixmap);
        pixmap.dispose();
        pixmap2.dispose();
        return texture ;
    }
}