package com.example.ericschumacher.bouncer.Fragments.Fragment_Overview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Overview_Selection;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Objects.Collection.Record;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Overview_Selection extends Fragment implements View.OnClickListener, Interface_Overview_Selection {

    // Counter
    private int cRecycling;
    private int cReuse;

    // Layout
    View Layout;
    TextView tvCollector;
    TextView tvCounterReuse;
    TextView tvCounterRecycling;
    TextView tvCounterTotal;

    TextView tvTitle;

    TableRow trCounterReuse;
    TableRow trCounterRecycling;
    TableRow trCounterTotal;

    Button bPause;
    Button bFinish;
    Button bDelete;

    // Shared Preferences
    SharedPreferences SharedPreferences;

    // Interface
    Interface_Selection iBouncer;

    // Objects
    Record oRecord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = inflater.inflate(R.layout.fragment_overview_selection, container, false);

        setLayout();

        // Interface
        iBouncer = (Interface_Selection)getActivity();

        // Objects
        oRecord = (Record) getArguments().getSerializable(Constants_Intern.OBJECT_RECORD);

        // Data
        updateUI();

        // SharedPreferences
        //SharedPreferences = getActivity().getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0);

        return Layout;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void setLayout() {
        tvCollector = Layout.findViewById(R.id.tvCollector);
        tvCounterReuse = Layout.findViewById(R.id.tvCounterReuse);
        tvCounterRecycling = Layout.findViewById(R.id.tvCounterRecycling);
        tvCounterTotal = Layout.findViewById(R.id.tvCounterTotal);
        tvTitle = Layout.findViewById(R.id.tvTitle);

        bPause = Layout.findViewById(R.id.bPause);
        bFinish = Layout.findViewById(R.id.bFinish);
        bDelete = Layout.findViewById(R.id.bDelete);

        trCounterReuse = Layout.findViewById(R.id.trCounterReuse);
        trCounterRecycling = Layout.findViewById(R.id.trCounterRecycling);
        trCounterTotal = Layout.findViewById(R.id.trCounterTotal);

        tvTitle.setText(getString(R.string.overview));

        bPause.setOnClickListener(this);
        bFinish.setOnClickListener(this);
        bDelete.setOnClickListener(this);
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
            case R.id.bFinish:
                iBouncer.finishRecord();
                break;
            case R.id.bDelete:
                iBouncer.deleteRecord();
                break;
            case R.id.bPause:
                iBouncer.pauseRecord();
                break;
        }
    }

    public void updateUI() {
        Log.i("Callled", "yes");
        tvCollector.setText(iBouncer.getNameCollector());
        tvCounterRecycling.setText(Integer.toString(iBouncer.getCountRecycling()));
        tvCounterReuse.setText(Integer.toString(iBouncer.getCountReuse()));
        tvCounterTotal.setText(Integer.toString(iBouncer.getCountRecycling()+iBouncer.getCountReuse()));
    }

    public void reset() {
    }

}
