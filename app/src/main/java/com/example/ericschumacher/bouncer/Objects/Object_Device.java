package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public class Object_Device implements Parcelable {
    private int Id;
    private String IMEI;
    private String Name;
    private int IdManufacturer;
    private int IdCharger;
    private int IdBattery;
    private int IdColor;
    private int IdModelColor;
    private String nameColor;
    private int Exploitation;
    private int Condition;
    private int Shape;
    private String nameManufacturer;
    private String nameCharger;
    private String nameBattery;
    private int LKU;

    public Object_Device() {
        Id = 0;
        Condition = Constants_Intern.CONDITION_NOT_SET;
        Condition = Constants_Intern.SHAPE_NOT_SET;
    }

    public Object_Device(int id, String name) {
        Id = id;
        Name = name;
        Condition = Constants_Intern.CONDITION_NOT_SET;
        Condition = Constants_Intern.SHAPE_NOT_SET;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getIdManufacturer() {
        return IdManufacturer;
    }

    public void setIdManufacturer(int idManufacturer) {
        IdManufacturer = idManufacturer;
    }

    public int getIdCharger() {
        return IdCharger;
    }

    public void setIdCharger(int idCharger) {
        IdCharger = idCharger;
    }

    public int getIdBattery() {
        return IdBattery;
    }

    public void setIdBattery(int idBattery) {
        IdBattery = idBattery;
    }

    public int getExploitation() {
        return Exploitation;
    }

    public void setExploitation(int exploitation) {
        Exploitation = exploitation;
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

    public String getNameManufacturer() {
        return nameManufacturer;
    }

    public void setNameManufacturer(String nameManufacturer) {
        this.nameManufacturer = nameManufacturer;
    }

    public String getNameCharger() {
        return nameCharger;
    }

    public void setNameCharger(String nameCharger) {
        this.nameCharger = nameCharger;
    }

    public String getNameBattery() {
        return nameBattery;
    }

    public void setNameBattery(String nameBattery) {
        this.nameBattery = nameBattery;
    }

    public int getIdColor() {
        return IdColor;
    }

    public void setIdColor(int idColor) {
        IdColor = idColor;
    }

    public String getNameColor() {
        return nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
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

    public String getExploitationForScreen(Context context) {
        if (Exploitation == Constants_Intern.EXPLOITATION_RECYCLING) {
            return context.getString(R.string.recycling);
        }
        if (Exploitation == Constants_Intern.EXPLOITATION_REUSE) {
            return context.getString(R.string.reuse);
        }
        return null;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Object_Choice createFromParcel(Parcel in) {
            return new Object_Choice(in);
        }

        public Object_Choice[] newArray(int size) {
            return new Object_Choice[size];
        }
    };

    public Object_Device(Parcel in){
        this.Id = in.readInt();
        this.IMEI = in.readString();
        this.Name = in.readString();
        this.IdManufacturer = in.readInt();
        this.IdCharger = in.readInt();
        this.IdBattery = in.readInt();
        this.IdColor = in.readInt();
        this.IdModelColor = in.readInt();
        this.Exploitation = in.readInt();
        this.Condition = in.readInt();
        this.Shape = in.readInt();
        this.nameManufacturer = in.readString();
        this.nameCharger = in.readString();
        this.nameBattery = in.readString();
        this.nameColor = in.readString();
        this.LKU = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.Id);
        parcel.writeString(this.IMEI);
        parcel.writeString(this.Name);
        parcel.writeInt(this.IdManufacturer);
        parcel.writeInt(this.IdCharger);
        parcel.writeInt(this.IdBattery);
        parcel.writeInt(this.IdColor);
        parcel.writeInt(this.IdModelColor);
        parcel.writeInt(this.Exploitation);
        parcel.writeInt(this.Condition);
        parcel.writeInt(this.Shape);
        parcel.writeString(this.nameManufacturer);
        parcel.writeString(this.nameCharger);
        parcel.writeString(this.nameBattery);
        parcel.writeString(this.nameColor);
        parcel.writeInt(this.LKU);
    }

    @Override
    public String toString() {
        return "Object_Choice{" +
                "id='" + Id + '\'' +
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
                ", nameColor='" + nameColor + '\'' +
                ", LKU='" + LKU + '\'' +
                '}';
    }
}
