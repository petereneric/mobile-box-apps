package com.example.ericschumacher.bouncer.Objects;

public class Ann {

    String jsonKeyGrandParent;
    String jsonKeyParent;
    String jsonKey;
    String cTitle;
    int nWeight;

    public Ann(String jsonKeyGrandParent, String jsonKeyParent, String jsonKey, String cTitle, int nWeight) {
        this.jsonKeyGrandParent = jsonKeyGrandParent;
        this.jsonKeyParent = jsonKeyParent;
        this.jsonKey = jsonKey;
        this.cTitle = cTitle;
        this.nWeight = nWeight;
    }

    public String getJsonKeyGrandParent() {
        return jsonKeyGrandParent;
    }

    public void setJsonKeyGrandParent(String jsonKeyGrandParent) {
        this.jsonKeyGrandParent = jsonKeyGrandParent;
    }

    public String getJsonKeyParent() {
        return jsonKeyParent;
    }

    public void setJsonKeyParent(String jsonKeyParent) {
        this.jsonKeyParent = jsonKeyParent;
    }

    public String getJsonKey() {
        return jsonKey;
    }

    public void setJsonKey(String jsonKey) {
        this.jsonKey = jsonKey;
    }

    public String getcTitle() {
        return cTitle;
    }

    public void setcTitle(String cTitle) {
        this.cTitle = cTitle;
    }

    public int getnWeight() {
        return nWeight;
    }

    public void setnWeight(int nWeight) {
        this.nWeight = nWeight;
    }
}
