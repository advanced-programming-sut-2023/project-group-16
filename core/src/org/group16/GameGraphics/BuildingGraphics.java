package org.group16.GameGraphics;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public enum BuildingGraphics {
    SMALL_TOWER("small_tower", .7f, .5f, 0, .45f, .12f),
    BIG_TOWER("big_tower", .85f, .55f, 0, .52f, .15f),
    SQUARE_TOWER("square_tower", 1, .6f, 0, .65f, .3f),
    CIRCLE_TOWER("circle_tower", 1, .6f, 0, .6f, .25f),
    ARCHER_TOWER("archer_tower", .5f, .75f, .25f, .8f, .1f),
    KING_CASTLE("king_castle", 1, .4f, -.05f, .35f, .3f);
//    CASTLE();
//    private final String assetName;

    private final String name;
    private final float size;
    private final float yOffset;
    private final float dOffset;
    private final float roofHeight;
    private final float roofWidth;
    private TextureAtlas.AtlasRegion atlasRegion;

    BuildingGraphics(String name, float size, float yOffset, float dOffset, float roofHeight, float roofWidth) {
        this.name = name;
        this.size = size;
        this.yOffset = yOffset;
        this.dOffset = dOffset;
        this.roofHeight = roofHeight;
        this.roofWidth = roofWidth;
    }

    public static void load(TextureAtlas atlas) {
        for (BuildingGraphics value : values()) {
            value.atlasRegion = atlas.findRegion(value.name);
        }
    }

    public float getdOffset() {
        return dOffset;
    }

    public String getName() {
        return name;
    }

    public float getyOffset() {
        return yOffset;
    }

    public float getRoofHeight() {
        return roofHeight;
    }

    public float getRoofWidth() {
        return roofWidth;
    }

    public float getSize() {
        return size;
    }

    public TextureAtlas.AtlasRegion getAtlasRegion() {
        return atlasRegion;
    }
}
