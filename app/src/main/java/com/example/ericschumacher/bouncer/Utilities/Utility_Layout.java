package com.example.ericschumacher.bouncer.Utilities;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.R;

public class Utility_Layout {

    public static void setRoundedCorners(Context context, View view, int kColor) {
        // Background
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] {Utility_Density.getDp(context, 16),Utility_Density.getDp(context, 16), Utility_Density.getDp(context, 16), Utility_Density.getDp(context, 16), Utility_Density.getDp(context, 16), Utility_Density.getDp(context, 16), Utility_Density.getDp(context, 16), Utility_Density.getDp(context, 16)});
        shape.setStroke(1, ResourcesCompat.getColor(context.getResources(), kColor, null));
        view.setBackground(shape);
    }

    public static void setImageDrawable(Context context, ImageView iv, int kDrawable) {
        iv.setImageDrawable(ContextCompat.getDrawable(context, kDrawable));
    }

    public static void setBackgroundColor(Context context, View view, int kColor) {
        view.setBackgroundColor(ContextCompat.getColor(context, kColor));
    }

    public static void setBackgroundDrawable(Context context, View view, int kDrawable) {
        view.setBackground(ContextCompat.getDrawable(context, kDrawable));
    }

    public static void setTextColor(Context context, TextView textView, int kColor) {
        textView.setTextColor(ResourcesCompat.getColor(context.getResources(), kColor, null));
    }
}
