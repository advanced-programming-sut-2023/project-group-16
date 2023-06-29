package org.group16.Model.People;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import org.group16.GameGraphics.HumanRenderer;
import org.group16.GameGraphics.Renderer;
import org.group16.Model.*;
import org.group16.Model.Buildings.Building;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Human extends GameObject implements Alive {
    protected GameObject fightingTarget;
    private int hp;
    private Building building;

    public Human(List<Cell> cells, Kingdom kingdom, int hp) {
        super(cells, kingdom);
        if (this instanceof Soldier)
            kingdom.addSoldier((Soldier) this);
        else
            kingdom.addHuman(this);
        this.hp = hp;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (this instanceof Soldier)
            getKingdom().removeSoldier((Soldier) this);
        else
            getKingdom().removeHuman(this);
    }

    protected void moveToward(Cell destination, boolean canUseLadder, boolean canClimbWalls, double distance, double randomness, Random random) {
        Cell currentCell = getCell();
        PathFindingQuery pathFindingQuery = new PathFindingQuery(Scene.getCurrent().getMap(), currentCell, destination, getKingdom().getTeam(), canUseLadder, canClimbWalls, randomness, random);
        pathFindingQuery.findShortestPath();
        Cell nextCell = pathFindingQuery.getNextCell(currentCell);
        double dx = nextCell.getX() - currentCell.getX();
        double dy = nextCell.getY() - currentCell.getY();
        if (nextCell.getX() != currentCell.getX() && nextCell.getY() != currentCell.getY()) {
            dx /= PathFindingQuery.DIAGONAL_COST_MULTIPLIER;
            dy /= PathFindingQuery.DIAGONAL_COST_MULTIPLIER;
        }
        dx *= distance;
        dy *= distance;
        relativeX += dx;
        relativeY += dy;
        int cellDx = 0, cellDy = 0;
        if (relativeX >= .5f) cellDx = 1;
        else if (relativeX <= -.5f) cellDx = -1;
        if (relativeY >= .5f) cellDy = 1;
        else if (relativeY <= -.5f) cellDy = -1;
        currentCell = Scene.getCurrent().getCellAt(currentCell.getX() + cellDx, currentCell.getY() + cellDy);
        relativeX -= cellDx;
        relativeY -= cellDy;
        setCell(currentCell);
    }

    @Override
    public void dealDamage(int damage) {
        hp -= damage;
        if (hp <= 0) destroy();
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void onTurnStart() {
        //TODO
    }

    @Override
    public void update(double currentTime) {
        fightingTarget = null;
        double deltaTime = Time.deltaTime;
        setBuilding(getCell().getBuilding());
        // TODO?
    }

    public void onTurnEnd() {
        //TODO
    }

    @Override
    public Renderer createRenderer() {
        HumanRenderer renderer = new HumanRenderer(SoldierDetail.HUMAN.getGraphics());
        renderer.setLocalPosition(calculateWorldPosition());
        return renderer;
    }

    @Override
    public void updateRenderer(Renderer renderer) {
        HumanRenderer humanRenderer = (HumanRenderer) renderer;
        Vector3 target = calculateWorldPosition();
        Vector3 delta = target.cpy().sub(humanRenderer.getLocalPosition());
        humanRenderer.getLocalPosition().mulAdd(delta, .03f);
        if (fightingTarget != null && fightingTarget.isAlive()) {
            delta = new Vector3(fightingTarget.getCell().getX() + fightingTarget.getRelativeX() - humanRenderer.getLocalPosition().x,
                    0,
                    fightingTarget.getCell().getY() + fightingTarget.getRelativeY() - humanRenderer.getLocalPosition().z);
            humanRenderer.setDirection(calculateDirection(delta));
            humanRenderer.playOrContinueAnimation("fighting", false);
        } else {
            humanRenderer.setDirection(calculateDirection(delta));
            if (delta.len2() > .5f) {
                humanRenderer.playOrContinueAnimation("running", true);
            } else if (delta.len2() > .1f) {
                humanRenderer.playOrContinueAnimation("walking", true);
            } else {
                humanRenderer.playOrContinueAnimation("idle", true);
            }
        }
    }

    public int calculateDirection(Vector3 vector) {
        float angle = MathUtils.atan2(vector.z, vector.x);
        float p_4 = MathUtils.PI / 4;
        float p_8 = MathUtils.PI / 8 + .05f;
        for (int i = -4; i < 4; i++) {
            float trg = i * p_4;
            if (angle >= trg - p_8 && angle <= trg + p_8) {
                return (i + 10) & 7;
            }
        }
        return 6;
    }

    public Vector3 calculateWorldPosition() {
        float x = relativeX;
        float y = relativeY;
        Building building = getCell().getBuilding();
        if (building == null || building.isTraversable())
            return new Vector3(getCell().getX() + x, 0, getCell().getY() + y);
        return building.getRenderer().getRoofPosition(x + .5f, y + .5f);
    }
}
