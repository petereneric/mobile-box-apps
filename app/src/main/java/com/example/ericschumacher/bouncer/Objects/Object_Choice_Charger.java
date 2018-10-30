package com.example.ericschumacher.bouncer.Objects;

/**
 * Created by Eric Schumacher on 05.06.2018.
 */

public class Object_Choice_Charger extends Object_Choice {

    boolean isSelected;

    public Object_Choice_Charger(int id, String name) {
        super(id, name);
        urlName = "images_chargers";
        isSelected = true;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
