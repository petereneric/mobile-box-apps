package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Callback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 08.07.2018.
 */

public class Model implements Parcelable, Serializable {

    // Variables
    Context Context;
    int kModel = Constants_Intern.ID_UNKNOWN;
    int nDps;
    Integer tDefaultExploitation;
    Integer tPhone = null;

    String name;

    Boolean bBatteryKnown = null;
    Boolean bBatteryRemovable = null;
    boolean bMultipleBatteries = false;
    Boolean bBackcoverRemovable = null;
    boolean bMatch = false;

    ArrayList<Model_Battery> lModelBatteries = new ArrayList<>();
    Charger oCharger = null;
    Manufacturer oManufacturer = null;
    ArrayList<Object_Model_Damage> lModelDamages = new ArrayList<>();
    Integer tClass = null;

    ArrayList<ModelColor> lModelColor = new ArrayList<>();

    ArrayList<ModelWipe> lModelWipes = new ArrayList<>();

    // Connection
    Volley_Connection cVolley;

    public Model() {
    }

    public Model(Context context) {
        Context = context;
        this.cVolley = new Volley_Connection(context);
    }



    public Model(Context context, JSONObject oJson) {
        Context = context;
        cVolley = new Volley_Connection(context);
        try {
            kModel = oJson.getInt(Constants_Extern.ID_MODEL);
            if (!oJson.isNull(Constants_Extern.NAME_MODEL)) {
                name = oJson.getString(Constants_Extern.NAME_MODEL);
            } else {
                name = "";
            }
            if (!oJson.isNull(Constants_Extern.TYPE_PHONE)) {
                tPhone = oJson.getInt(Constants_Extern.TYPE_PHONE);
            } else {
                tPhone = null;
            }
            bMatch = oJson.getInt("bMatch") == 1;
            if (!oJson.isNull(Constants_Extern.BATTERY_REMOVABLE))
                bBatteryRemovable = (oJson.getInt(Constants_Extern.BATTERY_REMOVABLE) == 1) ? true : false;
            if (!oJson.isNull(Constants_Extern.MULTIPLE_BATTERIES))
                bMultipleBatteries = (oJson.getInt(Constants_Extern.MULTIPLE_BATTERIES) == 1) ? true : false;
            if (!oJson.isNull(Constants_Extern.BACKCOVER_REMOVABLE))
                bBackcoverRemovable = (oJson.getInt(Constants_Extern.BACKCOVER_REMOVABLE) == 1) ? true : false;
            if (!oJson.isNull(Constants_Extern.EXPLOITATION)) {
                tDefaultExploitation = oJson.getInt(Constants_Extern.EXPLOITATION);
            } else {
                tDefaultExploitation = null;
            }

            nDps = oJson.getInt(Constants_Extern.DPS);
            if (!oJson.isNull(Constants_Extern.OBJECT_MANUFACTURER))
                oManufacturer = new Manufacturer(oJson.getJSONObject(Constants_Extern.OBJECT_MANUFACTURER));
            if (!oJson.isNull(Constants_Extern.OBJECT_CHARGER))
                oCharger = new Charger(oJson.getJSONObject(Constants_Extern.OBJECT_CHARGER));

            if (!oJson.isNull(Constants_Extern.LIST_MODEL_BATTERIES)) {
                JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.LIST_MODEL_BATTERIES);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Model_Battery oModelBattery = new Model_Battery(context, jsonArray.getJSONObject(i));
                    lModelBatteries.add(oModelBattery);
                }
            }

            if (!oJson.isNull(Constants_Extern.LIST_MODEL_DAMAGES)) {
                JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.LIST_MODEL_DAMAGES);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Object_Model_Damage oModelDamage = new Object_Model_Damage(context, jsonArray.getJSONObject(i));
                    lModelDamages.add(oModelDamage);
                }
            }

            if (!oJson.isNull("tClass"))
                tClass = oJson.getInt("tClass");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Model(int kModel, String name, int tDefaultExploitation, int idManufacturer, String nameManufacturer, int idBattery, String nameBattery, int idCharger, String nameCharger) {
        this.kModel = kModel;
        this.name = name;
        this.tDefaultExploitation = tDefaultExploitation;

        if (nameCharger != null) {
            oCharger = new Charger(idCharger, nameCharger);
        }
        if (nameManufacturer != null) {
            oManufacturer = new Manufacturer(idManufacturer, nameManufacturer);
        }

    }

    public Boolean getbBatteryKnown() {
        return bBatteryKnown;
    }

    public void setbBatteryKnown(Boolean bBatteryKnown) {
        this.bBatteryKnown = bBatteryKnown;
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
        update();
    }

    public ArrayList<Model_Battery> getlModelBatteries() {
        return lModelBatteries;
    }

    public void addModelBattery(Model_Battery oModelBattery) {
        lModelBatteries.add(oModelBattery);
        if (lModelBatteries.size() == 1) {
            oModelBattery.settStatus(Constants_Intern.MODEL_BATTERY_STATUS_PRIME);
        }
        update();
    }

    public void removeModelBattery(Model_Battery oModelBattery) {
        lModelBatteries.remove(oModelBattery);
        if (lModelBatteries.size() == 1) {
            lModelBatteries.get(0).settStatus(Constants_Intern.MODEL_BATTERY_STATUS_PRIME);
        }
        update();
    }

    public void setlModelBatteries(ArrayList<Model_Battery> lModelBatteries) {
        this.lModelBatteries = lModelBatteries;
        update();
    }

    public ArrayList<Model_Battery> addlModelBatteries(Model_Battery oModelBattery) {

        for (Model_Battery modelBattery : lModelBatteries) {
            if (modelBattery.getkModel() == oModelBattery.getkModel() && modelBattery.getoBattery().getId() == oModelBattery.getoBattery().getId()) {
                return lModelBatteries;
            }
        }
        lModelBatteries.add(oModelBattery);

        return lModelBatteries;
    }

    public Battery getoBattery() {

        if (lModelBatteries.size() > 0) {
            for (Model_Battery oModelBattery : lModelBatteries) {
                if (oModelBattery.gettStatus() == Constants_Intern.TYPE_MODEL_BATTERY_PRIME) {
                    return oModelBattery.getoBattery();
                }
            }
            for (Model_Battery oModelBattery : lModelBatteries) {
                if (oModelBattery.gettStatus() == Constants_Intern.TYPE_MODEL_BATTERY_NORMAL) {
                    return oModelBattery.getoBattery();
                }
            }
        }
        return null;
    }

    public Integer gettPhone() {
        return tPhone;
    }

    public void settPhone(Integer tPhone) {
        this.tPhone = tPhone;
        update();
    }

    public boolean isbMatch() {
        return bMatch;
    }

    public String gettPhoneName(Context context) {
        if (tPhone != null) {
            switch (tPhone) {
                case Constants_Intern.TYPE_PHONE_HANDY:
                    return context.getString(R.string.handy);
                case Constants_Intern.TYPE_PHONE_SMARTPHONE:
                    return context.getString(R.string.smartphone);
                default:
                    return context.getString(R.string.unknown);
            }
        } else {
            return context.getString(R.string.unknown);
        }
    }

    public boolean isbMultipleBatteries() {
        return bMultipleBatteries;
    }

    public void setbMultipleBatteries(boolean bMultipleBatteries) {
        this.bMultipleBatteries = bMultipleBatteries;
    }

    public com.example.ericschumacher.bouncer.Objects.Additive.Charger getoCharger() {
        return oCharger;
    }

    public void setoCharger(com.example.ericschumacher.bouncer.Objects.Additive.Charger oCharger) {
        this.oCharger = oCharger;
        update();
    }

    public com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer getoManufacturer() {
        return oManufacturer;
    }

    public ArrayList<Object_Model_Damage> getlModelDamages() {
        return lModelDamages;
    }

    public void setlModelDamages(ArrayList<Object_Model_Damage> lModelDamages) {
        this.lModelDamages = lModelDamages;
    }

    public void setoManufacturer(com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer oManufacturer) {
        this.oManufacturer = oManufacturer;
        update();
    }

    public Integer gettDefaultExploitation() {
        return tDefaultExploitation;
    }

    public void settDefaultExploitation(int tDefaultExploitation) {
        this.tDefaultExploitation = tDefaultExploitation;
        update();
    }

    public int getnDps() {
        return nDps;
    }

    public void setnDps(int nDps) {
        if (nDps > 1) {
            this.nDps = nDps;
            update();
        }
    }

    public ArrayList<ModelColor> getlModelColor() {
        return lModelColor;
    }

    public void setlModelColor(ArrayList<ModelColor> lModelColor) {
        this.lModelColor = lModelColor;
    }

    public Boolean isBatteryRemovable() {
        return bBatteryRemovable;
    }

    public void setBatteryRemovable(Boolean bBatteryRemovable) {
        this.bBatteryRemovable = bBatteryRemovable;
        Log.i("MOoooddel: ", "jooo");
        update();
    }

    public Boolean isBackcoverRemovable() {
        return bBackcoverRemovable;
    }

    public void setBackcoverRemovable(Boolean bBackcoverRemovable) {
        this.bBackcoverRemovable = bBackcoverRemovable;
        update();
    }

    public void update() {
        if (kModel > 0) {
            Log.i("MOddel: ", getJson().toString());
            cVolley.execute(Request.Method.PUT, Urls.URL_UPDATE_MODEL, getJson());
        }
    }

    public Integer gettClass() {
        return tClass;
    }

    public String getClassString() {
        if (tClass != null) {
            switch (tClass) {
                case 0: return Context.getString(R.string.automatic);
                case 1: return "I";
                case 2: return "II";
                case 3: return "III";
                case 4: return "IV";
                case 5: return "V";
            }
        }
        return Context.getString(R.string.unknown);
    }

    public void settClass(Integer tClass) {
        this.tClass = tClass;
        update();
    }

    public ArrayList<ModelWipe> getlModelWipes() {
        return lModelWipes;
    }

    public void setlModelWipes(ArrayList<ModelWipe> lModelWipes) {
        this.lModelWipes = lModelWipes;
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put(Constants_Extern.ID_MODEL, kModel);
            oJson.put(Constants_Extern.NAME_MODEL, name);
            oJson.put(Constants_Extern.DPS, nDps);
            oJson.put(Constants_Extern.EXPLOITATION, tDefaultExploitation);
            if (tDefaultExploitation != null) {
                oJson.put(Constants_Extern.EXPLOITATION, tDefaultExploitation);
            } else {
                oJson.put(Constants_Extern.EXPLOITATION, JSONObject.NULL);
            }
            if (tPhone != null) {
                oJson.put(Constants_Extern.TYPE_PHONE, tPhone);
            } else {
                oJson.put(Constants_Extern.TYPE_PHONE, JSONObject.NULL);
            }
            if (bBatteryRemovable != null) {
                oJson.put(Constants_Extern.BATTERY_REMOVABLE, bBatteryRemovable ? 1 : 0);
            } else {
                oJson.put(Constants_Extern.BATTERY_REMOVABLE, JSONObject.NULL);
            }
            oJson.put(Constants_Extern.MULTIPLE_BATTERIES, bMultipleBatteries ? 1 : 0);
            if (bBackcoverRemovable != null) {
                oJson.put(Constants_Extern.BACKCOVER_REMOVABLE, bBackcoverRemovable ? 1 : 0);
            } else {
                Log.i("MAACH", "JA");
                oJson.put(Constants_Extern.BACKCOVER_REMOVABLE, JSONObject.NULL);
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
            if (lModelBatteries.size() > 0) {
                JSONArray aJson = new JSONArray();
                for (Model_Battery oModelBattery : lModelBatteries) {
                    aJson.put(oModelBattery.getJson());
                }
                oJson.put(Constants_Extern.LIST_MODEL_BATTERIES, aJson);
            } else {
                oJson.put(Constants_Extern.LIST_MODEL_BATTERIES, JSONObject.NULL);
            }
            if (tClass != null) {
                oJson.put("tClass", tClass);
            } else {
                oJson.put("tClass", JSONObject.NULL);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oJson;
    }

    public String getExploitationName(Context context) {
        if (tDefaultExploitation != null) {
            switch (tDefaultExploitation) {
                case Constants_Intern.EXPLOITATION_NULL:
                    return Constants_Intern.UNKOWN;
                case Constants_Intern.EXPLOITATION_RECYCLING:
                    return context.getString(R.string.recycling);
                case Constants_Intern.EXPLOITATION_INTACT_REUSE:
                    return context.getString(R.string.reuse);
                case Constants_Intern.EXPLOITATION_DEFECT_REUSE:
                    return context.getString(R.string.repair);
                case Constants_Intern.DEFAULT_EXPLOITATION_TBD:
                    return context.getString(R.string.tbd);
            }
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

    public Model_Battery getModelBatteryByBattery(Battery oBattery) {
        for (Model_Battery oModelBattery : lModelBatteries) {
            if (oModelBattery.getoBattery().getId() == oBattery.getId()) {
                return oModelBattery;
            }
        }
        return null;
    }

    public void connectTac(String cTac) {
        cVolley.execute(Request.Method.PUT, Urls.URL_PUT_MODEL_TAC +getkModel()+"/"+cTac, null);
    }

    public Model(Parcel in) {
        this.kModel = in.readInt();
        this.name = in.readString();
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

    public void loadModelWipes(Interface_Callback iCallback) {
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_WIPES + kModel, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                lModelWipes.clear();
                if (oJson != null) {
                    JSONArray aJson = oJson.getJSONArray("lModelWipes");
                    for (int i = 0; i < aJson.length(); i++) {
                        JSONObject jsonModelWipe = aJson.getJSONObject(i);
                        ModelWipe modelWipe = new ModelWipe(cVolley, jsonModelWipe);
                        lModelWipes.add(modelWipe);
                    }
                }
                ModelWipe.sort(lModelWipes);
                iCallback.callback();
            }
        });
    }

}
