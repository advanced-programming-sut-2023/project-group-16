package org.group16.Model;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PathFindingQueryTest {
    private Map map;
    private Random random;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        map = new Map(10, 20);
        random = new Random(100);
    }

    @org.junit.jupiter.api.Test
    void getCellDistance() {
        PathFindingQuery pathFindingQuery = new PathFindingQuery(map, null, null, 0, random);
        int ax = random.nextInt(10), ay = random.nextInt(20);
        int bx = random.nextInt(10), by = random.nextInt(20);
        Cell cell1 = map.getCellAt(ax, ay);
        Cell cell2 = map.getCellAt(bx, by);
        int dx = bx - ax;
        int dy = by - ay;
        double distance = pathFindingQuery.getCellDistance(cell1, cell2);
        assertEquals(Math.sqrt(dx * dx + dy * dy), distance);
    }

    @org.junit.jupiter.api.Test
    void getHeuristicPath() {

    }

    @org.junit.jupiter.api.Test
    void findShortestPath() {
    }

    @org.junit.jupiter.api.Test
    void getNextCell() {
    }

    @Test
    void getShortestPath() {
    }
}