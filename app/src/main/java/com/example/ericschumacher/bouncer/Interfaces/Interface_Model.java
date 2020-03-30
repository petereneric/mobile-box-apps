package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Model;

public interface Interface_Model {
    void requestDps();
    void requestName();
    void requestManufacturer();
    void requestCharger(Model model);
    void requestBatteryRemovable(Model model);
    void requestBackcoverRemovable(Model model);
    void requestBattery(Model model);
    void requestDefaultExploitation(Model model);
    void requestModelDamages(Model model);
    void requestTypePhone();
}
