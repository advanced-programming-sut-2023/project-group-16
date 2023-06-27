package org.group16.GameGraphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    protected final List<Renderer> children = new ArrayList<>();
    protected final Vector3 localPosition = new Vector3();
    protected final Decal decal;
    protected final Vector3 forward, up;

    public Renderer(TextureRegion textureRegion, float size, Vector3 forward, Vector3 up) {
        this.forward = forward;
        this.up = up;
        decal = Decal.newDecal(size, size * textureRegion.getRegionHeight() / textureRegion.getRegionWidth(), textureRegion, true);
        decal.setRotation(forward, up);
    }

    public void addChild(Renderer child) {
        children.add(child);
    }

    public void removeChild(Renderer child) {
        children.remove(child);
    }

    public void setTextureRegion(TextureRegion textureRegion, float size) {
        decal.setTextureRegion(textureRegion);
        decal.setWidth(size);
        decal.setHeight(size * textureRegion.getRegionHeight() / textureRegion.getRegionWidth());
    }

    public void setScale(float scale) {
        decal.setScale(scale);
    }

    public void render(DecalBatch decalBatch, Vector3 parentPosition) {
        Vector3 worldPosition = parentPosition.cpy().add(localPosition);
        decal.setPosition(worldPosition);
        decalBatch.add(decal);
        for (Renderer child : children) {
            render(decalBatch, worldPosition);
        }
    }
}
