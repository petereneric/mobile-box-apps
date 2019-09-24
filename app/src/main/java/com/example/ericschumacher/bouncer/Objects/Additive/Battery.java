package com.example.ericschumacher.bouncer.Objects.Additive;

import com.example.ericschumacher.bouncer.Constants.Constants_Extern;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eric on 13.07.2018.
 */

public class Battery extends Additive {

    private int Exploitation;

    public Battery(int id, String name, int exploitation) {
        super(id, name);
        Exploitation = exploitation;
    }

    public Battery (JSONObject oJson) {
        try {
            Id = oJson.getInt(Constants_Extern.ID_BATTERY);
            Name = oJson.getString(Constants_Extern.NAME_BATTERY);
            Exploitation = oJson.getInt(Constants_Extern.EXPLOITATION);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();

        return oJson;
    }

    public int getExploitation() {
        return Exploitation;
    }

    public void setExploitation(int exploitation) {
        this.Exploitation = exploitation;
    }
}
