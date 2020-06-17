package com.example.ericschumacher.bouncer.Utilities;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

public class Utility_Image {

    public static void setImageResource(Context context, ImageView imageView, int kDrawable, Integer kColor) {
        imageView.setImageDrawable(ContextCompat.getDrawable(context, kDrawable));
        if (kColor != null) {
            imageView.setColorFilter(ContextCompat.getColor(context, kColor), android.graphics.PorterDuff.Mode.SRC_IN);
        }

    }
}
