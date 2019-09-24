package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Objects.Collection.Collector;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Marketing_Shipping {

    private Context context;
    private Volley_Connection vConnection;

    private int id;
    private Collector oCollector = null;
    private Object_Box oBox = null;
    private Object_Bricolage oBricolage = null;
    private Object_Flyer oFlyer = null;
    private Object_Poster oPoster = null;
    private int nBox;
    private int nBricolage;
    private int nFlyer;
    private int nPoster;
    private String kTracking;
    private Date dShipping;
    private boolean bSent = false;

    public Marketing_Shipping (Context context, JSONObject oJson) {

        this.context = context;

        try {
            if (!oJson.isNull(Constants_Extern.ID)) {
                id = oJson.getInt(Constants_Extern.ID);
            }
            if (!oJson.isNull(Constants_Extern.OBJECT_COLLECTOR)) {
                oCollector = new Collector(context, oJson.getJSONObject(Constants_Extern.OBJECT_COLLECTOR));
            }
            if (!oJson.isNull(Constants_Extern.OBJECT_BOX)) {
                oBox = new Object_Box(context, oJson.getJSONObject(Constants_Extern.OBJECT_BOX));
            }
            if (!oJson.isNull(Constants_Extern.OBJECT_BRICOLAGE)) {
                oBricolage = new Object_Bricolage(context, oJson.getJSONObject(Constants_Extern.OBJECT_BRICOLAGE));
            }
            if (!oJson.isNull(Constants_Extern.OBJECT_FLYER)) {
                oFlyer = new Object_Flyer(context, oJson.getJSONObject(Constants_Extern.OBJECT_FLYER));
            }
            if (!oJson.isNull(Constants_Extern.OBJECT_POSTER)) {
                oPoster = new Object_Poster(context, oJson.getJSONObject(Constants_Extern.OBJECT_POSTER));
            }
            if (!oJson.isNull(Constants_Extern.NUMBER_BOX)) {
                nBox = oJson.getInt(Constants_Extern.NUMBER_BOX);
            }
            if (!oJson.isNull(Constants_Extern.NUMBER_BRICOLAGE)) {
                nBricolage = oJson.getInt(Constants_Extern.NUMBER_BRICOLAGE);
            }
            if (!oJson.isNull(Constants_Extern.NUMBER_FLYER)) {
                nFlyer = oJson.getInt(Constants_Extern.NUMBER_FLYER);
            }
            if (!oJson.isNull(Constants_Extern.NUMBER_POSTER)) {
                nPoster = oJson.getInt(Constants_Extern.NUMBER_POSTER);
            }
            if (!oJson.isNull(Constants_Extern.ID_TRACKING)) {
                kTracking = oJson.getString(Constants_Extern.ID_TRACKING);
            }
            if (!oJson.isNull(Constants_Extern.DATE_SHIPPING)) {
                dShipping = new Date(oJson.getString(Constants_Extern.DATE_SHIPPING));
            }
            if (!oJson.isNull(Constants_Extern.IS_SENT)) {
                bSent = oJson.getBoolean(Constants_Extern.IS_SENT);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        vConnection.execute(Request.Method.PUT, Urls.URL_UPDATE_MARKETING_SHIPPING, getJson());
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put(Constants_Extern.ID, id);
            if (oCollector != null) {
                oJson.put(Constants_Extern.ID_COLLECTOR, oCollector.getId());
            }
            oJson.put(Constants_Extern.NUMBER_BOX, nBox);
            oJson.put(Constants_Extern.NUMBER_BRICOLAGE, nBricolage);
            oJson.put(Constants_Extern.NUMBER_FLYER, nFlyer);
            oJson.put(Constants_Extern.NUMBER_POSTER, nPoster);
            oJson.put(Constants_Extern.ID_TRACKING, kTracking);
            oJson.put(Constants_Extern.DATE_SHIPPING, dShipping);
            oJson.put(Constants_Extern.IS_SENT, bSent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oJson;
    }

    public int getId() {
        return id;
    }

    public Collector getoCollector() {
        return oCollector;
    }

    public void setoCollector(Collector oCollector) {
        this.oCollector = oCollector;
    }

    public Object_Box getoBox() {
        return oBox;
    }

    public void setoBox(Object_Box oBox) {
        this.oBox = oBox;
    }

    public Object_Bricolage getoBricolage() {
        return oBricolage;
    }

    public void setoBricolage(Object_Bricolage oBricolage) {
        this.oBricolage = oBricolage;
    }

    public Object_Flyer getoFlyer() {
        return oFlyer;
    }

    public void setoFlyer(Object_Flyer oFlyer) {
        this.oFlyer = oFlyer;
    }

    public Object_Poster getoPoster() {
        return oPoster;
    }

    public void setoPoster(Object_Poster oPoster) {
        this.oPoster = oPoster;
    }

    public String getkTracking() {
        return kTracking;
    }

    public void setkTracking(String kTracking) {
        this.kTracking = kTracking;
        update();
    }

    public Date getdShipping() {
        return dShipping;
    }

    public void setdShipping(Date dShipping) {
        this.dShipping = dShipping;
        update();
    }

    public boolean isbSent() {
        return bSent;
    }

    public void setbSent(boolean bSent) {
        this.bSent = bSent;
        update();
    }
}
