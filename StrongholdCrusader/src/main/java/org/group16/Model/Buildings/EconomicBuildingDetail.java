package org.group16.Model.Buildings;

import org.group16.Model.EconomyEffect;
import org.group16.Model.Resources.ProductData;
import org.group16.Model.Resources.StorageData;

public enum EconomicBuildingDetail {
    ;
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
