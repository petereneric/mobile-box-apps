package com.example.ericschumacher.bouncer.Objects.Collection;

import android.content.Context;

import com.example.ericschumacher.bouncer.Constants.Constants_Extern;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Collector implements Serializable {

    Context context;

    private int Id;
    private String Name;
    private String Type;
    private String NameDetails;
    private String PrenamePerson;
    private String SurnamePerson;
    private String TitlePerson;
    private boolean bAddressFormally;
    private String PhoneHome;
    private String PhoneMobile;
    private String Email;
    private String EmailCC;
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

    public Collector (Context context, JSONObject oJson) {
        this.context = context;

        try {
            if (!oJson.isNull(Constants_Extern.ID)) {
                Id = oJson.getInt(Constants_Extern.ID);
            }
            if (!oJson.isNull(Constants_Extern.NAME)) {
                Name = oJson.getString(Constants_Extern.NAME);
            }
            if (!oJson.isNull(Constants_Extern.NAME_DETAILS)) {
                NameDetails = oJson.getString(Constants_Extern.NAME_DETAILS);
            }
            if (!oJson.isNull(Constants_Extern.TYPE)) {
                Type = oJson.getString(Constants_Extern.TYPE);
            }
            if (!oJson.isNull(Constants_Extern.PRENAME_PERSON)) {
                PrenamePerson = oJson.getString(Constants_Extern.PRENAME_PERSON);
            }
            if (!oJson.isNull(Constants_Extern.SURNAME_PERSON)) {
                SurnamePerson = oJson.getString(Constants_Extern.SURNAME_PERSON);
            }
            if (!oJson.isNull(Constants_Extern.TITLE_PERSON)) {
                TitlePerson = oJson.getString(Constants_Extern.TITLE_PERSON);
            }
            if (!oJson.isNull(Constants_Extern.ADDRESS_FORMALLY)) {
                bAddressFormally = oJson.getBoolean(Constants_Extern.ADDRESS_FORMALLY);
            }
            if (!oJson.isNull(Constants_Extern.PHONE_FIXED_LINE)) {
                PhoneHome = oJson.getString(Constants_Extern.PHONE_FIXED_LINE);
            }
            if (!oJson.isNull(Constants_Extern.PHONE_MOBILE)) {
                PhoneMobile = oJson.getString(Constants_Extern.PHONE_MOBILE);
            }
            if (!oJson.isNull(Constants_Extern.E_MAIL)) {
                Email = oJson.getString(Constants_Extern.E_MAIL);
            }
            if (!oJson.isNull(Constants_Extern.E_MAIL_CC)) {
                EmailCC = oJson.getString(Constants_Extern.E_MAIL_CC);
            }
            if (!oJson.isNull(Constants_Extern.SHIPPING_NAME_ONE)) {
                ShippingNameOne = oJson.getString(Constants_Extern.SHIPPING_NAME_ONE);
            }
            if (!oJson.isNull(Constants_Extern.SHIPPING_NAME_TWO)) {
                ShippingNameTwo = oJson.getString(Constants_Extern.SHIPPING_NAME_TWO);
            }
            if (!oJson.isNull(Constants_Extern.SHIPPING_STREET)) {
                ShippingStreet = oJson.getString(Constants_Extern.SHIPPING_STREET);
            }
            if (!oJson.isNull(Constants_Extern.SHIPPING_STREET_NUMBER)) {
                ShippingStreetNumber = oJson.getString(Constants_Extern.SHIPPING_STREET_NUMBER);
            }
            if (!oJson.isNull(Constants_Extern.SHIPPING_ZIP)) {
                ShippingZip = oJson.getString(Constants_Extern.SHIPPING_ZIP);
            }
            if (!oJson.isNull(Constants_Extern.SHIPPING_CITY)) {
                ShippingCity = oJson.getString(Constants_Extern.SHIPPING_CITY);
            }
            if (!oJson.isNull(Constants_Extern.SHIPPING_COUNTRY)) {
                ShippingCountry = oJson.getString(Constants_Extern.SHIPPING_COUNTRY);
            }
            if (!oJson.isNull(Constants_Extern.ID_PAYEE)) {
                IdPayee = oJson.getInt(Constants_Extern.ID_PAYEE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public JSONObject getJson() {
        JSONObject oJson = new JSONObject();

        return oJson;
    }
}
