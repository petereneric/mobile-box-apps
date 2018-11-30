package com.example.ericschumacher.bouncer.Objects.Collection;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable{

    int Id;
    int kCollector;
    String cCollectorName;
    int kPayee;
    Date dLastUpdate;
    int nDevices;
    int nRecycling;
    int nReuse;
    double pQuote;
    double sPayment;
    double aPayment;
    String cNotes;
    boolean bSelected;
    int kBillPayee;
    int kBillCollector;

    public Record(int id, int kCollector, int kPayee, Date dLastUpdate, int nDevices, int nRecycling, int nReuse, double pQuote, double sPayment, double aPayment, String cNotes,
                  boolean bSelected, int kBillPayee, int kBillCollector) {
        Id = id;
        this.kCollector = kCollector;
        this.kPayee = kPayee;
        this.dLastUpdate = dLastUpdate;
        this.nDevices = nDevices;
        this.nRecycling = nRecycling;
        this.nReuse = nReuse;
        this.pQuote = pQuote;
        this.sPayment = sPayment;
        this.aPayment = aPayment;
        this.cNotes = cNotes;
        this.bSelected = bSelected;
        this.kBillPayee = kBillPayee;
        this.kBillCollector = kBillCollector;
    }

    public Record(int id, Date dLastUpdate, int nRecycling, int nReuse, int nDevices, String cName) {
        Id = id;
        this.dLastUpdate = dLastUpdate;
        this.nRecycling = nRecycling;
        this.nReuse = nReuse;
        this.nDevices = nDevices;
        this.cCollectorName = cName;
    }

    public Record(int id, String cName) {
        Id = id;
        this.cCollectorName = cName;
        nRecycling = 0;
        nReuse = 0;
    }

    public int incrementReuse() {
        return nReuse++;
    }

    public int incrementRecycling() {
        nRecycling = nRecycling+1;
        return nRecycling;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getkCollector() {
        return kCollector;
    }

    public void setkCollector(int kCollector) {
        this.kCollector = kCollector;
    }

    public String getcCollectorName() {
        return cCollectorName;
    }

    public void setcCollectorName(String cCollectorName) {
        this.cCollectorName = cCollectorName;
    }

    public int getkPayee() {
        return kPayee;
    }

    public void setkPayee(int kPayee) {
        this.kPayee = kPayee;
    }

    public Date getdLastUpdate() {
        return dLastUpdate;
    }

    public void setdLastUpdate(Date dLastUpdate) {
        this.dLastUpdate = dLastUpdate;
    }

    public int getnDevices() {
        return nDevices;
    }

    public void setnDevices(int nDevices) {
        this.nDevices = nDevices;
    }

    public int getnRecycling() {
        return nRecycling;
    }

    public void setnRecycling(int nRecycling) {
        this.nRecycling = nRecycling;
    }

    public int getnReuse() {
        return nReuse;
    }

    public void setnReuse(int nReuse) {
        this.nReuse = nReuse;
    }

    public double getpQuote() {
        return pQuote;
    }

    public void setpQuote(double pQuote) {
        this.pQuote = pQuote;
    }

    public double getsPayment() {
        return sPayment;
    }

    public void setsPayment(double sPayment) {
        this.sPayment = sPayment;
    }

    public double getaPayment() {
        return aPayment;
    }

    public void setaPayment(double aPayment) {
        this.aPayment = aPayment;
    }

    public String getcNotes() {
        return cNotes;
    }

    public void setcNotes(String cNotes) {
        this.cNotes = cNotes;
    }

    public boolean isbSelected() {
        return bSelected;
    }

    public void setbSelected(boolean bSelected) {
        this.bSelected = bSelected;
    }

    public int getkBillPayee() {
        return kBillPayee;
    }

    public void setkBillPayee(int kBillPayee) {
        this.kBillPayee = kBillPayee;
    }

    public int getkBillCollector() {
        return kBillCollector;
    }

    public void setkBillCollector(int kBillCollector) {
        this.kBillCollector = kBillCollector;
    }
}
