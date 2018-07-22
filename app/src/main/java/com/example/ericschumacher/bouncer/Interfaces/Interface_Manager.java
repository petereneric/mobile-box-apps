package com.example.ericschumacher.bouncer.Interfaces;

import android.graphics.Color;

import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Shape;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;

public interface Interface_Manager {
    Model getModel();
    Device getDevice();

    void updateUI();

    void returnDefaultExploitation(int exploitation);
    void returnName(String name);
    void returnManufacturer(Manufacturer manufacturer);
    void returnCharger(Charger charger);
    void returnBattery(String name);

    void returnShap(Variation_Shape shape);
    void returnColor(Variation_Color color);
}
