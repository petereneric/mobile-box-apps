package com.example.ericschumacher.bouncer.Interfaces;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public interface Interface_Selection {
    public void exploitReuse(int idModel);
    public void exploitRecycling(int idModel);
    void checkName (String name);
    void callbackManufacturer (int id);
    void checkBattery (String name);
    void checkDetails();
    void reset();
}
