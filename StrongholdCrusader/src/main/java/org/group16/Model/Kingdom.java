package org.group16.Model;

import org.group16.Lib.Pair;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.Buildings.EconomicBuilding;
import org.group16.Model.Buildings.EconomicBuildingDetail;
import org.group16.Model.People.Human;
import org.group16.Model.Resources.Resource;
import org.group16.Model.Resources.StorageData;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Kingdom {
    private final KingdomType kingdomType;
    private final User user;
    private final ArrayList<Human> humans = new ArrayList<>();
    private final ArrayList<Building> buildings = new ArrayList<>();
    private Team team;
    private int population;
    private int popularity;
    private int tax;
    private int fearRate;

    public Kingdom(KingdomType kingdomType, User user) {
        this.kingdomType = kingdomType;
        this.user = user;
        team = new Team(this);
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team.removeKingdom(this);
        this.team = team;
        this.team.addKingdom(this);
    }


    public User getUser() {
        return user;
    }

    public ArrayList<Human> getHumans() {
        return humans;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public int getPopulation() {
        return population;
    }

    public void addPopulation(int population) {
        if (getPopulationCapacity() < this.population + population || this.population < population) {
            //TODO
            return;
        }
        this.population += population;
    }

    public int getPopularity() {
        return popularity;
    }

    public void addPopularity(int popularity) {
        this.popularity += popularity;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getFearRate() {
        return fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public KingdomType getKingdomType() {
        return kingdomType;
    }

    public void addHuman(Human human) {
        humans.add(human);
    }

    public void removeHuman(Human human) {
        humans.remove(human);
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public void removeBuilding(Building building) {
        buildings.remove(building);
    }

    public int getResourceStorageCapacity(Resource resource) {
        int count = 0;
        for (Building building : buildings) {
            if (!(building instanceof EconomicBuilding))
                continue;
            for (StorageData storageData : ((EconomicBuilding) building).getDetail().getStorageData()) {
                if (storageData.resource().equals(resource))
                    count += ((EconomicBuilding) building).getAvailableCapacity();
            }
        }
        return count;
    }

    public int getResourceCount(Resource resource) {
        int count = 0;
        for (Building building : buildings) {
            if (!(building instanceof EconomicBuilding))
                continue;
            for (Pair<Resource, Integer> pair : ((EconomicBuilding) building).getStorage()) {
                if (pair.getA().equals(resource))
                    count += pair.getB();
            }
        }
        return count;
    }

    public boolean useResource(Resource resource, int count) {
        if (getResourceCount(resource) < count)
            return false;
        for (Building building : buildings) {
            if (count == 0)
                break;
            if (!(building instanceof EconomicBuilding))
                continue;
            for (Pair<Resource, Integer> pair : ((EconomicBuilding) building).getStorage()) {
                if (pair.getA().equals(resource)) {
                    int usage = Math.min(count, pair.getB());
                    ((EconomicBuilding) building).useResource(resource, usage);
                    count -= usage;
                }
            }
        }
        return true;
    }

    public boolean addRecourse(Resource resource, int count) {
        if (getResourceStorageCapacity(resource) < count)
            return false;
        for (Building building : buildings) {
            if (count == 0)
                break;
            if (!(building instanceof EconomicBuilding))
                continue;
            for (StorageData storageData : ((EconomicBuilding) building).getDetail().getStorageData()) {
                if (storageData.resource().equals(resource)){
                    int added = Math.max(count , ((EconomicBuilding) building).getAvailableCapacity()) ;
                    ((EconomicBuilding) building).addResource(resource , added);
                    count-=added;
                }
            }
        }
        return false;
    }


    public Integer getPopulationCapacity() {
        //TODO
        return null;
    }

    public void onTurnStart() {
        //TODO
    }

    public void update(double deltaTime) {
        //TODO
    }

    public void onTurnEnd() {
        //TODO
    }

    public ArrayList<EconomicBuilding> getEconomicBuildingsByType(BuildingType buildingType) {
        ArrayList<EconomicBuilding> buildingsArray = new ArrayList<>();
        for (Building building : buildings) {
            if (building.getBuildingType().equals(buildingType)) {
                buildingsArray.add((EconomicBuilding) building);
            }
        }
        return buildingsArray;
    }
}
