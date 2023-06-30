package org.group16.Model.People;

import org.group16.GameGraphics.HumanGraphics;
import org.group16.Lib.Pair;
import org.group16.Model.KingdomType;
import org.group16.Model.Resources.Resource;
import org.group16.Model.Resources.Weaponry;

import java.util.ArrayList;

public enum SoldierDetail implements Resource {
    HUMAN(KingdomType.EUROPEAN, 0, null, null, false, 1,
            false, false, false, false, false,
            false, 0, 0, 0, 0, 0, 4, null),
    WORKER(KingdomType.EUROPEAN, 0, null, null, false, 1,
            false, false, false, false, false,
            false, 0, 0, 0, 0, 0, 4, null),
    ARCHER(KingdomType.EUROPEAN, 12, null, Weaponry.BOW, false, 1,
            true, false, false, true, true,
            true, 10, 10, 15, 2, 2, 4, HumanGraphics.EUROPEAN_ARCHER),
    CROSSBOW_MAN(KingdomType.EUROPEAN, 20, Weaponry.LEATHER_ARMOR, Weaponry.CROSSBOW, false, 1,
            false, false, false, false, true,
            true, 12, 12, 15, 2, 3, 2, null),
    SPEAR_MAN(KingdomType.EUROPEAN, 8, null, Weaponry.SPEAR, false, 1,
            true, false, false, true, true,
            true, 0, 3, 10, 3, 1, 3, null),
    PIKE_MAN(KingdomType.EUROPEAN, 20, Weaponry.METAL_ARMOR, Weaponry.PIKE, false, 1,
            false, false, false, true, true,
            true, 0, 3, 10, 3, 4, 2, null),
    MACE_MAN(KingdomType.EUROPEAN, 20, Weaponry.LEATHER_ARMOR, Weaponry.MACE, false, 1,
            true, false, false, true, true,
            true, 0, 3, 10, 4, 3, 3, HumanGraphics.MACE_MAN),
    SWORDS_MAN(KingdomType.EUROPEAN, 40, Weaponry.METAL_ARMOR, Weaponry.SWORD, false, 1,
            false, false, false, false, true,
            true, 0, 3, 10, 5, 1, 1, null),
    KNIGHT(KingdomType.EUROPEAN, 40, Weaponry.METAL_ARMOR, Weaponry.SWORD, true, 1,
            false, false, false, false, true,
            true, 0, 3, 10, 5, 4, 5, null),
    ARCHER_BOW(KingdomType.ARAB, 75, null, Weaponry.BOW, false, 1,
            false, false, false, true, true,
            true, 10, 10, 15, 2, 2, 4, null),
    SLAVE(KingdomType.ARAB, 5, null, null, false, 1,
            false, false, false, true, true,
            true, 0, 3, 10, 1, 0, 4, HumanGraphics.SLAVE),
    SLINGER(KingdomType.ARAB, 12, null, null, false, 1,
            false, false, false, false, true,
            true, 8, 8, 15, 2, 1, 4, null),
    ASSASSIN(KingdomType.ARAB, 60, null, null, false, 1,
            false, true, true, true, false,
            true, 0, 3, 10, 3, 3, 3, HumanGraphics.ASSASSIN),
    HORSE_ARCHER(KingdomType.ARAB, 80, null, Weaponry.BOW, true, 1,
            false, false, false, false, true,
            true, 10, 10, 15, 2, 3, 5, null),
    ARABIAN_SWORDS_MAN(KingdomType.ARAB, 80, null, null, false, 1,
            false, false, false, false, true,
            true, 0, 3, 10, 4, 4, 2, null),
    FIRE_THROWER(KingdomType.ARAB, 100, null, null, false, 1,
            false, false, false, false, true,
            true, 5, 5, 10, 4, 2, 5, null),
    BLACK_MONK(KingdomType.EUROPEAN, 10, null, null, false, 1,
            false, false, false, false, true,
            true, 0, 3, 10, 3, 3, 2, null),
    LADDER_MAN(KingdomType.EUROPEAN, 30, null, null, false, 1,
            false, false, false, false, true,
            true, 0, 0, 0, 0, 1, 4, null),
    ENGINEER(KingdomType.EUROPEAN, 30, null, null, false, 1,
            false, false, false, false, true,
            true, 0, 0, 0, 0, 1, 3, null),
    KING(KingdomType.EUROPEAN, 0, null, null, false, 1,
            false, false, false, false, true,
            true, 0, 0, 0, 5, 6, 1, HumanGraphics.EUROPEAN_ARCHER);
    //TODO : fill in graphics

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
    private final HumanGraphics graphics;

    SoldierDetail(KingdomType kingdomType, int goldNeeded, Weaponry armor, Weaponry weapon, boolean hasHorse, int resultCount,
                  boolean canClimbLadder, boolean canClimbWalls, boolean canHide, boolean canDigMoats,
                  boolean canDamageHuman, boolean canDamageBuilding, int attackRange, int defensiveRange, int offensiveRange, int damage, int hp, int speed, HumanGraphics graphics) {
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
        this.graphics = graphics;
    }

    public static SoldierDetail getSoldierDetailByName(String name) {
        for (SoldierDetail soldierDetail : SoldierDetail.values())
            if (soldierDetail.toString().equals(name))
                return soldierDetail;
        return null;
    }

    public HumanGraphics getGraphics() {
        return graphics;
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
        switch (damage) {
            case 1 -> {
                return 10;
            }
            case 2 -> {
                return 25;
            }
            case 3 -> {
                return 80;
            }
            case 4 -> {
                return 160;
            }
            case 5 -> {
                return 250;
            }
        }
        return 0;
    }

    public int getHp() {
        switch (hp) {
            case 1 -> {
                return 100;
            }
            case 2 -> {
                return 200;
            }
            case 3 -> {
                return 300;
            }
            case 4 -> {
                return 500;
            }
            case 5 -> {
                return 700;
            }
            case 6 -> {
                return 3500;
            }
        }
        return 1;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public ArrayList<Pair<Resource, Integer>> getDependencies() {
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

    @Override
    public String GetName() {
        return this.name();
    }
}
