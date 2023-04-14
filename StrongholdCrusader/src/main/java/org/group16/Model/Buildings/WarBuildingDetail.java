package org.group16.Model.Buildings;

public enum WarBuildingDetail {
    ;

    private final int hp;
    private final int maxSoldierCount;
    private final int attackRange;
    private final int defendRange;
    WarBuildingDetail(int hp, int maxSoldierCount, int attackRange, int defendRange) {
        this.hp = hp;
        this.maxSoldierCount = maxSoldierCount;
        this.attackRange = attackRange;
        this.defendRange = defendRange;
    }

    public int getHp() {
        return hp;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getDefendRange() {
        return defendRange;
    }

    public int getMaxSoldierCount() {
        return maxSoldierCount;
    }

}
