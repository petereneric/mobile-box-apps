package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Diagnose;
import com.example.ericschumacher.bouncer.Objects.ModelCheck;

import java.util.ArrayList;

public interface Interface_Fragment_Checker {
    public ArrayList<Diagnose> getDiagnoses();
    public ArrayList<ModelCheck> getModelChecks();
    public void addDiagnose();
    public void editDiagnose(int position);
    public Diagnose getDiagnose();
    public void setDiagnose(Diagnose diagnose);
    public void deleteDiagnose(Diagnose diagnose);
    public void pauseDiagnose();
    public void showHandler();
    public void showTab(Integer position);
    public void diagnoseChange();
    public void changeModelChecks(boolean updateModelChecks);
    public void loadModelChecks();
}
