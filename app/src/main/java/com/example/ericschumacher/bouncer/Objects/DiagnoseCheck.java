package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class DiagnoseCheck {

    // Data
    Integer id = null;
    int kDiagnose;
    int kCheck;
    Date dLastUpdate;
    int tStatus;

    // Environment
    Context eContext;

    // Connection
    Volley_Connection cVolley;

    public DiagnoseCheck(Context eContext, JSONObject oJson) {
        this.eContext = eContext;
        cVolley = new Volley_Connection(this.eContext);

        try {
            id = oJson.getInt("id");
            kDiagnose = oJson.getInt("kDiagnose");
            dLastUpdate = Utility_DateTime.stringToDate(oJson.getString("dLastUpdate"));
            kCheck = oJson.getInt("kCheck");
            tStatus = oJson.getInt("tStatus");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public DiagnoseCheck(Context eContext, int kDiagnsoe, int kCheck, int tStatus) {
        this.eContext = eContext;
        cVolley = new Volley_Connection(this.eContext);
        this.kDiagnose = kDiagnsoe;
        this.kCheck = kCheck;
        this.tStatus = tStatus;
        create();
    }

    public void create() {
        if (id == null) {
            JSONObject jsonDiagnoseCheck = new JSONObject();
            try {
                jsonDiagnoseCheck.put("kDiagnose", kDiagnose);
                jsonDiagnoseCheck.put("kCheck", kCheck);
                jsonDiagnoseCheck.put("tStatus", tStatus);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cVolley.getResponse(Request.Method.PUT, Urls.URL_CREATE_DIAGNOSE_CHECK, jsonDiagnoseCheck, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    id = oJson.getInt("id");
                }
            });
        }
    }

    public void delete() {
        if (getId() != null) {
            cVolley.execute(Request.Method.DELETE, Urls.URL_DELETE_DIAGNOSE_CHECK+getId(), null);
        }
    }

    public void deleteWithoutId(int kDiagnose, int kCheck) {
        cVolley.execute(Request.Method.DELETE, Urls.URL_DELETE_DIAGNOSE_CHECK+kDiagnose+"/"+kCheck, null);
    }

    public Integer getId() {
        return id;
    }

    public int getkDiagnose() {
        return kDiagnose;
    }

    public void setkDiagnose(int kDiagnose) {
        this.kDiagnose = kDiagnose;
    }

    public int getkCheck() {
        return kCheck;
    }

    public void setkCheck(int kCheck) {
        this.kCheck = kCheck;
    }

    public Date getdLastUpdate() {
        return dLastUpdate;
    }

    public void setdLastUpdate(Date dLastUpdate) {
        this.dLastUpdate = dLastUpdate;
    }

    public int gettStatus() {
        return tStatus;
    }

    public void settStatus(int tStatus) {
        this.tStatus = tStatus;
        upload();
    }

    private void upload() {
        JSONObject jsonDiagnoseCheck = new JSONObject();
        try {
            jsonDiagnoseCheck.put("kDiagnoseCheck", id);
            jsonDiagnoseCheck.put("tStatus", tStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cVolley.execute(Request.Method.POST, Urls.URL_UPDATE_DIAGNOSE_CHECK, jsonDiagnoseCheck);
    }
}
