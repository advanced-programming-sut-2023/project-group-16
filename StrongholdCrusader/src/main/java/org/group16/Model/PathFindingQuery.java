package org.group16.Model;

import java.util.*;

public class PathFindingQuery {
    private final Map map;
    private final Cell start, end;
    private final double randomness;
    private final Random random;


    private final HashMap<Cell, Double> distance = new HashMap<>();
    private final HashMap<Cell, Cell> parent = new HashMap<>();
    private final double DIAGONAL_COST_MULTIPLIER = 1.4;
    private final PriorityQueue<Cell> astar = new PriorityQueue<>(Comparator.comparingDouble(this::getHeuristicPath));

    public PathFindingQuery(Map map, Cell start, Cell end, double randomness, Random random) {
        this.map = map;
        this.start = start;
        this.end = end;
        this.randomness = randomness;
        this.random = random;
    }

    public double getCellDistance(Cell a, Cell b) {
        int dx = b.getX() - a.getX();
        int dy = b.getY() - a.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getHeuristicPath(Cell cell) {
        return distance.get(cell) + getCellDistance(cell, start);
    }

    public ArrayList<Cell> findShortestPath() {
        distance.put(end, 0.0);
        astar.add(end);
        parent.put(end, null);

        while (astar.size() > 0) {
            Cell cell = astar.poll();
            if (cell == start) break;
            for (int dx = -1; dx <= 1; dx++)
                for (int dy = -1; dy <= 1; dy++)
                    updateEdge(cell, map.getCellAt(cell.getX() + dx, cell.getY() + dy));
        }

        ArrayList<Cell> result = new ArrayList<>();
        result.add(start);
        while (true) {
            Cell currentCell = result.get(result.size() - 1);
            Cell nextCell = parent.get(currentCell);
            if (nextCell == null) break;
            result.add(nextCell);
        }
        return result;
    }

    private void updateEdge(Cell from, Cell to) {
        if (!to.traversable()) return;

        double cost = (from.getTraverseCost() + to.getTraverseCost()) / 2 + random.nextDouble() * randomness;
        if (from.getX() != to.getX() && from.getY() != to.getY()) cost *= DIAGONAL_COST_MULTIPLIER;
        double newDistance = distance.get(from) + cost;
        if (!distance.containsKey(to) || distance.get(to) > newDistance) {
            distance.put(to, newDistance);
            parent.put(to, from);
            astar.add(to);
        }
    }

}
