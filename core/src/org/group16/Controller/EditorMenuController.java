package org.group16.Controller;

import org.group16.Model.Cell;
import org.group16.Model.CellType;
import org.group16.Model.Map;
import org.group16.Model.TreeType;
import org.group16.ViewTerminal.EditorMenu;

import java.util.ArrayList;

public class EditorMenuController {

    public static String setTexture(Map map, int x, int y, CellType cellType) {
        if (map == null) return "no map is selected";
        Cell cell = map.getCellAt(x, y);
        if (cell == null) return "incorrect position";
        if (cellType == null) return "invalid cell type";
        if (cell.getBuilding() != null) return "already there is a building";
        cell.setCellType(cellType);
        return "texture set successfully";
    }

    public static String setTexture(Map map, int x1, int y1, int x2, int y2, CellType cellType) {
        if (map == null) return "no map is selected";
        ArrayList<Cell> cells = new ArrayList<>();
        for (int x = x1; x <= x2; x++)
            for (int y = y1; y <= y2; y++)
                cells.add(map.getCellAt(x, y));
        for (Cell cell : cells)
            if (cell == null) return "incorrect boundaries";
        if (cellType == null) return "invalid cell type";
        for (Cell cell : cells)
            if (cell.getBuilding() != null) return "already there is a building";
        for (Cell cell : cells)
            cell.setCellType(cellType);
        return "texture set successfully";
    }

    public static String clear(Map map, int x, int y) {
        if (map == null) return "no map is selected";
        Cell cell = map.getCellAt(x, y);
        if (cell == null) return "incorrect position";
        cell.getGameObjects().clear();
        cell.setCellType(CellType.NORMAL);
        cell.setTreeType(null);
        return "cell cleared successfully";
    }

    public static String dropRock(Map map, int x, int y, String direction) {
        if (map == null) return "no map is selected";
        Cell cell = map.getCellAt(x, y);
        if (cell == null) return "incorrect position";
        if (cell.getBuilding() != null) return "already there is a building";
        if (!(direction.equals("n") || direction.equals("e") || direction.equals("w") ||
                direction.equals("s") || direction.equals("r"))) return "invalid direction";
        cell.setCellType(CellType.ROCK);
        return "rock dropped successfully";
    }

    public static String dropTree(Map map, int x, int y, TreeType treeType) {
        if (map == null) return "no map is selected";
        Cell cell = map.getCellAt(x, y);
        if (cell == null) return "incorrect position";
        if (treeType == null) return "invalid tree type";
        if (cell.getBuilding() != null) return "already there is a building";
        cell.setTreeType(treeType);
        return "tree dropped successfully";
    }

    public static String createMap(String name, int height, int width, EditorMenu editorMenu) {
        if (editorMenu.getMap() != null) return "invalid command";
        if (height <= 0 || width <= 0) return "width and height should be positive";
        if (Map.getMapByName(name) != null) return "map with this name already exist";
        Map map = new Map(name, width, height);
        Map.saveMap(map);
        return "map created successfully";
    }

    public static String selectMap(String name, EditorMenu editorMenu) {
        if (editorMenu.getMap() != null) return "invalid command";
        Map map = Map.getMapByName(name);
        if (map == null) return "no map with this name exist";
        editorMenu.setMap(map);
        return "map selected successfully";
    }

    public static String saveMap(EditorMenu editorMenu) {
        Map map = editorMenu.getMap();
        if (map == null) return "no map is selected";
        Map.saveMap(map);
        return "map saved successfully";
    }

    public static String deleteMap(String name, EditorMenu editorMenu) {
        if (editorMenu.getMap() != null) return "invalid command";
        if (Map.getMapByName(name) == null) return "no map with this name exist";
        Map.deleteMap(name);
        return "map deleted successfully";
    }
}
