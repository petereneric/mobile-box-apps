package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;
import android.util.Log;

import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;

public class Object_Model_Damage {

    private int Id;
    private int kModel;
    private Object_Damage oDamage;
    private boolean bRepairable;
    private int kSpare;
    private Time tDuration;
    private String cDescription;
    private String lGuide;
    private int tRepairPlace;

    // Connection
    Volley_Connection cVolley;

    public Object_Model_Damage(Context context, JSONObject oJson) {
        cVolley = new Volley_Connection(context);

        try {
            Id = oJson.getInt(Constants_Extern.ID_MODEL_DAMAGE);
            kModel = oJson.getInt(Constants_Extern.ID_MODEL);
            oDamage = new Object_Damage(context, oJson.getJSONObject(Constants_Extern.OBJECT_DAMAGE));
            bRepairable = (oJson.getInt(Constants_Extern.BOOLEAN_REPAIRABLE) == 1) ? true : false;
            tRepairPlace = oJson.getInt(Constants_Extern.TYPE_REPAIR_PLACE);
            if (!oJson.isNull(Constants_Extern.ID_SPARE)) {
                kSpare = oJson.getInt(Constants_Extern.ID_SPARE);
            } else {
                kSpare = Constants_Intern.ID_UNKNOWN;
            }
            // tDuration
            if (!oJson.isNull(Constants_Extern.DESCRIPTION)) {
                cDescription = oJson.getString(Constants_Extern.DESCRIPTION);
            } else {
                cDescription = null;
            }
            if (!oJson.isNull(Constants_Extern.LINK_GUIDE)) {
                lGuide = oJson.getString(Constants_Extern.LINK_GUIDE);
            } else {
                lGuide = null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put(Constants_Extern.ID_MODEL_DAMAGE, Id);
            oJson.put(Constants_Extern.ID_MODEL, kModel);
            oJson.put(Constants_Extern.OBJECT_DAMAGE, oDamage.getJson());
            oJson.put(Constants_Extern.BOOLEAN_REPAIRABLE, bRepairable ? 1 : 0);
            oJson.put(Constants_Extern.TYPE_REPAIR_PLACE, tRepairPlace);
            if (kSpare != Constants_Intern.ID_UNKNOWN) {
                oJson.put(Constants_Extern.ID_SPARE, kSpare);
            } else {
                oJson.put(Constants_Extern.ID_SPARE, null);
            }
            oJson.put(Constants_Extern.DESCRIPTION, cDescription);
            oJson.put(Constants_Extern.LINK_GUIDE, lGuide);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("WAS GEHT HIER", oJson.toString());
        return oJson;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getkModel() {
        return kModel;
    }

    public void setkModel(int kModel) {
        this.kModel = kModel;
    }

    public Object_Damage getoDamage() {
        return oDamage;
    }

    public void setoDamage(Object_Damage oDamage) {
        this.oDamage = oDamage;
    }

    public boolean isbRepairable() {
        return bRepairable;
    }

    public void setbRepairable(boolean bRepairable) {
        this.bRepairable = bRepairable;
    }

    public int gettRepairPlace() {
        return tRepairPlace;
    }

    public void settRepairPlace(int tRepairPlace) {
        this.tRepairPlace = tRepairPlace;
    }

    public int getkSpare() {
        return kSpare;
    }

    public void setkSpare(int kSpare) {
        this.kSpare = kSpare;
    }

    public Time gettDuration() {
        return tDuration;
    }

    public void settDuration(Time tDuration) {
        this.tDuration = tDuration;
    }

    public String getcDescription() {
        return cDescription;
    }

    public void setcDescription(String cDescription) {
        this.cDescription = cDescription;
    }

    public String getlGuide() {
        return lGuide;
    }

    public void setlGuide(String lGuide) {
        this.lGuide = lGuide;
    }

    public boolean equals(Object_Model_Damage oModelDamage) {
        if (this.Id == oModelDamage.Id) {
            return true;
        } else {
            return false;
        }
    }
}
