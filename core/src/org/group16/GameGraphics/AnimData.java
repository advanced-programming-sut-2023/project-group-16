package org.group16.GameGraphics;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class AnimData {
    private final Array<TextureAtlas.AtlasRegion> data;
    private final int directionCount;
    private final int fps;
    private final PlayMode playMode;


    public AnimData(Array<TextureAtlas.AtlasRegion> data, int directionCount, int fps, PlayMode playMode) {
        this.data = data;
        this.directionCount = directionCount;
        this.fps = fps;
        this.playMode = playMode;
    }

    public AnimData(Array<TextureAtlas.AtlasRegion> data, int directionCount, PlayMode playMode) {
        this(data, directionCount, 12, playMode);
    }

    public float getDuration() {
        return 1.0f * data.size / fps;
    }

    public TextureAtlas.AtlasRegion evaluate(float time, int direction) {
        direction %= directionCount;
        int frame = (int) (time * fps);
        return data.get((frame * directionCount + direction) % data.size);
    }

    public PlayMode getPlayMode() {
        return playMode;
    }

    public enum PlayMode {
        LOOP,
        PING_PONG,
        RANDOM_SELECT
    }
}
