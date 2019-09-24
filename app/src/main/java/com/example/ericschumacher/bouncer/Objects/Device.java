package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Exceptions.LocationException;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Shape;
import com.example.ericschumacher.bouncer.Objects.Additive.Station;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public class Device implements Serializable {

    // Url
    private final static String URL_UPDATE_DEVICE = "http://svp-server.com/svp-gmbh/erp/bouncer/src/api.php/device/update/2";

    private Volley_Connection vConnection;

    private int IdDevice = 0;
    private int kRecord = 0;
    private Boolean bBatteryContained = null;
    private String cNotes = null;
    private double nRpd = 0;

    private String IMEI = Constants_Intern.IMEI_UNKNOWN;
    private int LKU = Constants_Intern.LKU_UNKNOWN;
    private int Condition = Constants_Intern.CONDITION_UNKNOWN;
    private int Exploitation = Constants_Intern.EXPLOITATION_UNKNOWN;

    private int Destination = Constants_Intern.DESTINATION_UNKNOWN;
    private Station oStation = new Station(0, "null");
    private StoragePlace oStoragePlace = null;

    private Color oColor = null;
    private Shape oShape = null;

    private Model oModel = null;

    public Device() {
        super();
        IdDevice = Constants_Intern.ID_UNKNOWN;
        oModel = new Model();
    }

    public Device(String imei) {
        IMEI = imei;
    }

    public Device(JSONObject oJson, Context context) {
        vConnection = new Volley_Connection(context);
        try {
            IdDevice = oJson.getInt(Constants_Extern.ID_DEVICE);
            IMEI = oJson.getString(Constants_Extern.IMEI);
            if (!oJson.isNull(Constants_Extern.ID_RECORD))
                kRecord = oJson.getInt(Constants_Extern.ID_RECORD);
            if (!oJson.isNull(Constants_Extern.BOOLEAN_BATTERY_CONTAINED))
                bBatteryContained = (oJson.getInt(Constants_Extern.BOOLEAN_BATTERY_CONTAINED) == 1) ? true : false;
            if (!oJson.isNull(Constants_Extern.CONDITION))
                Condition = oJson.getInt(Constants_Extern.CONDITION);
            if (!oJson.isNull(Constants_Extern.EXPLOITATION))
                Exploitation = oJson.getInt(Constants_Extern.EXPLOITATION);
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
            if (!oJson.isNull(Constants_Extern.OBJECT_MODEL)) {
                oModel = new Model(context, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL));
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
            oJson.put(Constants_Extern.ID_RECORD, kRecord);
            oJson.put(Constants_Extern.EXPLOITATION, Exploitation);
            oJson.put(Constants_Extern.CONDITION, Condition);
            if (bBatteryContained != null) {
                oJson.put(Constants_Extern.BOOLEAN_BATTERY_CONTAINED, bBatteryContained ? 1 : 0);
            } else {
                oJson.put(Constants_Extern.BOOLEAN_BATTERY_CONTAINED, null);
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
            Log.i("update json", getJson().toString());
            vConnection.execute(Request.Method.PUT, URL_UPDATE_DEVICE, getJson());
        }
    }

    public StoragePlace getoStoragePlace() {
        return oStoragePlace;
    }

    public void setoStoragePlace(final StoragePlace oStoragePlace) {
        this.oStoragePlace = oStoragePlace;
        updateDevice();
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
            if (!oModel.isBatteryRemovable()) setBatteryContained(true);
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

    public int getDestination() {
        return Destination;
    }

    public void setDestination(int destination) {
        Destination = destination;
    }

    public Station getoStation() {
        return oStation;
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

    public int getExploitation() {
        return Exploitation;
    }

    public void setExploitation(int exploitation) {
        Exploitation = exploitation;
    }

    public Boolean isBatteryContained() {
        return bBatteryContained;
    }

    public void setBatteryContained(Boolean bBattery) {
        this.bBatteryContained = bBattery;
        updateDevice();
    }
}
