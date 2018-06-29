package com.example.ericschumacher.bouncer.Utilities;

import android.content.Context;

import com.dymo.label.framework.Framework;

/**
 * Created by Eric on 29.06.2018.
 */

public class Utility_Printer
{
    static public Utility_Printer instance_;

    static public Utility_Printer instance(Context context)
    {
        if (instance_ == null)
            instance_ = new Utility_Printer(context);

        return instance_;
    }

    private Framework framework_;

    private Utility_Printer(Context context)
    {
        framework_ = new Framework(context.getApplicationContext());
    }

    public Framework getFramework()
    {
        return framework_;
    }
}
