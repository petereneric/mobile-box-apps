package com.example.ericschumacher.bouncer.Objects.Additive;

import com.example.ericschumacher.bouncer.Constants.Constants_Extern;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eric on 13.07.2018.
 */

public class Manufacturer extends Additive {

    public Manufacturer(int id, String name) {
        super(id, name);
        UrlName = "icon_manufactures";
    }

    public Manufacturer(JSONObject oJson) {
        try {
            Id = oJson.getInt(Constants_Extern.ID_MANUFACTURER);
            Name = oJson.getString(Constants_Extern.NAME_MANUFACTURER);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();

        return oJson;
    }
}
