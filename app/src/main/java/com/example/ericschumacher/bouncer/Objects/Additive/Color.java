package com.example.ericschumacher.bouncer.Objects.Additive;

import android.content.Context;

import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Eric on 13.07.2018.
 */

public class Color extends Additive implements Serializable {

    String HexCode;
    Manufacturer Manufacturer;
    Model Model;
    int kModelColor = 0;

    public Color(int id, String name, String hexCode, Manufacturer manufacturer, Model model) {
        super(id, name);
        HexCode = hexCode;
        Manufacturer = manufacturer;
        Model = model;
    }

    public Color(int id, String name, String hexCode) {
        super(id, name);
        HexCode = hexCode;
    }

    public Color(Context context, JSONObject oJson) {
        try {
            Id = oJson.getInt(Constants_Extern.ID_COLOR);
            Name = oJson.getString(Constants_Extern.NAME_COLOR);
            HexCode = oJson.getString(Constants_Extern.HEX_CODE);
            if (!oJson.isNull(Constants_Extern.OBJECT_MANUFACTURER))
                Manufacturer = new Manufacturer(oJson.getJSONObject(Constants_Extern.OBJECT_MANUFACTURER));
            if (!oJson.isNull(Constants_Extern.OBJECT_MODEL))
                Model = new Model(context, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL));
            if (!oJson.isNull(Constants_Extern.ID_MODEL_COLOR))
                kModelColor = oJson.getInt(Constants_Extern.ID_MODEL_COLOR);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Color() {
        Id = Constants_Intern.ID_NULL;
        Name = Constants_Intern.NAME_NULL;
        HexCode = Constants_Intern.NAME_NULL;
        Manufacturer = null;
        Model = null;
    }

    public int getkModelColor() {
        return kModelColor;
    }

    public void setkModelColor(int kModelColor) {
        this.kModelColor = kModelColor;
    }

    public String getHexCode() {
        return HexCode;
    }

    public void setHexCode(String hexCode) {
        HexCode = hexCode;
    }

    public Manufacturer getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.Manufacturer = manufacturer;
    }

    public Model getModel() {
        return Model;
    }

    public void setModel(Model model) {
        this.Model = model;
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();

        return oJson;
    }
}
