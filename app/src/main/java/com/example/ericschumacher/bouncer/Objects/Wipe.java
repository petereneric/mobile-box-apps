package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Wipe {

    // VALUES & VARIABLES

    // Object
    int id;
    String cName;
    String cDescription;

    // Connection
    Volley_Connection cVolley;

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR

    public Wipe(Volley_Connection cVolley, JSONObject json) {
        this.cVolley = cVolley;
        try {
            id = json.getInt("id");
            cName = json.getString("cName");
            cDescription = json.getString("cDescription");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // GETTER & SETTER

    public int getId() {
        return id;
    }

    public String getcName() {
        return cName;
    }

    public String getcDescription() {
        return cDescription;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // CRUD

    public static void readAvailable(Context context, Volley_Connection cVolley, int kWipeprocedure, Interface_Read_List iRead) {
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_WIPE_AVAILABLE_BY_WIPEPROCEDURE + kWipeprocedure, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                JSONArray aJson = oJson.getJSONArray("lWipe");
                ArrayList<Wipe> lWipe = new ArrayList<>();
                for (int i = 0; i<aJson.length(); i++) {
                    lWipe.add(new Wipe(cVolley, aJson.getJSONObject(i)));
                }
                iRead.read(lWipe);
            }
        });
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Interfaces

    public interface Interface_Read_List {
        void read(ArrayList<Wipe> lWipe);
    }

}
