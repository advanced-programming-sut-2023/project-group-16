package org.group16;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import org.group16.GameGraphics.*;

import java.security.KeyFactorySpi;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.Gdx.*;

public class TestingGDX extends Game {
    private final List<Renderer> renderers = new ArrayList<>();
    private final Vector3 forward = new Vector3(1, 1, 1).nor();
    private final Vector3 right = new Vector3(1, 0, -1).nor();
    private final Vector3 up = forward.cpy().crs(right);
    float time = 0;
    private Renderer building;
    private Camera camera;
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
        System.out.println(up);
        camera = new PerspectiveCamera(30, 1f, 1f * graphics.getHeight() / graphics.getWidth());
//        camera = new OrthographicCamera(1, 1f * graphics.getHeight() / graphics.getWidth());
//        ((OrthographicCamera) camera).zoom = 10;
        camera.position.set(3f, 3f, 3f);
        camera.lookAt(0f, 0f, 0f);

        camera.near = 2f;
        camera.far = 50f;
        camera.update();

        decalBatch = new DecalBatch(10000000, new GS(camera,
                (x, y) -> Float.compare(x.getPosition().dot(forward), y.getPosition().dot(forward))
        ));

        atlas = new TextureAtlas("game/soldiers/european_archer.atlas");

        AnimCollection collection = new AnimCollection();
        collection.addAnimation("idle", new AnimData(atlas.findRegions("idle"), 1));
        collection.addAnimation("walking", new AnimData(atlas.findRegions("walking"), 8));
        collection.addAnimation("running", new AnimData(atlas.findRegions("running"), 8));
        collection.addAnimation("fighting", new AnimData(atlas.findRegions("fighting"), 8));
        float size = 0.3f;
        float buildingHeight = 2;
        for (float x = 0f; x <= 1.01f; x += .2f) {
            for (float y = 0f; y <= 1.01f; y += .2f) {
                Renderer par = new Renderer(null, false, 1, forward, up);
//                par.setLocalPosition(x, 0, y);
                par.setLocalPosition(.5f, 0, .5f);
                par.getLocalPosition().mulAdd(up, buildingHeight * .4f);
                par.getLocalPosition().mulAdd(up, -(1.5f + x + y) * buildingHeight * .06f);
                par.getLocalPosition().mulAdd(right, (x - y) * .3f);
                par.getLocalPosition().mulAdd(forward, (4 + x + y) * .01f);
                par.getLocalPosition().mulAdd(Vector3.Y, buildingHeight * .3f);
//                par.getLocalPosition().mulAdd(right, x + y);
                renderers.add(par);
                AnimatedRenderer renderer = new AnimatedRenderer(collection, true, size, forward, up);
                renderer.setLocalPosition(0, .1f, 0);
                par.addChild(renderer);
            }
        }
        TextureRegion ground = new TextureRegion(new Texture("game/tiles/desert_tile.jpg"));
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                Renderer cell = new Renderer(ground, false, 1, Vector3.Y, Vector3.X);
                cell.setLocalPosition(x + .5f, 0, y + .5f);
                renderers.add(cell);

                building = new Renderer(new TextureRegion(new Texture("game/tiles/collection9.png")), true, 1, forward, up);
                building.setLocalPosition(x + .5f, 0, y + .5f);
                building.getLocalPosition().mulAdd(Vector3.Y, buildingHeight * .3f);
                renderers.add(building);
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
        for (Renderer renderer : renderers) {
            renderer.update(dt);
        }
        time += dt;
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

        for (Renderer renderer : renderers) {
            renderer.render(decalBatch, new Vector3());
        }
//        gl.glDepthMask(false);
        decalBatch.flush();
//        gl.glDepthMask(true);

//        AnimatedRenderer soldier = (AnimatedRenderer) renderers.get(0);
//        if (input.isKeyPressed(Input.Keys.F)) {
//            soldier.playAnimation("fighting");
//        }
//        if (input.isKeyPressed(Input.Keys.R)) {
//            soldier.playAnimation("running");
//        }
//        if (input.isKeyPressed(Input.Keys.W)) {
//            soldier.playAnimation("walking");
//        }
//        if (input.isKeyPressed(Input.Keys.I)) {
//            soldier.playAnimation("idle");
//        }
//        if (input.isKeyPressed(Input.Keys.RIGHT)) soldier.setDirection(1);
//        if (input.isKeyPressed(Input.Keys.DOWN)) soldier.setDirection(3);
//        if (input.isKeyPressed(Input.Keys.LEFT)) soldier.setDirection(5);
//        if (input.isKeyPressed(Input.Keys.UP)) soldier.setDirection(7);
//        if (input.isKeyPressed(Input.Keys.UP) && input.isKeyPressed(Input.Keys.RIGHT)) soldier.setDirection(0);
//        if (input.isKeyPressed(Input.Keys.DOWN) && input.isKeyPressed(Input.Keys.RIGHT)) soldier.setDirection(2);
//        if (input.isKeyPressed(Input.Keys.DOWN) && input.isKeyPressed(Input.Keys.LEFT)) soldier.setDirection(4);
//        if (input.isKeyPressed(Input.Keys.UP) && input.isKeyPressed(Input.Keys.LEFT)) soldier.setDirection(6);
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
