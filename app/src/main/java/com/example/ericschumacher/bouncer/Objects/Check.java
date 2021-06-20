package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class Check {

    int id;
    String cName;
    String cDescription;
    Context context;
    Volley_Connection cVolley;

    public Check(Context context, JSONObject oJson) {
        this.context = context;
        cVolley = new Volley_Connection(this.context);

        try {
            id = oJson.getInt("id");
            cName = oJson.getString("cName");
            cDescription = oJson.getString("cDescription");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getcName() {
        return cName;
    }

    public String getcDescription() {
        return cDescription;
    }
}
