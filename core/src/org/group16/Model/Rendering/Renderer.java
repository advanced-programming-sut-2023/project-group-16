package org.group16.Model.Rendering;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Renderer implements Disposable {
    private final Array<Renderer> children = new Array<>();
    private Matrix4 local;


    @Override
    public void dispose() {
    }
}
