package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Collection.Collector;
import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Order {

    private Context context;
    private Volley_Connection cVolley;

    private int id;
    private Collector oCollector = null;
    private MobileBox oMobileBox = null;
    private Object_Bricolage oBricolage = null;
    private Object_Flyer oFlyer = null;
    private Object_Poster oPoster = null;
    private MarketingPackage oMarketingPackage;
    private int nAmountMobileBox;
    private int nAmountBricolage;
    private int nAmountFlyer;
    private int nAmountPoster;
    private String kTracking;
    private Date dCreation;
    private Date dShipping;
    private String zplLabel;
    private boolean bSent = false;

    public Order(Context context, JSONObject oJson) {
        this.context = context;
        cVolley = new Volley_Connection(context);
        try {
            if (!oJson.isNull(Constants_Extern.ID)) {
                id = oJson.getInt(Constants_Extern.ID);
            }
            if (!oJson.isNull(Constants_Extern.OBJECT_COLLECTOR)) {
                oCollector = new Collector(context, oJson.getJSONObject(Constants_Extern.OBJECT_COLLECTOR));
            }
            if (!oJson.isNull(Constants_Extern.OBJECT_MARKETING_PACKAGE)) {
                oMarketingPackage = new MarketingPackage(oJson.getJSONObject(Constants_Extern.OBJECT_MARKETING_PACKAGE));
            }
            if (!oJson.isNull(Constants_Extern.OBJECT_BOX)) {
                oMobileBox = new MobileBox(context, oJson.getJSONObject(Constants_Extern.OBJECT_BOX));
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
                nAmountMobileBox = oJson.getInt(Constants_Extern.NUMBER_BOX);
            }
            if (!oJson.isNull(Constants_Extern.NUMBER_BRICOLAGE)) {
                nAmountBricolage = oJson.getInt(Constants_Extern.NUMBER_BRICOLAGE);
            }
            if (!oJson.isNull(Constants_Extern.NUMBER_FLYER)) {
                nAmountFlyer = oJson.getInt(Constants_Extern.NUMBER_FLYER);
            }
            if (!oJson.isNull(Constants_Extern.NUMBER_POSTER)) {
                nAmountPoster = oJson.getInt(Constants_Extern.NUMBER_POSTER);
            }
            if (!oJson.isNull(Constants_Extern.ID_TRACKING)) {
                kTracking = oJson.getString(Constants_Extern.ID_TRACKING);
            }
            if (!oJson.isNull(Constants_Extern.ZPL_LABEL)) {
                zplLabel = oJson.getString(Constants_Extern.ZPL_LABEL);
            }
            if (!oJson.isNull(Constants_Extern.DATE_SHIPPING)) {
                dShipping = Utility_DateTime.stringToDate(oJson.getString(Constants_Extern.DATE_SHIPPING));
            } else {
                dShipping = null;
            }
            if (!oJson.isNull(Constants_Extern.IS_SENT)) {
                bSent = (oJson.getInt(Constants_Extern.IS_SENT) == 1) ? true : false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        cVolley.execute(Request.Method.PUT, Urls.URL_UPDATE_MARKETING_SHIPPING, getJson());
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put(Constants_Extern.ID, id);
            if (oCollector != null) {
                oJson.put(Constants_Extern.ID_COLLECTOR, oCollector.getId());
            }
            oJson.put(Constants_Extern.NUMBER_BOX, nAmountMobileBox);
            oJson.put(Constants_Extern.NUMBER_BRICOLAGE, nAmountBricolage);
            oJson.put(Constants_Extern.NUMBER_FLYER, nAmountFlyer);
            oJson.put(Constants_Extern.NUMBER_POSTER, nAmountPoster);
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

    public MarketingPackage getoMarketingPackage() {
        return oMarketingPackage;
    }

    public void setoMarketingPackage(MarketingPackage oMarketingPackage) {
        this.oMarketingPackage = oMarketingPackage;
    }

    public MobileBox getoMobileBox() {
        return oMobileBox;
    }

    public void setoMobileBox(MobileBox oMobileBox) {
        this.oMobileBox = oMobileBox;
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

    public String getZplLabel() {
        return zplLabel;
    }

    public void setZplLabel(String zplLabel) {
        this.zplLabel = zplLabel;
    }

    public String getkTracking() {
        return kTracking;
    }

    public void setkTracking(String kTracking) {
        this.kTracking = kTracking;
    }

    public Date getdShipping() {
        return dShipping;
    }

    public void setdShipping(Date dShipping) {
        this.dShipping = dShipping;
    }

    public boolean isbSent() {
        return bSent;
    }

    public int getnAmountMobileBox() {
        return nAmountMobileBox;
    }

    public void setnAmountMobileBox(int nAmountMobileBox) {
        this.nAmountMobileBox = nAmountMobileBox;
    }

    public int getnAmountBricolage() {
        return nAmountBricolage;
    }

    public void setnAmountBricolage(int nAmountBricolage) {
        this.nAmountBricolage = nAmountBricolage;
    }

    public int getnAmountFlyer() {
        return nAmountFlyer;
    }

    public void setnAmountFlyer(int nAmountFlyer) {
        this.nAmountFlyer = nAmountFlyer;
    }

    public int getnAmountPoster() {
        return nAmountPoster;
    }

    public void setnAmountPoster(int nAmountPoster) {
        this.nAmountPoster = nAmountPoster;
    }

    public void setbSent(boolean bSent) {
        this.bSent = bSent;
    }

    public void mail() {
        cVolley.getResponse(Request.Method.POST, Urls.URL_POST_ORDER_MAIL + getId(), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                } else {
                    Utility_Toast.showString(context, oJson.getString(Constants_Extern.DETAILS));
                }
            }
        });
    }
}
