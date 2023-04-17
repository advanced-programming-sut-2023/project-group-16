package org.group16.Model.Buildings;

import org.group16.Lib.Pair;
import org.group16.Model.Cell;
import org.group16.Model.Kingdom;
import org.group16.Model.People.Engineer;
import org.group16.Model.People.Human;
import org.group16.Model.People.Worker;
import org.group16.Model.Resources.Resource;

import java.util.ArrayList;

public class EconomicBuilding extends Building {

    private final ArrayList<Human> workers = new ArrayList<>();
    private final EconomicBuildingDetail detail;
    private final ArrayList<Pair<Resource, Integer>> storage = new ArrayList<>();

    public EconomicBuilding(ArrayList<Cell> cells, Kingdom kingdom, EconomicBuildingDetail detail) {
        super(cells, kingdom, detail.getHp());
        this.detail = detail;
    }

    public void addResource(Resource resource, int count) {
        //TODO
    }

    @Override
    public void initialize(ArrayList<Cell> spawnCell) {
        super.initialize(spawnCell);
        //TODO
    }

    public void useResource(Resource resource, int count) {
        //TODO
    }

    public void addWorker(Human human) {
        if (human instanceof Worker || human instanceof Engineer) {
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

}
