package org.group16.GameGraphics;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public enum DetailGraphics {
    ROCKS("rocks", 1, .5f, AnimData.PlayMode.RANDOM_SELECT),
    CACTII("cactii", .5f, .1f, AnimData.PlayMode.RANDOM_SELECT),
    SHRUB1("shrub1", .5f, .1f, AnimData.PlayMode.PING_PONG),
    SHRUB2("shrub2", .5f, .1f, AnimData.PlayMode.PING_PONG),
    TREE_BIRCH1("tree_birch1", 1, .3f, AnimData.PlayMode.PING_PONG),
    TREE_BIRCH2("tree_birch2", 1, .3f, AnimData.PlayMode.PING_PONG),
    TREE_BIRCH3("tree_birch3", 1, .3f, AnimData.PlayMode.PING_PONG),
    TREE_BIRCH4("tree_birch4", 1, .3f, AnimData.PlayMode.PING_PONG),

    TREE_CHESTNUT1("tree_chestnut1", 1, .3f, AnimData.PlayMode.PING_PONG),
    TREE_CHESTNUT2("tree_chestnut2", 1, .3f, AnimData.PlayMode.PING_PONG),
    TREE_CHESTNUT3("tree_chestnut3", 1, .3f, AnimData.PlayMode.PING_PONG),
    TREE_CHESTNUT4("tree_chestnut4", 1, .3f, AnimData.PlayMode.PING_PONG),

    TREE_OAK1("tree_oak1", 1, .3f, AnimData.PlayMode.PING_PONG),
    TREE_OAK2("tree_oak2", 1, .3f, AnimData.PlayMode.PING_PONG),
    TREE_OAK3("tree_oak3", 1, .3f, AnimData.PlayMode.PING_PONG),
    TREE_OAK4("tree_oak4", 1, .3f, AnimData.PlayMode.PING_PONG),

    TREE_PINE1("tree_pine1", 1, .3f, AnimData.PlayMode.PING_PONG),
    TREE_PINE2("tree_pine2", 1, .3f, AnimData.PlayMode.PING_PONG),
    TREE_PINE3("tree_pine3", 1, .3f, AnimData.PlayMode.PING_PONG),
    TREE_PINE4("tree_pine4", 1, .3f, AnimData.PlayMode.PING_PONG),
    ;
    private final String name;
    private final float size;
    private final float yOffset;
    private final AnimData.PlayMode playMode;
    private AnimData animData;

    DetailGraphics(String name, float size, float yOffset, AnimData.PlayMode playMode) {
        this.name = name;
        this.size = size;
        this.yOffset = yOffset;
        this.playMode = playMode;
    }

    public static void load(TextureAtlas atlas) {
        for (DetailGraphics detailGraphics : values()) {
            detailGraphics.animData = new AnimData(atlas.findRegions(detailGraphics.name), 1, detailGraphics.playMode);
        }
    }

    public AnimData.PlayMode getPlayMode() {
        return playMode;
    }

    public float getSize() {
        return size;
    }

    public float getyOffset() {
        return yOffset;
    }

    public String getName() {
        return name;
    }

    public AnimData getAnimData() {
        return animData;
    }
}
