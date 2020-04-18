package com.example.ericschumacher.bouncer.Utilities;

import android.content.Context;

public class Utility_Density {

    public static int getDp(Context context,  int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(dp * density);
    }
}
