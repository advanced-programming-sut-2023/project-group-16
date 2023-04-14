package org.group16.Model;

import org.group16.Model.Buildings.Building;
import org.group16.Model.People.Human;

public class WarCommand {
    private final Cell destination;
    private final Human targetHuman;
    private final Building targetBuilding;
    private Status status;

    public WarCommand(Cell destination) {
        this.destination = destination;
        targetHuman = null;
        targetBuilding = null;
    }

    public WarCommand(Human targetHuman) {
        this.targetHuman = targetHuman;
        targetBuilding = null;
        destination = null;
    }

    public WarCommand(Building targetBuilding) {
        this.targetBuilding = targetBuilding;
        destination = null;
        targetHuman = null;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Cell getDestination() {
        //TODO
        return destination;
    }

    public Human getTargetHuman() {
        return targetHuman;
    }

    public Building getTargetBuilding() {
        return targetBuilding;
    }

    public enum Status {
        DEFENSIVE,
        STAND_STILL,
        OFFENSIVE
    }
}
