package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;

public interface Interface_Model {
    void requestName();
    void requestManufacturer();
    void requestCharger(Model model);
    void requestBattery(Model model);
    void requestDefaultExploitation(Model model);
}
