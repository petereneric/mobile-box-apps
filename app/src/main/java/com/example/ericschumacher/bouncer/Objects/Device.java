package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Exceptions.LocationException;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Image;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Additive.Shape;
import com.example.ericschumacher.bouncer.Objects.Additive.Station;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public class Device implements Serializable {

    // Url
    private final static String URL_UPDATE_DEVICE = "http://svp-server.com/svp-gmbh/erp/bouncer/src/api.php/device/update/2";

    private Volley_Connection vConnection;

    private int IdDevice = 0;
    private Integer kRecord = null;
    private Boolean bBatteryContained = null;
    private Boolean bBackcoverContained = null;
    private String cNotes = null;
    private double nRpd = 0;
    private Battery oBattery = null;
    private Manufacturer oManufacturer = null;

    private String IMEI = Constants_Intern.IMEI_UNKNOWN;
    private int LKU = Constants_Intern.LKU_UNKNOWN;
    private int Condition = Constants_Intern.CONDITION_UNKNOWN;
    private Integer tState = Constants_Intern.STATE_UNKNOWN;
    private Boolean bSoftwareIntact = null;

    private ArrayList<Object_Device_Damage> lDeviceDamages = new ArrayList<>();

    private Station oStation = new Station(0, "null");
    private StoragePlace oStoragePlace = null;

    private Color oColor = null;
    private Shape oShape = null;

    private Model oModel = null;

    Context Context;

    public Device(Context context) {
        super();
        Context = context;
        IdDevice = Constants_Intern.ID_UNKNOWN;
        oModel = new Model();
    }

    public Device(Context context, String imei) {
        Context = context;
        IMEI = imei;
    }

    public Device(JSONObject oJson, Context context) {
        Context = context;
        vConnection = new Volley_Connection(context);
        try {
            IdDevice = oJson.getInt(Constants_Extern.ID_DEVICE);
            IMEI = oJson.getString(Constants_Extern.IMEI);
            if (!oJson.isNull(Constants_Extern.ID_RECORD))
                kRecord = oJson.getInt(Constants_Extern.ID_RECORD);
            if (!oJson.isNull(Constants_Extern.OBJECT_MODEL)) {
                oModel = new Model(context, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL));
            }
            if (!oJson.isNull(Constants_Extern.BOOLEAN_BATTERY_CONTAINED)) {
                bBatteryContained = (oJson.getInt(Constants_Extern.BOOLEAN_BATTERY_CONTAINED) == 1) ? true : false;
            } else {
                if (oModel != null && oModel.isBatteryRemovable() != null && !oModel.isBatteryRemovable()) {
                    bBatteryContained = true;
                }
            }
            if (bBatteryContained != null && bBatteryContained && oModel != null && oModel.getlModelBatteries().size() == 1) {
                setoBattery(oModel.getoBattery());
            }
            if (!oJson.isNull(Constants_Extern.BOOLEAN_BACKCOVER_CONTAINED)) {
                bBackcoverContained = (oJson.getInt(Constants_Extern.BOOLEAN_BACKCOVER_CONTAINED) == 1) ? true : false;
            } else {
                if (oModel != null && oModel.isBackcoverRemovable() != null && !oModel.isBackcoverRemovable()) {
                    bBackcoverContained = true;
                }
            }
            if (!oJson.isNull(Constants_Extern.CONDITION))
                Condition = oJson.getInt(Constants_Extern.CONDITION);
            if (!oJson.isNull(Constants_Extern.TYPE_STATE))
                tState = oJson.getInt(Constants_Extern.TYPE_STATE);
            if (!oJson.isNull(Constants_Extern.BOOLEAN_SOFTWARE_INTACT))
                bSoftwareIntact = oJson.getBoolean(Constants_Extern.BOOLEAN_SOFTWARE_INTACT);
            if (!oJson.isNull(Constants_Extern.RPD))
                nRpd = oJson.getDouble(Constants_Extern.RPD);
            if (!oJson.isNull(Constants_Extern.OBJECT_STATION))
                oStation = new Station(oJson.getJSONObject(Constants_Extern.OBJECT_STATION));
            if (!oJson.isNull(Constants_Extern.OBJECT_STORAGEPLACE))
                oStoragePlace = new StoragePlace(oJson.getJSONObject(Constants_Extern.OBJECT_STORAGEPLACE));
            if (!oJson.isNull(Constants_Extern.OBJECT_SHAPE))
                oShape = new Shape(oJson.getJSONObject(Constants_Extern.OBJECT_SHAPE));
            if (!oJson.isNull(Constants_Extern.OBJECT_COLOR))
                oColor = new Color(context, oJson.getJSONObject(Constants_Extern.OBJECT_COLOR));
            if (!oJson.isNull(Constants_Extern.LIST_DEVICE_DAMAGES)) {
                JSONArray aJson = oJson.getJSONArray(Constants_Extern.LIST_DEVICE_DAMAGES);
                for (int i = 0; i < aJson.length(); i++) {
                    Object_Device_Damage oDeviceDamage = new Object_Device_Damage(Context, aJson.getJSONObject(i));
                    lDeviceDamages.add(oDeviceDamage);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put(Constants_Extern.IMEI, IMEI);
            oJson.put(Constants_Extern.ID_DEVICE, IdDevice);
            if (kRecord != null) {
                oJson.put(Constants_Extern.ID_RECORD, kRecord);
            } else {
                oJson.put(Constants_Extern.ID_RECORD, JSONObject.NULL);
            }
            oJson.put(Constants_Extern.TYPE_STATE, tState);
            oJson.put(Constants_Extern.CONDITION, Condition);
            if (bBackcoverContained != null) {
                oJson.put(Constants_Extern.BOOLEAN_BACKCOVER_CONTAINED, bBackcoverContained ? 1 : 0);
            } else {
                oJson.put(Constants_Extern.BOOLEAN_BACKCOVER_CONTAINED, JSONObject.NULL);
            }
            if (bSoftwareIntact != null) {
                oJson.put(Constants_Extern.BOOLEAN_SOFTWARE_INTACT, bSoftwareIntact ? 1 : 0);
            } else {
                oJson.put(Constants_Extern.BOOLEAN_SOFTWARE_INTACT, JSONObject.NULL);
            }
            if (oStation != null) {
                oJson.put(Constants_Extern.ID_STATION, oStation.getId());
            } else {
                oJson.put(Constants_Extern.ID_STATION, JSONObject.NULL);
            }
            if (oModel != null) {
                oJson.put(Constants_Extern.ID_MODEL, oModel.getkModel());
            } else {
                oJson.put(Constants_Extern.ID_MODEL, JSONObject.NULL);
            }
            if (bBatteryContained != null) {
                oJson.put(Constants_Extern.BOOLEAN_BATTERY_CONTAINED, bBatteryContained ? 1 : 0);
            } else {
                oJson.put(Constants_Extern.BOOLEAN_BATTERY_CONTAINED, JSONObject.NULL);
            }
            if (oColor != null) {
                oJson.put(Constants_Extern.ID_COLOR, oColor.getId());
            } else {
                oJson.put(Constants_Extern.ID_COLOR, JSONObject.NULL);
            }
            if (oShape != null) {
                oJson.put(Constants_Extern.ID_SHAPE, oShape.getId());
            } else {
                oJson.put(Constants_Extern.ID_SHAPE, JSONObject.NULL);
            }
            if (cNotes != null) {
                oJson.put(Constants_Extern.NOTES, cNotes);
            } else {
                oJson.put(Constants_Extern.NOTES, JSONObject.NULL);
            }
            if (lDeviceDamages.size() > 0) {
                JSONArray aJson = new JSONArray();
                for (Object_Device_Damage oDeviceDamage : lDeviceDamages) {
                    aJson.put(oDeviceDamage.getJson());
                }
                oJson.put(Constants_Extern.LIST_DEVICE_DAMAGES, aJson);
            } else {
                oJson.put(Constants_Extern.LIST_DEVICE_DAMAGES, JSONObject.NULL);
            }
            JSONObject oJsonStoragePlace = new JSONObject();
            if (oStoragePlace != null) {
                oJsonStoragePlace.put(Constants_Extern.ID_STOCK, oStoragePlace.getkStock());
                oJsonStoragePlace.put(Constants_Extern.ID_LKU, oStoragePlace.getkLku());
                oJson.put(Constants_Extern.OBJECT_STORAGEPLACE, oJsonStoragePlace);
            } else {
                oJson.put(Constants_Extern.OBJECT_STORAGEPLACE, JSONObject.NULL);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("WAS GEHT HIER", oJson.toString());
        return oJson;
    }

    public void updateDevice() {
        Log.i("UPDATE_DEVICE:", getJson().toString());
        if (IdDevice > 0) {
            Log.i("updateLayout json", getJson().toString());
            vConnection.execute(Request.Method.PUT, URL_UPDATE_DEVICE, getJson());
        }
    }

    public static ArrayList<Device> getDevices(Context context, JSONObject oJson) {
        ArrayList<Device> lDevices = new ArrayList<>();
        try {
            JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.DEVICES);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                lDevices.add(new Device(jsonObject, context));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lDevices;
    }

    public static ArrayList<Device> sortByLku(ArrayList<Device> lDevices) {
        Collections.sort(lDevices, new Comparator() {
            public int compare(Object o1, Object o2) {
                Integer lku1 = ((Device)o1).getoStoragePlace().getkLku();
                Integer lku2 = ((Device)o2).getoStoragePlace().getkLku();
                int sComp = lku1.compareTo(lku2);

                if (sComp != 0) {
                    return sComp;
                }

                Integer pos1 = ((Device)o1).getoStoragePlace().getnPosition();
                Integer pos2 = ((Device)o2).getoStoragePlace().getnPosition();
                return pos1.compareTo(pos2);

            }
        });
        return lDevices;
    }

    public Battery getoBattery() {
        return oBattery;
    }

    public void setoBattery(Battery oBattery) {
        this.oBattery = oBattery;
    }

    public StoragePlace getoStoragePlace() {
        return oStoragePlace;
    }

    public void setoStoragePlace(final StoragePlace oStoragePlace) {
        this.oStoragePlace = oStoragePlace;
        updateDevice();
    }

    public Manufacturer getoManufacturer() {
        return oManufacturer;
    }

    public void setoManufacturer(Manufacturer oManufacturer) {
        this.oManufacturer = oManufacturer;
    }

    public double getnRpd() {
        return nRpd;
    }

    public void setnRpd(double nRpd) {
        this.nRpd = nRpd;
    }

    public Model getoModel() {
        return oModel;
    }

    public void setoModel(Model oModel) {
        this.oModel = oModel;
        if (isBatteryContained() == null) {
            if (oModel.bBatteryRemovable != null && !oModel.isBatteryRemovable())
                setBatteryContained(true);
        }
        if (isBackcoverContained() == null) {
            if (oModel.bBackcoverRemovable != null && !oModel.isBatteryRemovable())
                setbBackcoverContained(true);
        }
        updateDevice();
    }

    public int getkRecord() {
        return kRecord;
    }

    public void setkRecord(int kRecord) {
        this.kRecord = kRecord;
        updateDevice();
    }

//    public void addModelDamages(ArrayList<Object_Model_Damage> lModelDamages) {
//        for (Object_Model_Damage oModelDamage : lModelDamages) {
//            lDeviceDamages.add(new Object_Device_Damage(Context, IdDevice, oModelDamage));
//        }
//        updateDevice();
//    }

    public int getIdDevice() {
        return IdDevice;
    }

    public void setIdDevice(int idDevice) {
        IdDevice = idDevice;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
        updateDevice();
    }

    public String getTAC() {
        return IMEI.substring(0, 8);
    }

    public boolean testMode() {
        return false;
    }

    public int getCondition() {
        return Condition;
    }

    public void setCondition(int condition) {
        Condition = condition;
    }

    public Station getoStation() {
        return oStation;
    }

    public String getStateName() {
        switch (tState) {
            case Constants_Intern.STATE_INTACT_REUSE:
                return Context.getString(R.string.intact_reuse);
            case Constants_Intern.STATE_DEFECT_REPAIR:
                return Context.getString(R.string.defect_repair);
            case Constants_Intern.STATE_DEFECT_REUSE:
                return Context.getString(R.string.defect_reuse);
            case Constants_Intern.STATE_MODEL_UNKNOWN:
                return Context.getString(R.string.model_unknown);
            case Constants_Intern.STATE_RECYCLING:
                return Context.getString(R.string.recycling);
            case Constants_Intern.STATE_UNKNOWN:
            default:
                return Context.getString(R.string.unknown);
        }
    }

    public void setoStation(Station oStation) {
        if (oStation.getId() != Constants_Intern.STATION_PRIME_STOCK) {
            this.oStation = oStation;
            updateDevice();
        } else {
            LocationException eLocation = new LocationException("Asign Prime-Stock Location only through setStoragePlace");
            try {
                throw eLocation;
            } catch (LocationException e) {
                e.printStackTrace();
            }
        }

    }


    public Boolean isBackcoverContained() {
        return bBackcoverContained;
    }

    public void setbBackcoverContained(Boolean bBackcoverContained) {
        this.bBackcoverContained = bBackcoverContained;
        updateDevice();
    }

    public Boolean getbSoftwareIntact() {
        return bSoftwareIntact;
    }

    public void setbSoftwareIntact(Boolean bSoftwareIntact) {
        this.bSoftwareIntact = bSoftwareIntact;
    }

    public Color getoColor() {
        return oColor;
    }

    public void setoColor(Color oColor) {
        this.oColor = oColor;
        updateDevice();
    }

    public Shape getoShape() {
        return oShape;
    }

    public void setoShape(Shape oShape) {
        this.oShape = oShape;
        updateDevice();
    }

    public ArrayList<Object_Device_Damage> getlDeviceDamages() {
        return lDeviceDamages;
    }

    public void setlDeviceDamages(ArrayList<Object_Device_Damage> lDeviceDamages) {
        this.lDeviceDamages = lDeviceDamages;
        updateDevice();
    }

    public Integer gettState() {
        return tState;
    }

    public void settState(int tState) {
        this.tState = tState;
    }

    public Boolean isBatteryContained() {
        return bBatteryContained;
    }

    public void setBatteryContained(Boolean bBattery) {
        this.bBatteryContained = bBattery;
        updateDevice();
    }

    public static void showFragmentDialogImage(Context context, Device oDevice, final Fragment fTarget, final FragmentManager fManager) {
        Volley_Connection cVolley = new Volley_Connection(context);
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DEVICE_IMAGE_MAIN + oDevice.getIdDevice(), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    String sBitmap = oJson.getString(Constants_Intern.DEVICE_IMAGE_MAIN);
                    Fragment_Dialog_Image fDialogImage = new Fragment_Dialog_Image();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants_Intern.STRING_BITMAP, sBitmap);
                    fDialogImage.setArguments(bundle);
                    fDialogImage.setTargetFragment(fTarget, 0);
                    fDialogImage.show(fManager, Constants_Intern.FRAGMENT_DIALOG_IMAGE);
                }
            }
        });
    }
}
