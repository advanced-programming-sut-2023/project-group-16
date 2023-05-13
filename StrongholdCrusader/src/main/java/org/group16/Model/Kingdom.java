package org.group16.Model;

import org.group16.Lib.Pair;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.Buildings.EconomicBuilding;
import org.group16.Model.People.Human;
import org.group16.Model.People.Soldier;
import org.group16.Model.People.Worker;
import org.group16.Model.Resources.BasicResource;
import org.group16.Model.Resources.Food;
import org.group16.Model.Resources.Resource;
import org.group16.Model.Resources.StorageData;

import java.util.ArrayList;
import java.util.Iterator;

public class Kingdom {
    private final KingdomType kingdomType;
    private final User user;
    private  ArrayList<Human> humans = new ArrayList<>();
    private final ArrayList<Soldier> soldiers = new ArrayList<>();
    private final ArrayList<Building> buildings = new ArrayList<>();
    private Team team;
    //private int population;
    private int popularity = 0;
    private int tax = 0;
    private int fearRate = 0;
    private int foodRate = 0;
    private Soldier King ;

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
        int pop = 0;
        for (Human human : humans) {
            if (human.getHp() > 0) pop += 1;
        }
        return pop;
    }

    public void addPopulation(int population) {
        if (getPopulationCapacity().equals(getPopulation()) || availableHumans() >= 24) {
            return;
        }
        int added = Math.min(population, getPopulationCapacity() - getPopulation());
        while (added > 0) {
            new Human(getEconomicBuildingsByType(BuildingType.UNEMPLOYED_PLACE).get(0).getCells(), this, 100);
            added--;
        }
    }

    public int getPopularity() {
        return popularity;
    }

    public void addPopularity(int popularity) {
        this.popularity += popularity;
        this.popularity = Math.min(100, this.popularity);
        this.popularity = Math.max(0, this.popularity);
    }

    //TODO : function used is completely bullshit and may change
    public void populationGrowth() {
        int A = getPopularity() - 50;
        int added = A / 10;
        addPopulation(added);
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

    public ArrayList<Food> getFoodList() {
        ArrayList<Food> foods = new ArrayList<>();
        for (EconomicBuilding building : getEconomicBuildingsByType(BuildingType.GRANARY)) {
            if (!building.isActive()) continue;
            for (Pair<Resource, Integer> pair : building.getStorage()) {
                if (!foods.contains(pair.getA())) foods.add((Food) pair.getA());
            }
        }
        return foods;
    }

    public int getTaxEffectOnPopularity() {
        switch (tax) {
            case -3 -> {
                return 7;
            }
            case -2 -> {
                return 5;
            }
            case -1 -> {
                return 3;
            }
            case 0 -> {
                return 1;
            }
            case 1 -> {
                return -2;
            }
            case 2 -> {
                return -4;
            }
            case 3 -> {
                return -6;
            }
            case 4 -> {
                return -8;
            }
            case 5 -> {
                return -12;
            }
            case 6 -> {
                return -16;
            }
            case 7 -> {
                return -20;
            }
            case 8 -> {
                return -24;
            }
            default -> {
                return 0;
            }
        }
    }
    public double getFearRateEffectOnMorality(){
        return (double)(fearRate)*0.05 ;
    }

    public double getTaxGold() {
        switch (tax) {
            case -3 -> {
                return -1.0;
            }
            case -2 -> {
                return -0.8;
            }
            case -1 -> {
                return -0.6;
            }
            case 0 -> {
                return 0.0;
            }
            case 1 -> {
                return 0.6;
            }
            case 2 -> {
                return 0.8;
            }
            case 3 -> {
                return 1.0;
            }
            case 4 -> {
                return 1.2;
            }
            case 5 -> {
                return 1.4;
            }
            case 6 -> {
                return 1.6;
            }
            case 7 -> {
                return 1.8;
            }
            case 8 -> {
                return 2;
            }
            default -> {
                return 0;
            }
        }
    }

    public double getFoodForEachPerson() {
        switch (foodRate) {
            case -2 -> {
                return 0.0;
            }
            case -1 -> {
                return 0.5;
            }
            case 0 -> {
                return 1.0;
            }
            case 1 -> {
                return 1.5;
            }
            case 2 -> {
                return 2.0;
            }
            default -> {
                return -2.0;
            }
        }
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
        int cnt = 0;
        for (Human human : humans) {
            if (!(human instanceof Worker) && !(human instanceof Soldier) && human.getHp() > 0) {
                cnt++;
            }
        }
        return cnt;
    }

    public void useHuman(int cnt) {
        for (int ind = 0 ; ind < humans.size() ; ind++){
            Human human = humans.get(ind) ;
            if (cnt <= 0)
                break;
            if (!(human instanceof Worker) && !(human instanceof Soldier) && human.getHp() > 0){
                human.destroy();
                ind-- ;
                cnt-- ;
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
        if(resource == null)
            return 100000 ;
        int count = 0;
        for (Building building : buildings) {
            if (!(building instanceof EconomicBuilding)) continue;
            if (!((EconomicBuilding) building).isActive()) continue;
            for (StorageData storageData : ((EconomicBuilding) building).getDetail().getStorageData()) {
                if (storageData.resource().equals(resource))
                    count += ((EconomicBuilding) building).getAvailableCapacity();
            }
        }
        return count;
    }

    public int getResourceCount(Resource resource) {
        if (resource==null)
            return 100000 ;
        int count = 0;
        for (Building building : buildings) {
            if (!(building instanceof EconomicBuilding)) continue;
            if (!((EconomicBuilding) building).isActive()) continue;
            for (Pair<Resource, Integer> pair : ((EconomicBuilding) building).getStorage()) {
                if (pair.getA().equals(resource)) count += pair.getB();
            }
        }
        return count;
    }

    public boolean useResource(Resource resource, int count) {
        if (resource==null)
            return true ;
        if (getResourceCount(resource) < count) return false;
        for (Building building : buildings) {
            if (count == 0) break;
            if (!(building instanceof EconomicBuilding)) continue;
            if (!((EconomicBuilding) building).isActive()) continue;
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

    public boolean addResource(Resource resource, int count) {
        if (resource==null)
            return true ;
        if (getResourceStorageCapacity(resource) < count) return false;
        for (Building building : buildings) {
            if (count == 0) break;
            if (!(building instanceof EconomicBuilding)) continue;
            if (!((EconomicBuilding) building).isActive()) continue;
            for (StorageData storageData : ((EconomicBuilding) building).getDetail().getStorageData()) {
                if (storageData.resource().equals(resource)) {
                    int added = Math.min(count, ((EconomicBuilding) building).getAvailableCapacity());
                    ((EconomicBuilding) building).addResource(resource, added);
                    count -= added;
                }
            }
        }
        return true;
    }

    public Integer getPopulationCapacity() {
        int pop = 10;
        for (Building building : buildings) {
            if (!(building instanceof EconomicBuilding)) continue;
            if (building.getBuildingType().equals(BuildingType.HOVEL) || building.getBuildingType().equals(BuildingType.SMALL_STONE_GATEHOUSE))
                pop += 8;
            else if (building.getBuildingType().equals(BuildingType.LARGE_STONE_GATEHOUSE)) pop += 10;
        }
        return pop;
    }

    public int getGold() {
        return getResourceCount(BasicResource.GOLD);
    }

    public boolean addGold(int count) {
        if (count < 0)
            return useResource(BasicResource.GOLD , -count) ;
        else
            return addResource(BasicResource.GOLD, count);
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getFoodEffectOnPopularity() {
        return getFoodRate() - 1;
    }

    public void onTurnStart() {
        //TODO : on turn start
    }

    public void update(double deltaTime) {
        if (getKing().getHp() <= 0){
            return;
        }
        if (Time.isItTurned(deltaTime, Time.day)) {
            //tax
            addPopularity(getTaxEffectOnPopularity());
            addGold((int) Math.floor(getTaxGold() * getPopulation()));
            //food
            int availableFood = 0;
            for (Food food : getFoodList())
                availableFood += getResourceCount(food);
            int foodNeeded;
            while (true) {
                foodNeeded = (int) getFoodForEachPerson() * getPopulation();
                if (availableFood < foodNeeded)
                    setFearRate(getFoodEffectOnPopularity());
                else
                    break;
            }
            addPopularity(getFoodList().size() - 1);
            addPopularity(getFoodRate() * 4);
            for (Food food : getFoodList()) {
                if (foodNeeded == 0)
                    break;
                int eatenFood = Math.min(foodNeeded, getResourceCount(food));
                useResource(food, eatenFood);
            }
            //fearRate
            addPopularity(-getFearRate());
            //TODO : religion ?

            //population added by popularity
            populationGrowth();
        }
    }

    public void onTurnEnd() {
        //TODO : on turn end
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

    public Soldier getKing() {
        return King;
    }
}
