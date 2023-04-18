package org.group16.Model.Buildings;

import org.group16.Model.EconomyEffect;
import org.group16.Model.Kingdom;
import org.group16.Model.Resources.BasicResource;
import org.group16.Model.Resources.Food;
import org.group16.Model.Resources.ProductData;
import org.group16.Model.Resources.StorageData;

public enum EconomicBuildingDetail {
    SMALL_STONE_GATEHOUSE(null , null , null , 1000 , 0 , 0) ,
    LARGE_STONE_GATEHOUSE(null , null , null , 2000 , 0 , 0) ,
    //ARMOURY(null,new StorageData[]{new StorageData()},null , 500 , , ) ,
    //BARRACKS(new ProductData[]{new ProductData()} , null , 500 , , ) ,
    //MERCENARY_POST
    //ENGINEER_GUILD

    INN(null, null, new EconomyEffect() {
        @Override
        public void applyEffect(Kingdom kingdom) {
            kingdom.addPopularity(1);
        }
    } , 300 , 1 , 0) ,
    //TODO :
    //not sure about rates
    MILL(new ProductData[]{new ProductData(Food.FLOUR ,true , 10)} , null , null , 300 , 3 , 0) ,
    IRON_MINE(new ProductData[]{new ProductData(BasicResource.IRON , true , 1)} , null , null , 300 , 2 , 0) ,



    ;//TODO

    private final int hp;
    private final int neededWorkers;
    private final int neededEngineers;
    private final ProductData[] productsData;
    private final StorageData[] storageData;
    private final EconomyEffect economyEffect;

    private EconomicBuildingDetail(ProductData[] productsData, StorageData[] storageData, EconomyEffect economyEffect, int hp, int neededWorkers, int neededEngineers) {
        this.productsData = productsData;
        this.storageData = storageData;
        this.economyEffect = economyEffect;
        this.neededEngineers = neededEngineers;
        this.neededWorkers = neededWorkers;
        this.hp = hp;
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
