package com.example.ericschumacher.bouncer.Objects;

import com.example.ericschumacher.bouncer.Constants.Constants_Extern;

import org.json.JSONException;
import org.json.JSONObject;

public class StoragePlace {

    private int kLku;
    private int kStock;
    private String cStock;
    private int nPosition;

    public StoragePlace(int kStock, int kLku) {
        this.kLku = kLku;
        this.kStock = kStock;
    }

    public StoragePlace (JSONObject oJson) {
        try {
            kLku = oJson.getInt(Constants_Extern.ID_LKU);
            kStock = oJson.getInt(Constants_Extern.ID_STOCK);
            if (!oJson.isNull(Constants_Extern.POSITION)) {
                nPosition = oJson.getInt(Constants_Extern.POSITION);
            } else {
                nPosition = Constants_Extern.UNKNOWN_POSITION;
            }
            cStock = oJson.getString(Constants_Extern.NAME_STOCK);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getkLku() {
        return kLku;
    }

    public void setkLku(int kLku) {
        this.kLku = kLku;
    }

    public int getkStock() {
        return kStock;
    }

    public void setkStock(int kStock) {
        this.kStock = kStock;
    }

    public int getnPosition() {
        return nPosition;
    }

    public void setnPosition(int nPosition) {
        this.nPosition = nPosition;
    }

    public String getcStock() {
        return cStock;
    }

    public void setcStock(String cStock) {
        this.cStock = cStock;
    }

    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants_Extern.ID_LKU, kLku);
            jsonObject.put(Constants_Extern.ID_STOCK, kStock);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
