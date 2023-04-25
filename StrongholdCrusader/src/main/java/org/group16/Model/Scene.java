package org.group16.Model;

import org.group16.Model.Buildings.Building;

import java.util.*;

public class Scene {

    private static Scene current;
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private final HashMap<UUID, GameObject> gameObjectIndex = new HashMap<>();
    private final Map map;
    private final Random random = new Random();

    public Scene(Map map) {
        this.map = new Map(map);
    }

    public static Scene getCurrent() {
        return current;
    }

    public Random getRandom() {
        return random;
    }

    public Map getMap() {
        return map;
    }

    public Cell getCellAt(int i, int j) {
        return map.getCellAt(i, j);
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }


    public void onTurnStart() {
        Collections.shuffle(gameObjects, random);
        for (var go : gameObjects) go.onTurnStart();
    }

    public void update(double deltaTime) {
        Collections.shuffle(gameObjects, random);
        for (var go : gameObjects) go.update(deltaTime);
    }

    public void onTurnEnd() {
        Collections.shuffle(gameObjects, random);
        for (var go : gameObjects) go.onTurnEnd();
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        gameObjectIndex.put(gameObject.getUuid(), gameObject);
    }

    public GameObject getGameObjectByUuid(UUID uuid) {
        return gameObjectIndex.get(uuid);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
        gameObjectIndex.remove(gameObject.getUuid(), gameObject);
    }
}
