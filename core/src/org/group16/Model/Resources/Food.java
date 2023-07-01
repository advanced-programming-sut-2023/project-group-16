package org.group16.Model.Resources;

import org.group16.Lib.Pair;

import java.util.ArrayList;

public enum Food implements Resource {
    MEAT(null, 1),
    APPLE(null, 1),
    CHEESE(null, 1),
    FLOUR(new Pair<>(BasicResource.WHEAT, 1), 1),
    BREAD(new Pair<>(Food.FLOUR, 1), 1),
    ALE(new Pair<>(BasicResource.HOPS, 1), 1),
    ;

    private final Pair<Resource, Integer> dependency;
    private final int resultCount;

    private Food(Pair<Resource, Integer> dependency, int resultCount) {
        this.dependency = dependency;
        this.resultCount = resultCount;
    }

    @Override
    public ArrayList<Pair<Resource, Integer>> getDependencies() {
        var arr = new ArrayList<Pair<Resource, Integer>>();
        if (dependency != null) arr.add(dependency);
        return arr;
    }

    @Override
    public int getResultCount() {
        return resultCount;
    }

    @Override
    public int getPrice() {
        return 3;
    }

    @Override
    public String GetName() {
        return this.name() ;
    }
}
