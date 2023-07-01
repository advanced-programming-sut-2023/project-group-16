package org.group16.Model;

import com.google.gson.Gson;
import org.group16.Vec2;

import java.io.*;
import java.util.*;

public class Map implements Serializable {

    private final int width, height;
    private final String name;
    private final Cell[][] cells;
    private int currentX, currentY;

    public Map(String name, int mapWidth, int mapHeight) {
        this.name = name;
        this.width = mapWidth;
        this.height = mapHeight;
        cells = new Cell[mapWidth][mapHeight];
        for (int i = 0; i < mapWidth; i++)
            for (int j = 0; j < mapHeight; j++)
                cells[i][j] = new Cell(i, j, CellType.NORMAL);
        this.currentX = mapWidth / 2;
        this.currentY = mapHeight / 2;
    }

    public Map(Map map) {
        this(map.name, map.width, map.height);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                cells[i][j] = new Cell(i, j, map.cells[i][j]);
    }

    public static double getCellDistance(Cell a, Cell b) {
        return Vec2.sub(a.getPosition(), b.getPosition()).length();
    }

    public static double getDistanceFromLine(Cell a, Cell b, Cell x) {
        Vec2 ab = Vec2.sub(b.getPosition(), a.getPosition());
        Vec2 abNorm = ab.normal().normalize();
        double len = ab.length();
        Vec2 ax = Vec2.sub(x.getPosition(), a.getPosition());
        double dot = Vec2.dot(ab, ax);
        if (dot < 0) return getCellDistance(x, a);
        if (dot > len * len) return getCellDistance(x, b);
        return Math.abs(Vec2.dot(abNorm, ax));
    }

    public static Map getMapByName(String name) {
        Gson gson = new Gson();
        String filePath = new File("").getAbsolutePath().concat("/Data/Maps/").concat(name).
                concat(".json");
        FileReader fileReader;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            return null;
        }
        Map map = gson.fromJson(fileReader, Map.class);
        try {
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public static ArrayList<Map> getAllMaps() {
        Gson gson = new Gson();
        String folderPath = new File("").getAbsolutePath().concat("/Data/Maps");
        File[] files = new File(folderPath).listFiles();
        ArrayList<Map> maps = new ArrayList<>();

        if (files != null) for (File file : files) {
            if (file.isDirectory()) continue;
            FileReader fileReader;
            try {
                fileReader = new FileReader(file);
                Map map = null;
                map = gson.fromJson(fileReader, Map.class);
                fileReader.close();
                if (map != null)
                    maps.add(map);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return maps;
    }

    public static void saveMap(Map map) {
        Gson gson = new Gson();
        String filePath = new File("").getAbsolutePath().concat("/Data/Maps/");
        FileWriter fileWriter;
        try {
            File file = new File(filePath);
            file.mkdirs();
            fileWriter = new FileWriter(filePath.concat(map.name).concat(".json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gson.toJson(map, fileWriter);
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteMap(String name) {
        String filePath = new File("").getAbsolutePath().concat("/Data/Maps/").concat(name).concat(".json");
        File file = new File(filePath);
        file.delete();
    }

    public static ArrayList<String> getAllMapNames() {
        Gson gson = new Gson();
        String folderPath = new File("").getAbsolutePath().concat("/Data/Maps");
        File[] files = new File(folderPath).listFiles();
        ArrayList<String> names = new ArrayList<>();

        if (files != null) for (File file : files) {
            if (file.isDirectory()) continue;
            FileReader fileReader;
            try {
                fileReader = new FileReader(file);
                Map map = null;
                map = gson.fromJson(fileReader, Map.class);
                fileReader.close();
                if (map != null)
                    names.add(file.getName().split("\\.")[0]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return names;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getCellAt(int i, int j) {
        if (i < 0 || i >= width) return null;
        if (j < 0 || j >= height) return null;
        return cells[i][j];
    }

    public ArrayList<Cell> getCellsInRange(Cell origin, double range) {
        ArrayList<Cell> result = new ArrayList<>();
        int dxBound = (int) range;
        for (int dx = -dxBound; dx <= dxBound; dx++) {
            int dyBound = (int) Math.sqrt(range * range - dx * dx);
            for (int dy = -dyBound; dy <= dyBound; dy++) {
                Cell cell = getCellAt(origin.getX() + dx, origin.getY() + dy);
                if (cell != null) result.add(cell);
            }
        }
        return result;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public String getName() {
        return name;
    }
}
