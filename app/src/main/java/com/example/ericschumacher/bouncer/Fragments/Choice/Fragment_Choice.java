package com.example.ericschumacher.bouncer.Fragments.Choice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Adapter.List.Choice.Adapter_List_Choice;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

/*
Info:
Fragment_Choice is the parent Fragment for following child Fragments:
- Fragment_Choice_Simple_..
- Fragment_Choice_Multiple_..
 */

public class Fragment_Choice extends Fragment {

    // Data
    public Bundle bData;
    String cTitle;

    // Layout
    int kLayout = R.layout.fragment_choice;
    public View vLayout;
    TextView tvTitle;
    public RecyclerView rvData;

    // Adapter
    public Adapter_List_Choice aChoice;

    // Connection
    public Volley_Connection cVolley;

    // Interface
    public Interface_Choice iChoice;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cVolley = new Volley_Connection(getActivity());

        // Arguments
        bData = getArguments();
        cTitle = bData.getString(Constants_Intern.TITLE);

        // Interface
        iChoice = (Interface_Choice)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Layout
        setLayout(inflater, container);

        return vLayout;
    }

    // Layout
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // Initiate
        vLayout = inflater.inflate(kLayout, container, false);
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        rvData = vLayout.findViewById(R.id.rvData);

        // RecyclerView
        rvData.setLayoutManager(new GridLayoutManager(getActivity(), getSpanCount()));

        // Fill with arguments
        tvTitle.setText(cTitle);
    }

    // Interface
    public interface Interface_Choice {
        public void returnChoice(String cTag, Object object);
    }

    public int getSpanCount() {
        return 3;
    }
}
