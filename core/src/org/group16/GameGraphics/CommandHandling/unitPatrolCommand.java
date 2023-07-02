package org.group16.GameGraphics.CommandHandling;

import org.group16.Controller.UnitMenuController;
import org.group16.GameGraphics.GameRenderer;
import org.group16.Model.Game;
import org.group16.Model.People.Soldier;
import org.group16.Model.SerializedReference;
import org.group16.Model.User;

import java.util.ArrayList;

public class unitPatrolCommand extends UserCommand{
    private final ArrayList<SerializedReference<Soldier>> serializedReferenceArrayList = new ArrayList<>() ;
    private final int x1 , x2 , y1 , y2 ;
    public unitPatrolCommand(User user ,  ArrayList<Soldier> soldiers , int x1 , int y1 , int x2 , int y2) {
        super(user);
        for (Soldier soldier :soldiers){
            serializedReferenceArrayList.add(new SerializedReference<Soldier>(soldier)) ;
        }
        this.x1 = x1 ;
        this.x2 = x2 ;
        this.y1 = y1 ;
        this.y2 = y2 ;
    }

    @Override
    public String execute(Game game, GameRenderer gameRenderer) {
        ArrayList<Soldier> soldiers = new ArrayList<>()  ;
        for (SerializedReference<Soldier> serializedReference : serializedReferenceArrayList){
            soldiers.add(serializedReference.getValue()) ;
        }
        String res = UnitMenuController.patrolUnit(game ,soldiers , x1 , y1 , x2 , y2) ;
        if(!res.equals("OK")){
            return res ;
        }
        return success() ;
    }

    @Override
    public UserCommand getUndoCommand() {
        return null;
    }
}
