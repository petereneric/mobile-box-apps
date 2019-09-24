package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import org.json.JSONObject;

public class Object_Id_Model_Color_Shape {

    private int Id_model_color_shape;
    private int Id_color;
    private int Id_shape;
    private String Name;
    private double Rpd;

    public Object_Id_Model_Color_Shape(int id_model_color_shape, int id_color, int id_shape, String name) {
        Id_model_color_shape = id_model_color_shape;
        Id_color = id_color;
        Id_shape = id_shape;
        Name = name;
        Rpd = 0.0;
    }

    public Object_Id_Model_Color_Shape(Context context, JSONObject oJson) {

    }

    public int getId_model_color_shape() {
        return Id_model_color_shape;
    }

    public void setId_model_color_shape(int id_model_color_shape) {
        Id_model_color_shape = id_model_color_shape;
    }

    public int getId_color() {
        return Id_color;
    }

    public void setId_color(int id_color) {
        Id_color = id_color;
    }

    public int getId_shape() {
        return Id_shape;
    }

    public void setId_shape(int id_shape) {
        Id_shape = id_shape;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getRpd() {
        return Rpd;
    }

    public void setRpd(double rpd) {
        Rpd = rpd;
    }
}
