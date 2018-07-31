package com.example.ericschumacher.bouncer.Interfaces;

import android.os.Bundle;

import com.example.ericschumacher.bouncer.Objects.Device;

public interface Interface_Devices {
    void scan(int idDevice, boolean isPartOfList);
    void delete(Device device);
    void getDevices(Bundle data, Interface_Fragment_Devices iCallback);
}
