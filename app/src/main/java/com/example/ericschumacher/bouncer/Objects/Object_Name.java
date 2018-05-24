package com.example.ericschumacher.bouncer.Objects;

/**
 * Created by Eric on 24.05.2018.
 */

public class Object_Name {
    private int Id;
    private String Name;

    public Object_Name() {

    }

    public Object_Name(int id, String name) {
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
