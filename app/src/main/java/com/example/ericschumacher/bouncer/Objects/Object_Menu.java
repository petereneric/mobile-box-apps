package com.example.ericschumacher.bouncer.Objects;

import android.content.Intent;

public class Object_Menu {

    String Name;
    Intent IntentActivity;
    int IdColor;

    public Object_Menu(String name, Intent intentActivity, int idColor) {
        Name = name;
        IntentActivity = intentActivity;
        IdColor = idColor;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Intent getIntentActivity() {
        return IntentActivity;
    }

    public void setIntentActivity(Intent intentActivity) {
        IntentActivity = intentActivity;
    }

    public int getIdColor() {
        return IdColor;
    }

    public void setIdColor(int idColor) {
        IdColor = idColor;
    }
}
