package org.group16.Model.Buildings;

import org.group16.Model.EconomyEffect;
import org.group16.Model.Kingdom;
import org.group16.Model.Resources.*;

public enum EconomicBuildingDetail {
    SMALL_STONE_GATEHOUSE(null, null, 0, null, 1000, 0, 0, BuildingType.SMALL_STONE_GATEHOUSE),
    LARGE_STONE_GATEHOUSE(null, null, 0, null, 2000, 0, 0, BuildingType.LARGE_STONE_GATEHOUSE),
    ARMOURY(null, new StorageData[]{
            new StorageData(Weaponry.SPEAR, 0),
            new StorageData(Weaponry.PIKE, 0),
            new StorageData(Weaponry.BOW, 0),
            new StorageData(Weaponry.CROSSBOW, 0),
            new StorageData(Weaponry.MACE, 0),
            new StorageData(Weaponry.SWORD, 0),
            new StorageData(Weaponry.METAL_ARMOR, 0),
            new StorageData(Weaponry.LEATHER_ARMOR, 0),
    }, 100, null, 300, 0, 0, BuildingType.ARMOURY),
    //TODO : soldiers should be  added
    BARRACKS(new ProductData[]{}, null, 0, null, 300, 0, 0, BuildingType.BARRACKS),
    MERCENARY_POST(new ProductData[]{}, null, 0, null, 300, 0, 0, BuildingType.MERCENARY_POST),
    ENGINEER_GUILD(new ProductData[]{}, null, 0, null, 300, 0, 0, BuildingType.ENGINEER_GUILD),

    INN(null, null, 0, new EconomyEffect() {
        @Override
        public void applyEffect(Kingdom kingdom) {
            kingdom.addPopularity(1);
        }
    }, 300, 1, 0, BuildingType.INN),
    //TODO :
    //not sure about rates
    MILL(new ProductData[]{new ProductData(Food.FLOUR, true, 10)}, null, 0, null, 300, 3, 0, BuildingType.MILL),
    IRON_MINE(new ProductData[]{new ProductData(BasicResource.IRON, true, 1)}, null, 0, null, 300, 2, 0, BuildingType.IRON_MINE),
    MARKET(null, null, 0, null, 300, 1, 0, BuildingType.MARKET),
    OX_TETHER(new ProductData[]{new ProductData(BasicResource.STONE, true, 10)}, null, 0, null, 300, 1, 0, BuildingType.OX_TETHER),
    PITCH_RIG(new ProductData[]{new ProductData(BasicResource.OIL, true, 10)}, null, 0, null, 300, 1, 0, BuildingType.PITCH_RIG),
    QUARRY(new ProductData[]{new ProductData(BasicResource.PRIME_STONE, true, 10)}, new StorageData[]{new StorageData(BasicResource.PRIME_STONE, 0)}, 100, null, 300, 3, 0, BuildingType.QUARRY),
    STOCKPILE(null, new StorageData[]{
            new StorageData(BasicResource.WHEAT, 0),
            new StorageData(BasicResource.HOPS, 0),
            new StorageData(BasicResource.IRON, 0),
            new StorageData(BasicResource.WOOD, 0),
            new StorageData(BasicResource.STONE, 0),
            new StorageData(BasicResource.OIL, 0),
            new StorageData(BasicResource.GOLD, 0),
    }, 1000, null, 300, 0, 0, BuildingType.STOCKPILE),
    WOOD_CUTTER(new ProductData[]{new ProductData(BasicResource.WOOD, true, 10)}, null, 0, null, 300, 1, 0, BuildingType.WOOD_CUTTER),
    HOVEL(null, null, 0, null, 100, 0, 0, BuildingType.HOVEL),
    CHURCH(null, null, 0, new EconomyEffect() {
        @Override
        public void applyEffect(Kingdom kingdom) {
            kingdom.addPopularity(2);
        }
    }, 800, 0, 0, BuildingType.CHURCH),
    //TODO:soldiers should be  added
    CATHEDRAL(new ProductData[]{}, null, 0, new EconomyEffect() {
        @Override
        public void applyEffect(Kingdom kingdom) {
            kingdom.addPopularity(2);
        }
    }, 1200, 0, 0, BuildingType.CATHEDRAL),
    ARMOURER(new ProductData[]{new ProductData(Weaponry.METAL_ARMOR, false, 1)}, null, 0, null, 300, 1, 0, BuildingType.ARMOURER),
    BLACKSMITH(new ProductData[]{new ProductData(Weaponry.MACE, false, 1), new ProductData(Weaponry.SWORD, false, 1)}, null, 0, null, 300, 1, 0, BuildingType.BLACKSMITH),
    FLETCHER(new ProductData[]{new ProductData(Weaponry.BOW, false, 1), new ProductData(Weaponry.CROSSBOW, false, 1)}, null, 0, null, 300, 1, 0, BuildingType.FLETCHER),
    POLE_TURNER(new ProductData[]{new ProductData(Weaponry.PIKE, false, 1), new ProductData(Weaponry.SPEAR, false, 1)}, null, 0, null, 300, 1, 0, BuildingType.POLE_TURNER),
    //TODO : not sure about OIL_SMELTER
    OIL_SMELTER(new ProductData[]{new ProductData(Weaponry.HOT_OIL_POT, true, 1)}, new StorageData[]{new StorageData(Weaponry.HOT_OIL_POT, 0)}, 4, null, 300, 0, 1, BuildingType.OIL_SMELTER),
    STABLE(null, new StorageData[]{new StorageData(BasicResource.HORSE, 4)}, 0, null, 300, 0, 0, BuildingType.STABLE),
    APPLE_ORCHARD(new ProductData[]{new ProductData(Food.APPLE, true, 10)}, null, 0, null, 100, 1, 0, BuildingType.APPLE_ORCHARD),
    //TODO : not sure about diary
    DIARY_FARMER(new ProductData[]{new ProductData(Food.CHEESE, true, 10)}, new StorageData[]{new StorageData(BasicResource.COW, 4)}, 4, null, 100, 1, 0, BuildingType.DIARY_FARMER),
    HOPS_FARMER(new ProductData[]{new ProductData(BasicResource.HOPS, true, 10)}, null, 0, null, 100, 1, 0, BuildingType.HOPS_FARMER),
    HUNTER_POST(new ProductData[]{new ProductData(Food.MEAT, true, 10)}, null, 0, null, 100, 1, 0, BuildingType.HUNTER_POST),
    WHEAT_FARMER(new ProductData[]{new ProductData(BasicResource.WHEAT, true, 10)}, null, 0, null, 100, 1, 0, BuildingType.WHEAT_FARMER),
    BAKERY(new ProductData[]{new ProductData(Food.BREAD, true, 10)}, null, 0, null, 100, 1, 0, BuildingType.BAKERY),
    BREWER(new ProductData[]{new ProductData(Food.ALE, true, 10)}, null, 0, null, 100, 1, 0, BuildingType.BREWER),
    GRANARY(null, new StorageData[]{
            new StorageData(Food.MEAT, 0),
            new StorageData(Food.BREAD, 0),
            new StorageData(Food.APPLE, 0),
            new StorageData(Food.CHEESE, 0)
    }, 1000, null, 100, 0, 0, BuildingType.GRANARY),
    //main building where king lives and ...
    TOWN_BUILDING(null , null , 0 , null , 4000 , 0 , 0 , BuildingType.TOWN_BUILDING) ,
    UNEMPLOYED_PLACE(null , null , 0 , null , Integer.MAX_VALUE , 0 , 0 , BuildingType.UNEMPLOYED_PLACE)
    ;

    private final int hp;
    private final int neededWorkers;
    private final int neededEngineers;
    private final int capacity;
    private final ProductData[] productsData;
    private final StorageData[] storageData;
    private final EconomyEffect economyEffect;
    private final BuildingType buildingType;

    private EconomicBuildingDetail(ProductData[] productsData, StorageData[] storageData, int capacity, EconomyEffect economyEffect, int hp, int neededWorkers, int neededEngineers, BuildingType buildingType) {
        this.productsData = productsData;
        this.storageData = storageData;
        this.capacity = capacity;
        this.economyEffect = economyEffect;
        this.neededEngineers = neededEngineers;
        this.neededWorkers = neededWorkers;
        this.hp = hp;
        this.buildingType = buildingType;
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

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getCapacity() {
        return capacity;
    }

    public EconomyEffect getEconomyEffect() {
        return economyEffect;
    }
}
