package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Diagnose;

import java.util.ArrayList;

public interface Interface_Fragment_Checker {
    public ArrayList<Diagnose> getDiagnoses();
    public void addDiagnose();
    public void editDiagnose(int position);
    public Diagnose getDiagnose();
    public void deleteDiagnose();
    public void pauseDiagnose();
    public void showHandler();
    public void showTab(int position);
}
