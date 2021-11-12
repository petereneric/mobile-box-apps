package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Object_Device_Damage {

    private int Id = Constants_Intern.ID_UNKNOWN;
    private int kDevice;
    private Object_Model_Damage oModelDamage;
    private Date dCreation;
    private Date dLastUpdate;
    private int tStatus;
    private String cNotes;

    public Volley_Connection cVolley;

    public Object_Device_Damage(Context context, JSONObject oJson) {
        cVolley = new Volley_Connection(context);

        try {
            if (!oJson.isNull(Constants_Extern.ID_DEVICE_DAMAGE)) {
                Id = oJson.getInt(Constants_Extern.ID_DEVICE_DAMAGE);
            } else {
                Id = Constants_Intern.ID_UNKNOWN;
            }
            kDevice = oJson.getInt(Constants_Extern.ID_DEVICE);
            oModelDamage = new Object_Model_Damage(context, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL_DAMAGE));
            //dCreation = oJson.getInt(Constants_Extern.ID_DEVICE_DAMAGE);
            //dLastUpdate = oJson.getInt(Constants_Extern.ID_DEVICE_DAMAGE);
            tStatus = oJson.getInt(Constants_Extern.TYPE_STATUS);
            //cNotes = oJson.getString(Constants_Extern.NOTES);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Object_Device_Damage(Context context, int kDevice, Object_Model_Damage oModelDamage, int tStatus) {
        cVolley = new Volley_Connection(context);

        this.kDevice = kDevice;
        this.oModelDamage = oModelDamage;
        this.tStatus = tStatus;
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            if (Id != Constants_Intern.ID_UNKNOWN) {
                oJson.put(Constants_Extern.ID_DEVICE_DAMAGE, Id);
            } else {
                oJson.put(Constants_Extern.ID_DEVICE_DAMAGE, JSONObject.NULL);
            }
            oJson.put(Constants_Extern.ID_DEVICE, kDevice);
            oJson.put(Constants_Extern.ID_MODEL_DAMAGE, oModelDamage.getId());
            oJson.put(Constants_Extern.OBJECT_MODEL_DAMAGE, oModelDamage.getJson());
            oJson.put(Constants_Extern.TYPE_STATUS, tStatus);
            oJson.put(Constants_Extern.NOTES, cNotes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("WAS GEHT HIER", oJson.toString());
        return oJson;
    }

    private JSONObject getJsonNew() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put("kDeviceDamage", getId());
            oJson.put("tStatus", tStatus);
            oJson.put("cNotes", cNotes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oJson;
    }

    public void upload() {
        cVolley.execute(Request.Method.POST, Urls.URL_UPDATE_DEVICE_DAMAGE, getJsonNew());
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getkDevice() {
        return kDevice;
    }

    public void setkDevice(int kDevice) {
        this.kDevice = kDevice;
    }

    public Object_Model_Damage getoModelDamage() {
        return oModelDamage;
    }

    public void setoModelDamage(Object_Model_Damage oModelDamage) {
        this.oModelDamage = oModelDamage;
    }

    public Date getdCreation() {
        return dCreation;
    }

    public void setdCreation(Date dCreation) {
        this.dCreation = dCreation;
    }

    public Date getdLastUpdate() {
        return dLastUpdate;
    }

    public void setdLastUpdate(Date dLastUpdate) {
        this.dLastUpdate = dLastUpdate;
    }

    public int gettStatus() {
        return tStatus;
    }

    public void settStatus(int tStatus) {
        this.tStatus = tStatus;
        upload();
    }

    public String getcNotes() {
        return cNotes;
    }

    public void setcNotes(String cNotes) {
        this.cNotes = cNotes;
    }

    public String getlIcon() {
        return getoModelDamage().getoDamage().getlIcon(tStatus);
    }

    public int getFrame() {
        switch (tStatus) {
            case Constants_Intern.STATUS_DAMAGE_REPAIRABLE:
                return R.drawable.background_rounded_corners_repair;
            case Constants_Intern.STATUS_DAMAGE_REPAIRED:
                return R.drawable.background_rounded_corners_positive;
            case Constants_Intern.STATUS_DAMAGE_NOT_REPAIRABLE:
                return R.drawable.background_rounded_corners_negative;
            default:
                return Constants_Intern.STATUS_DAMAGE_NOT_HIGHLIGHTED;
        }
    }

    public String getStatusText(Context context) {
        switch (tStatus) {
            case Constants_Intern.STATUS_DAMAGE_REPAIRABLE:
                return context.getString(R.string.damaged);
            case Constants_Intern.STATUS_DAMAGE_REPAIRED:
                return context.getString(R.string.repaired);
            case Constants_Intern.STATUS_DAMAGE_NOT_REPAIRABLE:
                return context.getString(R.string.not_repairable);
            default:
                return "";
        }
    }

    public int getColor() {
        switch (tStatus) {
            case Constants_Intern.STATUS_DAMAGE_REPAIRABLE:
                return R.color.color_defect_repair;
            case Constants_Intern.STATUS_DAMAGE_REPAIRED:
                return R.color.color_choice_positive;
            case Constants_Intern.STATUS_DAMAGE_NOT_REPAIRABLE:
                return R.color.color_choice_negative;
            default:
                return R.color.color_grey_light;
        }
    }

    public int getIcon() {
        switch (tStatus) {
            case Constants_Intern.STATUS_DAMAGE_REPAIRABLE:
                return R.color.color_defect_repair;
            case Constants_Intern.STATUS_DAMAGE_REPAIRED:
                return R.color.color_choice_positive;
            case Constants_Intern.STATUS_DAMAGE_NOT_REPAIRABLE:
                return R.color.color_choice_negative;
            default:
                return R.color.color_grey_light;
        }
    }


}
