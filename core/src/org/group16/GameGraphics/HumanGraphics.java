package org.group16.GameGraphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public enum HumanGraphics {
    EUROPEAN_ARCHER("game/soldiers/european_archer.atlas", .3f, .1f,
            new String[]{"idle", "walking", "running", "fighting"},
            new int[]{1, 8, 8, 8}),
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
