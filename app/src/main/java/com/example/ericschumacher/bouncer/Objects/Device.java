package com.example.ericschumacher.bouncer.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Additive.Station;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Shape;

import java.io.Serializable;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public class Device extends Model implements Serializable {

    private int IdDevice;

    private String IMEI = Constants_Intern.IMEI_UNKNOWN;
    private int LKU = Constants_Intern.LKU_UNKNOWN;
    private int Condition = Constants_Intern.CONDITION_UNKNOWN;

    private int Destination = Constants_Intern.DESTINATION_UNKNOWN;
    private Station Station = new Station(0, "null");

    private Color VariationColor = null;
    private Shape VariationShape = null;

    public Device() {
        super();
        IdDevice = Constants_Intern.ID_UNKNOWN;
    }

    public Device (String imei) {
        IMEI = imei;
    }

    public Device (int idModel, String name, int defaultExploitation, int idManufacturer, String nameManufacturer, int idBattery, String nameBattery, int idCharger, String nameCharger, int idDevice,
                   String imei, int lku, int condition,
                   int destination, int id_station, String name_station, int idVariationColor, String nameColor, String hexCode, int idVariationShape, String nameShape) {
        super(idModel, name, defaultExploitation, idManufacturer, nameManufacturer, idBattery, nameBattery, idCharger, nameCharger);

        IdDevice = idDevice;
        IMEI = imei;
        LKU = lku;
        Condition = condition;

        Destination = destination;
        Station = new Station(id_station, name_station);

        VariationColor = new Color(idVariationColor, nameColor, hexCode, new Manufacturer(idManufacturer, nameManufacturer), new Model(idModel, name));
        VariationShape = new Shape(idVariationShape, nameShape);

    }

    public int getIdDevice() {
        return IdDevice;
    }

    public void setIdDevice(int idDevice) {
        IdDevice = idDevice;
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
        return false;
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

    public Station getStation() {
        return Station;
    }

    public void setStation(Station station) {
        Station = station;
    }

    public Color getVariationColor() {
        return VariationColor;
    }

    public void setVariationColor(Color variationColor) {
        VariationColor = variationColor;
    }

    public Shape getVariationShape() {
        return VariationShape;
    }

    public void setVariationShape(Shape variationShape) {
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
                "IdModel='" + IdModel + '\'' +
                ", IMEI_UNKNOWN='" + IMEI + '\'' +
                ", Name='" + Name + '\'' +
                ", LKU='" + LKU + '\'' +
                '}';
    }
}
