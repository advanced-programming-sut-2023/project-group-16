package org.group16.GameGraphics.CommandHandling;

import org.group16.Controller.UnitMenuController;
import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.People.Soldier;
import org.group16.Model.SerializedReference;
import org.group16.Model.User;

import java.util.ArrayList;

public class unitDisbandCommand extends UserCommand {
    private final ArrayList<SerializedReference<Soldier>> serializedReferenceArrayList = new ArrayList<>() ;
    public unitDisbandCommand(User user , ArrayList<Soldier>soldiers) {
        super(user);
        for (Soldier soldier :soldiers){
            serializedReferenceArrayList.add(new SerializedReference<Soldier>(soldier)) ;
        }
    }

    @Override
    public String execute(Game game, GameRenderer gameRenderer) {
        ArrayList<Soldier> soldiers = new ArrayList<>()  ;
        for (SerializedReference<Soldier> serializedReference : serializedReferenceArrayList){
            soldiers.add(serializedReference.getValue()) ;
        }
        String res = UnitMenuController.disbandUnit(game , soldiers) ;
        if (!res.equals("OK"))
            return res ;
        return success() ;
    }

    @Override
    public UserCommand getUndoCommand() {
        return null;
    }
}
