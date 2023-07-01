package org.group16.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    @Test
    public void testGettersAndSetters() {
        Map map = new Map("testMap", 100, 200);
        assertNull(map.getCellAt(100, 200));
        assertNull(map.getCellAt(10, 1000));
        assertNull(map.getCellAt(10, -10));
        assertNull(map.getCellAt(-10, -10));
        assertNotNull(map.getCellAt(0, 0));
        assertNotNull(map.getCellAt(50, 50));
        assertNotNull(map.getCells()[0][0]);
        assertEquals(100, map.getWidth());
        assertEquals(200, map.getHeight());
        assertEquals(map.getWidth() / 2, map.getCurrentX());
        assertEquals(map.getHeight() / 2, map.getCurrentY());
        map.setCurrentX(10);
        map.setCurrentY(20);
        assertEquals(10, map.getCurrentX());
        assertEquals(20, map.getCurrentY());
        assertEquals(5.0, Map.getCellDistance(map.getCellAt(0, 0), map.getCellAt(3, 4)));
        assertEquals(1.0, Map.getDistanceFromLine(map.getCellAt(0, 1), map.getCellAt(1, 1),
                map.getCellAt(0, 0)));
        assertEquals(3, map.getCellsInRange(map.getCellAt(0, 0), 1.0).size());
        Map copyMap = new Map(map);
        assertEquals(map.getWidth(), copyMap.getWidth());
    }

    @Test
    public void testSaveSystem() {
        Map map = new Map("testMap", 100, 100);
        assertNull(Map.getMapByName("testMap"));
        Map.saveMap(map);
        assertNotNull(Map.getMapByName("testMap"));
        Map.deleteMap("testMap");
        assertNull(Map.getMapByName("testMap"));
    }
}
