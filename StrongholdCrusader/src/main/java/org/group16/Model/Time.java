package org.group16.Model;

public class Time {
    static public final double deltaTime = 0.05 ;
    static public final double day = 1 ;
    static public boolean isItTurned(double time , double timeUnit){
        double zPart = Math.floor(time / timeUnit) ;
        double rPart = time - timeUnit*zPart ;
        return rPart < deltaTime;
    }
}
