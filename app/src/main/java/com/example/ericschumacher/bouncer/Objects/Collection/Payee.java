package com.example.ericschumacher.bouncer.Objects.Collection;

import java.io.Serializable;

public class Payee implements Serializable {
    private int Id;
    private String Name;
    private String PrenamePerson;
    private String SurnamePerson;
    private String Phone;
    private String Email;
    private String AccountHolder;
    private String Iban;
    private int BillType;
    private int IdRewardModel;

    public Payee() {
    }

    public Payee(int id, String name, String prenamePerson, String surnamePerson, String phone, String email, String accountHolder, String iban, int billType, int idRewardModel) {
        Id = id;
        Name = name;
        PrenamePerson = prenamePerson;
        SurnamePerson = surnamePerson;
        Phone = phone;
        Email = email;
        AccountHolder = accountHolder;
        Iban = iban;
        BillType = billType;
        IdRewardModel = idRewardModel;
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

    public String getAccountHolder() {
        return AccountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        AccountHolder = accountHolder;
    }

    public String getIban() {
        return Iban;
    }

    public void setIban(String iban) {
        Iban = iban;
    }

    public int getBillType() {
        return BillType;
    }

    public void setBillType(int billType) {
        BillType = billType;
    }

    public int getIdRewardModel() {
        return IdRewardModel;
    }

    public String getPrenamePerson() {
        return PrenamePerson;
    }

    public void setPrenamePerson(String prenamePerson) {
        PrenamePerson = prenamePerson;
    }

    public String getSurnamePerson() {
        return SurnamePerson;
    }

    public void setSurnamePerson(String surnamePerson) {
        SurnamePerson = surnamePerson;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setIdRewardModel(int idRewardModel) {
        IdRewardModel = idRewardModel;


    }
}
