package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Image;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class Article {

    Context Context;

    private int kArticle;
    private String kSku;
    private String cArticle;
    private String cModel;
    private String cColor;
    private String cShape;
    private int nGb;
    private Bitmap bitmapOne;
    private Bitmap bitmapTwo;
    private int nAmountStock;

    public Article(Context context, JSONObject oJson) {
        Context = context;

        try {
            kArticle = oJson.getInt(Constants_Extern.ID_ARTIKEL);
            if (!oJson.isNull(Constants_Extern.ID_SKU)) {
                kSku = oJson.getString(Constants_Extern.ID_SKU);
            }
            if (!oJson.isNull(Constants_Extern.NAME_ARTICLE)) {
                cArticle = oJson.getString(Constants_Extern.NAME_ARTICLE);
            }
            if (!oJson.isNull(Constants_Extern.NAME_MODEL)) {
                cModel = oJson.getString(Constants_Extern.NAME_MODEL);
            }
            if (!oJson.isNull(Constants_Extern.NAME_COLOR)) {
                cColor = oJson.getString(Constants_Extern.NAME_COLOR);
            }
            if (!oJson.isNull(Constants_Extern.NAME_SHAPE)) {
                cShape = oJson.getString(Constants_Extern.NAME_SHAPE);
            }
            if (!oJson.isNull(Constants_Extern.AMOUNT_GB)) {
                nGb = oJson.getInt(Constants_Extern.AMOUNT_GB);
            }
            if (!oJson.isNull(Constants_Extern.IMAGE_ONE)) {
                byte[] decodedString = Base64.decode(oJson.getString(Constants_Extern.IMAGE_ONE), Base64.DEFAULT);
                bitmapOne = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            }
            if (!oJson.isNull(Constants_Extern.IMAGE_TWO)) {
                byte[] decodedString = Base64.decode(oJson.getString(Constants_Extern.IMAGE_TWO), Base64.DEFAULT);
                bitmapTwo = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            }
            if (!oJson.isNull(Constants_Extern.STOCK_AMOUNT)) {
                nAmountStock = oJson.getInt(Constants_Extern.STOCK_AMOUNT);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.kArticle = kArticle;
    }

    public int getkArticle() {
        return kArticle;
    }

    public void setkArticle(int kArticle) {
        this.kArticle = kArticle;
    }

    public String getkSku() {
        return kSku;
    }

    public void setkSku(String kSku) {
        this.kSku = kSku;
    }

    public String getcArticle() {
        return cArticle;
    }

    public void setcArticle(String cArticle) {
        this.cArticle = cArticle;
    }

    public String getcModel() {
        return cModel;
    }

    public void setcModel(String cModel) {
        this.cModel = cModel;
    }

    public String getcColor() {
        return cColor;
    }

    public void setcColor(String cColor) {
        this.cColor = cColor;
    }

    public String getcShape() {
        return cShape;
    }

    public void setcShape(String cShape) {
        this.cShape = cShape;
    }

    public int getcGb() {
        return nGb;
    }

    public void setcGb(int nGb) {
        this.nGb = nGb;
    }

    public Bitmap getBitmapOne() {
        return bitmapOne;
    }

    public void setBitmapOne(Bitmap bitmapOne) {
        this.bitmapOne = bitmapOne;
    }

    public Bitmap getBitmapTwo() {
        return bitmapTwo;
    }

    public void setBitmapTwo(Bitmap bitmapTwo) {
        this.bitmapTwo = bitmapTwo;
    }

    public int getnAmountStock() {
        return nAmountStock;
    }

    public void setnAmountStock(int nAmountStock) {
        this.nAmountStock = nAmountStock;
    }

    public static void showFragmentDialogImage(Context context, Article oArticle, final Fragment fTarget, final FragmentManager fManager) {
        Volley_Connection cVolley = new Volley_Connection(context);
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_ARTICLE_IMAGE_MAIN + oArticle.getkArticle(), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    String sBitmap = oJson.getString(Constants_Intern.ARTIKEL_IMAGE_MAIN);
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
