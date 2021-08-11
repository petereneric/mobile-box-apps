package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;
import android.util.Log;

import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class Object_Damage {

    // Connection
    Volley_Connection cVolley;

    private int kDamage;
    private int tDamage;
    private String cName;
    private String cDescription;
    private String lIcon;

    public Object_Damage(Context context, JSONObject oJson) {
        cVolley = new Volley_Connection(context);
        try {
            kDamage = oJson.getInt(Constants_Extern.ID_DAMAGE);
            tDamage = oJson.getInt(Constants_Extern.TYPE_DAMAGE);
            cName = oJson.getString(Constants_Extern.NAME);
            cDescription = oJson.getString(Constants_Extern.DESCRIPTION);
            lIcon = oJson.getString(Constants_Extern.LINK_ICON);
            lIcon = lIcon.replaceAll("'\\'", "");
            Log.i("LIINK", lIcon);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put(Constants_Extern.ID_DAMAGE, kDamage);
            oJson.put(Constants_Extern.TYPE_DAMAGE, tDamage);
            oJson.put(Constants_Extern.NAME, cName);
            oJson.put(Constants_Extern.DESCRIPTION, cDescription);
            oJson.put(Constants_Extern.LINK_ICON, lIcon);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("WAS GEHT HIER", oJson.toString());
        return oJson;
    }



    public int getkDamage() {
        return kDamage;
    }

    public void setkDamage(int kDamage) {
        this.kDamage = kDamage;
    }

    public int gettDamage() {
        return tDamage;
    }

    public void settDamage(int tDamage) {
        this.tDamage = tDamage;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcDescription() {
        return cDescription;
    }

    public void setcDescription(String cDescription) {
        this.cDescription = cDescription;
    }

    public String getlIcon(int tStatus) {
        switch (tStatus) {
            case Constants_Intern.STATUS_DAMAGE_NOT_HIGHLIGHTED:
                return lIcon + "not_highlighted.png";
            case Constants_Intern.STATUS_DAMAGE_REPAIRABLE:
                return lIcon + "defect_repair.png";
            case Constants_Intern.STATUS_DAMAGE_REPAIRED:
                return lIcon + "repaired.png";
            case Constants_Intern.STATUS_DAMAGE_NOT_REPAIRABLE:
                return lIcon + "not_repairable.png";
            default:
                String cd;
                return lIcon + "not_highlighted.png";
        }
    }
}
