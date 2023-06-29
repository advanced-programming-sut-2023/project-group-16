package org.group16.GameGraphics;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.Random;

public class AnimState {
    private AnimData currentData;
    private float currentTime = 0;

    public AnimState(AnimData currentData) {
        this.currentData = currentData;
    }

    public void update(float dt) {
        if (currentData.getPlayMode() != AnimData.PlayMode.RANDOM_SELECT)
            currentTime += dt;
    }

    public TextureAtlas.AtlasRegion evaluate(int direction) {
        float time = currentTime;
        if (currentData.getPlayMode() == AnimData.PlayMode.PING_PONG) {
            time %= (2 * currentData.getDuration());
            if (time > currentData.getDuration())
                time = 2 * currentData.getDuration() - time;
        }
        return currentData.evaluate(time, direction);
    }

    public AnimData getCurrentData() {
        return currentData;
    }

    public void setCurrentData(AnimData currentData) {
        this.currentData = currentData;
    }

    public float getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(float currentTime) {
        this.currentTime = currentTime;
    }
}
