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
import com.badlogic.gdx.utils.TimeUtils;
import org.group16.GameGraphics.AnimData;
import org.group16.GameGraphics.AnimState;

import static com.badlogic.gdx.Gdx.*;

public class TestingGDX extends Game {
    private PerspectiveCamera camera;
    private DecalBatch decalBatch;
    //    private Array<TextureAtlas.AtlasRegion> currentAnimation;
    private AnimData idleAnim, walkingAnim, runningAnim, fightingAnim;
    private AnimState animState;

    private TextureAtlas atlas;
    private Decal decal;
    private int direction = 0;

    private long lastFrame = TimeUtils.millis();

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
        idleAnim = new AnimData(atlas.findRegions("idle"), 1);
        walkingAnim = new AnimData(atlas.findRegions("walking"), 8);
        runningAnim = new AnimData(atlas.findRegions("running"), 8);
        fightingAnim = new AnimData(atlas.findRegions("fighting"), 8);
        animState = new AnimState(idleAnim);

        decal = Decal.newDecal(animState.evaluate(direction), true);
        decal.setPosition(100, 0, 0);
        decal.setRotation(new Vector3(1, 1, 1).nor(), camera.up);
    }

    @Override
    public void render() {
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        long milis = TimeUtils.timeSinceMillis(lastFrame);
        animState.update(milis / 1000f);
        lastFrame += milis;
        decal.setTextureRegion(animState.evaluate(direction));

        decalBatch.add(decal);
        decalBatch.flush();

        if (input.isKeyPressed(Input.Keys.F)) {
            animState.setCurrentData(fightingAnim);
            animState.setCurrentTime(0);
        }
        if (input.isKeyPressed(Input.Keys.R)) {
            animState.setCurrentData(runningAnim);
            animState.setCurrentTime(0);
        }
        if (input.isKeyPressed(Input.Keys.W)) {
            animState.setCurrentData(walkingAnim);
            animState.setCurrentTime(0);
        }
        if (input.isKeyPressed(Input.Keys.I)) {
            animState.setCurrentData(idleAnim);
            animState.setCurrentTime(0);
        }
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
