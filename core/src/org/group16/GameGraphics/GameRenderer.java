package org.group16.GameGraphics;

import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import org.group16.Model.*;

import java.util.HashMap;
import java.util.Random;

public class GameRenderer extends Renderer {
    private final Game game;
    private final Random random = new Random();
    private final HashMap<GameObject, Renderer> renderers = new HashMap<>();
    private float lastActionTime = -1;
    private float currentTime = 0;
    private int currentStep = 0;

    public GameRenderer(Game game) {
        super(null, false, 1, Util.forward, Util.up);
        this.game = game;
        Map map = game.getScene().getMap();
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCellAt(x, y);
                CellType type = cell.getCellType();
                CellRenderer cellRenderer = new CellRenderer(type.getGraphics(), x, y);
                addChild(cellRenderer);
                for (DetailGraphics detail : type.getDetails()) {
                    DetailRenderer detailRenderer = new DetailRenderer(detail,
                            .25f - random.nextFloat() / 2, .25f - random.nextFloat() / 2);
                    cellRenderer.addChild(detailRenderer);
                }
                TreeType treeType = cell.getTreeType();
                if (treeType.getGraphics().length > 0) {
                    int cnt = random.nextInt(4);
                    for (int i = 0; i < cnt; i++) {
                        DetailRenderer detailRenderer = new DetailRenderer(
                                treeType.getGraphics()[random.nextInt(treeType.getGraphics().length)],
                                .25f - random.nextFloat() / 2, .25f - random.nextFloat() / 2);
                        cellRenderer.addChild(detailRenderer);
                    }
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        currentTime += dt;
        if (currentTime >= lastActionTime + Time.deltaTime) {
            if (currentStep == 0)
                game.onTurnStart();
            game.update();
            if (currentStep == 9) {
                game.onTurnEnd();
                currentStep = -1;
            }
            currentStep++;

            float relx = game.getKingdoms().get(0).getKing().getRelativeX();
            float rely = game.getKingdoms().get(0).getKing().getRelativeY();
            System.out.printf("(%f,%f)\n", relx, rely);
            lastActionTime = currentTime;
        }
        for (GameObject go : game.getScene().getGameObjects()) {
            if (renderers.containsKey(go))
                go.updateRenderer(renderers.get(go));
            else {
                throw new RuntimeException();
            }
        }
        super.update(dt);
    }

    public void createRenderer(GameObject go) {
        Renderer renderer = go.createRenderer();
        renderers.put(go, renderer);
        addChild(renderer);
    }
}
