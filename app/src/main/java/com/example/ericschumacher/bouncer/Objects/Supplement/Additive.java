package com.example.ericschumacher.bouncer.Objects.Supplement;

/**
 * Created by Eric on 13.07.2018.
 */

public class Additive {
    int Id;
    String Name;
    String urlName;

    public Additive(int id, String name) {
        Id = id;
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
