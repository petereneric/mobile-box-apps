package com.example.ericschumacher.bouncer.Objects;

public class Session_Bouncer {

    Device oDevice;
    Unit_Backcover uBackcover;
    Unit_Battery uBattery;

    public Session_Bouncer() {
        oDevice = null;
        uBackcover = null;
        uBattery = null;
    }

    public Device getoDevice() {
        return oDevice;
    }

    public void setoDevice(Device oDevice) {
        this.oDevice = oDevice;
    }

    public Unit_Backcover getuBackcover() {
        return uBackcover;
    }

    public void setuBackcover(Unit_Backcover uBackcover) {
        this.uBackcover = uBackcover;
    }

    public Unit_Battery getuBattery() {
        return uBattery;
    }

    public void setuBattery(Unit_Battery uBattery) {
        this.uBattery = uBattery;
    }

    public void reset() {
        oDevice = null;
        uBackcover = null;
        uBattery = null;
    }
}
