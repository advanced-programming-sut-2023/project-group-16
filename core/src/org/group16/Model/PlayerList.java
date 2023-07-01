package org.group16.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerList implements Serializable {
    public final ArrayList<User> users = new ArrayList<>();
    public final ArrayList<KingdomType> kingdomTypes = new ArrayList<>();
}
