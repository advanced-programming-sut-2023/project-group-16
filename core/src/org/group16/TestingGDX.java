package org.group16;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import org.group16.GameGraphics.*;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Gdx.*;

public class TestingGDX extends Game {
    private final List<Renderer> renderers = new ArrayList<>();
    private final Vector3 forward = new Vector3(1, 1, 1).nor();
    private final Vector3 right = new Vector3(1, 0, -1).nor();
    private final Vector3 up = forward.cpy().crs(right);
    public AssetManager assetManager;
    float time = 0;
    private BuildingRenderer building;
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
        
        TextureRegion ground = new TextureRegion(new Texture("game/tiles/desert_tile.jpg"));
        TextureRegion buildingTexture = new TextureRegion(new Texture("game/tiles/Market-menu.png"));
        TextureAtlas buildingAtlas = new TextureAtlas("game/tiles/buildings.atlas");
        TextureAtlas detailsAtlas = new TextureAtlas("game/tiles/details.atlas");
        TextureAtlas cellsAtlas = new TextureAtlas("game/tiles/cells.atlas");
        BuildingGraphics.load(buildingAtlas);
        HumanGraphics.load(new AssetManager());
        DetailGraphics.load(detailsAtlas);
        CellGraphics.load(cellsAtlas);


        for (int x = 0; x <= 5; x++) {
            for (int y = 0; y <= 5; y++) {
                Renderer cell = new Renderer(ground, false, 1, Vector3.Y, Vector3.X);
                cell.setLocalPosition(x + .5f, 0, y + .5f);
                renderers.add(cell);

                DetailRenderer detailRenderer = new DetailRenderer(DetailGraphics.TREE_PINE1, x + .5f, y + .5f);
                renderers.add(detailRenderer);


                for (float sx = 0f; sx <= 1.01f; sx += .2f) {
                    for (float sy = 0f; sy <= 1.01f; sy += .2f) {
                        HumanRenderer renderer = new HumanRenderer(HumanGraphics.EUROPEAN_ARCHER);
                        renderer.setLocalPosition(x + sx, 0, y + sy);
                        renderers.add(renderer);
                        renderer.playAnimation("walking", true);
                        renderer.setDirection(2);
                    }
                }
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
