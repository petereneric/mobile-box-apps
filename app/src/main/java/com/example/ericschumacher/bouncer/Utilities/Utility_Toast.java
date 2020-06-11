package com.example.ericschumacher.bouncer.Utilities;

import android.content.Context;
import android.widget.Toast;

public class Utility_Toast {

    public static void show(Context context, int idString) {
        Toast.makeText(context, context.getString(idString), Toast.LENGTH_LONG).show();
    }

    public static void showString(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
