package org.group16.Model.Resources;

import org.group16.Lib.Pair;

import java.util.ArrayList;

public interface Resource {
    public ArrayList<Pair<Resource, Integer>> getDependencies();

    public int getResultCount();

    public int getPrice();
}
