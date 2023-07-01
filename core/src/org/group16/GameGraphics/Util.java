package org.group16.GameGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import org.group16.Model.Cell;
import org.group16.Model.Game;
import org.group16.Vec2;

public class Util {
    public static final Vector3 forward = new Vector3(1, 1, 1).nor();
    public static final Vector3 right = new Vector3(1, 0, -1).nor();
    public static final Vector3 up = forward.cpy().crs(right);
    public static final Vector3 mousePosition = new Vector3();

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

    public static void updateMousePosition(Camera camera) {
        Ray ray = camera.getPickRay(Gdx.input.getX(), Gdx.input.getY());
//        ray.origin.y + t * ray.direction.y = 0
        float t = -ray.origin.y / ray.direction.y;
        mousePosition.set(ray.origin).mulAdd(ray.direction, t);
    }

    public static Cell getMouseCell(Game game) {
        float x = mousePosition.x, y = mousePosition.z;
        int X = (int) x, Y = (int) y;
        if (x > X + .5f) X++;
        if (y > Y + .5f) Y++;
        return game.getScene().getCellAt(X, Y);
    }
}
