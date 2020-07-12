package com.example.ericschumacher.bouncer.Objects;

import com.example.ericschumacher.bouncer.Constants.Constants_Extern;

import org.json.JSONException;
import org.json.JSONObject;

public class MarketingPackage {

    int kId;
    String cName;

    public MarketingPackage(JSONObject oJson) {
        try {
            kId = oJson.getInt(Constants_Extern.ID);
            cName = oJson.getString(Constants_Extern.NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getkId() {
        return kId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }
}
