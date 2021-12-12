package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Wipe_Procedure {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Object
    int id;
    int kProcedure;
    Wipe oWipe;
    String cDescription;

    // Context
    Context mContext;

    // Connection
    Volley_Connection cVolley;


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR

    public Wipe_Procedure(Context context, JSONObject json) {
        mContext = context;
        cVolley = new Volley_Connection(mContext);

        try {
            id = json.getInt("id");
            kProcedure = json.getInt("kProcedure");
            oWipe = new Wipe(cVolley, json.getJSONObject("oWipe"));
            cDescription = json.getString("cDescription");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PRIVATE


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // GETTER & SETTER

    public int getId() {
        return id;
    }

    public int getkProcedure() {
        return kProcedure;
    }

    public Wipe getoWipe() {
        return oWipe;
    }

    public String getcDescription() {
        return cDescription;
    }

    public void setcDescription(String cDescription) {
        this.cDescription = cDescription;
        update();
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // CRUD

    public static void create(Context context, Volley_Connection cVolley, int kWipe, int kProcedure, Interface_Create iCreate) {
        cVolley.getResponse(Request.Method.PUT, Urls.URL_ADD_WIPE_PROCEDURE+kWipe+"/"+kProcedure, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                Wipe_Procedure.read(context, cVolley, oJson.getInt("id"), new Interface_Read() {
                    @Override
                    public void read(Wipe_Procedure wipeProcedure) {
                        iCreate.created(wipeProcedure);
                    }
                });
            }
        });
    }

    public static void read(Context context, Volley_Connection cVolley, int id, Interface_Read iRead) {
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_WIPE_PROCEDURE + id, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                iRead.read(new Wipe_Procedure(context, oJson));
            }
        });
    }

    private void update() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("cDescription", cDescription);
            cVolley.execute(Request.Method.POST, Urls.URL_UPDATE_WIPE_PROCEDURE, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        cVolley.execute(Request.Method.DELETE, Urls.URL_DELETE_WIPE_PROCEDURE+id, null);
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Interfaces

    public interface Interface_Create {
        void created(Wipe_Procedure wipeProcedure);
    }

    public interface Interface_Read {
        void read(Wipe_Procedure wipeProcedure);
    }
}
