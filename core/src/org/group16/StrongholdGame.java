package org.group16;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.decals.GroupStrategy;
import com.badlogic.gdx.graphics.g3d.particles.values.MeshSpawnShapeValue;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;

import static com.badlogic.gdx.Gdx.*;


public class StrongholdGame extends Game {
    public static NativeFileChooser fileChooser;
    public static NativeFileChooserConfiguration fileChooserConfiguration;
    public static StrongholdGame singleton;
    public AssetManager assetManager = new AssetManager();
    private DecalBatch batch;
    private PerspectiveCamera camera;
    private TextureAtlas atlas;


    @Override
    public void create() {
        singleton = this;

        manageAssets();

        fileChooserConfiguration.directory = Gdx.files.absolute(System.getProperty("user.home"));

        atlas = new TextureAtlas("soldiers/european_archer.atlas");
        camera = new PerspectiveCamera(67, graphics.getWidth(), graphics.getHeight());
        batch = new DecalBatch(new CameraGroupStrategy(camera));
        batch.add(Decal.newDecal(atlas.findRegion("walking")));
    }

    private void manageAssets() {
        assetManager.finishLoading();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);
        super.render();

        batch.flush();
    }

    @Override
    public void dispose() {
        if (getScreen() != null) getScreen().dispose();
        assetManager.dispose();
    }
}
