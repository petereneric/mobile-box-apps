package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Supplement.Battery;
import com.example.ericschumacher.bouncer.Objects.Supplement.Charger;
import com.example.ericschumacher.bouncer.Objects.Supplement.Manufacturer;
import com.example.ericschumacher.bouncer.R;

/**
 * Created by Eric Schumacher on 08.07.2018.
 */

public class Object_Model implements Parcelable {
    int Id;
    String Name;

    Battery Battery = null;
    Charger Charger = null;
    Manufacturer Manufacturer = null;

    int DefaultExploitation;

    public Object_Model() {

    }

    public Object_Model(int id, String name, int defaultExploitation, int idManufacturer, String nameManufacturer, int idBattery, String nameBattery, int idCharger, String nameCharger) {
        Id = id;
        Name = name;
        DefaultExploitation = defaultExploitation;

        if (nameBattery != null) {
            Battery = new Battery(idBattery, nameBattery);
        }
        if (nameCharger != null) {
            Charger = new Charger(idCharger, nameCharger);
        }
        if (nameManufacturer != null) {
            Manufacturer = new Manufacturer(idManufacturer, nameManufacturer);
        }

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

    public int getExploitation() {
        return DefaultExploitation;
    }

    public void setExploitation(int exploitation) {
        DefaultExploitation = exploitation;
    }

    public String getExploitationForScreen(Context context) {
        switch (DefaultExploitation) {
            case Constants_Intern.EXPLOITATION_NULL:
                return context.getString(R.string.not_defined);
            case Constants_Intern.EXPLOITATION_RECYCLING:
                return context.getString(R.string.recycling);
            case Constants_Intern.EXPLOITATION_REUSE:
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
        this.Id = in.readInt();
        this.Name = in.readString();
        this.DefaultExploitation = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.Id);
        parcel.writeString(this.Name);
        parcel.writeInt(this.DefaultExploitation);
    }

    @Override
    public String toString() {
        return "Object_Choice{" +
                "id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Exploitation='" + DefaultExploitation + '\'' +
                '}';
    }

}
