package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;

import java.util.ArrayList;

public interface Interface_Devices {
    public ArrayList<Model> getModels();
    public void clickModel(Model model);
    public int numberDevices(Model model);
    public boolean isSelected(Model model);
}
