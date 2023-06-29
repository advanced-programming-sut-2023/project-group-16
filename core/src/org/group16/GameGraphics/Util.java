package org.group16.GameGraphics;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;

public class Util {
    public static final Vector3 forward = new Vector3(1, 1, 1).nor();
    public static final Vector3 right = new Vector3(1, 0, -1).nor();
    public static final Vector3 up = forward.cpy().crs(right);

    public static DecalBatch createDecalBatch(Camera camera) {
        return new DecalBatch(1000000, new GS(camera,
                (x, y) -> Float.compare(x.getPosition().dot(forward), y.getPosition().dot(forward))
        ));
    }

    public static void load(AssetManager assetManager) {
        assetManager.load("game/tiles/buildings.atlas", TextureAtlas.class);
        assetManager.load("game/tiles/details.atlas", TextureAtlas.class);
        assetManager.load("game/tiles/cells.atlas", TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas buildingsAtlas = assetManager.get("game/tiles/buildings.atlas", TextureAtlas.class);
        TextureAtlas detailsAtlas = assetManager.get("game/tiles/details.atlas", TextureAtlas.class);
        TextureAtlas cellsAtlas = assetManager.get("game/tiles/cells.atlas", TextureAtlas.class);
        HumanGraphics.load(assetManager);

        BuildingGraphics.load(buildingsAtlas);
        HumanGraphics.load(assetManager);
        DetailGraphics.load(detailsAtlas);
        CellGraphics.load(cellsAtlas);
    }
}
