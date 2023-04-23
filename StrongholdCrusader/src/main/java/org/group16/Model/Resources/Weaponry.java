package org.group16.Model.Resources;

import org.group16.Lib.Pair;

import javax.management.relation.InvalidRoleInfoException;
import java.util.ArrayList;

public enum Weaponry implements Resource {
    BOW(new Pair<>(BasicResource.WOOD, 2), 1),
    CROSSBOW(new Pair<>(BasicResource.WOOD, 3), 1),
    SPEAR(new Pair<>(BasicResource.WOOD, 1), 1),
    PIKE(new Pair<>(BasicResource.WOOD, 2), 1),
    MACE(new Pair<>(BasicResource.IRON, 1), 1),
    SWORD(new Pair<>(BasicResource.IRON, 1), 1),
    LEATHER_ARMOR(new Pair<>(BasicResource.COW, 1), 3),
    METAL_ARMOR(new Pair<>(BasicResource.IRON, 1), 1),
    HOT_OIL_POT(new Pair<>(BasicResource.OIL, 1), 1);

    private final Pair<Resource, Integer> dependency;
    private final int resultCount;

    private Weaponry(Pair<Resource, Integer> dependency, int resultCount) {
        this.dependency = dependency;
        this.resultCount = resultCount;
    }

    @Override
    public ArrayList<Pair<Resource, Integer>> getDependencies() {
        var arr = new ArrayList<Pair<Resource, Integer>>();
        arr.add(dependency);
        return arr;
    }

    @Override
    public int getResultCount() {
        return resultCount;
    }

    @Override
    public int getPrice() {
        return Integer.MAX_VALUE;
    }
}
