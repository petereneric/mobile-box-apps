package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.example.ericschumacher.bouncer.Constants.Constants_Extern;

import org.json.JSONException;
import org.json.JSONObject;

public class Object_Flyer {


        private Context context;

        private int id;
        private String cName = null;
        private String cDescription = null;
        private String cVersion = null;
        private int dWidth = 0;
        private int dHeight = 0;
        private int nPages = 0;
        private String fDesignPrint = null;
        private String fDesignWeb = null;
        private int nStock = 0;
        private double sPrice = 0;

        public Object_Flyer(Context context, JSONObject oJson) {

            this.context = context;

            try {
                if (!oJson.isNull(Constants_Extern.ID)) {
                    id = oJson.getInt(Constants_Extern.ID);
                }
                if (!oJson.isNull(Constants_Extern.NAME)) {
                    cName = oJson.getString(Constants_Extern.NAME);
                }
                if (!oJson.isNull(Constants_Extern.DESCRIPTION)) {
                    cDescription = oJson.getString(Constants_Extern.DESCRIPTION);
                }
                if (!oJson.isNull(Constants_Extern.VERSION)) {
                    cVersion = oJson.getString(Constants_Extern.VERSION);
                }
                if (!oJson.isNull(Constants_Extern.WIDTH)) {
                    dWidth = oJson.getInt(Constants_Extern.WIDTH);
                }
                if (!oJson.isNull(Constants_Extern.HEIGHT)) {
                    dHeight = oJson.getInt(Constants_Extern.HEIGHT);
                }
                if (!oJson.isNull(Constants_Extern.PAGES)) {
                    nPages = oJson.getInt(Constants_Extern.PAGES);
                }
                if (!oJson.isNull(Constants_Extern.FILE_DESIGN_PRINT)) {
                    fDesignPrint = oJson.getString(Constants_Extern.FILE_DESIGN_PRINT);
                }
                if (!oJson.isNull(Constants_Extern.FILE_DESIGN_WEB)) {
                    fDesignWeb = oJson.getString(Constants_Extern.FILE_DESIGN_WEB);
                }
                if (!oJson.isNull(Constants_Extern.NUMBER_STOCK)) {
                    nStock = oJson.getInt(Constants_Extern.NUMBER_STOCK);
                }
                if (!oJson.isNull(Constants_Extern.PRICE)) {
                    sPrice = oJson.getDouble(Constants_Extern.PRICE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcDescription() {
        return cDescription;
    }

    public void setcDescription(String cDescription) {
        this.cDescription = cDescription;
    }

    public String getcVersion() {
        return cVersion;
    }

    public void setcVersion(String cVersion) {
        this.cVersion = cVersion;
    }

    public int getdWidth() {
        return dWidth;
    }

    public void setdWidth(int dWidth) {
        this.dWidth = dWidth;
    }

    public int getdHeight() {
        return dHeight;
    }

    public void setdHeight(int dHeight) {
        this.dHeight = dHeight;
    }

    public int getnPages() {
        return nPages;
    }

    public void setnPages(int nPages) {
        this.nPages = nPages;
    }

    public String getfDesignPrint() {
        return fDesignPrint;
    }

    public void setfDesignPrint(String fDesignPrint) {
        this.fDesignPrint = fDesignPrint;
    }

    public String getfDesignWeb() {
        return fDesignWeb;
    }

    public void setfDesignWeb(String fDesignWeb) {
        this.fDesignWeb = fDesignWeb;
    }

    public int getnStock() {
        return nStock;
    }

    public void setnStock(int nStock) {
        this.nStock = nStock;
    }

    public double getsPrice() {
        return sPrice;
    }

    public void setsPrice(double sPrice) {
        this.sPrice = sPrice;
    }
}
