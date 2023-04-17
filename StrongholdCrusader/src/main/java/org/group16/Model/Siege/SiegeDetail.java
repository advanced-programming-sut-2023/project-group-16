package org.group16.Model.Siege;

import org.group16.Model.TargetType;

public enum SiegeDetail {
    ;//TODO
    private final int hp;
    private final boolean canMove;
    private final boolean canRotate;
    private final int damage;
    private final int speed;
    private final int operatorCount;
    private final TargetType targetType;

    SiegeDetail(int hp, boolean canMove, boolean canRotate, int damage, int speed, int operatorCount, TargetType targetType) {
        this.hp = hp;
        this.canMove = canMove;
        this.canRotate = canRotate;
        this.damage = damage;
        this.speed = speed;
        this.operatorCount = operatorCount;
        this.targetType = targetType;
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
