package org.group16.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PathFindingQueryTest {
    private Map map;
    private Random random;

    @BeforeEach
    void setUp() {
        map = new Map("test", 10, 20);
        random = new Random(100);
    }

    @Test
    void testMap() {
        assertEquals(10, map.getWidth());
        assertEquals(20, map.getHeight());
        Cell cell = map.getCellAt(9, 19);
        assertEquals(9, cell.getX());
        assertEquals(19, cell.getY());
        cell = map.getCellAt(10, 1);
        assertNull(cell);
    }

    @Test
    void getCellDistance() {
        PathFindingQuery pathFindingQuery = new PathFindingQuery(map, null, null, null, true, 0, random);
        int ax = random.nextInt(10), ay = random.nextInt(20);
        int bx = random.nextInt(10), by = random.nextInt(20);
        Cell cell1 = map.getCellAt(ax, ay);
        Cell cell2 = map.getCellAt(bx, by);
        int dx = bx - ax;
        int dy = by - ay;
        double distance = pathFindingQuery.getCellDistance(cell1, cell2);
        assertEquals(Math.sqrt(dx * dx + dy * dy), distance);
    }

    @Test
    void findShortestPath() {
        Cell from = map.getCellAt(0, 0);
        Cell to = map.getCellAt(9, 19);

        assertNotNull(from);
        assertNotNull(to);

        PathFindingQuery pathFindingQuery = new PathFindingQuery(map, from, to, null, true, 0, random);
        pathFindingQuery.findShortestPath();
        assertNotNull(pathFindingQuery.getNextCell(from));
        ArrayList<Cell> cells = pathFindingQuery.getShortestPath();
        for (Cell cell : cells) {
            System.out.printf("%s , ", cell);
        }
        System.out.println();
        System.out.println(pathFindingQuery.getPathLength());
    }
}