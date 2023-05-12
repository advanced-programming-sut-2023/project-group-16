package org.group16.Model;

import org.group16.Model.Resources.Resource;

public class Trade {
    private static int totalTrades = 0;
    private final Resource resource;
    private final Kingdom seller;
    private final int price, amount, id;
    private final String sellerMessage;
    private Kingdom buyer;
    private String buyerMessage;

    public Trade(Resource resource, Kingdom seller, int price, int amount, String sellerMessage) {
        this.resource = resource;
        this.seller = seller;
        this.price = price;
        this.amount = amount;
        this.sellerMessage = sellerMessage;
        this.id = ++totalTrades;
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

    public void setBuyer(Kingdom buyer) {
        this.buyer = buyer;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public String getSellerMessage() {
        return sellerMessage;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    @Override
    public String toString() {
        if (buyer == null) return id + ") " + seller.getUser().getUsername() + ": " + sellerMessage + "\n" +
                "[" + ((Enum) resource).name() + " | amount=" + amount + " | price=" + price + "]\n";
        return id + ") " + " from " + seller.getUser().getUsername() + ": " + sellerMessage + "\nto " +
                buyer.getUser().getUsername() + ": " + buyerMessage + "\n[" +
                ((Enum) resource).name() + " | amount=" + amount + " | price=" + price + "]\n";
    }
}
