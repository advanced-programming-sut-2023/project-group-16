package org.group16.Model;

import org.group16.Vec2;

import java.util.*;

public class PathFindingQuery {
    public static final double DIAGONAL_COST_MULTIPLIER = 1.4;
    private final Map map;
    private final Cell start, end;
    private final Team pathfinderTeam;
    private final boolean canUseLadder;
    private final boolean canClimbWalls;
    private final double randomness;
    private final Random random;
    private final HashMap<Cell, Double> distance = new HashMap<>();
    private final HashMap<Cell, Cell> parent = new HashMap<>();
    private final PriorityQueue<Cell> astar = new PriorityQueue<>(Comparator.comparingDouble(this::getHeuristicPath));

    public PathFindingQuery(Map map, Cell start, Cell end, Team pathfinderTeam, boolean canUseLadder, boolean canClimbWalls, double randomness, Random random) {
        this.map = map;
        this.start = start;
        this.end = end;
        this.pathfinderTeam = pathfinderTeam;
        this.canUseLadder = canUseLadder;
        this.canClimbWalls = canClimbWalls;
        this.randomness = randomness;
        this.random = random;
    }

    public double getCellDistance(Cell a, Cell b) {
        return Vec2.sub(a.getPosition(), b.getPosition()).length();
    }

    public double getHeuristicPath(Cell cell) {
        return distance.get(cell) + getCellDistance(cell, start);
    }

    public void findShortestPath() {
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
    }

    public ArrayList<Cell> getShortestPath() {
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
        if (to == null) return;
        boolean canEnterCell = to.isTraversable();
        canEnterCell |= to.getHasLadder() && canUseLadder;
        if (to.getBuilding() != null) {
            canEnterCell |= to.getBuilding().getKingdom().getTeam() == pathfinderTeam;
            canEnterCell |= canClimbWalls;
        }
        if (!canEnterCell) return;

        double cost = (from.getTraverseCost() + to.getTraverseCost()) / 2 + random.nextDouble() * randomness;
        if (from.getX() != to.getX() && from.getY() != to.getY()) cost *= DIAGONAL_COST_MULTIPLIER;
        double newDistance = distance.get(from) + cost;
        if (!distance.containsKey(to) || distance.get(to) > newDistance) {
            distance.put(to, newDistance);
            parent.put(to, from);
            astar.add(to);
        }
    }

    public Cell getNextCell(Cell cell) {
        return parent.get(cell);
    }

    public double getPathLength() {
        return distance.get(start);
    }
}
