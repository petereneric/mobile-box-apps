package com.example.ericschumacher.bouncer.Objects;

import com.example.ericschumacher.bouncer.Constants.Constants_Extern;

import org.json.JSONException;
import org.json.JSONObject;

public class Article {

    private int kArticle;
    private String kSku;
    private String cArticle;
    private String cModel;
    private String cColor;
    private String cShape;
    private int nGb;
    private String iOne;
    private String iTwo;
    private int nAmountStock;

    public Article(JSONObject oJson) {
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
                iOne = oJson.getString(Constants_Extern.IMAGE_ONE);
            }
            if (!oJson.isNull(Constants_Extern.IMAGE_TWO)) {
                iTwo = oJson.getString(Constants_Extern.IMAGE_TWO);
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

    public String getiOne() {
        return iOne;
    }

    public void setiOne(String iOne) {
        this.iOne = iOne;
    }

    public String getiTwo() {
        return iTwo;
    }

    public void setiTwo(String iTwo) {
        this.iTwo = iTwo;
    }

    public int getnAmountStock() {
        return nAmountStock;
    }

    public void setnAmountStock(int nAmountStock) {
        this.nAmountStock = nAmountStock;
    }
}
