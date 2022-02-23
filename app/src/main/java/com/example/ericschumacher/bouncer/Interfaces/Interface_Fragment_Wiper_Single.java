package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Protocol;

import java.util.ArrayList;

public interface Interface_Fragment_Wiper_Single {
    public ArrayList<Protocol> getProtocols();
    public Protocol getProtocol();
    public void editProtocol(int position);
    public void addProtocol();
    public void showHandler();
    public void showDefects();
    public void setProtocol(Protocol protocol);
}
