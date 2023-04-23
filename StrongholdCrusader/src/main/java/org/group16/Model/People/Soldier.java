package org.group16.Model.People;

import org.group16.Model.*;
import org.group16.Model.Siege.Siege;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Soldier extends Human {
    private final SoldierDetail soldierDetail;
    private WarCommand warCommand;
    private Siege siege;

    public Soldier(ArrayList<Cell> cells, Kingdom kingdom, int hp) {
        super(cells, kingdom, hp);
        soldierDetail = null;
    }

    public Soldier(ArrayList<Cell> cells, Kingdom kingdom, SoldierDetail detail) {
        super(cells, kingdom, detail.getHp());
        this.soldierDetail = detail;
    }


    public SoldierDetail getSoldierDetail() {
        return soldierDetail;
    }

    public WarCommand getWarCommand() {
        return warCommand;
    }

    public void setWarCommand(WarCommand warCommand) {
        this.warCommand = warCommand;
    }

    @Override
    public void onTurnStart() {
    }

    @Override
    public void update(double deltaTime) {
        Cell moveDestination = warCommand.getDestination();
        Human humanTarget = warCommand.getTargetHuman();
        WarCommand.Status status = warCommand.getStatus();

        ArrayList<Human> enemyPeopleInAttackRange = getEnemyPeopleInRange(soldierDetail.getAttackRange());
        ArrayList<Human> enemyPeopleInDefensiveRange = getEnemyPeopleInRange(soldierDetail.getDefensiveRange());
        ArrayList<Human> enemyPeopleInOffensiveRange = getEnemyPeopleInRange(soldierDetail.getOffensiveRange());

        // Defensive State
        if (status == WarCommand.Status.DEFENSIVE && enemyPeopleInDefensiveRange.size() > 0) {
            return;
        }
        // Move Command
        if (moveDestination != null) {
            if (getCells().get(0) != moveDestination)
                moveToward(moveDestination, deltaTime * soldierDetail.getSpeed(), 0, Scene.getCurrent().getRandom());
            return;
        }
        // Attack Command
        if (humanTarget != null) {
            //TODO
            return;
        }
        // Offensive State
        if (status == WarCommand.Status.OFFENSIVE && enemyPeopleInOffensiveRange.size() > 0) {
            return;
        }

    }

    public ArrayList<Human> getEnemyPeopleInRange(double range) {
        Cell currentCell = this.getCells().get(0);
        ArrayList<Human> people = new ArrayList<>();
        ArrayList<Cell> cells = Scene.getCurrent().getMap().getCellsInRange(currentCell, range);
        for (Cell cell : cells) {
            if (cell == null) continue;

            for (GameObject go : cell.getGameObjects())
                if (go instanceof Human human)
                    if (human.getKingdom().getTeam() != getKingdom().getTeam())
                        people.add(human);
        }
        return people;
    }

    public Human getTarget(ArrayList<Human> people) {
        Random random = Scene.getCurrent().getRandom();


    }

    public void attackTarget(Alive alive, int damage) {
        alive.dealDamage(damage);
    }

    @Override
    public void onTurnEnd() {
    }

    public Siege getSiege() {
        return siege;
    }

    public void setSiege(Siege siege) {
        this.siege = siege;
    }

}
