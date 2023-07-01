package org.group16.Model.Siege;

import org.group16.GameGraphics.HumanGraphics;
import org.group16.Model.TargetType;

public enum SiegeDetail {
    PORTABLE_SHIELD(1, true, true, 1, 1, 1, 1, TargetType.NONE, null),
    SIEGE_TOWER(1, true, true, 1, 1, 1, 1, TargetType.NONE, null),
    CATAPULT(1, true, true, 1, 1, 1, 1, TargetType.BUILDING, null),
    TRABUCHET(1, true, true, 1, 1, 1, 1, TargetType.BUILDING, null),
    FIRE_BALLISTA(1, true, true, 1, 1, 1, 1, TargetType.UNIT, null);
    //TODO : values
    private final int hp;
    private final boolean canMove;
    private final boolean canRotate;
    private final int attackRange;
    private final int damage;
    private final int speed;
    private final int operatorCount;
    private final TargetType targetType;
    private final HumanGraphics graphics;
    SiegeDetail(int hp, boolean canMove, boolean canRotate, int attackRange, int damage, int speed, int operatorCount, TargetType targetType, HumanGraphics graphics) {
        this.hp = hp;
        this.canMove = canMove;
        this.canRotate = canRotate;
        this.attackRange = attackRange;
        this.damage = damage;
        this.speed = speed;
        this.operatorCount = operatorCount;
        this.targetType = targetType;
        this.graphics = graphics;
    }

    public static SiegeDetail getSiegeDetailByName(String name) {
        for (SiegeDetail siegeDetail : SiegeDetail.values()) {
            if (siegeDetail.toString().equals(name))
                return siegeDetail;
        }
        return null;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public boolean isCanRotate() {
        return canRotate;
    }

    public HumanGraphics getGraphics() {
        return graphics;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public int getOperatorCount() {
        return operatorCount;
    }

    public int getHp() {
        return hp;
    }

    public boolean getCanMove() {
        return canMove;
    }

    public boolean getCanRotate() {
        return canRotate;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }
}
