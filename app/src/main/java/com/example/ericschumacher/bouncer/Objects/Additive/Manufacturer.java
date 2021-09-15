package com.example.ericschumacher.bouncer.Objects.Additive;

import com.example.ericschumacher.bouncer.Constants.Constants_Extern;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eric on 13.07.2018.
 */

public class Manufacturer extends Additive {

    private String cShortcut;
    private int tDefaultExploitation;

    public Manufacturer(int id, String name) {
        super(id, name);
        UrlName = "icon_manufactures";
    }

    public Manufacturer(JSONObject oJson) {
        UrlName = "icon_manufactures";
        try {
            Id = oJson.getInt(Constants_Extern.ID_MANUFACTURER);
            Name = oJson.getString(Constants_Extern.NAME_MANUFACTURER);
            cShortcut = oJson.getString(Constants_Extern.SHORTCUT);
            tDefaultExploitation = oJson.getInt(Constants_Extern.TYPE_DEFAULT_EXPLOITATION);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put(Constants_Extern.SHORTCUT, cShortcut);
            oJson.put(Constants_Extern.NAME_MANUFACTURER, Name);
            oJson.put(Constants_Extern.ID_MANUFACTURER, Id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oJson;
    }

    public String getUrlIcon() {
        return "https://svp-api.com/api/public/index.php/manufacturer/image/" + Integer.toString(Id);
    }

    public String getcShortcut() {
        return cShortcut;
    }

    public void setcShortcut(String cShortcut) {
        this.cShortcut = cShortcut;
    }

    public int gettDefaultExploitation() {
        return tDefaultExploitation;
    }

    public void settDefaultExploitation(int tDefaultExploitation) {
        this.tDefaultExploitation = tDefaultExploitation;
    }
}
