package com.example.ericschumacher.bouncer.Objects.Additive;

import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;

import org.json.JSONException;
import org.json.JSONObject;

public class Station extends Additive {

    public Station(int id, String name) {
        super(id, name);
    }

    public Station (int id) {
        Id = id;
        if (id == Constants_Intern.STATION_SCREENING) Name = "Screening";
        if (id == Constants_Intern.STATION_RECYCLING) Name = "Recycling";
        if (id == Constants_Intern.STATION_EXCESS_STOCK) Name = "Vorrats-Lager";
        if (id == Constants_Intern.STATION_PRIME_STOCK) Name = "Haupt-Lager";
        if (id == Constants_Intern.STATION_REUSE) Name = "Wiederverwendung";
        if (id == Constants_Intern.STATION_PRE_STOCK) Name = "Pre-Stock";
        if (id == Constants_Intern.STATION_UNKNOWN) Name = "Unbekannt";
        if (id == Constants_Intern.STATION_CHECK_ONE) Name = "Prüfung I";
    }

    public Station(JSONObject oJson) {
        try {
            setId(oJson.getInt(Constants_Extern.ID_STATION));
            setName(oJson.getString(Constants_Extern.NAME_STATION));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put(Constants_Extern.ID_STATION, Id);
            oJson.put(Constants_Extern.NAME_STATION, Name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return oJson;
    }

}
