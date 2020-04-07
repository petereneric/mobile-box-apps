package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Model_Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;

public interface Interface_Edit_Model_Battery {
    void returnSelectedModelBattery(Battery oBattery);
    void connectModelBattery(int kModel, int kBattery, Fragment_Edit_Model_Battery fragment);
    void deleteModelBattery(int kModelBattery);

}
