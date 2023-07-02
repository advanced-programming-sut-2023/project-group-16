package org.group16.GameGraphics;

import org.group16.GameGraphics.CommandHandling.InputProcessor;
import org.group16.Model.*;

import java.util.HashMap;
import java.util.Random;

public class GameRenderer extends Renderer {
    private final Game game;
    private final Random random = new Random();
    private final HashMap<GameObject, Renderer> renderers = new HashMap<>();
    private final InputProcessor inputProcessor;
    private float lastActionTime = -1;
    private float currentTime = 0;
    private int currentStep = 0;
    private boolean executeLogic;


    public GameRenderer(Game game, InputProcessor inputProcessor) {
        super(null, false, 1, Util.forward, Util.up);
        this.game = game;
        this.inputProcessor = inputProcessor;
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
        if (!executeLogic)
            executeLogic = inputProcessor.process(game, this);

        if (executeLogic)
            if (currentTime >= lastActionTime + Time.deltaTime) {
                if (currentStep == 0)
                    game.onTurnStart();
                game.update();
                if (currentStep == Time.updateIterationCount - 1) {
                    game.onTurnEnd();
                    currentStep = -1;
                    executeLogic = false;
                }
                currentStep++;

                lastActionTime = currentTime;
            }

        for (GameObject go : game.getScene().getGameObjects()) {
            if (renderers.containsKey(go))
                go.updateRenderer(renderers.get(go));
            else {
                Renderer renderer = go.createRenderer();
                renderers.put(go, renderer);
                addChild(renderer);
            }
        }
        super.update(dt);
    }

    public void createRenderer(GameObject go) {
        Renderer renderer = go.createRenderer();
        renderers.put(go, renderer);
        go.setDestroyCallback(this::destroyRenderer);
        addChild(renderer);
    }

    public void destroyRenderer(GameObject go) {
        // TODO : animation?
        removeChild(renderers.get(go));
    }
}
