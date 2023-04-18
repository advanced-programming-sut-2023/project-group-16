package org.group16.Model.Buildings;

import org.group16.Model.EconomyEffect;
import org.group16.Model.Kingdom;
import org.group16.Model.Resources.*;

public enum EconomicBuildingDetail {
    SMALL_STONE_GATEHOUSE(null, null, null, 1000, 0, 0 , BuildingType.SMALL_STONE_GATEHOUSE),
    LARGE_STONE_GATEHOUSE(null, null, null, 2000, 0, 0 , BuildingType.LARGE_STONE_GATEHOUSE),
    //ARMOURY(null,new StorageData[]{new StorageData()},null , 500 , , ) ,
    //BARRACKS(new ProductData[]{new ProductData()} , null , 500 , , ) ,
    //MERCENARY_POST
    //ENGINEER_GUILD

    INN(null, null, new EconomyEffect() {
        @Override
        public void applyEffect(Kingdom kingdom) {
            kingdom.addPopularity(1);
        }
    }, 300, 1, 0 , BuildingType.INN),
    //TODO :
    //not sure about rates
    MILL(new ProductData[]{new ProductData(Food.FLOUR, true, 10)}, null, null, 300, 3, 0 , BuildingType.MILL),
    IRON_MINE(new ProductData[]{new ProductData(BasicResource.IRON, true, 1)}, null, null, 300, 2, 0 , BuildingType.IRON_MINE),
    MARKET(null, null, null, 300, 1, 0 , BuildingType.MARKET),
    //OX_TETHER
    PITCH_RIG(new ProductData[]{new ProductData(BasicResource.OIL, true, 10)}, null, null, 300, 1, 0 , BuildingType.PITCH_RIG),
    QUARRY(new ProductData[]{new ProductData(BasicResource.STONE, true, 10)}, null, null, 300, 3, 0 , BuildingType.QUARRY),
    //STOCKPILE(null , ? , null , 300 , 0 , 0)
    WOOD_CUTTER(new ProductData[]{new ProductData(BasicResource.WOOD, true, 10)}, null, null, 300, 1, 0 , BuildingType.WOOD_CUTTER),
    HOVEL(null, null, null, 100, 0, 0 , BuildingType.HOVEL),
    CHURCH(null, null, new EconomyEffect() {
        @Override
        public void applyEffect(Kingdom kingdom) {
            kingdom.addPopularity(2);
        }
    }, 800, 0, 0 , BuildingType.CHURCH),
    //    CATHEDRAL(, , new EconomyEffect() {
//        @Override
//        public void applyEffect(Kingdom kingdom) {
//            kingdom.addPopularity(2);
//        }
//    }, 1200, 0, 0)
    ARMOURER(new ProductData[]{new ProductData(Weaponry.METAL_ARMOR, false, 1)}, null, null, 300, 1, 0 , BuildingType.ARMOURER),
    BLACKSMITH(new ProductData[]{new ProductData(Weaponry.MACE, false, 1), new ProductData(Weaponry.SWORD, false, 1)}, null, null, 300, 1, 0 , BuildingType.BLACKSMITH),
    FLETCHER(new ProductData[]{new ProductData(Weaponry.BOW, false, 1), new ProductData(Weaponry.CROSSBOW, false, 1)}, null, null, 300, 1, 0 , BuildingType.FLETCHER),
    POLE_TURNER(new ProductData[]{new ProductData(Weaponry.PIKE, false, 1), new ProductData(Weaponry.SPEAR, false, 1)}, null, null, 300, 1, 0 , BuildingType.POLE_TURNER),
    //TODO : ?
    //OIL_SMELTER
    STABLE(null , new StorageData[]{new StorageData(BasicResource.HORSE , 4 , 4)} , null , 300 , 0 , 0 , BuildingType.STABLE) ,
    APPLE_ORCHARD(new ProductData[]{new ProductData(Food.APPLE , true , 10)} , null , null , 100 , 1 , 0 , BuildingType.APPLE_ORCHARD) ,
    //TODO : not sure about diary
    DIARY_FARMER(new ProductData[]{new ProductData(Food.CHEESE , true , 10)} , new StorageData[]{new StorageData(BasicResource.COW , 4 , 4)} , null ,100 , 1 , 0 , BuildingType.DIARY_FARMER) ,
    HOPS_FARMER(new ProductData[]{new ProductData(BasicResource.HOPS , true , 10)} , null , null , 100 , 1 , 0 , BuildingType.HOPS_FARMER) ,
    HUNTER_POST(new ProductData[]{new ProductData(Food.MEAT , true , 10)} , null , null , 100 , 1 , 0 , BuildingType.HUNTER_POST) ,
    WHEAT_FARMER(new ProductData[]{new ProductData(BasicResource.WHEAT , true , 10)} , null , null , 100 , 1 , 0 , BuildingType.WHEAT_FARMER) ,
    BAKERY(new ProductData[]{new ProductData(Food.BREAD , true , 10)} , null , null , 100 , 1 , 0 , BuildingType.BAKERY) ,
    BREWER(new ProductData[]{new ProductData(Food.ALE , true , 10)} , null , null , 100 , 1 , 0 , BuildingType.BREWER) ,
    GRANARY(null , new StorageData[]{
            new StorageData(Food.MEAT , 100 , 0) ,
            new StorageData(Food.BREAD , 100 , 0),
            new StorageData(Food.APPLE, 100 , 0),
            new StorageData(Food.CHEESE , 100 , 0)
    } , null , 100 , 0 , 0 , BuildingType.GRANARY) ,
    ;//TODO

    private final int hp;
    private final int neededWorkers;
    private final int neededEngineers;
    private final ProductData[] productsData;
    private final StorageData[] storageData;
    private final EconomyEffect economyEffect;
    private final BuildingType buildingType ;

    private EconomicBuildingDetail(ProductData[] productsData, StorageData[] storageData, EconomyEffect economyEffect, int hp, int neededWorkers, int neededEngineers , BuildingType buildingType) {
        this.productsData = productsData;
        this.storageData = storageData;
        this.economyEffect = economyEffect;
        this.neededEngineers = neededEngineers;
        this.neededWorkers = neededWorkers;
        this.hp = hp;
        this.buildingType = buildingType ;
    }

    public int getNeededWorkers() {
        return neededWorkers;
    }

    public int getNeededEngineers() {
        return neededEngineers;
    }

    public ProductData[] getProductsData() {
        return productsData;
    }

    public int getHp() {
        return hp;
    }

    public StorageData[] getStorageData() {
        return storageData;
    }

    public EconomyEffect getEconomyEffect() {
        return economyEffect;
    }
}
