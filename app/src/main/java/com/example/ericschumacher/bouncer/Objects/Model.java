package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.R;

/**
 * Created by Eric Schumacher on 08.07.2018.
 */

public class Model implements Parcelable {
    int IdModel = Constants_Intern.ID_UNKNOWN;
    String Name;

    Battery Battery = null;
    Charger Charger = null;
    Manufacturer Manufacturer = null;

    int DefaultExploitation = 0;

    public Model() {
    }

    public Model(int idModel, String name) {
        IdModel = idModel;
        Name = name;
    }

    public Model(int idModel, String name, int defaultExploitation, int idManufacturer, String nameManufacturer, int idBattery, String nameBattery, int idCharger, String nameCharger) {
        IdModel = idModel;
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

    public com.example.ericschumacher.bouncer.Objects.Additive.Battery getBattery() {
        return Battery;
    }

    public void setBattery(com.example.ericschumacher.bouncer.Objects.Additive.Battery battery) {
        Battery = battery;
    }

    public com.example.ericschumacher.bouncer.Objects.Additive.Charger getCharger() {
        return Charger;
    }

    public void setCharger(com.example.ericschumacher.bouncer.Objects.Additive.Charger charger) {
        Charger = charger;
    }

    public com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer manufacturer) {
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
                return Constants_Intern.UNKOWN;
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
        this.IdModel = in.readInt();
        this.Name = in.readString();
        this.Battery = in.readParcelable(Battery.class.getClassLoader());
        this.Charger = in.readParcelable(Charger.class.getClassLoader());
        this.Manufacturer = in.readParcelable(Manufacturer.class.getClassLoader());
        this.DefaultExploitation = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.IdModel);
        parcel.writeString(this.Name);
        parcel.writeParcelable(Battery, i);
        parcel.writeParcelable(Charger, i);
        parcel.writeParcelable(Manufacturer, i);
        parcel.writeInt(this.DefaultExploitation);
    }

    @Override
    public String toString() {
        return "Object_Choice{" +
                "IdModel='" + IdModel + '\'' +
                ", Name='" + Name + '\'' +
                ", Exploitation='" + DefaultExploitation + '\'' +
                '}';
    }

}
