package com.example.ericschumacher.bouncer.Objects.Additive;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;

public class Station extends Additive {

    public Station(int id, String name) {
        super(id, name);
    }

    public Station (String name) {
        Name = name;
        if (name.equals(Constants_Intern.STATION_SCREENING)) Id = 2;
        if (name.equals(Constants_Intern.STATION_RECYCLING)) Id = 3;
        if (name.equals(Constants_Intern.STATION_PRESORT)) Id = 4;
        if (name.equals(Constants_Intern.STATION_EXCESS_STOCKING)) Id = 5;
        if (name.equals(Constants_Intern.STATION_LKU_STOCKING)) Id = 6;
        if (name.equals(Constants_Intern.STATION_REUSE)) Id = 7;

    }
}
