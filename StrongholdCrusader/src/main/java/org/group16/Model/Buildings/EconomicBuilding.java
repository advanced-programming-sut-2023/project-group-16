package org.group16.Model.Buildings;

import org.group16.Lib.Pair;
import org.group16.Model.Cell;
import org.group16.Model.Kingdom;
import org.group16.Model.People.Engineer;
import org.group16.Model.People.Human;
import org.group16.Model.People.Worker;
import org.group16.Model.Resources.ProductData;
import org.group16.Model.Resources.Resource;
import org.group16.Model.Time;

import java.util.ArrayList;

public class EconomicBuilding extends Building {

    private final ArrayList<Human> workers = new ArrayList<>();
    private final EconomicBuildingDetail detail;
    private final ArrayList<Pair<Resource, Integer>> storage = new ArrayList<>();
    private int usedCapacity = 0;

    public EconomicBuilding(ArrayList<Cell> cells, Kingdom kingdom, double buildTime, EconomicBuildingDetail detail) {
        super(cells, kingdom, detail.getHp(), buildTime, detail.getBuildingType());
        this.detail = detail;
    }

    //storage
    public void addResource(Resource resource, int count) {
        storage.add(new Pair<>(resource, count));
        usedCapacity += count;
    }

    //storage
    public void useResource(Resource resource, int count) {
        usedCapacity += count;
        for (Pair<Resource, Integer> pair : storage) {
            if (count == 0)
                break;
            if (pair.getA().equals(resource)) {
//                storage.remove(pair);
                int usage = Math.min(count, pair.getB());
                pair.setB(pair.getB()-usage);
                count = count - usage;
                usedCapacity -= usage;
            }
        }
    }

    public boolean makeResource(Resource resource, int count) {
        ProductData productData = null;
        for (ProductData pr : detail.getProductsData()) {
            if (pr.resource().equals(resource))
                productData = pr;
        }
        boolean canBeBuilt = true;
        for (Pair<Resource, Integer> needed : resource.getDependencies()) {
            if (getKingdom().getResourceCount(needed.getA()) < needed.getB() * count)
                canBeBuilt = false;
        }
        if (getKingdom().getResourceStorageCapacity(resource) < count)
            canBeBuilt = false;
        if (!canBeBuilt || productData == null)
            return false;
        for (Pair<Resource, Integer> needed : resource.getDependencies()) {
            getKingdom().useResource(needed.getA(), needed.getB() * count);
        }
        getKingdom().addResource(resource, count);
        return true;
    }

    public void addWorker(Human human) {
        if ((human instanceof Worker || human instanceof Engineer) && human.getBuilding() == null) {
            workers.add(human);
            human.setBuilding(this);
        }
    }

    public void removeWorker(Human human) {
        human.setBuilding(null);
        workers.remove(human);
    }

    public ArrayList<Human> getWorkers() {
        return workers;
    }

    public EconomicBuildingDetail getDetail() {
        return detail;
    }

    @Override
    public void destroy() {
        while(workers.size()>0)
            workers.get(0).destroy();
        super.destroy();
    }

    @Override
    public void onTurnEnd() {
        super.onTurnEnd();
        //TODO : on turn end
    }

    @Override
    public void onTurnStart() {
        //TODO : on turn start
    }

    @Override
    public void update(double currentTime) {
        //TODO : Time needed may change -
        if (!isActive() || !Time.isItTurned(currentTime - getBuildTime(), Time.day))
            return;
        for (ProductData productData : detail.getProductsData()) {
            if (!productData.isManual())
                continue;
            makeResource(productData.resource(), productData.maxRate());
        }
        if (detail.getEconomyEffect() != null)
            detail.getEconomyEffect().applyEffect(getKingdom());
    }

    @Override
    public void delete() {
        for (var worker : workers) removeWorker(worker);
        super.delete();
    }

    public boolean isActive() {
        int workerCount = 0;
        int engineerCount = 0;
        for (var human : workers) {
            if (human instanceof Engineer) engineerCount++;
            else workerCount++;
        }
        return workerCount >= detail.getNeededWorkers() && engineerCount >= detail.getNeededEngineers();
    }

    public ArrayList<Pair<Resource, Integer>> getStorage() {
        return storage;
    }

    public int getUsedCapacity() {
        return usedCapacity;
    }

    public int getAvailableCapacity() {
        return detail.getCapacity() - usedCapacity;
    }
}
