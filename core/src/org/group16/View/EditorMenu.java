package org.group16.View;

import org.group16.Controller.EditorMenuController;
import org.group16.Model.*;
import org.group16.View.Command.Command;
import org.group16.View.Command.CommandHandler;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class EditorMenu {
    private final Scanner scanner;
    private final User currentUser;
    private Map map;

    public EditorMenu(Scanner scanner, Map map, User currentUser) {
        this.scanner = scanner;
        this.map = map;
        this.currentUser = currentUser;
    }

    public void run() {
        while (true) {
            String input = scanner.nextLine();
            TreeMap<String, ArrayList<String>> map;
            if ((map = CommandHandler.matches(Command.SET_TEXTURE, input)) != null) setTexture(map);
            else if ((map = CommandHandler.matches(Command.SET_RECTANGULAR_TEXTURE, input)) != null)
                setRectangularTexture(map);
            else if ((map = CommandHandler.matches(Command.CLEAR, input)) != null) clear(map);
            else if ((map = CommandHandler.matches(Command.DROP_ROCK, input)) != null) dropRock(map);
            else if ((map = CommandHandler.matches(Command.DROP_TREE, input)) != null) dropTree(map);
            else if ((map = CommandHandler.matches(Command.CREATE_MAP, input)) != null) createMap(map);
            else if ((map = CommandHandler.matches(Command.SELECT_MAP, input)) != null) selectMap(map);
            else if (CommandHandler.matches(Command.SAVE_MAP, input) != null) saveMap();
            else if ((map = CommandHandler.matches(Command.DELETE_MAP, input)) != null) deleteMap(map);
            else if (CommandHandler.matches(Command.BACK, input) != null) back();
            else if (CommandHandler.matches(Command.EXIT, input) != null) {
                System.out.println("exit editor menu successfully");
                break;
            } else System.out.println("invalid command");
        }
    }

    private void back() {
        if (map == null) {
            System.out.println("no map is selected");
            return;
        }
        map = null;
        System.out.println("exit map successfully");
    }

    private void setTexture(TreeMap<String, ArrayList<String>> map) {
        int x, y;
        try {
            x = Integer.parseInt(map.get("x").get(0));
            y = Integer.parseInt(map.get("y").get(0));
        } catch (NumberFormatException exception) {
            System.out.println("invalid position format");
            return;
        }
        CellType cellType = CellType.getCellTypeByName(map.get("t").get(0));
        System.out.println(EditorMenuController.setTexture(this.map, x, y, cellType));
    }

    private void setRectangularTexture(TreeMap<String, ArrayList<String>> map) {
        int x1, y1, x2, y2;
        try {
            x1 = Integer.parseInt(map.get("x1").get(0));
            y1 = Integer.parseInt(map.get("y1").get(0));
            x2 = Integer.parseInt(map.get("x2").get(0));
            y2 = Integer.parseInt(map.get("y2").get(0));
        } catch (NumberFormatException exception) {
            System.out.println("invalid position format");
            return;
        }
        CellType cellType = CellType.getCellTypeByName(map.get("t").get(0));
        System.out.println(EditorMenuController.setTexture(this.map, x1, y1, x2, y2, cellType));
    }

    private void clear(TreeMap<String, ArrayList<String>> map) {
        int x, y;
        try {
            x = Integer.parseInt(map.get("x").get(0));
            y = Integer.parseInt(map.get("y").get(0));
        } catch (NumberFormatException exception) {
            System.out.println("invalid position format");
            return;
        }
        System.out.println(EditorMenuController.clear(this.map, x, y));
    }

    private void dropRock(TreeMap<String, ArrayList<String>> map) {
        int x, y;
        try {
            x = Integer.parseInt(map.get("x").get(0));
            y = Integer.parseInt(map.get("y").get(0));
        } catch (NumberFormatException exception) {
            System.out.println("invalid position format");
            return;
        }
        String direction = map.get("d").get(0);
        System.out.println(EditorMenuController.dropRock(this.map, x, y, direction));
    }

    private void dropTree(TreeMap<String, ArrayList<String>> map) {
        int x, y;
        try {
            x = Integer.parseInt(map.get("x").get(0));
            y = Integer.parseInt(map.get("y").get(0));
        } catch (NumberFormatException exception) {
            System.out.println("invalid position format");
            return;
        }
        TreeType treeType = TreeType.getTreeTypeByName(map.get("t").get(0));
        System.out.println(EditorMenuController.dropTree(this.map, x, y, treeType));
    }

    private void createMap(TreeMap<String, ArrayList<String>> map) {
        int h, w;
        try {
            h = Integer.parseInt(map.get("h").get(0));
            w = Integer.parseInt(map.get("w").get(0));
        } catch (NumberFormatException exception) {
            System.out.println("invalid map size format");
            return;
        }
        String name = map.get("n").get(0);
        System.out.println(EditorMenuController.createMap(name, h, w, this));
    }

    private void selectMap(TreeMap<String, ArrayList<String>> map) {
        String name = map.get("n").get(0);
        System.out.println(EditorMenuController.selectMap(name, this));
    }

    private void saveMap() {
        System.out.println(EditorMenuController.saveMap(this));
    }

    private void deleteMap(TreeMap<String, ArrayList<String>> map) {
        System.out.println(EditorMenuController.deleteMap(map.get("n").get(0), this));
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
