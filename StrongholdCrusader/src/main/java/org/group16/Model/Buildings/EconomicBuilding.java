package org.group16.Model.Buildings;

import org.group16.Lib.Pair;
import org.group16.Model.Cell;
import org.group16.Model.Kingdom;
import org.group16.Model.People.Engineer;
import org.group16.Model.People.Human;
import org.group16.Model.People.Worker;
import org.group16.Model.Resources.ProductData;
import org.group16.Model.Resources.Resource;

import java.util.ArrayList;

public class EconomicBuilding extends Building {

    private final ArrayList<Human> workers = new ArrayList<>();
    private final EconomicBuildingDetail detail;
    private final ArrayList<Pair<Resource, Integer>> storage = new ArrayList<>();
    private int usedCapacity = 0;

    public EconomicBuilding(ArrayList<Cell> cells, Kingdom kingdom, EconomicBuildingDetail detail) {
        super(cells, kingdom, detail.getHp(), detail.getBuildingType());
        this.detail = detail;
    }

    //storage
    public void addResource(Resource resource, int count) {
        storage.add(new Pair<>(resource , count)) ;
        usedCapacity+=count ;
    }

    //storage
    public void useResource(Resource resource, int count) {
        usedCapacity+=count ;
        for (Pair<Resource , Integer> pair : storage){
            if (count==0)
                break;
            if (pair.getA().equals(resource)){
                storage.remove(pair);
                int usage = Math.min(count , pair.getB()) ;
                if (pair.getB() > usage){
                    storage.add(new Pair<>(resource , pair.getB()-usage)) ;
                }
                count = count - usage ;
            }
        }
    }

    public void makeResource(Resource resource, int count) {
        ProductData productData = null;
        for (ProductData pr : detail.getProductsData()) {
            if (pr.resource().equals(resource))
                productData = pr;
        }
        boolean canBeBuilt = true;
        for (Pair<Resource , Integer> needed : resource.getDependencies()){
            if (getKingdom().getResourceCount(needed.getA()) < needed.getB() )
                canBeBuilt = false ;
        }
        if (getKingdom().getResourceStorageCapacity(resource) < count)
            canBeBuilt = false;
        if (!canBeBuilt)
            return;
        for (Pair<Resource , Integer> needed : resource.getDependencies()){
            getKingdom().useResource(needed.getA() , needed.getB()) ;
        }
        getKingdom().addRecourse(resource , count) ;
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
        for (var worker : workers) worker.destroy();
        super.destroy();
    }

    @Override
    public void onTurnEnd() {
        super.onTurnEnd();
        //TODO
    }

    @Override
    public void onTurnStart() {
        //TODO
    }

    @Override
    public void update(double deltaTime) {
        //TODO
        //checking deltaTime
        if (!isActive())
            return;
        for (ProductData productData : detail.getProductsData()){
            if (!productData.isManual())
                continue;
            makeResource(productData.resource(), productData.maxRate());
        }
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
    public int getAvailableCapacity(){
        return detail.getCapacity()-usedCapacity;
    }
}
