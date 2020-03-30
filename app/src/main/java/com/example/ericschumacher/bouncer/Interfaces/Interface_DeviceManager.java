package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Additive.Additive;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Additive.Shape;
import com.example.ericschumacher.bouncer.Objects.Additive.Station;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.Objects.Model_Battery;
import com.example.ericschumacher.bouncer.Objects.Object_Device_Damage;

import java.util.ArrayList;

public interface Interface_DeviceManager {
    Model getModel();
    Device getDevice();
    Color getColor();
    void searchMatchingModel (String namePart, Interface_Search_Model iSearchodel);
    void searchMatchingManufacturer (String namePart, Interface_Search_Manufacturer iSearchManufacturer);

    void updateUI();

    void returnDefaultExploitation(int exploitation);
    void returnName(String name, int id);
    void returnManufacturer(Manufacturer manufacturer);
    void returnCharger(Charger charger);
    void returnBattery(String name);
    void returnDamages(ArrayList<Object_Device_Damage> lDeviceDamages);
    void unknownBattery();
    void onClickLKU();
    void onScan(String text);
    void returnCondition(int condition);
    void onClickInteractionButton(String tFragment, int tButton);
    void onClickInteractionMultipleChoiceCommit(String tFragment, ArrayList<Additive> lAdditive);
    void bookDeviceIntoLKUStock();
    void bookDeviceOutOfLKUStock();
    void returnStation(Station station);
    void addColor(Color color);
    void updateColor(Color color);
    void fragmentColorUpdate();
    void fragmentColorAdd();
    void requestDamages();
    void returnDeviceBattery(Battery oBattery);
    void returnModelBatteries(ArrayList<Model_Battery> lModelBatteries);

    void returnShape(Shape shape);
    void returnColor(Color color);
    void continueWithRoutine();
}
