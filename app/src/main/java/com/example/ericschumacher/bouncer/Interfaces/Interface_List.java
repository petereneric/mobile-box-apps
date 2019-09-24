package com.example.ericschumacher.bouncer.Interfaces;

import android.widget.TextView;

import com.example.ericschumacher.bouncer.Objects.Ann;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public interface Interface_List {
    public void onClick(JSONObject oJson, String tagFragment);
    public void onSwipeLeft(int position, JSONObject oJson, String tagFragment, Interface_Fragment_List iFragmentList) throws JSONException;
    public void onSwipeRight(int position, JSONObject oJson, String tagFragment, Interface_Fragment_List iFragmentList) throws JSONException;
    public JSONArray getData(String tagFragment);
    public ArrayList<Ann> getAnn (String tagFragment);
    public void setSwipeViewLeft(TextView tvBackground);
    public void setSwipeViewRight(TextView tvBackground);
}
