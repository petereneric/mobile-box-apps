package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;

public interface Interface_Device {
    void requestLKU();
    void requestDestination();
    void requestStation();
    void requestShape();
    void requestColor(Device device);

    void requestName();
    void requestManufacturer();
    void requestCharger(Device device);
    void requestBattery(Device device);
    void requestDefaultExploitation(Model model);
}
