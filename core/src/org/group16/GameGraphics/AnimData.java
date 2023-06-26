package org.group16.GameGraphics;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class AnimData {
    private final Array<TextureAtlas.AtlasRegion> data;
    private final int directionCount;
    private final int fps;


    public AnimData(Array<TextureAtlas.AtlasRegion> data, int directionCount, int fps) {
        this.data = data;
        this.directionCount = directionCount;
        this.fps = fps;
    }

    public AnimData(Array<TextureAtlas.AtlasRegion> data, int directionCount) {
        this.data = data;
        this.directionCount = directionCount;
        this.fps = 12;
    }

    public TextureAtlas.AtlasRegion evaluate(float time, int direction) {
        direction %= directionCount;
        int frame = (int) (time * fps);
        return data.get((frame * directionCount + direction) % data.size);
    }
}
