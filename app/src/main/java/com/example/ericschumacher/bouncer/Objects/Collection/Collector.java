package com.example.ericschumacher.bouncer.Objects.Collection;

import java.io.Serializable;

public class Collector implements Serializable {

    private int Id;
    private String Name;
    private String NameDetails;
    private String PrenamePerson;
    private String SurnamePerson;
    private String PhoneHome;
    private String PhoneMobile;
    private String Email;
    private String ShippingNameOne;
    private String ShippingNameTwo;
    private String ShippingStreet;
    private String ShippingStreetNumber;
    private String ShippingZip;
    private String ShippingCity;
    private String ShippingCountry;
    private int IdPayee;

    public Collector(int id, String name, String nameDetails, String prenamePerson, String surnamePerson, String phoneHome, String phoneMobile, String email, String shippingNameOne, String shippingNameTwo, String shippingStreet, String shippingStreetNumber, String shippingZip, String shippingCity, String shippingCountry, int idPayee) {
        Id = id;
        Name = name;
        NameDetails = nameDetails;
        PrenamePerson = prenamePerson;
        SurnamePerson = surnamePerson;
        PhoneHome = phoneHome;
        PhoneMobile = phoneMobile;
        Email = email;
        ShippingNameOne = shippingNameOne;
        ShippingNameTwo = shippingNameTwo;
        ShippingStreet = shippingStreet;
        ShippingStreetNumber = shippingStreetNumber;
        ShippingZip = shippingZip;
        ShippingCity = shippingCity;
        ShippingCountry = shippingCountry;
        IdPayee = idPayee;
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

    public String getNameDetails() {
        return NameDetails;
    }

    public void setNameDetails(String nameDetails) {
        NameDetails = nameDetails;
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

    public String getPhoneHome() {
        return PhoneHome;
    }

    public void setPhoneHome(String phoneHome) {
        PhoneHome = phoneHome;
    }

    public String getPhoneMobile() {
        return PhoneMobile;
    }

    public void setPhoneMobile(String phoneMobile) {
        PhoneMobile = phoneMobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getShippingNameOne() {
        return ShippingNameOne;
    }

    public void setShippingNameOne(String shippingNameOne) {
        ShippingNameOne = shippingNameOne;
    }

    public String getShippingNameTwo() {
        return ShippingNameTwo;
    }

    public void setShippingNameTwo(String shippingNameTwo) {
        ShippingNameTwo = shippingNameTwo;
    }

    public String getShippingStreet() {
        return ShippingStreet;
    }

    public void setShippingStreet(String shippingStreet) {
        ShippingStreet = shippingStreet;
    }

    public String getShippingStreetNumber() {
        return ShippingStreetNumber;
    }

    public void setShippingStreetNumber(String shippingStreetNumber) {
        ShippingStreetNumber = shippingStreetNumber;
    }

    public String getShippingZip() {
        return ShippingZip;
    }

    public void setShippingZip(String shippingZip) {
        ShippingZip = shippingZip;
    }

    public String getShippingCity() {
        return ShippingCity;
    }

    public void setShippingCity(String shippingCity) {
        ShippingCity = shippingCity;
    }

    public String getShippingCountry() {
        return ShippingCountry;
    }

    public void setShippingCountry(String shippingCountry) {
        ShippingCountry = shippingCountry;
    }

    public int getIdPayee() {
        return IdPayee;
    }

    public void setIdPayee(int idPayee) {
        IdPayee = idPayee;
    }
}
