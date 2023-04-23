package org.group16.Model.People;

import org.group16.Lib.Pair;
import org.group16.Model.KingdomType;
import org.group16.Model.Resources.Resource;
import org.group16.Model.Resources.Weaponry;

import java.util.ArrayList;

public enum SoldierDetail implements Resource {
    ;//TODO

    private final Weaponry armor;
    private final Weaponry weapon;
    private final int resultCount;
    private final boolean hasHorse, canClimbLadder, canClimbWalls, canHide, canDigMoats, canDamageHuman, canDamageBuilding;
    private final int attackRange;
    private final int defensiveRange;
    private final int offensiveRange;
    private final int damage;
    private final int hp;
    private final double speed;
    private final int goldNeeded;
    private final KingdomType kingdomType;

    SoldierDetail(KingdomType kingdomType, int goldNeeded, Weaponry armor, Weaponry weapon, boolean hasHorse, int resultCount,
                  boolean canClimbLadder, boolean canClimbWalls, boolean canHide, boolean canDigMoats,
                  boolean canDamageHuman, boolean canDamageBuilding, int attackRange, int defensiveRange, int offensiveRange, int damage, int hp, int speed) {
        this.kingdomType = kingdomType;
        this.goldNeeded = goldNeeded;
        this.armor = armor;
        this.weapon = weapon;
        this.hasHorse = hasHorse;
        this.resultCount = resultCount;
        this.canClimbLadder = canClimbLadder;
        this.canClimbWalls = canClimbWalls;
        this.canHide = canHide;
        this.canDigMoats = canDigMoats;
        this.canDamageHuman = canDamageHuman;
        this.canDamageBuilding = canDamageBuilding;
        this.attackRange = attackRange;
        this.defensiveRange = defensiveRange;
        this.offensiveRange = offensiveRange;
        this.damage = damage;
        this.hp = hp;
        this.speed = speed;
    }

    public int getOffensiveRange() {
        return offensiveRange;
    }

    public boolean isCanDamageHuman() {
        return canDamageHuman;
    }

    public boolean isCanDamageBuilding() {
        return canDamageBuilding;
    }

    public int getDefensiveRange() {
        return defensiveRange;
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

    public double getSpeed() {
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

    public int getGoldNeeded() {
        return goldNeeded;
    }

    public KingdomType getKingdomType() {
        return kingdomType;
    }
}
