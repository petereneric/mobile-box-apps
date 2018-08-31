package com.example.ericschumacher.bouncer.Activities.Parent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Shape;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;
import com.example.ericschumacher.bouncer.Zebra.ManagerPrinter;

public class Activity_Device extends AppCompatActivity implements Interface_Manager {

    Device oDevice;
    Utility_Network uNetwork;

    // Printer
    public boolean usePrinter = false;
    public ManagerPrinter mPrinter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uNetwork = new Utility_Network(this);
        if (usePrinter) mPrinter = new ManagerPrinter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (usePrinter) mPrinter.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (usePrinter) mPrinter.disconnect();
    }

    @Override
    public Model getModel() {
        return oDevice;
    }

    @Override
    public Device getDevice() {
        return oDevice;
    }

    @Override
    public void updateUI() {

    }

    @Override
    public void returnDefaultExploitation(int exploitation) {
        setExploitation(exploitation);
    }

    private void setExploitation(int exploitation) {
        oDevice.setExploitation(exploitation);
        updateUI();
        if (oDevice.getExploitation() == Constants_Intern.EXPLOITATION_RECYCLING) {
            oDevice.setDestination(Constants_Intern.EXPLOITATION_RECYCLING);
            uNetwork.exploitRecycling(oDevice);
        } else {
            oDevice.setDestination(Constants_Intern.EXPLOITATION_REUSE);
            uNetwork.exploitReuse(oDevice);
        }
    }

    @Override
    public void returnName(String name) {

    }

    @Override
    public void returnManufacturer(Manufacturer manufacturer) {

    }

    @Override
    public void returnCharger(Charger charger) {

    }

    @Override
    public void returnBattery(String name) {

    }

    @Override
    public void onClickLKU() {

    }

    @Override
    public void returnShape(Variation_Shape shape) {

    }

    @Override
    public void returnColor(Variation_Color color) {

    }
}
