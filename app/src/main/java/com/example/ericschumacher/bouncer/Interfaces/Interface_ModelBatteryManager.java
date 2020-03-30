package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Fragments.Fragment_Interaction_Multiple_Choice_Model_Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;

public interface Interface_ModelBatteryManager {
    void returnSelectedModelBattery(Battery oBattery);
    void connectModelBattery(int kModel, int kBattery, Fragment_Interaction_Multiple_Choice_Model_Battery fragment);
    void deleteModelBattery(int kModelBattery);

}
