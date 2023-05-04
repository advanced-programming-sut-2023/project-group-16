package org.group16.Model.Resources;

import org.group16.Lib.Pair;

import java.util.ArrayList;

public enum BasicResource implements Resource {
    GOLD("GOLD", Integer.MAX_VALUE),
    WOOD("WOOD", 1),
    OIL("OIL", 1),
    //prime stine is the stone that stores directly in Quarry and waits for OX_TETHER
    PRIME_STONE("PRIME_STONE", Integer.MAX_VALUE),
    //stone that stores in StockPile
    STONE("STONE", 1),
    IRON("IRON", 1),
    WHEAT("WHEAT", 1),
    HOPS("HOPS", 1),
    COW("COW", 1),
    HORSE("HORSE", Integer.MAX_VALUE);


    private final String name;
    private final int price;

    private BasicResource(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public ArrayList<Pair<Resource, Integer>> getDependencies() {
        return new ArrayList<>();
    }

    @Override
    public int getResultCount() {
        return 1;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }
}
