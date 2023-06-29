package org.group16.GameGraphics;

public class HumanRenderer extends Renderer {
    private AnimatedRenderer shape;

    public HumanRenderer(HumanGraphics humanGraphics) {
        super(null, false, 1, Util.forward, Util.up);
        shape = new AnimatedRenderer(humanGraphics.getAnimCollection(), true, humanGraphics.getSize(), Util.forward, Util.up);
        shape.localPosition.set(0, humanGraphics.getyOffset(), 0);
        addChild(shape);
    }

    public void playAnimation(String name, boolean randomizeStart) {
        shape.playAnimation(name, randomizeStart);
    }

    public int getDirection() {
        return shape.getDirection();
    }

    public void setDirection(int direction) {
        shape.setDirection(direction);
    }
}