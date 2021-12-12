package com.example.ericschumacher.bouncer.Objects;

import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Shape;

public class Backcover {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Data
    int id;
    Model oModel;
    Color oColor;
    Shape oShape;
    int tState;

    public Backcover() {

    }

    public Model getoModel() {
        return oModel;
    }

    public void setoModel(Model oModel) {
        this.oModel = oModel;
    }

    public Color getoColor() {
        return oColor;
    }

    public void setoColor(Color oColor) {
        this.oColor = oColor;
    }

    public Shape getoShape() {
        return oShape;
    }

    public void setoShape(Shape oShape) {
        this.oShape = oShape;
    }

    public int gettState() {
        return tState;
    }

    public void settState(int tState) {
        this.tState = tState;
    }
}
