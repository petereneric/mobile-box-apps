package com.example.ericschumacher.bouncer.Fragments.Input;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class Fragment_Input_StockPrimeCapacity extends Fragment_Input {

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        // EditText
        etSearch.setInputType(InputType.TYPE_CLASS_NUMBER);
    }
}
