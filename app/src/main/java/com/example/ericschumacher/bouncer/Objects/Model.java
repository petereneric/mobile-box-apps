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

public class Model implements Parcelable {
    int Id = Constants_Intern.ID_UNKNOWN;
    String Name;

    Battery Battery = null;
    Charger Charger = null;
    Manufacturer Manufacturer = null;

    int DefaultExploitation;

    public Model() {

    }

    public Model(int id, String name, int defaultExploitation, int idManufacturer, String nameManufacturer, int idBattery, String nameBattery, int idCharger, String nameCharger) {
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

    public com.example.ericschumacher.bouncer.Objects.Supplement.Battery getBattery() {
        return Battery;
    }

    public void setBattery(com.example.ericschumacher.bouncer.Objects.Supplement.Battery battery) {
        Battery = battery;
    }

    public com.example.ericschumacher.bouncer.Objects.Supplement.Charger getCharger() {
        return Charger;
    }

    public void setCharger(com.example.ericschumacher.bouncer.Objects.Supplement.Charger charger) {
        Charger = charger;
    }

    public com.example.ericschumacher.bouncer.Objects.Supplement.Manufacturer getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(com.example.ericschumacher.bouncer.Objects.Supplement.Manufacturer manufacturer) {
        Manufacturer = manufacturer;
    }

    public int getDefaultExploitation() {
        return DefaultExploitation;
    }

    public void setDefaultExploitation(int defaultExploitation) {
        DefaultExploitation = defaultExploitation;
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
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public Model(Parcel in){
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
