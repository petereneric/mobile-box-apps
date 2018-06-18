package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public class Object_Model implements Parcelable {
    private int Id;
    private String IMEI;
    private String Name;
    private int IdManufacturer;
    private int IdCharger;
    private int IdBattery;
    private int Exploitation;
    private int Condition;
    private int Shape;

    public Object_Model() {

    }

    public Object_Model(int id, String name) {
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

    public Object_Model(Parcel in){
        this.Id = in.readInt();
        this.IMEI = in.readString();
        this.Name = in.readString();
        this.IdManufacturer = in.readInt();
        this.IdCharger = in.readInt();
        this.IdBattery = in.readInt();
        this.Exploitation = in.readInt();
        this.Condition = in.readInt();
        this.Shape = in.readInt();
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
        parcel.writeInt(this.Exploitation);
        parcel.writeInt(this.Condition);
        parcel.writeInt(this.Shape);
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
                ", Exploitation='" + Exploitation + '\'' +
                ", Condition='" + Condition + '\'' +
                ", Shape='" + Shape + '\'' +
                '}';
    }
}
