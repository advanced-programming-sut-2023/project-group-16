package org.group16.GameGraphics;

import com.badlogic.gdx.math.Vector3;

public class CellRenderer extends AnimatedRenderer {
    public CellRenderer(CellGraphics cellGraphics, float x, float y) {
        super(new AnimCollection(cellGraphics.getAnimData()), false, 1, Vector3.Y, Vector3.X);
        localPosition.set(x, 0, y);
    }
}
