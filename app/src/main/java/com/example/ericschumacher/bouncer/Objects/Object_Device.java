package com.example.ericschumacher.bouncer.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public class Object_Device extends Object_Model implements Parcelable {

    private String IMEI;
    private int IdColor;
    private int IdModelColor;
    private String NameColor;
    private String NameShape;
    private int IdModelColorShape;
    private int Condition;
    private int Shape;
    private int LKU;

    public Object_Device() {
        IdModel = 0;
        Condition = Constants_Intern.CONDITION_NOT_SET;
        Condition = Constants_Intern.SHAPE_NOT_SET;
    }

    public Object_Device(int id, String name) {
        IdModel = id;
        Name = name;
        Condition = Constants_Intern.CONDITION_NOT_SET;
        Condition = Constants_Intern.SHAPE_NOT_SET;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public int getCondition() {
        return Condition;
    }

    public void setCondition(int condition) {
        Condition = condition;
    }

    public int getShape() {
        return Shape;
    }

    public void setShape(int shape) {
        Shape = shape;
    }

    public int getIdColor() {
        return IdColor;
    }

    public void setIdColor(int idColor) {
        IdColor = idColor;
    }

    public String getNameColor() {
        return NameColor;
    }

    public void setNameColor(String nameColor) {
        this.NameColor = nameColor;
    }

    public int getIdModelColor() {
        return IdModelColor;
    }

    public void setIdModelColor(int idModelColor) {
        IdModelColor = idModelColor;
    }

    public int getLKU() {
        return LKU;
    }

    public void setLKU(int LKU) {
        this.LKU = LKU;
    }

    public String getNameShape() {
        return NameShape;
    }

    public void setNameShape(String nameShape) {
        this.NameShape = nameShape;
    }

    public int getIdModelColorShape() {
        return IdModelColorShape;
    }

    public void setIdModelColorShape(int idModelColorShape) {
        this.IdModelColorShape = idModelColorShape;
    }

    public String getTAC() {
        return IMEI.substring(0,8);
    }

    public boolean testMode() {
        return IMEI.equals("000000000000000");
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Object_Device createFromParcel(Parcel in) {
            return new Object_Device(in);
        }

        public Object_Device[] newArray(int size) {
            return new Object_Device[size];
        }
    };

    public Object_Device(Parcel in){
        super(in);
        this.IMEI = in.readString();
        this.IdColor = in.readInt();
        this.IdModelColor = in.readInt();
        this.Condition = in.readInt();
        this.Shape = in.readInt();
        this.NameColor = in.readString();
        this.LKU = in.readInt();
        this.IdModelColorShape = in.readInt();
        this.NameShape = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.IMEI);
        parcel.writeInt(this.IdColor);
        parcel.writeInt(this.IdModelColor);
        parcel.writeInt(this.Condition);
        parcel.writeInt(this.Shape);
        parcel.writeString(this.NameColor);
        parcel.writeInt(this.LKU);
        parcel.writeInt(this.IdModelColorShape);
        parcel.writeString(this.NameShape);
    }

    @Override
    public String toString() {
        return "Object_Choice{" +
                "id='" + IdModel + '\'' +
                ", IMEI='" + IMEI + '\'' +
                ", Name='" + Name + '\'' +
                ", IdManufacturer='" + IdManufacturer + '\'' +
                ", IdCharger='" + IdCharger + '\'' +
                ", IdCharger='" + IdCharger + '\'' +
                ", IdBattery='" + IdBattery + '\'' +
                ", IdColor='" + IdColor + '\'' +
                ", IdModelColor='" + IdModelColor + '\'' +
                ", Exploitation='" + Exploitation + '\'' +
                ", Condition='" + Condition + '\'' +
                ", Shape='" + Shape + '\'' +
                ", nameManufacturer='" + nameManufacturer + '\'' +
                ", nameCharger='" + nameCharger + '\'' +
                ", NameColor='" + NameColor + '\'' +
                ", LKU='" + LKU + '\'' +
                ", IdModelColorShape='" + IdModelColorShape + '\'' +
                ", NameShape='" + NameShape + '\'' +
                '}';
    }
}
