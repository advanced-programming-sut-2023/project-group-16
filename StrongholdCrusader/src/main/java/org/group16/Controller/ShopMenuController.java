package org.group16.Controller;

import org.group16.Model.Kingdom;
import org.group16.Model.Resources.BasicResource;
import org.group16.Model.Resources.Food;
import org.group16.Model.Resources.Resource;
import org.group16.Model.Resources.Weaponry;

public class ShopMenuController {
    public static String showPriceList() {
        String output = "items:\n";
        for (BasicResource resource : BasicResource.values())
            if (resource.getPrice() != Integer.MAX_VALUE)
                output += resource.name() + ": " + resource.getPrice() + " gold\n";
        for (Food resource : Food.values())
            if (resource.getPrice() != Integer.MAX_VALUE)
                output += resource.name() + ": " + resource.getPrice() + " gold\n";
        for (Weaponry resource : Weaponry.values())
            if (resource.getPrice() != Integer.MAX_VALUE)
                output += resource.name() + ": " + resource.getPrice() + " gold\n";
        return output;
    }

    public static String buyItem(Kingdom kingdom, String itemName, int amount) {
        Resource resource = getResourceByName(itemName);
        if (resource == null) return "invalid item name";
        if (amount <= 0) return "amount should be positive";
        if (kingdom.getResourceStorageCapacity(resource) < amount) return "insufficient storage capacity";
        if (kingdom.getGold() < resource.getPrice() * amount) return "insufficient gold";
        kingdom.addResource(resource, amount);
        kingdom.addGold(-resource.getPrice() * amount);
        return "item bought successfully";
    }

    public static String sellItem(Kingdom kingdom, String itemName, int amount) {
        Resource resource = getResourceByName(itemName);
        if (resource == null) return "invalid item name";
        if (amount <= 0) return "amount should be positive";
        if (kingdom.getResourceCount(resource) < amount) return "insufficient item";
        if (!kingdom.addGold(resource.getPrice() * amount * 8 / 10)) return "insufficient gold storage capacity";
        kingdom.useResource(resource, amount);
        return "item sold successfully";
    }

    private static Resource getResourceByName(String name) {
        name = name.toUpperCase();
        Resource resource;
        try {
            resource = BasicResource.valueOf(name);
        } catch (IllegalArgumentException e1) {
            try {
                resource = Food.valueOf(name);
            } catch (IllegalArgumentException e2) {
                try {
                    resource = Weaponry.valueOf(name);
                } catch (IllegalArgumentException e3) {
                    return null;
                }
            }
        }
        return resource;
    }
}
