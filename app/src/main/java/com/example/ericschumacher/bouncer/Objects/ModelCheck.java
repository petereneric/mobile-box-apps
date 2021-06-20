package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelCheck {

    // Connection
    Context Context;
    Volley_Connection cVolley;

    // Data
    int id;
    int kModel;
    Check oCheck;
    String cDescription;
    int nCount;
    int nPosition;
    boolean bPositionFixed;

    public ModelCheck(JSONObject json, Context context) {
        Context = context;

        try {
            id = json.getInt("id");
            kModel = json.getInt("kModel");
            cDescription = json.getString("cDescription");
            nCount = json.getInt("nCount");
            nPosition = json.getInt("nPosition");
            bPositionFixed = json.getInt("bPositionFixed") == 1;
            oCheck = new Check(Context, json.getJSONObject("oCheck"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getcDescription() {
        return cDescription;
    }

    public void setcDescription(String cDescription) {
        this.cDescription = cDescription;
    }

    public int getnCount() {
        return nCount;
    }

    public void setnCount(int nCount) {
        this.nCount = nCount;
    }

    public int getnPosition() {
        return nPosition;
    }

    public void setnPosition(int nPosition) {
        this.nPosition = nPosition;
    }

    public boolean isbPositionFixed() {
        return bPositionFixed;
    }

    public void setbPositionFixed(boolean bPositionFixed) {
        this.bPositionFixed = bPositionFixed;
    }

    public Check getoCheck() {
        return oCheck;
    }
}
