package org.group16.Model;

import org.group16.Model.Resources.Resource;

public class Trade {
    Resource resource;
    private Kingdom seller;
    private Kingdom buyer;
    private int price, amount;

    public Trade(Resource resource, Kingdom seller, int price, int amount) {
        this.resource = resource;
        this.seller = seller;
        this.price = price;
        this.amount = amount;
    }
}
