package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Device;

import java.util.ArrayList;

public interface Interface_VolleyCallback_ArrayList_Devices {
    void onSuccess(ArrayList<Device> devices);
    void onFailure();
}
