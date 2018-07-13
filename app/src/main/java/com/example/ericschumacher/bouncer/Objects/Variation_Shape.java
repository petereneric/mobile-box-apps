package com.example.ericschumacher.bouncer.Objects;

/**
 * Created by Eric on 13.07.2018.
 */

public class Variation_Shape {

    int Id;
    int Shape;

    public Variation_Shape(int id, int shape) {
        Id = id;
        Shape = shape;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getShape() {
        return Shape;
    }

    public void setShape(int shape) {
        Shape = shape;
    }
}
