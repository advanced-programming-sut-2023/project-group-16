package org.group16.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class UserList implements Serializable {
    public final ArrayList<User> users;

    public UserList(ArrayList<User> users) {
        this.users = users;
    }
}
