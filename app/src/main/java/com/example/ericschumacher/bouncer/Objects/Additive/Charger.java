package com.example.ericschumacher.bouncer.Objects.Additive;

import com.example.ericschumacher.bouncer.Constants.Constants_Extern;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eric on 13.07.2018.
 */

public class Charger extends Additive {

    private boolean Selected;
    private Manufacturer oManufacturer = null;
    private int tLoadingStation;

    public Charger(int id, String name) {
        super(id, name);
        UrlName = "images_chargers";
        Selected = true;
    }

    public Charger(JSONObject oJson) {
        UrlName = "images_chargers";
        Selected = true;
        try {
            Id = oJson.getInt(Constants_Extern.ID_CHARGER);
            Name = oJson.getString(Constants_Extern.NAME_CHARGER);
            tLoadingStation = oJson.getInt(Constants_Extern.TYPE_LOADING_STATION);
            if (!oJson.isNull(Constants_Extern.OBJECT_MANUFACTURER))
                oManufacturer = new Manufacturer(oJson.getJSONObject(Constants_Extern.OBJECT_MANUFACTURER));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();

        return oJson;
    }

    public String getUrlIcon() {
        return "http://svp-server.com/svp-gmbh/erp/files/images_chargers/" + Integer.toString(Id) + ".jpg";
    }

    public String gettLoadingStation() {
        switch (tLoadingStation) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            default:
                return "0";
        }
    }
}
