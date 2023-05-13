package org.group16.Model;

import org.group16.Model.Buildings.Building;
import org.group16.Model.People.Human;
import org.group16.Model.People.Soldier;
import org.group16.View.UnitMenu;

import java.util.ArrayList;

public class WarCommand {
    private final ArrayList<Soldier> unit;
    private final Cell destination;
    private final Cell patrolDestination;
    private final boolean attackCell;
    private final Human targetHuman;
    private final Building targetBuilding;
    private PatrollingStatus patrollingStatus;
    private Status status = Status.STAND_STILL;

    public WarCommand(ArrayList<Soldier> unit) {
        this.unit = new ArrayList<>(unit);
        destination = null;
        attackCell = false;
        targetHuman = null;
        targetBuilding = null;
        for (Soldier soldier : unit) soldier.setWarCommand(this);
        patrolDestination = null;
    }

    public WarCommand(ArrayList<Soldier> unit, Cell destination, boolean attackCell) {
        this.unit = new ArrayList<>(unit);
        this.destination = destination;
        this.attackCell = attackCell;
        targetHuman = null;
        targetBuilding = null;
        for (Soldier soldier : unit) soldier.setWarCommand(this);
        patrolDestination = null;
    }

    public WarCommand(ArrayList<Soldier> unit, Cell destination, Cell patrolDestination) {
        this.unit = new ArrayList<>(unit);
        this.destination = destination;
        this.patrolDestination = patrolDestination;
        patrollingStatus = PatrollingStatus.TO_END;
        attackCell = false;
        targetHuman = null;
        targetBuilding = null;
        for (Soldier soldier : unit) soldier.setWarCommand(this);
    }

    public WarCommand(ArrayList<Soldier> unit, Human targetHuman) {
        this.unit = new ArrayList<>(unit);
        this.targetHuman = targetHuman;
        attackCell = false;
        targetBuilding = null;
        destination = null;
        for (Soldier soldier : unit) soldier.setWarCommand(this);
        patrolDestination = null;
    }

    public WarCommand(ArrayList<Soldier> unit, Building targetBuilding) {
        this.unit = new ArrayList<>(unit);
        this.targetBuilding = targetBuilding;
        attackCell = false;
        destination = null;
        targetHuman = null;
        for (Soldier soldier : unit) soldier.setWarCommand(this);
        patrolDestination = null;
    }

    public Cell getPatrolDestination() {
        return patrolDestination;
    }

    public ArrayList<Soldier> getUnit() {
        return unit;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Cell getDestination() {
        return destination;
    }

    public boolean isAttackCell() {
        return attackCell;
    }

    public Human getTargetHuman() {
        if (targetHuman.isAlive())
            return targetHuman;
        return null;
    }

    public Building getTargetBuilding() {
        if (targetBuilding.isAlive())
            return targetBuilding;
        return null;
    }

    public Cell getCurrentDestination() {
        if (patrolDestination == null) return destination;

        if (patrollingStatus == PatrollingStatus.TO_END)
            return patrolDestination;
        else
            return destination;
    }

    public void onReachDestination() {
        if (patrollingStatus == PatrollingStatus.TO_START) patrollingStatus = PatrollingStatus.TO_END;
        else patrollingStatus = PatrollingStatus.TO_START;
    }

    public enum Status {
        DEFENSIVE,
        STAND_STILL,
        OFFENSIVE
    }

    public enum PatrollingStatus {
        TO_START,
        TO_END
    }
}
