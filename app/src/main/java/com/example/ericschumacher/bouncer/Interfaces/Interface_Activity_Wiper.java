package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.Objects.ModelWipe;

import java.util.ArrayList;

public interface Interface_Activity_Wiper {
    public ArrayList<Device> getSelectedDevices();
    public ArrayList<ModelWipe> getModelWipes();
    public void finishAll();
    public Model getSelectedModel();

}
