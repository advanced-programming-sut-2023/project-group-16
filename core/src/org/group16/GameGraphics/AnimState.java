package org.group16.GameGraphics;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AnimState {
    private AnimData currentData;
    private float currentTime = 0;
    public AnimState(AnimData currentData) {
        this.currentData = currentData;
    }

    public void update(float dt) {
        currentTime += dt;
    }

    public TextureAtlas.AtlasRegion evaluate(int direction) {
        return currentData.evaluate(currentTime, direction);
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
