package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public interface Interface_Selection {
    public void exploitReuse(int idModel);
    public void exploitRecycling(int idModel);
    void checkName (String name);
    void checkDetails();
    void reset();
    void showResult();
    void setCondition(int condition);
    void finishDevice();
    void saveDevice();
}
