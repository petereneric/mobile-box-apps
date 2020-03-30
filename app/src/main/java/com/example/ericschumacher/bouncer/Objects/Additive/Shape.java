package com.example.ericschumacher.bouncer.Objects.Additive;

import com.example.ericschumacher.bouncer.Constants.Constants_Extern;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eric on 13.07.2018.
 */

public class Shape extends Additive {

    public Shape(int id, String name) {
        super(id, name);
    }

    public Shape(int id) {
        this.Id = id;
        String name;
        switch (id) {
            case 1:
                name = "Cherry";
                break;
            case 2:
                name = "Very Good";
                break;
            case 3:
                name = "Good";
                break;
            case 4:
                name = "Acceptable";
                break;
            case 5:
                name = "Broken";
                break;
            default:
                name = "Not defined";
        }
        this.Name = name;
    }

    public Shape (JSONObject oJson) {
        try {
            Id = oJson.getInt(Constants_Extern.ID_SHAPE);
            Name = oJson.getString(Constants_Extern.NAME_SHAPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();

        return oJson;
    }
}
