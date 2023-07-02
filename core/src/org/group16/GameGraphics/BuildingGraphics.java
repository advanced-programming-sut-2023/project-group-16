package org.group16.GameGraphics;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public enum BuildingGraphics {

    SMALL_TOWER("SQUARE_TOWER", .7f, .5f, 0, .45f, .12f), //TESTED
    BIG_TOWER("DEFENCE_TURRET", .85f, .55f, 0, .52f, .15f), //TESTED
    SQUARE_TOWER("PRIMER_TOWER", 1, .6f, 0, .65f, .3f), //TESTED
    CIRCLE_TOWER("ROUND_TOWER", 1, .6f, 0, .6f, .25f), //TESTED
    ARCHER_TOWER("LOOKOUT_TOWER", .5f, .75f, .25f, .8f, .1f), //TESTED
    KING_CASTLE("TOWN_BUILDING", 1, .4f, -.05f, .35f, .3f), //TESTED
    SMALL_STONE_GATEHOUSE("SMALL_STONE_GATEHOUSE", .6f, .25f, 0, .25f, .17f), //TESTED
    LARGE_STONE_GATEHOUSE("LARGE_STONE_GATEHOUSE", .9f, .4f, 0, .35f, .35f), //TESTED
    DRAWBRIDGE("DRAWBRIDGE", .5f, .15f, 0, 0, 0),//TESTED
    ARMOURY("ARMOURY", .8f, .2f, 0, 0, 0),//TESTED
    BARRACKS("BARRACKS", .5f, .2f, -.1f, 0, 0),//TESTED
    MERCENARY_POST("MERCENARY_POST", .7f, .15f, -.1f, 0, 0),//TESTED
    ENGINEER_GUILD("ENGINEER_GUILD", .6f, .15f, -.1f, 0, 0),//TESTED
    INN("INN", .4f, .2f, 0, 0, 0),//TESTED
    MILL("MILL", .6f, .5f, .1f, 0, 0),//TESTED
    IRON_MINE("IRON_MINE", .6f, .15f, -.1f, 0, 0),//TESTED
    MARKET("MARKET", .5f, .2f, 0, 0, 0), //TESTED
    OX_TETHER("OX_TETHER", .4f, .12f, -.05f, 0, 0),//TESTED
    PITCH_RIG("PITCH_RIG", .4f, .12f, -.05f, 0, 0),//TESTED
    QUARRY("QUARRY", .7f, .25f, -.05f, 0, 0),//TESTED
    STOCKPILE("STOCKPILE", 0.5f, 0.05f, -0.1f, 0, 0),//TESTED
    WOOD_CUTTER("WOOD_CUTTER", .4f, .15f, -.05f, 0, 0),//TESTED
    HOVEL("HOVEL", .5f, .15f, -.05f, 0, 0),//TESTED
    CHURCH("CHURCH", 1, .3f, -.15f, 0, 0),//TESTED
    CATHEDRAL("CATHEDRAL", 1, .3f, -.15f, 0, 0),//TESTED
    ARMOURER("ARMOURER", .4f, .15f, -.05f, 0, 0),//TESTED
    BLACKSMITH("BLACKSMITH", .4f, .15f, -.05f, 0, 0),//TESTED
    FLETCHER("FLETCHER", .4f, .15f, -.05f, 0, 0),//TESTED
    POLE_TURNER("POLE_TURNER", .4f, .15f, -.05f, 0, 0),//TESTED
    OIL_SMELTER("OIL_SMELTER", .6f, .2f, -.05f, 0, 0),//TESTED
    STABLE("STABLE", .7f, .2f, -.15f, 0, 0),//TESTED
    APPLE_ORCHARD("APPLE_ORCHARD", .4f, .15f, -.05f, 0, 0),//TESTED
    DIARY_FARMER("DIARY_FARMER", .4f, .15f, -.05f, 0, 0),//TESTED
    HOPS_FARMER("HOPS_FARMER", .4f, .15f, -.05f, 0, 0),//TESTED
    HUNTER_POST("HUNTER_POST", .5f, .15f, -.05f, 0, 0),//TESTED
    WHEAT_FARMER("WHEAT_FARMER", .5f, .15f, -.05f, 0, 0),//TESTED
    BAKERY("BAKERY", .5f, .2f, -.05f, 0, 0),//TESTED
    BREWER("BREWER", .4f, .2f, -.05f, 0, 0),//TESTED
    GRANARY("GRANARY", .5f, .15f, -.05f, 0, 0),//TESTED
    UNEMPLOYED_PLACE("UNEMPLOYED_PLACE", 0.5f, .1f, -.1f, 0, 0),



    ;
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
