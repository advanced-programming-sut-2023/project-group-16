package org.group16.GameGraphics;

import java.util.HashMap;

public class AnimCollection {
    private final HashMap<String, AnimData> animations = new HashMap<>();

    public void addAnimation(String name, AnimData animData) {
        animations.put(name, animData);
    }

    public void removeAnimation(String name) {
        animations.remove(name);
    }

    public AnimData getAnimation(String name) {
        return animations.get(name);
    }
}
