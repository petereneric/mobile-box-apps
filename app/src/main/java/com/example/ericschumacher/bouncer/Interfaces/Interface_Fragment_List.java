package com.example.ericschumacher.bouncer.Interfaces;

import org.json.JSONArray;

public interface Interface_Fragment_List {
    public void update();
    public void remove(int position);
    public void setData(JSONArray jsonArrayData);
}
