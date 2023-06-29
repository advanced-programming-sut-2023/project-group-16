package org.group16.GameGraphics;

import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import org.group16.Model.*;
import org.group16.Model.Buildings.Building;
import org.group16.Model.People.Human;
import org.group16.Model.People.Soldier;
import org.group16.Model.Siege.Siege;

import java.util.HashMap;
import java.util.Random;

public class GameRenderer extends Renderer {
    private final Game game;
    private final Random random = new Random();
    private final HashMap<GameObject, Renderer> renderer = new HashMap<>();

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
    public void render(DecalBatch decalBatch, Vector3 parentPosition) {
        for (GameObject go : game.getScene().getGameObjects()) {
            if (renderer.containsKey(go))
                go.updateRenderer(renderer.get(go));
            else renderer.put(go, go.createRenderer());
        }
        super.render(decalBatch, parentPosition);
    }
}
