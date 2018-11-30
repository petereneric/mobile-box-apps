package com.example.ericschumacher.bouncer.Utilities;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utility_Keyboard {

    public static void openKeyboard(Context context, View v) {
        InputMethodManager inputMethodManager =  (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        v.requestFocus();
    }
}
