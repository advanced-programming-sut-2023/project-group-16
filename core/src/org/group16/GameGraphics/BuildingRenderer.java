package org.group16.GameGraphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class BuildingRenderer extends Renderer {
    private final Vector3 xDir, yDir;
    private Renderer shape;
    private Vector3 roofOrigin;

    public BuildingRenderer(BuildingGraphics graphics, float x, float y) {
        super(null, false, 1, Util.forward, Util.up);
        localPosition.set(x, 0, y);

        shape = new Renderer(graphics.getAtlasRegion(), true, graphics.getSize(), Util.forward, Util.up);
        shape.localPosition.set(-graphics.getdOffset(), graphics.getyOffset(), -graphics.getdOffset());
        addChild(shape);

        roofOrigin = getLocalPosition().cpy().add(shape.getLocalPosition()).mulAdd(Util.up, graphics.getRoofHeight());

        xDir = Util.right.cpy().mulAdd(Util.up, -.5f).scl(graphics.getRoofWidth()).mulAdd(Util.forward, .01f);
        yDir = Util.right.cpy().mulAdd(Util.up, .5f).scl(-graphics.getRoofWidth()).mulAdd(Util.forward, .01f);
    }

    @Override
    public boolean isHovering() {
        return shape.isHovering();
    }

    public Vector3 getRoofPosition(float x, float y) {
        Vector3 pos = roofOrigin.cpy();
        pos.mulAdd(xDir, x);
        pos.mulAdd(yDir, y);
        return pos;
    }
}
