package org.group16;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import static com.badlogic.gdx.Gdx.*;

public class TestingGDX extends Game {
    private final long initialTime = TimeUtils.millis();
    private PerspectiveCamera camera;
    private DecalBatch decalBatch;
    private Array<TextureAtlas.AtlasRegion> currentAnimation;

    private TextureAtlas atlas;
    private Decal decal;
    private int direction = 0;

    private int getFrame() {
        float frm = TimeUtils.timeSinceMillis(initialTime) / 82f;
        return (int) frm;
    }

    private int getIndex() {
        return (getFrame() * 8 + direction) % currentAnimation.size;
    }

    @Override
    public void create() {
        camera = new PerspectiveCamera(67, 1f, 1f * graphics.getHeight() / graphics.getWidth());
        camera.position.set(100f, 100f, 100f);
        camera.lookAt(0f, 0f, 0f);

        camera.near = 1f;
        camera.far = 300f;
        camera.update();

        decalBatch = new DecalBatch(new CameraGroupStrategy(camera));

        atlas = new TextureAtlas("game/soldiers/european_archer.atlas");
        currentAnimation = atlas.findRegions("walking");
        decal = Decal.newDecal(currentAnimation.get(0), true);
        decal.setPosition(0, 0, 0);
        decal.setRotation(new Vector3(1, 1, 1).nor(), camera.up);
    }

    @Override
    public void render() {
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        decal.setTextureRegion(currentAnimation.get(getIndex()));

        decalBatch.add(decal);
        decalBatch.flush();

        if (input.isKeyPressed(Input.Keys.RIGHT)) direction = 1;
        if (input.isKeyPressed(Input.Keys.DOWN)) direction = 3;
        if (input.isKeyPressed(Input.Keys.LEFT)) direction = 5;
        if (input.isKeyPressed(Input.Keys.UP)) direction = 7;
        if (input.isKeyPressed(Input.Keys.UP) && input.isKeyPressed(Input.Keys.RIGHT)) direction = 0;
        if (input.isKeyPressed(Input.Keys.DOWN) && input.isKeyPressed(Input.Keys.RIGHT)) direction = 2;
        if (input.isKeyPressed(Input.Keys.DOWN) && input.isKeyPressed(Input.Keys.LEFT)) direction = 4;
        if (input.isKeyPressed(Input.Keys.UP) && input.isKeyPressed(Input.Keys.LEFT)) direction = 6;
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 1f;
        camera.viewportHeight = 1f * graphics.getHeight() / graphics.getWidth();
        camera.update();
    }

    @Override
    public void dispose() {
        decalBatch.dispose();
        atlas.dispose();
    }
}
