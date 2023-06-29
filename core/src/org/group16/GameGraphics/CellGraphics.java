package org.group16.GameGraphics;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public enum CellGraphics {
    SAND("sand", AnimData.PlayMode.LOOP),
    GRASS("grass", AnimData.PlayMode.LOOP),
    ROCKY_SAND("rocky_sand", AnimData.PlayMode.LOOP),
    ROCK("rock", AnimData.PlayMode.LOOP),
    ;
    private final String name;
    private final AnimData.PlayMode playMode;
    private AnimData animData;

    CellGraphics(String name, AnimData.PlayMode playMode) {
        this.name = name;
        this.playMode = playMode;
    }

    public static void load(TextureAtlas atlas) {
        for (CellGraphics cellGraphics : values()) {
            cellGraphics.animData = new AnimData(atlas.findRegions(cellGraphics.name), 1, cellGraphics.playMode);
        }
    }

    public String getName() {
        return name;
    }

    public AnimData getAnimData() {
        return animData;
    }
}
