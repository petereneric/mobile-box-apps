package com.example.ericschumacher.bouncer.Objects.Additive;

/**
 * Created by Eric on 13.07.2018.
 */

public class Charger extends Additive {

    private boolean Selected;

    public Charger(int id, String name) {
        super(id, name);
        UrlName = "images_chargers";
        Selected = true;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }
}
