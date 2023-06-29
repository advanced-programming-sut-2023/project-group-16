package org.group16.Model.People;

import org.group16.GameGraphics.HumanRenderer;
import org.group16.GameGraphics.Renderer;
import org.group16.Model.*;
import org.group16.Model.Buildings.Building;
import org.group16.Model.Siege.Siege;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Soldier extends Human {
    public static final double PATH_FINDING_RANDOMNESS = 2;
    public static final double TARGET_SELECTION_RANDOMNESS = 1.1;
    private final SoldierDetail soldierDetail;
    protected WarCommand warCommand;
    private Siege siege;
    private Human currentTarget;

    public Soldier(ArrayList<Cell> cells, Kingdom kingdom, int hp) {
        super(cells, kingdom, hp);
        soldierDetail = null;
        new WarCommand(new ArrayList<>(List.of(this)));
    }

    public Soldier(ArrayList<Cell> cells, Kingdom kingdom, SoldierDetail detail) {
        super(cells, kingdom, detail.getHp());
        this.soldierDetail = detail;
        new WarCommand(new ArrayList<>(List.of(this)));
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
    public void update(double currentTime) {
        super.update(currentTime);
        double deltaTime = Time.deltaTime;

        if (currentTarget != null && !currentTarget.isAlive()) currentTarget = null;

        Cell moveDestination = warCommand.getDestination();
        Cell patrolDestination = warCommand.getPatrolDestination();
        boolean attackCell = warCommand.isAttackCell();
        Human humanTarget = warCommand.getTargetHuman();
        Building buildingTarget = warCommand.getTargetBuilding();
        WarCommand.Status status = warCommand.getStatus();
        double maxFollowRange;
        switch (status) {
            case STAND_STILL -> maxFollowRange = soldierDetail.getAttackRange();
            case DEFENSIVE -> maxFollowRange = soldierDetail.getDefensiveRange();
            case OFFENSIVE -> maxFollowRange = soldierDetail.getOffensiveRange();
            default -> maxFollowRange = 0;
        }

        // Fight if possible
        if (humanTarget != null && Map.getCellDistance(humanTarget.getCell(), getCell()) <= soldierDetail.getAttackRange()) {
            followAndFight(humanTarget, deltaTime);
            return;
        }
        if (buildingTarget != null && Map.getCellDistance(buildingTarget.getCell(), getCell()) <= soldierDetail.getAttackRange()) {
            followAndFight(buildingTarget, deltaTime);
            return;
        }

        ArrayList<Human> possibleEnemyTargets = getEnemyPeopleInRange(getCell(), maxFollowRange);

        if (currentTarget == null && possibleEnemyTargets.size() > 0)
            currentTarget = getTarget(possibleEnemyTargets, TARGET_SELECTION_RANDOMNESS);

        if (currentTarget != null) {
            double distance = Map.getCellDistance(currentTarget.getCell(), getCell());
            if (moveDestination != null)
                distance = Map.getCellDistance(moveDestination, currentTarget.getCell());
            if (patrolDestination != null)
                distance = Map.getDistanceFromLine(moveDestination, patrolDestination, currentTarget.getCell());

            if (distance <= maxFollowRange) {
                followAndFight(currentTarget, deltaTime);
                return;
            }
        }


        // Move Command
        if (moveDestination != null) {
            possibleEnemyTargets = getEnemyPeopleInRange(moveDestination, 0);
            if (attackCell && possibleEnemyTargets.size() > 0) {
                currentTarget = getTarget(possibleEnemyTargets, TARGET_SELECTION_RANDOMNESS);
                followAndFight(currentTarget, deltaTime);
            } else {
                if (getCell() != warCommand.getCurrentDestination())
                    moveToward(warCommand.getCurrentDestination(), soldierDetail.isCanClimbLadder(), soldierDetail.isCanClimbWalls(), deltaTime * soldierDetail.getSpeed(), PATH_FINDING_RANDOMNESS, Scene.getCurrent().getRandom());
                else
                    warCommand.onReachDestination();
            }
            return;
        }
        // Attack Command
        if (humanTarget != null) {
            followAndFight(humanTarget, deltaTime);
            return;
        }
        if (buildingTarget != null) {
            followAndFight(buildingTarget, deltaTime);
            return;
        }
    }

    private <T extends GameObject & Alive> void followAndFight(T target, double deltaTime) {
        double distance = Map.getCellDistance(target.getCell(), getCell());
        double damage = soldierDetail.getDamage() * (1 + getKingdom().getFearRateEffectOnMorality());
        if (distance <= soldierDetail.getAttackRange())
            attackTarget(target, (int) (damage * deltaTime));
        else
            moveToward(target.getCell(), soldierDetail.isCanClimbLadder(), soldierDetail.isCanClimbWalls(), soldierDetail.getSpeed() * deltaTime, PATH_FINDING_RANDOMNESS, Scene.getCurrent().getRandom());
    }


    public ArrayList<Human> getEnemyPeopleInRange(Cell origin, double range) {
        ArrayList<Human> people = new ArrayList<>();
        ArrayList<Cell> cells = Scene.getCurrent().getMap().getCellsInRange(origin, range);
        for (Cell cell : cells) {
            if (cell == null) continue;

            for (GameObject go : cell.getGameObjects())
                if (go instanceof Human human)
                    if (human.getKingdom().getTeam() != getKingdom().getTeam())
                        people.add(human);
        }
        return people;
    }

    public Human getTarget(ArrayList<Human> people, double selectionTolerance) {
        Random random = Scene.getCurrent().getRandom();

        double minDist = 1e10;
        Cell currentCell = getCell();
        for (Human human : people) {
            Cell humanCell = human.getCell();
            double distance = Map.getCellDistance(currentCell, humanCell);
            minDist = Double.min(minDist, distance);
        }
        minDist *= selectionTolerance;
        int acceptCnt = 0;
        for (Human human : people) {
            Cell humanCell = human.getCell();
            double distance = Map.getCellDistance(currentCell, humanCell);
            if (distance <= minDist)
                acceptCnt++;
        }
        acceptCnt = Scene.getCurrent().getRandom().nextInt(acceptCnt) + 1;
        for (Human human : people) {
            Cell humanCell = human.getCell();
            double distance = Map.getCellDistance(currentCell, humanCell);
            if (distance <= minDist)
                acceptCnt--;
            if (acceptCnt == 0) return human;
        }
        return people.get(0);
    }

    public void attackTarget(Alive target, int damage) {
        fightingTarget = (GameObject) target;
        target.dealDamage(damage);
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

    @Override
    public Renderer createRenderer() {
        HumanRenderer renderer = new HumanRenderer(soldierDetail.getGraphics());
        renderer.setLocalPosition(calculateWorldPosition());
        return renderer;
    }
}
