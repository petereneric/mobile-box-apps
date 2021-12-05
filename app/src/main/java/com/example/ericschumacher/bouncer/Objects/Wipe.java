package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class Wipe {

    // VALUES & VARIABLES

    // Object
    int id;
    String cName;

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR

    public Wipe(JSONObject json) {

        try {
            id = json.getInt("id");
            cName = json.getString("cName");
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
}
