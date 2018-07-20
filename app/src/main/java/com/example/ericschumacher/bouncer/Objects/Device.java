package com.example.ericschumacher.bouncer.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Shape;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public class Device extends Model implements Parcelable {

    private String IMEI = Constants_Intern.IMEI_UNKNOWN;
    private int LKU = Constants_Intern.LKU_UNKNOWN;
    private int Condition = Constants_Intern.CONDITION_UNKNOWN;

    private int Destination = Constants_Intern.DESTINATION_UNKNOWN;
    private int Station = Constants_Intern.CURRENT_STATION_UNKNOWN;

    private Variation_Color VariationColor = null;
    private Variation_Shape VariationShape = null;

    public Device() {

    }

    public Device (String imei) {
        IMEI = imei;
    }

    public Device (int id, String name, int defaultExploitation, int idManufacturer, String nameManufacturer, int idBattery, String nameBattery, int idCharger, String nameCharger, String imei, int lku, int condition,
                   int destination, int station, int idVariationColor, String nameColor, String hexCode, int idVariationShape, String shape) {
        super(id, name, defaultExploitation, idManufacturer, nameManufacturer, idBattery, nameBattery, idCharger, nameCharger);

        IMEI = imei;
        LKU = lku;
        Condition = condition;

        Destination = destination;
        Station = station;

        VariationColor = new Variation_Color(idVariationColor, nameColor, hexCode);
        VariationShape = new Variation_Shape(idVariationShape, shape);

    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getTAC() {
        return IMEI.substring(0,8);
    }

    public boolean testMode() {
        return IMEI.equals("000000000000000");
    }

    public int getLKU() {
        return LKU;
    }

    public void setLKU(int LKU) {
        this.LKU = LKU;
    }

    public int getCondition() {
        return Condition;
    }

    public void setCondition(int condition) {
        Condition = condition;
    }

    public int getDestination() {
        return Destination;
    }

    public void setDestination(int destination) {
        Destination = destination;
    }

    public int getStation() {
        return Station;
    }

    public void setStation(int station) {
        Station = station;
    }

    public Variation_Color getVariationColor() {
        return VariationColor;
    }

    public void setVariationColor(Variation_Color variationColor) {
        VariationColor = variationColor;
    }

    public Variation_Shape getVariationShape() {
        return VariationShape;
    }

    public void setVariationShape(Variation_Shape variationShape) {
        VariationShape = variationShape;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        public Device[] newArray(int size) {
            return new Device[size];
        }
    };

    public Device(Parcel in){
        super(in);
        this.IMEI = in.readString();
        this.LKU = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.IMEI);
        parcel.writeInt(this.LKU);
    }

    @Override
    public String toString() {
        return "Object_Choice{" +
                "id='" + Id + '\'' +
                ", IMEI_UNKNOWN='" + IMEI + '\'' +
                ", Name='" + Name + '\'' +
                ", LKU='" + LKU + '\'' +
                '}';
    }
}
