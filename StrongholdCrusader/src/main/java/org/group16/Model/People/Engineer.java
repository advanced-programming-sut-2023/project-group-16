package org.group16.Model.People;

import org.group16.Model.*;
import org.group16.Model.Siege.PortableShield;
import org.group16.Model.Siege.Siege;
import org.group16.Model.Siege.SiegeDetail;
import org.group16.Model.Siege.SiegeTower;

import java.util.ArrayList;

public class Engineer extends Soldier {
    private SiegeDetail creationCommand;

    public Engineer(ArrayList<Cell> cells, Kingdom kingdom, SoldierDetail detail) {
        super(cells, kingdom, detail);
    }

    @Override
    public void update(double currentTime) {
        double deltaTime = Time.deltaTime;
        if (getSiege() != null) {
            setCell(getSiege().getCell());
            return;
        }

        Cell destination = warCommand.getDestination();
        moveToward(destination, false, deltaTime * getSoldierDetail().getSpeed(), PATH_FINDING_RANDOMNESS, Scene.getCurrent().getRandom());
        if (creationCommand != null)
            if (getCell() == destination)
                tryCreate();
    }

    private void tryCreate() {
        ArrayList<Engineer> result = new ArrayList<>();
        for (GameObject gameObject : getCell().getGameObjects())
            if (gameObject instanceof Engineer engineer && engineer.creationCommand == creationCommand)
                result.add(engineer);
        if (result.size() >= creationCommand.getOperatorCount()) {
            Siege createdSiege = createSiege();
            for (int i = 0; i < creationCommand.getOperatorCount(); i++)
                createdSiege.addOperator(result.get(i));
        }
    }

    private Siege createSiege() {
        Siege result;
        switch (creationCommand) {
            case PORTABLE_SHIELD -> result = new PortableShield(getCells(), getKingdom(), creationCommand);
            case SIEGE_TOWER -> result = new SiegeTower(getCells(), getKingdom(), creationCommand);
            default -> result = new Siege(getCells(), getKingdom(), creationCommand);
        }
        return result;
    }

    public void setCreationCommand(SiegeDetail creationCommand) {
        this.creationCommand = creationCommand;
    }
}
