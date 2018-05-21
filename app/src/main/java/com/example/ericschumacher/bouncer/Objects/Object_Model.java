package com.example.ericschumacher.bouncer.Objects;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public class Object_Model {
    private int Id;
    private String Name;

    public Object_Model(int id, String name) {
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
