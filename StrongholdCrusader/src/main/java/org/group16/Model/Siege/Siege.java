package org.group16.Model.Siege;

import org.group16.Model.*;
import org.group16.Model.Buildings.Building;
import org.group16.Model.People.Engineer;
import org.group16.Model.People.Human;
import org.group16.Model.People.Soldier;

import java.util.ArrayList;
import java.util.Random;

public class Siege extends Soldier {
    private final SiegeDetail siegeDetail;
    private Direction direction;
    private ArrayList<Engineer> operators = new ArrayList<>();

    public Siege(ArrayList<Cell> cells, Kingdom kingdom, SiegeDetail detail) {
        super(cells, kingdom, detail.getHp());
        this.siegeDetail = detail;
    }

    public boolean isActive() {
        return operators.size() >= siegeDetail.getOperatorCount();
    }


    public boolean canMove() {
        return isActive() && siegeDetail.getCanMove();
    }

    public SiegeDetail getSiegeDetail() {
        return siegeDetail;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void addOperator(Engineer engineer) {
        engineer.setSiege(this);
        operators.add(engineer);
    }

    public ArrayList<Engineer> getOperators() {
        return operators;
    }

    @Override
    public void destroy() {
        for (var engineer : operators) engineer.destroy();
        super.destroy();
    }


    @Override
    public void update(double currentTime) {
        Cell moveDestination = warCommand.getDestination();
        Human humanTarget = warCommand.getTargetHuman();
        Building buildingTarget = warCommand.getTargetBuilding();

        // Fight if possible
        if (humanTarget != null && Map.getCellDistance(humanTarget.getCell(), getCell()) <= siegeDetail.getAttackRange()) {
            attackTarget(humanTarget, (int) (siegeDetail.getDamage() * currentTime));
            return;
        }
        if (buildingTarget != null && Map.getCellDistance(buildingTarget.getCell(), getCell()) <= siegeDetail.getAttackRange()) {
            attackTarget(buildingTarget, (int) (siegeDetail.getDamage() * currentTime));
            return;
        }

        // Move Command
        if (moveDestination != null) {
            if (getCell() != moveDestination)
                moveToward(moveDestination, currentTime * siegeDetail.getSpeed(), PATH_FINDING_RANDOMNESS, Scene.getCurrent().getRandom());
            return;
        }
    }

    @Override
    protected void moveToward(Cell destination, double distance, double randomness, Random random) {
        Cell from = getCell();
        super.moveToward(destination, distance, randomness, random);
        Cell to = getCell();
        if (from != to) {
            int dx = to.getX() - from.getX();
            int dy = to.getY() - from.getY();
            if (dx < 0) direction = Direction.LEFT;
            if (dy < 0) direction = Direction.DOWN;
            if (dx > 0) direction = Direction.RIGHT;
            if (dy > 0) direction = Direction.UP;
        }
    }
}
