package org.group16.Model;

import org.group16.Lib.Pair;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.Buildings.EconomicBuilding;
import org.group16.Model.Buildings.EconomicBuildingDetail;
import org.group16.Model.People.Human;
import org.group16.Model.People.Soldier;
import org.group16.Model.People.Worker;
import org.group16.Model.Resources.Resource;
import org.group16.Model.Resources.StorageData;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Kingdom {
    private final KingdomType kingdomType;
    private final User user;
    private final ArrayList<Human> humans = new ArrayList<>();
    private final ArrayList<Soldier> soldiers = new ArrayList<>();
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

    public int availableHumans() {
        int cnt = 0 ;
        for (Human human : humans) {
            if (!(human instanceof Worker) && !(human instanceof Soldier) && human.getHp() > 0) {
                cnt++ ;
            }
        }
        return cnt;
    }
    public void useHuman(int cnt){
        for (Human human : humans) {
            if (cnt==0)
                break ;
            if (!(human instanceof Worker) && !(human instanceof Soldier) && human.getHp() > 0) {
                removeHuman(human);
                cnt--;
            }
        }
    }
    public void addSoldier(Soldier soldier) {
        soldiers.add(soldier);
    }

    public void removeSoldier(Soldier soldier) {
        soldiers.remove(soldier);
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
            if (!((EconomicBuilding) building).isActive())
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
            if (!((EconomicBuilding) building).isActive())
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
            if (!((EconomicBuilding) building).isActive())
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
            if (!((EconomicBuilding) building).isActive())
                continue;
            for (StorageData storageData : ((EconomicBuilding) building).getDetail().getStorageData()) {
                if (storageData.resource().equals(resource)) {
                    int added = Math.max(count, ((EconomicBuilding) building).getAvailableCapacity());
                    ((EconomicBuilding) building).addResource(resource, added);
                    count -= added;
                }
            }
        }
        return false;
    }


    public Integer getPopulationCapacity() {
        int pop = 0;
        for (Building building : buildings) {
            if (!(building instanceof EconomicBuilding))
                continue;
            if (building.getBuildingType().equals(BuildingType.HOVEL) ||
                    building.getBuildingType().equals(BuildingType.SMALL_STONE_GATEHOUSE)
            )
                pop += 8;
            else if (building.getBuildingType().equals(BuildingType.LARGE_STONE_GATEHOUSE))
                pop += 10;
        }
        return pop;
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
