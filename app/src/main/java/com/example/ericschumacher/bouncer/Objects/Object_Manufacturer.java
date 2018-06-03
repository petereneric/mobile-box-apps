package com.example.ericschumacher.bouncer.Objects;

import android.graphics.Bitmap;

/**
 * Created by Eric Schumacher on 26.05.2018.
 */

public class Object_Manufacturer extends Object{

    private int id;
    private String name;
    private Bitmap icon;

    public Object_Manufacturer() {
    }

    public Object_Manufacturer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
}
