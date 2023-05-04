package org.group16.Model.Buildings;

public enum WarBuildingDetail {
    //TODO : not sure about details
    DRAWBRIDGE(300, 0 , 0 , 0 , 0 , BuildingType.DRAWBRIDGE) ,
    LOOKOUT_TOWER(2000 , 10, 20 , 2 , 0 , BuildingType.LOOKOUT_TOWER) ,
    PERIMETER_TOWER(1000 , 10 , 10 , 2 , 0 , BuildingType.PERIMETER_TOWER) ,
    DEFENCE_TURRET(1200 , 10 , 10 , 2 , 0 , BuildingType.DEFENCE_TURRET) ,
    SQUARE_TOWER(1600 , 10 , 10 , 2 , 0 , BuildingType.SQUARE_TOWER) ,
    ROUND_TOWER(2000 , 20 , 10 , 2 , 0 , BuildingType.ROUND_TOWER) ,
    KILLING_PIT(1 , 0 , 0 , 0 , 300 , BuildingType.KILLING_PIT) ,
    ;

    private final int hp;
    private final int maxSoldierCount;
    private final int attackRange;
    private final int defendRange;
    private final int currentDamage;
    private final BuildingType buildingType ;

    WarBuildingDetail(int hp, int maxSoldierCount, int attackRange, int defendRange, int currentDamage , BuildingType buildingType) {
        this.hp = hp;
        this.maxSoldierCount = maxSoldierCount;
        this.attackRange = attackRange;
        this.defendRange = defendRange;
        this.currentDamage = currentDamage;
        this.buildingType = buildingType ;
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

    public int getCurrentDamage() {
        return currentDamage;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }
}
