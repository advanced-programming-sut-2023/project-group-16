package org.group16.GameGraphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Renderer {
    protected final List<Renderer> children = new ArrayList<>();
    protected final Vector3 localPosition = new Vector3();
    protected final Decal decal;
    private final Vector3 worldPosition = new Vector3();

    public Renderer(TextureRegion textureRegion, boolean transparent, float size, Vector3 forward, Vector3 up) {
        if (textureRegion == null)
            decal = null;
        else {
            decal = Decal.newDecal(size, size * textureRegion.getRegionHeight() / textureRegion.getRegionWidth(), textureRegion, transparent);
            decal.setRotation(forward, up);
        }
    }

    public boolean isHovering() {
        if (decal != null) return Util.mousePosition.dst(worldPosition) < (decal.getWidth() / 2);
        return false;
    }

    public void update(float dt) {
        for (Renderer renderer : children) renderer.update(dt);
    }

    public void setLocalPosition(float x, float y, float z) {
        localPosition.set(x, y, z);
    }

    public Vector3 getLocalPosition() {
        return localPosition;
    }

    public void setLocalPosition(Vector3 position) {
        localPosition.set(position);
    }

    public void addChild(Renderer child) {
        children.add(child);
    }

    public void removeChild(Renderer child) {
        children.remove(child);
    }

    public void setScale(float scale) {
        decal.setScale(scale);
    }

    public void render(DecalBatch decalBatch, Vector3 parentPosition) {
//        HOVER DEBUG
//        if (decal != null) {
//            if (isHovering()) decal.setColor(1, 0, 0, 1);
//            else decal.setColor(1, 1, 1, 1);
//        }
        worldPosition.set(parentPosition).add(localPosition);
        if (decal != null) {
            decal.setPosition(worldPosition);
            decalBatch.add(decal);
        }
        for (Renderer child : children) {
            child.render(decalBatch, worldPosition);
        }
    }
}
