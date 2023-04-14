package org.group16.Model.People;

import org.group16.Lib.Pair;
import org.group16.Model.Resources.Resource;
import org.group16.Model.Resources.Weaponry;
import org.group16.Model.TargetType;

import java.util.ArrayList;

public enum SoldierDetail implements Resource {
    ;

    private final Weaponry armor;
    private final Weaponry weapon;
    private final int resultCount;
    private final boolean hasHorse, canClimbLadder, canClimbWalls, canHide, canDigMoats;
    private final int attackRange, damage, hp, speed;

    SoldierDetail(Weaponry armor, Weaponry weapon, boolean hasHorse, int resultCount,
                  boolean canClimbLadder, boolean canClimbWalls, boolean canHide, boolean canDigMoats,
                  int attackRange, int damage, int hp, int speed) {
        this.armor = armor;
        this.weapon = weapon;
        this.hasHorse = hasHorse;
        this.resultCount = resultCount;
        this.canClimbLadder = canClimbLadder;
        this.canClimbWalls = canClimbWalls;
        this.canHide = canHide;
        this.canDigMoats = canDigMoats;
        this.attackRange = attackRange;
        this.damage = damage;
        this.hp = hp;
        this.speed = speed;
    }

    public Weaponry getArmor() {
        return armor;
    }

    public Weaponry getWeapon() {
        return weapon;
    }

    public boolean isHasHorse() {
        return hasHorse;
    }

    public boolean isCanClimbLadder() {
        return canClimbLadder;
    }

    public boolean isCanClimbWalls() {
        return canClimbWalls;
    }

    public boolean isCanHide() {
        return canHide;
    }

    public boolean isCanDigMoats() {
        return canDigMoats;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getDamage() {
        return damage;
    }

    public int getHp() {
        return hp;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public ArrayList<Pair<Resource, Integer>> getDependencies() {
        //TODO
        return null;
    }

    @Override
    public int getResultCount() {
        return resultCount;
    }

    @Override
    public int getPrice() {
        return Integer.MAX_VALUE;
    }
}
