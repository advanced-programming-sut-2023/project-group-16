package org.group16.GameGraphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Random;

public class AnimatedRenderer extends Renderer {
    protected final AnimCollection animCollection;
    protected final AnimState animState;
    private final Random random = new Random();
    protected int direction;

    public AnimatedRenderer(AnimCollection animCollection, boolean transparent, float size, Vector3 forward, Vector3 up) {
        super(animCollection.getAnimation("idle").evaluate(0, 0), transparent, size, forward, up);
        animState = new AnimState(animCollection.getAnimation("idle"));
        this.animCollection = animCollection;
        animState.setCurrentTime(random.nextFloat() * animState.getCurrentData().getDuration());
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void playAnimation(String name, boolean randomizeStart) {
        animState.setCurrentData(animCollection.getAnimation(name));
        if (randomizeStart)
            animState.setCurrentTime(random.nextFloat() * animState.getCurrentData().getDuration());
        else
            animState.setCurrentTime(0);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        animState.update(dt);
        decal.setTextureRegion(animState.evaluate(direction));
    }
}
