package com.example.ericschumacher.bouncer.Objects;

/**
 * Created by Eric on 13.07.2018.
 */

public class Variation_Color {

    int Id;
    int IdColor;
    String Name;
    String HexCode;

    public Variation_Color(int id, int idColor, String name, String hexCode) {
        Id = id;
        IdColor = idColor;
        Name = name;
        HexCode = hexCode;
    }

    public Variation_Color(int id, String name, String hexCode) {
        Id = id;
        Name = name;
        HexCode = hexCode;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdColor() {
        return IdColor;
    }

    public void setIdColor(int idColor) {
        IdColor = idColor;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHexCode() {
        return HexCode;
    }

    public void setHexCode(String hexCode) {
        HexCode = hexCode;
    }
}
