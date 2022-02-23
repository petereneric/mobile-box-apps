package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.ModelWipe;

import java.util.ArrayList;

public interface Interface_Fragment_Wiper {
    public ArrayList<ModelWipe> getModelWipes();
    public void changeModelWipes(boolean updateModelWipes);
}
