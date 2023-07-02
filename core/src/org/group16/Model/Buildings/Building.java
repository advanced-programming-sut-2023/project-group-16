package org.group16.Model.Buildings;

import org.group16.GameGraphics.BuildingRenderer;
import org.group16.GameGraphics.Renderer;
import org.group16.Model.Cell;
import org.group16.Model.GameObject;
import org.group16.Model.Kingdom;
import org.group16.Model.People.Alive;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Building extends GameObject implements Alive {
    private int hp;
    private boolean traversable;
    private BuildingType buildingType;

    private double buildTime;
    private BuildingRenderer renderer;

    public Building(List<Cell> cells, Kingdom kingdom, int hp) {
        super(cells, kingdom);
        this.hp = hp;
        kingdom.addBuilding(this);
    }

    public Building(List<Cell> cells, Kingdom kingdom, int hp, double buildTime, BuildingType buildingType) {
        this(cells, kingdom, hp);
        this.buildTime = buildTime;
        this.buildingType = buildingType;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public double getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(double buildTime) {
        this.buildTime = buildTime;
    }

    public boolean isTraversable() {
        if (this instanceof WarBuilding)
            return false;
        else
            return true ;
    }

    public void setTraversable(boolean traversable) {
        this.traversable = traversable;
    }

    public void repair() {
        int basicHp;
        if (this instanceof EconomicBuilding)
            basicHp = ((EconomicBuilding) this).getDetail().getHp();
        else
            basicHp = ((WarBuilding) this).getDetail().getHp();
        setHp(basicHp);
    }

    @Override
    public void destroy() {
        super.destroy();
        getKingdom().removeBuilding(this);
    }

    public void delete() {
        super.destroy();
    }

    @Override
    public void onTurnEnd() {
        if (hp <= 0) {
            destroy();
            hp = 0;
        }
    }

    @Override
    public void dealDamage(int damage) {
        hp -= damage;
        if (hp <= 0) destroy();
    }

    public BuildingRenderer getRenderer() {
        return renderer;
    }

    @Override
    public Renderer createRenderer() {
        return renderer = new BuildingRenderer(buildingType.getGraphics(), getCell().getX(), getCell().getY());
    }

    @Override
    public void updateRenderer(Renderer renderer) {
        // TODO?
    }
}
