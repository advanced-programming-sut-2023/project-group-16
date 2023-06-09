package org.group16.Model;

import org.group16.Model.Buildings.Building;

import java.util.*;

public class Scene {

    private static Scene current;
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private final HashMap<UUID, GameObject> gameObjectIndex = new HashMap<>();
    private final Map map;
    private final Random random;

    public Scene(Map map, long randomSeed) {
        this.map = new Map(map);
        current = this;
        random = new Random(randomSeed);
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

    public void update(double currentTime) {
        Collections.shuffle(gameObjects, random);
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject go = gameObjects.get(i);
            go.update(currentTime);
            if (!go.isAlive()) i--;
        }
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
