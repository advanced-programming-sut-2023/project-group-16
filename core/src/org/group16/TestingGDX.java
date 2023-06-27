package org.group16;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import org.group16.GameGraphics.AnimCollection;
import org.group16.GameGraphics.AnimData;
import org.group16.GameGraphics.AnimState;
import org.group16.GameGraphics.AnimatedRenderer;

import java.security.KeyFactorySpi;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.Gdx.*;

public class TestingGDX extends Game {
    private Camera camera;
    private DecalBatch decalBatch;
    //    private Array<TextureAtlas.AtlasRegion> currentAnimation;
    private AnimData idleAnim, walkingAnim, runningAnim, fightingAnim;
    private AnimState animState;

    private TextureAtlas atlas;
    private Decal decal;
    private int direction = 0;

    private long lastFrame = TimeUtils.millis();

    private List<AnimatedRenderer> soldiers = new ArrayList<>();

    @Override
    public void create() {
        camera = new PerspectiveCamera(67, 1f, 1f * graphics.getHeight() / graphics.getWidth());
//        camera = new OrthographicCamera(1, 1f * graphics.getHeight() / graphics.getWidth());
        camera.position.set(2f, 2f, 2f);
        camera.lookAt(0f, 0f, 0f);

        camera.near = 0.01f;
        camera.far = 300f;
        camera.update();

        decalBatch = new DecalBatch(100000, new CameraGroupStrategy(camera,
                (a, b) -> Float.compare(camera.position.dst2(b.getPosition()), camera.position.dst2(a.getPosition()))
        ));

        atlas = new TextureAtlas("game/soldiers/european_archer.atlas");

        Vector3 forward = new Vector3(1, 1, 1).nor();
        Vector3 up = new Vector3(-1, 1, -1).nor();
        AnimCollection collection = new AnimCollection();
        collection.addAnimation("idle", new AnimData(atlas.findRegions("idle"), 1));
        collection.addAnimation("walking", new AnimData(atlas.findRegions("walking"), 8));
        collection.addAnimation("running", new AnimData(atlas.findRegions("running"), 8));
        collection.addAnimation("fighting", new AnimData(atlas.findRegions("fighting"), 8));
        float size = 0.5f;
        for (float x = 0; x <= 1; x += .15f) {
            for (float y = 0; y <= 1; y += .15f) {
                AnimatedRenderer renderer = new AnimatedRenderer(collection, size, forward, up);
                renderer.setLocalPosition(x, 0, y);
                soldiers.add(renderer);
            }
        }

    }

    @Override
    public void render() {
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        long milis = TimeUtils.timeSinceMillis(lastFrame);
        float dt = milis / 1000f;
        lastFrame += milis;
        for (AnimatedRenderer renderer : soldiers) {
            renderer.update(dt);
        }
        if (input.isKeyPressed(Input.Keys.J))
            camera.position.add(-dt, 0, dt);
        if (input.isKeyPressed(Input.Keys.L))
            camera.position.add(dt, 0, -dt);
        if (input.isKeyPressed(Input.Keys.I))
            camera.position.add(-dt, 0, -dt);
        if (input.isKeyPressed(Input.Keys.K))
            camera.position.add(dt, 0, dt);
        if (input.isKeyPressed(Input.Keys.U))
            camera.position.add(-dt, -dt, -dt);
        if (input.isKeyPressed(Input.Keys.O))
            camera.position.add(dt, dt, dt);
//        if (input.isKeyPressed(Input.Keys.N))
//            camera.rotateAround(, Vector3.Y, -dt * 180);
//        if (input.isKeyPressed(Input.Keys.M))
//            camera.rotate(new Vector3(0, 1, 0), dt * 180);
        camera.update();

        for (AnimatedRenderer renderer : soldiers) {
            renderer.render(decalBatch, new Vector3());
        }
        gl.glDepthMask(false);
        decalBatch.flush();
        gl.glDepthMask(true);

        AnimatedRenderer soldier = soldiers.get(0);
        if (input.isKeyPressed(Input.Keys.F)) {
            soldier.playAnimation("fighting");
        }
        if (input.isKeyPressed(Input.Keys.R)) {
            soldier.playAnimation("running");
        }
        if (input.isKeyPressed(Input.Keys.W)) {
            soldier.playAnimation("walking");
        }
        if (input.isKeyPressed(Input.Keys.I)) {
            soldier.playAnimation("idle");
        }
        if (input.isKeyPressed(Input.Keys.RIGHT)) soldier.setDirection(1);
        if (input.isKeyPressed(Input.Keys.DOWN)) soldier.setDirection(3);
        if (input.isKeyPressed(Input.Keys.LEFT)) soldier.setDirection(5);
        if (input.isKeyPressed(Input.Keys.UP)) soldier.setDirection(7);
        if (input.isKeyPressed(Input.Keys.UP) && input.isKeyPressed(Input.Keys.RIGHT)) soldier.setDirection(0);
        if (input.isKeyPressed(Input.Keys.DOWN) && input.isKeyPressed(Input.Keys.RIGHT)) soldier.setDirection(2);
        if (input.isKeyPressed(Input.Keys.DOWN) && input.isKeyPressed(Input.Keys.LEFT)) soldier.setDirection(4);
        if (input.isKeyPressed(Input.Keys.UP) && input.isKeyPressed(Input.Keys.LEFT)) soldier.setDirection(6);
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
