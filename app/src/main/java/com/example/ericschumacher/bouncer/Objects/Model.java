package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eric Schumacher on 08.07.2018.
 */

public class Model implements Parcelable {

    // Variables
    int kModel = Constants_Intern.ID_UNKNOWN;
    int tDeclaration;
    int nDps;
    int tDefaultExploitation = 0;

    String name;

    Boolean bBatteryRemovable = null;
    Battery oBattery = null;
    Charger oCharger = null;
    Manufacturer oManufacturer = null;

    // Connection
    Volley_Connection cVolley;

    public Model() {
    }

    public Model(Context context) {
        this.cVolley = new Volley_Connection(context);
    }

    public Model(Context context, JSONObject oJson) {
        cVolley = new Volley_Connection(context);
        try {
            kModel = oJson.getInt(Constants_Extern.ID_MODEL);
            if (!oJson.isNull(Constants_Extern.NAME_MODEL)) {
                name = oJson.getString(Constants_Extern.NAME_MODEL);
            } else {
                name = "";
            }
            if (!oJson.isNull(Constants_Extern.DECLARATION)) {
                tDeclaration = oJson.getInt(Constants_Extern.DECLARATION);
            } else {
                tDeclaration = 0;
            }
            if (!oJson.isNull(Constants_Extern.BATTERY_REMOVABLE))
                bBatteryRemovable = (oJson.getInt(Constants_Extern.BATTERY_REMOVABLE) == 1) ? true : false;
            tDefaultExploitation = oJson.getInt(Constants_Extern.EXPLOITATION);
            nDps = oJson.getInt(Constants_Extern.DPS);
            if (!oJson.isNull(Constants_Extern.OBJECT_MANUFACTURER))
                oManufacturer = new Manufacturer(oJson.getJSONObject(Constants_Extern.OBJECT_MANUFACTURER));
            if (!oJson.isNull(Constants_Extern.OBJECT_BATTERY))
                oBattery = new Battery(oJson.getJSONObject(Constants_Extern.OBJECT_BATTERY));
            if (!oJson.isNull(Constants_Extern.OBJECT_CHARGER))
                oCharger = new Charger(oJson.getJSONObject(Constants_Extern.OBJECT_CHARGER));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Model(int kModel, String name, int tDefaultExploitation, int idManufacturer, String nameManufacturer, int idBattery, String nameBattery, int idCharger, String nameCharger) {
        this.kModel = kModel;
        this.name = name;
        this.tDefaultExploitation = tDefaultExploitation;

        if (nameBattery != null) {
            oBattery = new Battery(idBattery, nameBattery, 0);
        }
        if (nameCharger != null) {
            oCharger = new Charger(idCharger, nameCharger);
        }
        if (nameManufacturer != null) {
            oManufacturer = new Manufacturer(idManufacturer, nameManufacturer);
        }

    }

    public int getkModel() {
        return kModel;
    }

    public void setkModel(int kModel) {
        this.kModel = kModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public com.example.ericschumacher.bouncer.Objects.Additive.Battery getoBattery() {
        return oBattery;
    }

    public void setoBattery(com.example.ericschumacher.bouncer.Objects.Additive.Battery oBattery) {
        this.oBattery = oBattery;
    }

    public com.example.ericschumacher.bouncer.Objects.Additive.Charger getoCharger() {
        return oCharger;
    }

    public void setoCharger(com.example.ericschumacher.bouncer.Objects.Additive.Charger oCharger) {
        this.oCharger = oCharger;
    }

    public com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer getoManufacturer() {
        return oManufacturer;
    }

    public void setoManufacturer(com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer oManufacturer) {
        this.oManufacturer = oManufacturer;
    }

    public int gettDefaultExploitation() {
        return tDefaultExploitation;
    }

    public void settDefaultExploitation(int tDefaultExploitation) {
        this.tDefaultExploitation = tDefaultExploitation;
    }

    public int getnDps() {
        return nDps;
    }

    public void setnDps(int nDps) {
        this.nDps = nDps;
    }

    public Boolean isBatteryRemovable() {
        return bBatteryRemovable;
    }

    public void setBatteryRemovable(Boolean bBatteryRemovable) {
        this.bBatteryRemovable = bBatteryRemovable;
        Log.i("MOoooddel: ", "jooo");
        update();
    }

    public void update() {
        if (kModel > 0) {
            Log.i("MOddel: ", getJson().toString());
            cVolley.execute(Request.Method.PUT, Urls.URL_UPDATE_MODEL, getJson());
        }
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put(Constants_Extern.ID_MODEL, kModel);
            oJson.put(Constants_Extern.NAME_MODEL, name);
            oJson.put(Constants_Extern.DPS, nDps);
            oJson.put(Constants_Extern.EXPLOITATION, tDefaultExploitation);
            if (bBatteryRemovable != null) {
                oJson.put(Constants_Extern.BATTERY_REMOVABLE, bBatteryRemovable ? 1 : 0);
            } else {
                oJson.put(Constants_Extern.BATTERY_REMOVABLE, null);
            }
            if (oBattery != null) {
                oJson.put(Constants_Extern.ID_BATTERY, oBattery.getId());
            } else {
                oJson.put(Constants_Extern.ID_BATTERY, JSONObject.NULL);
            }
            if (oCharger != null) {
                oJson.put(Constants_Extern.ID_CHARGER, oCharger.getId());
            } else {
                oJson.put(Constants_Extern.ID_CHARGER, JSONObject.NULL);
            }
            if (oManufacturer != null) {
                oJson.put(Constants_Extern.ID_MANUFACTURER, oManufacturer.getId());
            } else {
                oJson.put(Constants_Extern.ID_MANUFACTURER, JSONObject.NULL);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oJson;
    }

    public String getExploitationForScreen(Context context) {
        switch (tDefaultExploitation) {
            case Constants_Intern.EXPLOITATION_NULL:
                return Constants_Intern.UNKOWN;
            case Constants_Intern.EXPLOITATION_RECYCLING:
                return "recycling";
            case Constants_Intern.EXPLOITATION_REUSE:
                return "reuse";
        }
        return null;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public Model(Parcel in) {
        this.kModel = in.readInt();
        this.name = in.readString();
        this.oBattery = in.readParcelable(Battery.class.getClassLoader());
        this.oCharger = in.readParcelable(Charger.class.getClassLoader());
        this.oManufacturer = in.readParcelable(Manufacturer.class.getClassLoader());
        this.tDefaultExploitation = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.kModel);
        parcel.writeString(this.name);
        parcel.writeParcelable(oBattery, i);
        parcel.writeParcelable(oCharger, i);
        parcel.writeParcelable(oManufacturer, i);
        parcel.writeInt(this.tDefaultExploitation);
    }

    @Override
    public String toString() {
        return "Object_Choice{" +
                "kModel='" + kModel + '\'' +
                ", name='" + name + '\'' +
                ", Exploitation='" + tDefaultExploitation + '\'' +
                '}';
    }

}
