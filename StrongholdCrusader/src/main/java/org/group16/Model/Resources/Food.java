package org.group16.Model.Resources;

import org.group16.Lib.Pair;

import java.util.ArrayList;

public enum Food implements Resource {
    MEAT("MEAT", null, 1),
    APPLE("APPLE", null, 1),
    CHEESE("CHEESE", null, 1),
    FLOUR("FLOUR", new Pair<>(BasicResource.WHEAT, 1), 1),
    BREAD("BREAD", new Pair<>(Food.FLOUR, 1), 1),
    ALE("ALE", new Pair<>(BasicResource.HOPS, 1), 1),
    ;

    private final Pair<Resource, Integer> dependency;
    private final int resultCount;
    private final String name;

    private Food(String name, Pair<Resource, Integer> dependency, int resultCount) {
        this.name = name;
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
        return Integer.MAX_VALUE;
    }

    @Override
    public String getName() {
        return name;
    }
}
