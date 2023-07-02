package org.group16.GameGraphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public enum HumanGraphics {
    EUROPEAN_ARCHER("game/soldiers/european_archer.atlas", .3f, .1f,
            new String[]{"idle", "walking", "running", "fighting"},
            new int[]{1, 8, 8, 8}),
    SLAVE("game/soldiers/slave.atlas", .3f, .1f,
            new String[]{"idle", "walking", "running", "fighting"},
            new int[]{1, 8, 8, 8}) ,
    ASSASSIN("game/soldiers/assassin.atlas", .3f, .1f,
            new String[]{"idle", "walking", "running", "fighting"},
            new int[]{1, 8, 8, 8}) ,
    //TODO : wtf ?
    ARABIAN_ARCHER("game/soldiers/arabian_archer.atlas", .3f, .1f,
            new String[]{"idle", "walking", "running", "fighting"},
            new int[]{1, 8, 8, 8}) ,

    MACE_MAN("game/soldiers/maceman.atlas", .3f, .1f,
            new String[]{"idle", "walking", "running", "fighting"},
            new int[]{1, 8, 8, 8}) ,

    KHAR("game/soldiers/khar.atlas", .3f, .1f,
            new String[]{"idle", "walking", "running", "fighting"},
            new int[]{1, 8, 8, 8}) ,
    SLINGER("game/soldiers/slinger.atlas", .3f, .1f,
            new String[]{"idle", "walking", "running", "fighting"},
            new int[]{1, 8, 8, 8}),
    SWORD_MAN("game/soldiers/swordman.atlas", .3f, .1f,
            new String[]{"idle", "walking", "running", "fighting"},
            new int[]{1, 8, 8, 8}) ,
    MONK("game/soldiers/monk.atlas", .3f, .1f,
            new String[]{"idle", "walking", "running", "fighting"},
            new int[]{1, 8, 8, 8})
    ;
    private final String path;
    private final float size;
    private final float yOffset;
    private final String[] animNames;
    private final int[] animDirs;
    private AnimCollection animCollection;

    HumanGraphics(String path, float size, float yOffset, String[] animNames, int[] animDirs) {
        this.path = path;
        this.size = size;
        this.yOffset = yOffset;
        this.animNames = animNames;
        this.animDirs = animDirs;
    }

    public static void load(AssetManager assetManager) {
        for (HumanGraphics soldierGraphics : values()) {
            assetManager.load(soldierGraphics.path, TextureAtlas.class);
        }
        assetManager.finishLoading();
        for (HumanGraphics humanGraphics : values()) {
            TextureAtlas atlas = assetManager.get(humanGraphics.path, TextureAtlas.class);
            humanGraphics.animCollection = new AnimCollection();
            for (int i = 0; i < humanGraphics.animNames.length; i++) {
                String name = humanGraphics.animNames[i];
                int dirs = humanGraphics.animDirs[i];
                humanGraphics.animCollection.addAnimation(name, new AnimData(atlas.findRegions(name), dirs, AnimData.PlayMode.LOOP));
            }
        }
    }

    public AnimCollection getAnimCollection() {
        return animCollection;
    }

    public float getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }

    public float getyOffset() {
        return yOffset;
    }
}
