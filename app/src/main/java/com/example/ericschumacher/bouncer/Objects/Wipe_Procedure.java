package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class Wipe_Procedure {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Object
    int id;
    int kWipeprocedure;
    Wipe oWipe;
    String cDescription;
    int nPosition;

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
            kWipeprocedure = json.getInt("kWipeprocedure");
            oWipe = new Wipe(cVolley, json.getJSONObject("oWipe"));
            cDescription = json.getString("cDescription");
            nPosition = json.getInt("nPosition");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PRIVATE

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PUBLIC

    public static void updatePosition(ArrayList<Wipe_Procedure> lWipeProcedures) {
        for (Wipe_Procedure oWipeProcedure : lWipeProcedures) {
            if (oWipeProcedure.getnPosition() != lWipeProcedures.indexOf(oWipeProcedure)) {
                oWipeProcedure.setnPosition(lWipeProcedures.indexOf(oWipeProcedure));
            }
        }
    }

    public static void sortByPosition(ArrayList<Wipe_Procedure> lWipeProcedures) {
        Collections.sort(lWipeProcedures, (o1, o2) -> o1.getnPosition() - o2.getnPosition());
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // GETTER & SETTER

    public int getId() {
        return id;
    }

    public int getkWipeprocedure() {
        return kWipeprocedure;
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

    public int getnPosition() {
        return nPosition;
    }

    public void setnPosition(int nPosition) {
        this.nPosition = nPosition;
        update();
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // CRUD

    public static void create(Context context, Volley_Connection cVolley, int kWipe, int kWipeprocedure, Interface_Create iCreate) {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put("kWipe", kWipe);
            oJson.put("kWipeprocedure", kWipeprocedure);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cVolley.getResponse(Request.Method.PUT, Urls.URL_ADD_WIPE_PROCEDURE, oJson, new Interface_VolleyResult() {
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
            json.put("nPosition", nPosition);
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
