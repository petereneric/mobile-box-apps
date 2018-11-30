package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Additive.Station;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Shape;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;

public interface Interface_Manager {
    Model getModel();
    Device getDevice();
    Color getColor();
    void searchMatchingModel (String namePart, Interface_Search_Model iSearchodel);
    void searchMatchingManufacturer (String namePart, Interface_Search_Manufacturer iSearchManufacturer);

    void updateUI();

    void returnDefaultExploitation(int exploitation);
    void returnName(String name);
    void returnManufacturer(Manufacturer manufacturer);
    void returnCharger(Charger charger);
    void returnBattery(String name);
    void unknownBattery();
    void onClickLKU();
    void onScan(String text);
    void returnCondition(int condition);
    void bookDeviceIntoLKUStock();
    void bookDeviceOutOfLKUStock();
    void returnStation(Station station);
    void addColor(Color color);
    void updateColor(Color color);
    void fragmentColorUpdate();
    void fragmentColorAdd();

    void returnShape(Shape shape);
    void returnColor(Color color);
}
