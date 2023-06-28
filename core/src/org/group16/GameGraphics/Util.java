package org.group16.GameGraphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;

public class Util {
    public static final Vector3 forward = new Vector3(1, 1, 1).nor();
    public static final Vector3 right = new Vector3(1, 0, -1).nor();
    public static final Vector3 up = forward.cpy().crs(right);

    public static DecalBatch createDecalBatch(Camera camera) {
        return new DecalBatch(10000000, new GS(camera,
                (x, y) -> Float.compare(x.getPosition().dot(forward), y.getPosition().dot(forward))
        ));
    }
}
