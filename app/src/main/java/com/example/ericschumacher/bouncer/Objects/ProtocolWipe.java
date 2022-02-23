package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class ProtocolWipe {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Data
    private int id;
    private int kProtocol;
    private Wipe oWipe;

    // Connection
    Volley_Connection cVolley;

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR

    public ProtocolWipe(Volley_Connection cVolley, JSONObject oJson) {
        this.cVolley = cVolley;

        try {
            id = oJson.getInt("id");
            kProtocol = oJson.getInt("kProtocol");
            oWipe = new Wipe(cVolley, oJson.getJSONObject("oWipe"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
