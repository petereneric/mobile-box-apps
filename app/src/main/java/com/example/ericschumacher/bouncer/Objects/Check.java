package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Check implements Serializable {

    int id;
    String cName;
    String cDescription;
    Integer kDamage = null;
    Context context;
    Volley_Connection cVolley;

    public Check(Context context, JSONObject oJson) {
        this.context = context;
        cVolley = new Volley_Connection(this.context);

        try {
            id = oJson.getInt("id");
            cName = oJson.getString("cName");
            cDescription = oJson.getString("cDescription");
            if (!oJson.isNull("kDamage")) {
                kDamage = oJson.getInt("kDamage");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getcName() {
        return cName;
    }

    public String getcDescription() {
        return cDescription;
    }

    public Integer getkDamage() {
        return kDamage;
    }
}
