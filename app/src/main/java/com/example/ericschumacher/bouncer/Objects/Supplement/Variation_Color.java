package com.example.ericschumacher.bouncer.Objects.Supplement;

/**
 * Created by Eric on 13.07.2018.
 */

public class Variation_Color extends Additive {

    int IdColor;
    String HexCode;

    public Variation_Color(int id, int idColor, String name, String hexCode) {
        super(id, name);
        IdColor = idColor;
        HexCode = hexCode;
    }

    public int getIdColor() {
        return IdColor;
    }

    public void setIdColor(int idColor) {
        IdColor = idColor;
    }

    public String getHexCode() {
        return HexCode;
    }

    public void setHexCode(String hexCode) {
        HexCode = hexCode;
    }
}
