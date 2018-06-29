package com.example.ericschumacher.bouncer.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eric Schumacher on 27.06.2018.
 */

public class Object_Choice_Color extends Object_Choice {

    private String hexCode;

    public Object_Choice_Color(int id, String name, String hexCode) {
        super(id, name);
        this.hexCode = hexCode;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Object_Choice_Color createFromParcel(Parcel in) {
            return new Object_Choice_Color(in);
        }

        public Object_Choice_Color[] newArray(int size) {
            return new Object_Choice_Color[size];
        }
    };

    public Object_Choice_Color(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.urlName =  in.readString();
        this.hexCode =  in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.urlName);
        parcel.writeString(this.hexCode);
    }

    @Override
    public String toString() {
        return "Object_Choice{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", urlName='" + urlName + '\'' +
                ", hexCode='" + hexCode + '\'' +
                '}';
    }
}
