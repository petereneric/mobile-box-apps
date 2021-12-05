package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelColor {

    // Attributes
    private int id = 0;
    private int kModel;
    private Color oColor;
    private boolean bMatch;
    private int tExploitation;
    private boolean bAutoExploitation;
    private Bitmap iPreview;

    // Member
    private Context mContext;

    // Connection
    Volley_Connection cVolley;

    public ModelColor(Context context, JSONObject oJson) {
        mContext = context;
        cVolley = new Volley_Connection(mContext);
        try {
            id = oJson.getInt("id");
            kModel = oJson.getInt("kModel");
            oColor = new Color(mContext, oJson.getJSONObject("oColor"));
            bMatch = oJson.getInt("bMatch") == 1;
            tExploitation = oJson.getInt("tExploitation");
            bAutoExploitation = oJson.getInt("bAutoExploitation") == 1;
            if (!oJson.isNull("iPreview")) {
                Log.i("joooooooooooo", oJson.getString("iPreview"));
                byte[] decodedString = Base64.decode(oJson.getString("iPreview"), Base64.DEFAULT);
                Log.i("jooooo", decodedString.toString());
                iPreview = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put("id", id);
            oJson.put("tExploitation", tExploitation);
            oJson.put("bAutoExploitation", bAutoExploitation ? 1 : 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oJson;
    }

    public void update() {
        if (id > 0) {
            cVolley.execute(Request.Method.POST, Urls.URL_UPDATE_MODEL_COLOR, getJson());
        }
    }

    public int getId() {
        return id;
    }

    public boolean isbMatch() {
        return bMatch;
    }

    public int gettExploitation() {
        return tExploitation;
    }

    public void settExploitation(int tExploitation) {
        this.tExploitation = tExploitation;
        update();
    }

    public boolean isbAutoExploitation() {
        return bAutoExploitation;
    }

    public void setbAutoExploitation(boolean bAutoExploitation) {
        this.bAutoExploitation = bAutoExploitation;
        update();
    }

    public Color getoColor() {
        return oColor;
    }

    public Bitmap getiPreview() {
        return iPreview;
    }
}
