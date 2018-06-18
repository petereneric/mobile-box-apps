package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Object_Model;
import com.example.ericschumacher.bouncer.Objects.Object_SearchResult;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public interface Interface_Selection {
    public void exploitReuse(int idModel);
    public void exploitRecycling(int idModel);
    void checkName (String name);
    void callbackManufacturer (int id);
    void callbackCharger(int id);
    void checkBattery (String name);
    void checkDetails();
    void reset();
    Object_Model getModel();
    void startFragmentResult(String result);
    void setShape(int shape);
    void setCondition(int condition);

}
