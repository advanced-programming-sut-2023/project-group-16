package org.group16.GameGraphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;

public class AnimatedRenderer extends Renderer {
    private final AnimCollection animCollection;
    private final AnimState animState;
    private int direction;

    public AnimatedRenderer(AnimCollection animCollection, boolean transparent, float size, Vector3 forward, Vector3 up) {
        super(animCollection.getAnimation("idle").evaluate(0, 0), transparent, size, forward, up);
        animState = new AnimState(animCollection.getAnimation("idle"));
        this.animCollection = animCollection;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void playAnimation(String name) {
        animState.setCurrentData(animCollection.getAnimation(name));
        animState.setCurrentTime(0);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        animState.update(dt);
        decal.setTextureRegion(animState.evaluate(direction));
    }
}
