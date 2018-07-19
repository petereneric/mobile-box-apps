package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Device;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public interface Interface_Selection {
    public void exploitReuse(int idModel);
    public void exploitRecycling(int idModel);
    void checkName (String name);
    void callbackManufacturer (int id, String name);
    void callbackCharger(int id, String name);
    void callbackColor(int id, int idColor, String name, String hexCode);
    void checkBattery (String name);
    void checkDetails();
    void reset();
    Device getModel();
    void startFragmentResult();
    void setShape(int shape);
    void setCondition(int condition);
    void setModel(int id, int name);
    void finishDevice();

    void edit

}
