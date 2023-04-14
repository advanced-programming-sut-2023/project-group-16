package org.group16.View;

import org.group16.Model.Buildings.Building;
import org.group16.Model.Game;
import org.group16.Model.People.SoldierType;
import org.group16.Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenu {
    private final Scanner scanner;
    private final Game game;
    private final Building building;
    private final User currentUser;

    public BuildingMenu(Scanner scanner, Game game, Building building, User currentUser) {
        this.scanner = scanner;
        this.game = game;
        this.building = building;
        this.currentUser = currentUser;
    }

    public void run() {

    }

    private void createUnit(Matcher matcher) {

    }

    private void repair(Matcher matcher) {

    }
}
