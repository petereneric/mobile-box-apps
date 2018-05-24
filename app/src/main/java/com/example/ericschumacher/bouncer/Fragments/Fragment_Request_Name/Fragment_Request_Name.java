package com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Name;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

/**
 * Created by Eric Schumacher on 22.05.2018.
 */

public class Fragment_Request_Name extends Fragment implements View.OnClickListener {

    // Layout
    View Layout;
    EditText etModel;
    Button bCommit;

    // Variables
    int IdModel;
    int Id;
    Utility_Network uNetwork;

    // Interface
    Interface_Selection iSelection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Layout
        Layout = inflater.inflate(R.layout.fragment_request_name, container, false);
        setLayout();

        // Variables
        IdModel = getArguments().getInt(Constants_Intern.SELECTION_ID_MODEL);
        uNetwork = new Utility_Network(getActivity());


        // Interface
        iSelection = (Interface_Selection)getActivity();

        return Layout;
    }

    // Layout
    private void setLayout() {
        etModel = Layout.findViewById(R.id.et_name);
        bCommit = Layout.findViewById(R.id.b_commit);
    }

    private void handleInteraction() {
        bCommit.setOnClickListener(this);
        etModel.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void afterTextChanged(Editable editable) {
                // RecyclerView with suggestionss
            }
        });
    }


    @Override
    public void onClick(View view) {

    }
}
