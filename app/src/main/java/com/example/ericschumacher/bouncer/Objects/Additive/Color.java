package com.example.ericschumacher.bouncer.Objects.Additive;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Model;

import java.io.Serializable;

/**
 * Created by Eric on 13.07.2018.
 */

public class Color extends Additive implements Serializable {

    String HexCode;
    Manufacturer Manufacturer;
    Model Model;

    public Color(int id, String name, String hexCode, Manufacturer manufacturer, Model model) {
        super(id, name);
        HexCode = hexCode;
        Manufacturer = manufacturer;
        Model = model;
    }

    public Color(int id, String name, String hexCode) {
        super(id, name);
        HexCode = hexCode;
    }

    public Color() {
        Id = Constants_Intern.ID_NULL;
        Name = Constants_Intern.NAME_NULL;
        HexCode = Constants_Intern.NAME_NULL;
        Manufacturer = null;
        Model = null;
    }

    public String getHexCode() {
        return HexCode;
    }

    public void setHexCode(String hexCode) {
        HexCode = hexCode;
    }

    public Manufacturer getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.Manufacturer = manufacturer;
    }

    public Model getModel() {
        return Model;
    }

    public void setModel(Model model) {
        this.Model = model;
    }
}
