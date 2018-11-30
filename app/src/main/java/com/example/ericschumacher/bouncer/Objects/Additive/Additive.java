package com.example.ericschumacher.bouncer.Objects.Additive;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ericschumacher.bouncer.Objects.Object_Choice;

import java.io.Serializable;

/**
 * Created by Eric on 13.07.2018.
 */

public class Additive implements Serializable, Parcelable {
    int Id;
    String Name;
    String UrlName;

    public Additive(int id, String name) {
        Id = id;
        Name = name;
    }

    public Additive() {
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

    public String getUrlName() {
        return UrlName;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Object_Choice createFromParcel(Parcel in) {
            return new Object_Choice(in);
        }

        public Object_Choice[] newArray(int size) {
            return new Object_Choice[size];
        }
    };

    public Additive(Parcel in){
        this.Id = in.readInt();
        this.Name = in.readString();
        this.UrlName =  in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.Id);
        parcel.writeString(this.Name);
        parcel.writeString(this.UrlName);
    }

    @Override
    public String toString() {
        return "Object_Choice{" +
                "id='" + Id + '\'' +
                ", name='" + Name + '\'' +
                ", UrlName='" + UrlName + '\'' +
                '}';
    }
}
