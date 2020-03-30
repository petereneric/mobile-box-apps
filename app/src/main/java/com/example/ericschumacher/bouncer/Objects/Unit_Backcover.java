package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Unit_Backcover implements Serializable {

    private Volley_Connection vConnection;

    private int kId;
    private int kModel;
    private int kColor;
    private int kShape;

    public Unit_Backcover() {
        kId = Constants_Intern.ID_UNKNOWN;
        kModel = Constants_Intern.ID_UNKNOWN;
        kColor = Constants_Intern.ID_UNKNOWN;
        kShape = Constants_Intern.ID_UNKNOWN;
    }

    public Unit_Backcover(JSONObject oJson, Context context) {
        vConnection = new Volley_Connection(context);
        try {
            kId = oJson.getInt(Constants_Extern.ID_BACKCOVER);
            kModel = oJson.getInt(Constants_Extern.ID_MODEL);
            kColor = oJson.getInt(Constants_Extern.ID_COLOR);
            kShape = oJson.getInt(Constants_Extern.ID_SHAPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put(Constants_Extern.ID_BACKCOVER, kId);
            oJson.put(Constants_Extern.ID_MODEL, kModel);
            oJson.put(Constants_Extern.ID_COLOR, kColor);
            oJson.put(Constants_Extern.ID_SHAPE, kShape);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oJson;
    }

    public void updateUnitBackcover() {
        if (kId > 0) {
            vConnection.execute(Request.Method.PUT, Urls.URL_UPDATE_UNIT_BACKCOVER, getJson());
        }
    }

    public int getkId() {
        return kId;
    }

    public void setkId(int kId) {
        this.kId = kId;
    }

    public int getkModel() {
        return kModel;
    }

    public void setkModel(int kModel) {
        this.kModel = kModel;
    }

    public int getkColor() {
        return kColor;
    }

    public void setkColor(int kColor) {
        this.kColor = kColor;
    }

    public int getkShape() {
        return kShape;
    }

    public void setkShape(int kShape) {
        this.kShape = kShape;
    }
}
