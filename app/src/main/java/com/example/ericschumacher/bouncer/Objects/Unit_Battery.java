package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Unit_Battery implements Serializable {

    private Volley_Connection vConnection;

    private int kId;
    private Battery oBattery;
    private boolean bBatteryIntact;

    public Unit_Battery() {
        kId = Constants_Intern.ID_UNKNOWN;
        oBattery = null;
        bBatteryIntact = true;
    }

    public Unit_Battery(JSONObject oJson, Context context) {
        vConnection = new Volley_Connection(context);
        try {
            kId = oJson.getInt(Constants_Extern.ID_BACKCOVER);
            if (!oJson.isNull(Constants_Extern.OBJECT_BATTERY)) {
                oBattery = new Battery(oJson.getJSONObject(Constants_Extern.OBJECT_BATTERY), context);
            } else {
                oBattery = null;
            }
            bBatteryIntact = oJson.getBoolean(Constants_Extern.BOOLEAN_BATTERY_INTACT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put(Constants_Extern.ID_UNIT_BATTERY, kId);
            if (oBattery != null) {
                oJson.put(Constants_Extern.ID_BATTERY, oBattery.getId());
            } else {
                oJson.put(Constants_Extern.ID_BATTERY, JSONObject.NULL);
            }
            oJson.put(Constants_Extern.BOOLEAN_BATTERY_INTACT, bBatteryIntact);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oJson;
    }

    public void updateUnitBattery() {
        if (kId > 0) {
            vConnection.execute(Request.Method.PUT, Urls.URL_UPDATE_UNIT_BATTERY, getJson());
        }
    }

    public int getkId() {
        return kId;
    }

    public void setkId(int kId) {
        this.kId = kId;
    }

    public Battery getoBattery() {
        return oBattery;
    }

    public void setoBattery(Battery oBattery) {
        this.oBattery = oBattery;
    }

    public boolean isbBatteryIntact() {
        return bBatteryIntact;
    }

    public void setbBatteryIntact(boolean bBatteryIntact) {
        this.bBatteryIntact = bBatteryIntact;
    }
}
