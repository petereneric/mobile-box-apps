package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Device;

import java.util.ArrayList;

public interface Interface_LKU {
    public ArrayList<Device> getDevices(int lku);
    void removeDevice(Device device);
}
