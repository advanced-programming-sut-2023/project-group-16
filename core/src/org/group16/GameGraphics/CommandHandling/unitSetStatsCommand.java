package org.group16.GameGraphics.CommandHandling;

import org.group16.Controller.UnitMenuController;
import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.People.Soldier;
import org.group16.Model.SerializedReference;
import org.group16.Model.User;
import org.group16.Model.WarCommand;

import java.util.ArrayList;

public class unitSetStatsCommand extends UserCommand {

    private final ArrayList<SerializedReference<Soldier>> serializedReferenceArrayList = new ArrayList<>() ;
    private final WarCommand.Status warCommandStats ;
    public unitSetStatsCommand(User user , ArrayList<Soldier>soldiers , WarCommand.Status warCommandStats ) {
        super(user);
        for (Soldier soldier :soldiers){
            serializedReferenceArrayList.add(new SerializedReference<Soldier>(soldier)) ;
        }
        this.warCommandStats = warCommandStats ;
    }

    @Override
    public String execute(Game game, GameRenderer gameRenderer) {
        ArrayList<Soldier> soldiers = new ArrayList<>()  ;
        for (SerializedReference<Soldier> serializedReference : serializedReferenceArrayList){
            soldiers.add(serializedReference.getValue()) ;
        }
        String res = UnitMenuController.setStats(game , soldiers , warCommandStats) ;
        if (!res.equals("OK"))
            return res ;
        return success() ;
    }

    @Override
    public UserCommand getUndoCommand() {
        return null;
    }
}
