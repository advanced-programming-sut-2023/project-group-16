package org.group16.GameGraphics;

public class DetailRenderer extends AnimatedRenderer {
    public DetailRenderer(DetailGraphics detailGraphics, float x, float y) {
        super(new AnimCollection(detailGraphics.getAnimData()), true, detailGraphics.getSize(), Util.forward, Util.up);
        localPosition.set(x, detailGraphics.getyOffset(), y);
    }
}
