package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Protocol implements Comparable<Protocol> {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Data
    int id;
    int kDevice;
    int kUser;
    String cUser;
    Date dCreation;
    Date dLastUpdate;
    Boolean bPassed;
    ArrayList<ProtocolWipe> lProtocolWipes = new ArrayList<ProtocolWipe>();

    // Connection
    Volley_Connection cVolley;

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR

    public Protocol(Volley_Connection cVolley, JSONObject oJson) {
        this.cVolley = cVolley;

        try {
            id = oJson.getInt("id");
            kDevice = oJson.getInt("kDevice");
            kUser = oJson.getInt("kUser");
            cUser = oJson.getString("cUser");
            dCreation = Utility_DateTime.stringToDateTime(oJson.getString("dCreation"));
            dLastUpdate = Utility_DateTime.stringToDateTime(oJson.getString("dLastUpdate"));
            bPassed = oJson.isNull("bPassed") ? null : oJson.getInt("bPassed") == 1;
            if (!oJson.isNull("lProtocolWipes")) {
                JSONArray aJson = oJson.getJSONArray("lProtocolWipes");
                for (int i = 0; i < aJson.length(); i++) {
                    JSONObject jsonProtocolWipe = aJson.getJSONObject(i);
                    ProtocolWipe oProtocolWipe = new ProtocolWipe(cVolley, jsonProtocolWipe);
                    lProtocolWipes.add(oProtocolWipe);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // GETTER & SETTER

    public int getkDevice() {
        return kDevice;
    }

    public void setkDevice(int kDevice) {
        this.kDevice = kDevice;
    }

    public int getkUser() {
        return kUser;
    }

    public void setkUser(int kUser) {
        this.kUser = kUser;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser;
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

    public Boolean isbPassed() {
        return bPassed;
    }

    public void setbPassed(Boolean bPassed) {
        this.bPassed = bPassed;
    }

    public ArrayList<ProtocolWipe> getlProtocolWipes() {
        return lProtocolWipes;
    }

    @Override
    public int compareTo(Protocol o) {
        if (getdLastUpdate() == null || o.getdLastUpdate() == null) {
            return 0;
        }
        return o.getdLastUpdate().compareTo(getdLastUpdate());
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // CRUD

    public static void create(Volley_Connection cVolley, int kDevice, int kUser, boolean bPassed, Protocol.Interface_Create iCreate) {
        JSONObject json = new JSONObject();
        try {
            json.put("kDevice", kDevice);
            json.put("kUser", kUser);
            json.put("bPassed", bPassed ? 1 : 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cVolley.getResponse(Request.Method.PUT, Urls.URL_ADD_PROTOCOL, json, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                Protocol.read(cVolley, oJson.getInt("kProtocol"), new Protocol.Interface_Read() {
                    @Override
                    public void read(Protocol protocol) {
                        iCreate.created(protocol);
                    }
                });
            }
        });
    }

    public static void read(Volley_Connection cVolley, int id, Protocol.Interface_Read iRead) {
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_PROTOCOL + id, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                iRead.read(new Protocol(cVolley, oJson));
            }
        });
    }

    public void delete() {
        cVolley.execute(Request.Method.DELETE, Urls.URL_DELETE_PROTOCOL+id, null);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Interfaces

    public interface Interface_Create {
        void created(Protocol protocol);
    }

    public interface Interface_Read {
        void read(Protocol protocol);
    }
}
