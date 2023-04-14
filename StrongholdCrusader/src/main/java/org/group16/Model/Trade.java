package org.group16.Model;

import org.group16.Model.Resources.Resource;

public class Trade {
    private final Resource resource;
    private final Kingdom seller;
    private Kingdom buyer;
    private final int price, amount;

    public void setBuyer(Kingdom buyer) {
        this.buyer = buyer;
    }

    public Resource getResource() {
        return resource;
    }

    public Kingdom getSeller() {
        return seller;
    }

    public Kingdom getBuyer() {
        return buyer;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public Trade(Resource resource, Kingdom seller, int price, int amount) {
        this.resource = resource;
        this.seller = seller;
        this.price = price;
        this.amount = amount;
    }
}
