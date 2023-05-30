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
        if (buyer == null)
            return String.format("%d) %s: %s\n[%s | amount=%d | price=%d]\n",
                    id, seller.getUser().getUsername(), sellerMessage,
                    ((Enum) resource).name(), amount, price);

        return String.format("%d) from %s: %s\nto %s: %s\n[%s | amount=%d | price=%d]\n",
                id, seller.getUser().getUsername(), sellerMessage,
                buyer.getUser().getUsername(), buyerMessage,
                ((Enum) resource).name(), amount, price);
    }
}
