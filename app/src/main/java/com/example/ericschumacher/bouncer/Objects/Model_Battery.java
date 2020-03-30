package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class Model_Battery {

    // Member Variables
    private Integer kId = null;
    private Battery oBattery;
    private int kModel;
    private int tStatus;

    // Connection

    Volley_Connection cVolley;

    public void setkId(Integer kId) {
        this.kId = kId;
    }

    public void setoBattery(Battery oBattery) {
        this.oBattery = oBattery;
    }

    public int getkModel() {
        return kModel;
    }

    public void setkModel(int kModel) {
        this.kModel = kModel;
    }

    public Model_Battery(Context context, int kModel, Battery oBattery, int tStatus) {
        cVolley = new Volley_Connection(context);

        this.kModel = kModel;
        this.oBattery = oBattery;
        this.tStatus = tStatus;
    }

    public Model_Battery(Context context, JSONObject oJson) {
        cVolley = new Volley_Connection(context);
        try {
            kId = oJson.getInt(Constants_Extern.ID_MODEL_BATTERY);
            oBattery = new Battery(oJson.getJSONObject(Constants_Extern.OBJECT_BATTERY), context);
            kModel = oJson.getInt(Constants_Extern.ID_MODEL);
            tStatus = oJson.getInt(Constants_Extern.TYPE_STATUS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getkId() {
        return kId;
    }

    public int gettStatus() {
        return tStatus;
    }

    public void settStatus(int tStatus) {
        this.tStatus = tStatus;
        update();

    }

    public void changeStatus() {
        if (tStatus == 2) {
            settStatus(0);
        } else {
            settStatus(gettStatus()+1);
        }
    }

    public Battery getoBattery() {
        return oBattery;
    }

    public void update() {
        if (kId != null) {
            cVolley.execute(Request.Method.PUT, Urls.URL_UPDATE_MODEL_BATTERY, getJson());
        }
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            if (kId != null) {
                oJson.put(Constants_Extern.ID_MODEL_BATTERY, kId);
            } else {
                oJson.put(Constants_Extern.ID_MODEL_BATTERY, JSONObject.NULL);
            }
            oJson.put(Constants_Extern.ID_MODEL, kModel);
            oJson.put(Constants_Extern.OBJECT_BATTERY, oBattery.getJson());
            oJson.put(Constants_Extern.TYPE_STATUS, tStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oJson;
    }
}
