package com.example.ericschumacher.bouncer.Objects.Additive;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eric on 13.07.2018.
 */

public class Variation_Color extends Additive {

    String HexCode;

    public Variation_Color(int id, String name, String hexCode) {
        super(id, name);
        HexCode = hexCode;
    }

    public String getHexCode() {
        return HexCode;
    }

    public void setHexCode(String hexCode) {
        HexCode = hexCode;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Variation_Color createFromParcel(Parcel in) {
            return new Variation_Color(in);
        }

        public Variation_Color[] newArray(int size) {
            return new Variation_Color[size];
        }
    };

    public Variation_Color(Parcel in){
        super(in);
        this.HexCode =  in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.HexCode);
    }

    @Override
    public String toString() {
        return "Object_Choice{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", UrlName='" + UrlName + '\'' +
                ", HexCode='" + HexCode + '\'' +
                '}';
    }
}
