package org.group16.View;

import org.group16.Model.Game;
import org.group16.Model.People.Soldier;
import org.group16.Model.User;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class UnitMenu {
    private final Scanner scanner;
    private final Game game;
    private final ArrayList<Soldier> unit;
    private final User currentUser;

    public UnitMenu(Scanner scanner, Game game, ArrayList<Soldier> unit, User currentUser) {
        this.scanner = scanner;
        this.game = game;
        this.unit = unit;
        this.currentUser = currentUser;
    }

    public void run() {

    }//TODO

    private void patrolUnit(Matcher matcher) {

    }//TODO

    private void setStats(Matcher matcher) {

    }//TODO

    private void attackEnemy(Matcher matcher) {

    }//TODO

    private void pourOil(Matcher matcher) {

    }//TODO

    private void digTunnel(Matcher matcher) {

    }//TODO

    private void buildEquipment(Matcher matcher) {

    }//TODO

    private void disbandUnit(Matcher matcher) {

    }//TODO
}
