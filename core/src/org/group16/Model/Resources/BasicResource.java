package org.group16.Model.Resources;

import org.group16.Lib.Pair;

import java.util.ArrayList;

public enum BasicResource implements Resource {
    GOLD(Integer.MAX_VALUE),
    WOOD(1),
    OIL(1),
    //prime stone is the stone that stores directly in Quarry and waits for OX_TETHER
    PRIME_STONE(Integer.MAX_VALUE),
    //stone that stores in StockPile
    STONE(1),
    IRON(1),
    WHEAT(1),
    HOPS(1),
    COW(Integer.MAX_VALUE),
    HORSE(Integer.MAX_VALUE);


    private final int price;

    private BasicResource(int price) {
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
    public String GetName() {
        return this.name() ;
    }
}
