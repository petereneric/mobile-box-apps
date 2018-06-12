package com.example.ericschumacher.bouncer.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eric Schumacher on 12.06.2018.
 */

public class Object_SearchResult implements Parcelable {
    private int id;
    private String name;

    public Object_SearchResult(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Object_Choice createFromParcel(Parcel in) {
            return new Object_Choice(in);
        }

        public Object_Choice[] newArray(int size) {
            return new Object_Choice[size];
        }
    };

    public Object_SearchResult(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
    }

    @Override
    public String toString() {
        return "Object_Choice{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
