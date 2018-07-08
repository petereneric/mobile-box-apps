package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

/**
 * Created by Eric Schumacher on 08.07.2018.
 */

public class Object_Model implements Parcelable {
    int IdModel;
    String Name;
    int IdManufacturer;
    int IdCharger;
    int IdBattery;
    int Exploitation;
    String nameManufacturer;
    String nameCharger;
    String nameBattery;
    int[] listLKU;

    public Object_Model() {

    }

    public Object_Model(int idModel, String name) {
        IdModel = idModel;
        Name = name;
    }

    public int getIdModel() {
        return IdModel;
    }

    public void setIdModel(int idModel) {
        IdModel = idModel;
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
        public Object_Model createFromParcel(Parcel in) {
            return new Object_Model(in);
        }

        public Object_Model[] newArray(int size) {
            return new Object_Model[size];
        }
    };

    public Object_Model(Parcel in){
        this.IdModel = in.readInt();
        this.Name = in.readString();
        this.IdManufacturer = in.readInt();
        this.IdCharger = in.readInt();
        this.IdBattery = in.readInt();
        this.Exploitation = in.readInt();
        this.nameManufacturer = in.readString();
        this.nameCharger = in.readString();
        this.nameBattery = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.IdModel);
        parcel.writeString(this.Name);
        parcel.writeInt(this.IdManufacturer);
        parcel.writeInt(this.IdCharger);
        parcel.writeInt(this.IdBattery);
        parcel.writeInt(this.Exploitation);
        parcel.writeString(this.nameManufacturer);
        parcel.writeString(this.nameCharger);
        parcel.writeString(this.nameBattery);
    }

    @Override
    public String toString() {
        return "Object_Choice{" +
                "id='" + IdModel + '\'' +
                ", Name='" + Name + '\'' +
                ", IdManufacturer='" + IdManufacturer + '\'' +
                ", IdCharger='" + IdCharger + '\'' +
                ", IdCharger='" + IdCharger + '\'' +
                ", IdBattery='" + IdBattery + '\'' +
                ", Exploitation='" + Exploitation + '\'' +
                ", nameManufacturer='" + nameManufacturer + '\'' +
                ", nameCharger='" + nameCharger + '\'' +
                '}';
    }

}
