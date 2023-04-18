package org.group16.Model;

import org.group16.Model.Buildings.Building;
import org.group16.Model.Buildings.EconomicBuilding;
import org.group16.Model.People.Human;
import org.group16.Model.Resources.Resource;

import java.util.ArrayList;

public class Kingdom {
    private final KingdomType kingdomType;
    private final User user;
    private final ArrayList<Human> humans = new ArrayList<>();
    private final ArrayList<Building> buildings = new ArrayList<>();
    private int population;
    private int popularity;
    private int tax;
    private int fearRate;

    public Kingdom(KingdomType kingdomType, User user) {
        this.kingdomType = kingdomType;
        this.user = user;
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

    public ArrayList<EconomicBuilding> getResourceStorage(Resource resource) {
        //TODO
        return null;
    }

    public int getResourceCount(Resource resource) {
        //TODO
        return 0;
    }

    public void useResource(Resource resource, int count) {
        //TODO
    }

    public void addResource(Resource resource, int count) {
        //TODO
    }
    public Integer getPopulationCapacity(){
        //TODO
        return null ;
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
}
