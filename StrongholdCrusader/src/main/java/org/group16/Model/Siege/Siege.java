package org.group16.Model.Siege;

import org.group16.Model.Direction;
import org.group16.Model.People.Engineer;
import org.group16.Model.People.Soldier;

import java.util.ArrayList;

public class Siege extends Soldier {
    private final SiegeDetail siegeDetail;
    private Direction direction;
    private ArrayList<Engineer> operators = new ArrayList<>();

    public Siege(SiegeDetail detail) {
        super(detail.getHp());
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
    public void onTurnStart() {
        super.onTurnStart();
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void onTurnEnd() {
        super.onTurnEnd();
    }
}
