package org.group16.Model.Siege;

import org.group16.Model.TargetType;

import javax.swing.plaf.TreeUI;

public enum SiegeDetail {
    PORTABLE_SHIELD(1, true, true, 1, 1, 1, 1, TargetType.NONE),
    SIEGE_TOWER(1, true, true, 1, 1, 1, 1, TargetType.NONE),
    CATAPULT(1, true, true, 1, 1, 1, 1, TargetType.BUILDING),
    TRABUCHET(1, true, true, 1, 1, 1, 1, TargetType.BUILDING),
    FIRE_BALLISTA(1, true, true, 1, 1, 1, 1, TargetType.UNIT);
    //TODO : values
    private final int hp;
    private final boolean canMove;
    private final boolean canRotate;
    private final int attackRange;
    private final int damage;
    private final int speed;
    private final int operatorCount;
    private final TargetType targetType;

    SiegeDetail(int hp, boolean canMove, boolean canRotate, int attackRange, int damage, int speed, int operatorCount, TargetType targetType) {
        this.hp = hp;
        this.canMove = canMove;
        this.canRotate = canRotate;
        this.attackRange = attackRange;
        this.damage = damage;
        this.speed = speed;
        this.operatorCount = operatorCount;
        this.targetType = targetType;
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
