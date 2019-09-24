package com.example.ericschumacher.bouncer.Interfaces;

import android.widget.TextView;

import com.example.ericschumacher.bouncer.Objects.Ann;

import org.json.JSONObject;

import java.util.ArrayList;

public interface Interface_Activity_List {
    public void getData(String cTag, Interface_Fragment_List interfaceFragmentList);
    public ArrayList<Ann> getAnn(String cTag);
    public void onSwipeLeft(int nPosition, String cTag, JSONObject jsonObject, Interface_Fragment_List iFragmentList);
    public void onSwipeRight(int nPosition, String cTag, JSONObject jsonObject, Interface_Fragment_List iFragmentList);
    public void setViewSwipeRight(String cTag, TextView tvBackground);
    public void setViewSwipeLeft(String cTag, TextView tvBackground);
    public void onClick(int nPosition,String cTag, JSONObject jsonObject);
    public boolean bHeader(String cTag);
    public void setToolbar(String cTag);
    public JSONObject getJsonObject(int position, String cTag);
    public int getSwipeBehaviour(String cTag);
}
