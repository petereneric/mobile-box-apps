package com.example.ericschumacher.bouncer.Objects.Collection;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Box {

    // Variables
    private int kId;
    private Date dCreation;
    private Date dLastUpdate;
    private Record oRecord;

    // Context
    Context Context;

    // VolleyConnection
    Volley_Connection cVolley;


    public Box(Context context, JSONObject oJson) {
        Context = context;
        try {
            kId = oJson.getInt(Constants_Intern.ID_BOX);
            dCreation = Utility_DateTime.stringToDate(oJson.getString(Constants_Extern.DATE_CREATION));
            dLastUpdate = Utility_DateTime.stringToDate(oJson.getString(Constants_Extern.DATE_LAST_UPDATE));
            if (!oJson.isNull(Constants_Extern.OBJECT_MANUFACTURER)) {
                oRecord = new Record(Context, oJson.getJSONObject(Constants_Extern.OBJECT_RECORD));
            } else {
                oRecord = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getkId() {
        return kId;
    }

    public Date getdCreation() {
        return dCreation;
    }

    public void setdCreation(Date dCreation) {
        this.dCreation = dCreation;
    }

    public Date getdLastUpdate() {
        return dLastUpdate;
    }

    public void setdLastUpdate(Date dLastUpdate) {
        this.dLastUpdate = dLastUpdate;
    }

    public Record getoRecord() {
        return oRecord;
    }

    public void setoRecord(Record oRecord) {
        this.oRecord = oRecord;
    }

    public void update() {
        cVolley.execute(Request.Method.PUT, Urls.URL_PUT_BOX_UPDATE, getJson());
    }

    public void delete() {
        cVolley.execute(Request.Method.DELETE, Urls.URL_DELETE_BOX_DELETE+kId, getJson());
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put(Constants_Extern.ID_BOX, kId);
            if (oRecord != null) {
                oJson.put(Constants_Extern.ID_RECORD, oRecord.getId());
            } else {
                oJson.put(Constants_Extern.ID_RECORD, JSONObject.NULL);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oJson;
    }
}
