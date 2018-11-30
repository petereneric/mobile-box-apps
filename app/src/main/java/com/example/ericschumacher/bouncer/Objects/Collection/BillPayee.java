package com.example.ericschumacher.bouncer.Objects.Collection;

import java.util.Date;

public class BillPayee {

    int Id;
    Date dCreation;
    String cName;
    String cAccountHolder;
    String cIban;
    double sPayment;

    public BillPayee(int id, Date dCreation, String cName, String cAccountHolder, String cIban, double sPayment) {
        Id = id;
        this.dCreation = dCreation;
        this.cName = cName;
        this.cAccountHolder = cAccountHolder;
        this.cIban = cIban;
        this.sPayment = sPayment;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getdCreation() {
        return dCreation;
    }

    public void setdCreation(Date dCreation) {
        this.dCreation = dCreation;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcAccountHolder() {
        return cAccountHolder;
    }

    public void setcAccountHolder(String cAccountHolder) {
        this.cAccountHolder = cAccountHolder;
    }

    public String getcIban() {
        return cIban;
    }

    public void setcIban(String cIban) {
        this.cIban = cIban;
    }

    public double getsPayment() {
        return sPayment;
    }

    public void setsPayment(double sPayment) {
        this.sPayment = sPayment;
    }
}
