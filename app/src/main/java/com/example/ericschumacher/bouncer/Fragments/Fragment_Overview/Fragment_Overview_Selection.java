package com.example.ericschumacher.bouncer.Fragments.Fragment_Overview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Overview_Selection;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Overview_Selection extends Fragment implements View.OnClickListener, Interface_Overview_Selection {

    // Counter
    private int cRecycling;
    private int cReuse;

    // Layout
    View Layout;
    TextView tvCounterReuse;
    TextView tvCounterRecycling;
    TextView tvCounterTotal;

    TableRow trCounterReuse;
    TableRow trCounterRecycling;
    TableRow trCounterTotal;

    // Shared Preferences
    SharedPreferences SharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = inflater.inflate(R.layout.fragment_overview_selection, container, false);

        setLayout();

        // SharedPreferences
        SharedPreferences = getActivity().getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0);

        return Layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvCounterReuse.setText(Integer.toString(SharedPreferences.getInt(Constants_Intern.COUNTER_REUSE, 0)));
        tvCounterRecycling.setText(Integer.toString(SharedPreferences.getInt(Constants_Intern.COUNTER_RECYCLING, 0)));
        tvCounterTotal.setText(Integer.toString(SharedPreferences.getInt(Constants_Intern.COUNTER_REUSE, 0))+Integer.toString(SharedPreferences.getInt(Constants_Intern.COUNTER_RECYCLING, 0)));
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.edit().putInt(Constants_Intern.COUNTER_RECYCLING, cRecycling).commit();
        SharedPreferences.edit().putInt(Constants_Intern.COUNTER_REUSE, cReuse).commit();
    }

    private void setLayout() {
        tvCounterReuse = Layout.findViewById(R.id.tvCounterReuse);
        tvCounterRecycling = Layout.findViewById(R.id.tvCounterRecycling);
        tvCounterTotal = Layout.findViewById(R.id.tvCounterTotal);

        trCounterReuse = Layout.findViewById(R.id.trCounterReuse);
        trCounterRecycling = Layout.findViewById(R.id.trCounterRecycling);
        trCounterTotal = Layout.findViewById(R.id.trCounterTotal);

        trCounterReuse.setOnClickListener(this);
        trCounterRecycling.setOnClickListener(this);
        trCounterTotal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trCounterReuse:

                break;
            case R.id.trCounterRecycling:

                break;
            case R.id.trCounterTotal:

                break;
        }
    }

    @Override
    public void incrementCounterReuse() {
        cRecycling++;
        updateUI();

    }

    @Override
    public void incrementCounterRecycling() {
        cReuse++;
        updateUI();
    }

    @Override
    public int getCounterRecycling() {
        return cRecycling;
    }

    @Override
    public int getCounterReuse() {
        return cReuse;
    }

    @Override
    public int getCounterTotal() {
        return cReuse+cRecycling;
    }

    public void updateUI() {
        tvCounterRecycling.setText(Integer.toString(cRecycling));
        tvCounterReuse.setText(Integer.toString(cReuse));
        tvCounterReuse.setText(Integer.toString(cReuse+cRecycling));
    }

    public void reset() {
        cReuse = 0;
        cRecycling = 0;
        updateUI();
    }

}
