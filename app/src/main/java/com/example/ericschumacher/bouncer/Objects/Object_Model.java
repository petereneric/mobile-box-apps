package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public class Object_Model {
    private int Id;
    private String Name;
    private int IdManufacturer;
    private int IdCharger;
    private int IdBattery;
    private int Exploitation;

    public Object_Model() {

    }

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

    public int getIdManufacturer() {
        return IdManufacturer;
    }

    public void setIdManufacturer(int idManufacturer) {
        IdManufacturer = idManufacturer;
    }

    public int getIdCharger() {
        return IdCharger;
    }

    public void setIdCharger(int idCharger) {
        IdCharger = idCharger;
    }

    public int getIdBattery() {
        return IdBattery;
    }

    public void setIdBattery(int idBattery) {
        IdBattery = idBattery;
    }

    public int getExploitation() {
        return Exploitation;
    }

    public void setExploitation(int exploitation) {
        Exploitation = exploitation;
    }

    public String getExploitationForScreen(Context context) {
        if (Exploitation == Constants_Intern.EXPLOITATION_RECYCLING) {
            return context.getString(R.string.recycling);
        }
        if (Exploitation == Constants_Intern.EXPLOITATION_REUSE) {
            return context.getString(R.string.reuse);
        }
        return null;
    }
}
