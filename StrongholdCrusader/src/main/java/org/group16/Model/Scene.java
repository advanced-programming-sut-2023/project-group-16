package org.group16.Model;

import org.group16.Model.Buildings.Building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Scene {

    private static Scene current;
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private final HashMap<UUID, GameObject> gameObjectIndex = new HashMap<>();
    private final Map map;

    public Scene(Map map) {
        this.map = new Map(map);
    }

    public static Scene getCurrent() {
        return current;
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
        for (var go : gameObjects) go.onTurnStart();
    }

    public void update(double deltaTime) {
        for (var go : gameObjects) go.update(deltaTime);
        gameObjects.removeIf(GameObject::nullOrDead);
        map.updateDeadObjects();
    }

    public void onTurnEnd() {
        for (var go : gameObjects) go.onTurnEnd();
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        gameObjectIndex.put(gameObject.getUuid(), gameObject);
        for (Cell cell : gameObject.getCells())
            cell.addGameObject(gameObject);
    }

    public GameObject getGameObjectByUuid(UUID uuid) {
        return gameObjectIndex.get(uuid);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
        gameObjectIndex.remove(gameObject.getUuid(), gameObject);
    }
}
