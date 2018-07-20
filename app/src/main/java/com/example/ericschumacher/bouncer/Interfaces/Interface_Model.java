package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Model;

public interface Interface_Model {
    Model getModel();
    void requestName();
    void returnName(String name);

    void requestNameModel();
    void requestManufacturer();
    void requestCharger();
    void requestBattery();
    void reqeuestDefaultExploitation();
}