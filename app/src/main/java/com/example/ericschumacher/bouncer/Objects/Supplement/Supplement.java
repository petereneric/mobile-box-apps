package com.example.ericschumacher.bouncer.Objects.Supplement;

/**
 * Created by Eric on 13.07.2018.
 */

public class Supplement {
    int Id;
    String Name;

    public Supplement(int id, String name) {
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
