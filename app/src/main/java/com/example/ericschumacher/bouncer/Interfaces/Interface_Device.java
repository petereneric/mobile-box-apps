package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;

public interface Interface_Device{
    void requestLKU();
    void requestStation();
    void requestShape();
    void requestCondition();
    void requestColor(Device device);
    void requestBatteryContained();

    void requestName();
    void requestManufacturer();
    void requestCharger(Device device);
    void requestBatteryRemovable(Device device);
    void requestBattery(Device device);
    void requestDefaultExploitation(Model model);
}
