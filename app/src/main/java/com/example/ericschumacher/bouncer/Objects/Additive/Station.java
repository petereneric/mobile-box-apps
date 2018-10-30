package com.example.ericschumacher.bouncer.Objects.Additive;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;

public class Station extends Additive {

    public Station(int id, String name) {
        super(id, name);
    }

    public Station (int id) {
        Id = id;
        if (id == Constants_Intern.STATION_SELECTION_INT) Name = Constants_Intern.STATION_SELECTION;
        if (id == Constants_Intern.STATION_RECYCLING_INT) Name = Constants_Intern.STATION_RECYCLING;
        if (id == Constants_Intern.STATION_PRESORT_INT) Name = Constants_Intern.STATION_PRESORT;
        if (id == Constants_Intern.STATION_EXCESS_STOCKING_INT) Name = Constants_Intern.STATION_EXCESS_STOCKING;
        if (id == Constants_Intern.STATION_LKU_STOCKING_INT) Name = Constants_Intern.STATION_LKU_STOCKING;
        if (id == Constants_Intern.STATION_UNKNOWN_INT) Name = Constants_Intern.STATION_UNKNOWN;
    }

    public Station (String name) {
        Name = name;
        if (name.equals(Constants_Intern.STATION_SELECTION)) Id = 2;
        if (name.equals(Constants_Intern.STATION_RECYCLING)) Id = 3;
        if (name.equals(Constants_Intern.STATION_PRESORT)) Id = 4;
        if (name.equals(Constants_Intern.STATION_EXCESS_STOCKING)) Id = 5;
        if (name.equals(Constants_Intern.STATION_LKU_STOCKING)) Id = 6;
        if (name.equals(Constants_Intern.STATION_UNKNOWN)) Id = 8;
    }

    public static int getId (String name) {
        if (name.equals(Constants_Intern.STATION_SELECTION)) return 2;
        if (name.equals(Constants_Intern.STATION_RECYCLING)) return 3;
        if (name.equals(Constants_Intern.STATION_PRESORT)) return 4;
        if (name.equals(Constants_Intern.STATION_EXCESS_STOCKING)) return 5;
        if (name.equals(Constants_Intern.STATION_LKU_STOCKING)) return  6;
        if (name.equals(Constants_Intern.STATION_REUSE)) return 7;
        if (name.equals(Constants_Intern.STATION_UNKNOWN)) return 8;
        return 0;
    }

}
