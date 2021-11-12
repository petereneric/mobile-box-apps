package com.example.ericschumacher.bouncer.Objects.Additive;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eric on 13.07.2018.
 */

public class Battery extends Additive {

    Context Context;

    private Manufacturer oManufacturer;
    private int lStock;
    private Integer lkuBattery = null;

    // Connection
    Volley_Connection cVolley;

    public Battery(int id, String name, int exploitation) {
        super(id, name);
    }

    public Battery (JSONObject oJson, Context context) {
        Context = context;
        cVolley = new Volley_Connection(Context);
        try {
            Id = oJson.getInt(Constants_Extern.ID_BATTERY);
            Name = oJson.getString(Constants_Extern.NAME_BATTERY);
            if (!oJson.isNull(Constants_Extern.LEVEL_STOCK))
                lStock = oJson.getInt(Constants_Extern.LEVEL_STOCK);
            if (!oJson.isNull(Constants_Extern.OBJECT_MANUFACTURER))
                oManufacturer = new Manufacturer(oJson.getJSONObject(Constants_Extern.OBJECT_MANUFACTURER));
            if (!oJson.isNull(Constants_Extern.LKU_BATTERY))
                lkuBattery = oJson.getInt(Constants_Extern.LKU_BATTERY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put(Constants_Extern.ID_BATTERY, Id);
            oJson.put(Constants_Extern.NAME_BATTERY, Name);
            oJson.put(Constants_Extern.LEVEL_STOCK, lStock);
            if (oManufacturer != null) {
                oJson.put(Constants_Extern.ID_MANUFACTURER, oManufacturer.getId());
            } else {
                oJson.put(Constants_Extern.ID_MANUFACTURER, null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oJson;
    }

    private void updateBattery() {
        Log.i("Battery, Json: ", getJson().toString());
        cVolley.execute(Request.Method.PUT, Urls.URL_UPDATE_BATTERY, getJson());
    }

    public Manufacturer getoManufacturer() {
        return oManufacturer;
    }

    public void setoManufacturer(Manufacturer oManufacturer) {
        this.oManufacturer = oManufacturer;
        updateBattery();
    }

    public int getlStock() {
        return lStock;
    }

    public void setlStock(int lStock) {
        this.lStock = lStock;
        if (lStock == Constants_Intern.NO_STORAGE) {
            lkuBattery = null;
        }
        updateBattery();
    }

    public String getlStockName() {
        switch (lStock) {
            case Constants_Intern.MAIN_STORAGE:
                return Context.getString(R.string.main_storage);
            case Constants_Intern.RESERVE_STORAGE:
                return Context.getString(R.string.reserve_storage);
            case Constants_Intern.NO_MORE_STORAGE:
                return Context.getString(R.string.no_more_storage);
            case Constants_Intern.NO_STORAGE:
                return Context.getString(R.string.no_storage);
                default:
                    return "?";
        }
    }

    public Integer getLku() {
        return lkuBattery;
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        updateBattery();
    }
}
