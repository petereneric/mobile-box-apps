package com.example.ericschumacher.bouncer.Objects.Collection;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable {

    int Id;
    Collector oCollector;
    //Payee oPayee;
    Date dCreation;
    Date dLastUpdate;
    int nDevices;
    int nRecycling;
    int nReuse;
    int nRepair;
    //double pQuote;
    //double sPayment;
    //double aPayment;
    //String cNotes;
    boolean bSelected;
    //int kBillPayee;
    //int kBillCollector;

    Context Context;
    Volley_Connection vConnection;

    public Record(Context context, JSONObject jsonObject) {
        Context = context;
        vConnection = new Volley_Connection(Context);
        try {
            Id = jsonObject.getInt(Constants_Extern.ID_RECORD);
            oCollector = new Collector(Context, jsonObject.getJSONObject(Constants_Extern.OBJECT_COLLECTOR));
            dCreation = Utility_DateTime.stringToDate(jsonObject.getString(Constants_Extern.DATE_CREATION));
            dLastUpdate = Utility_DateTime.stringToDate(jsonObject.getString(Constants_Extern.DATE_LAST_UPDATE));
            nDevices = jsonObject.getInt(Constants_Extern.NUMBER_DEVICES);
            nRecycling = jsonObject.getInt(Constants_Extern.NUMBER_RECYCLING);
            nReuse = jsonObject.getInt(Constants_Extern.NUMBER_REUSE);
            nRepair = jsonObject.getInt(Constants_Extern.NUMBER_REPAIR);
            bSelected = (jsonObject.getInt(Constants_Extern.BOOLEAN_SELECTED) == 1) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void update() {
        vConnection.execute(Request.Method.PUT, Urls.URL_PUT_UPDATE_RECORD, getJson());
    }

    public void incrementReuse() {
        nReuse++;
        sum();
        update();
    }

    public void incrementRecycling() {
        nRecycling++;
        sum();
        update();
    }

    public void incrementRepair() {
        nRepair++;
        sum();
        update();
    }

    private void sum() {
        nDevices = nRecycling+nReuse+nRepair;
    }

    public Collector getoCollector() {
        return oCollector;
    }

    public void setoCollector(Collector oCollector) {
        this.oCollector = oCollector;
    }

    public String getDateCreation() {
        return Utility_DateTime.dateTimeToString(dCreation);
    }

    public void setdCreation(Date dCreation) {
        this.dCreation = dCreation;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }


    public String getDateLastUpdate() {
        return Utility_DateTime.dateTimeToString(dLastUpdate);
    }

    public void setdLastUpdate(Date dLastUpdate) {
        this.dLastUpdate = dLastUpdate;
    }

    public int getnDevices() {
        return nDevices;
    }

    public void setnDevices(int nDevices) {
        this.nDevices = nDevices;
    }

    public int getnRecycling() {
        return nRecycling;
    }

    public void setnRecycling(int nRecycling) {
        this.nRecycling = nRecycling;
    }

    public int getnReuse() {
        return nReuse;
    }

    public void setnReuse(int nReuse) {
        this.nReuse = nReuse;
    }

    public boolean isbSelected() {
        return bSelected;
    }

    public void setbSelected(boolean bSelected) {
        this.bSelected = bSelected;
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put(Constants_Extern.ID_RECORD, Id);
            if (oCollector != null) {
                oJson.put(Constants_Extern.ID_COLLECTOR, oCollector.getId());
            } else {
                oJson.put(Constants_Extern.ID_COLLECTOR, JSONObject.NULL);
            }
            oJson.put(Constants_Extern.NUMBER_DEVICES, nDevices);
            oJson.put(Constants_Extern.NUMBER_RECYCLING, nRecycling);
            oJson.put(Constants_Extern.NUMBER_REUSE, nReuse);
            oJson.put(Constants_Extern.NUMBER_REPAIR, nRepair);
            oJson.put(Constants_Extern.BOOLEAN_SELECTED, bSelected ? 1 : 0);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oJson;
    }
}
