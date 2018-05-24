package com.example.ericschumacher.bouncer.Interfaces;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public interface Interface_Selection {
    public void addGreenList (int idModel);
    public void addBlackList (int idModel);
    void checkName (String name);
    void checkManufacturer (String name);
    void checkCharger (String name);
    void checkBattery (String name);
    void checkDetails();
    void reset();
}
