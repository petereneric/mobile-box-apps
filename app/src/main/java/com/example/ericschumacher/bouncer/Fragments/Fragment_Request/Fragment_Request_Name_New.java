package com.example.ericschumacher.bouncer.Fragments.Fragment_Request;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List_Simple;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Adapter_List_Simple;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Request_Name;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Keyboard;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

public class Fragment_Request_Name_New extends Fragment implements Interface_Adapter_List_Simple, View.OnClickListener {

    // Layout
    View Layout;
    EditText etName;
    Button bUnknown;
    Button bCommit;
    RecyclerView rvList;
    TextView tvTitle;

    // Interface
    Interface_Request_Name iRequestName;

    // Adapter
    Adapter_List_Simple aListSimple;

    // Data
    int tInterface;
    String cTitle;

    // Connection
    Volley_Connection cVolley;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Connection
        cVolley = new Volley_Connection(getActivity());

        // Data
        cTitle = getArguments().getString(Constants_Intern.INTERACTION_TITLE);
        tInterface = getArguments().getInt(Constants_Intern.TYPE_INTERFACE);

        // Interface
        if (true || tInterface == Constants_Intern.TYPE_INTERFACE_FROM_ACTIVITY) {
            iRequestName = (Interface_Request_Name)getActivity();
        } else {
            iRequestName = (Interface_Request_Name)getParentFragment();
        }

        // Layout
        Layout = inflater.inflate(R.layout.fragment_request_name_new, container, false);
        setLayout();

        return Layout;
    }

    @Override
    public void onPause() {
        super.onPause();
        Utility_Keyboard.hideKeyboardFrom(getActivity(), etName);
    }

    // Layout
    void setLayout() {

        // Views
        tvTitle = Layout.findViewById(R.id.tvTitle);
        etName = Layout.findViewById(R.id.etName);
        bUnknown = Layout.findViewById(R.id.bUnknown);
        bCommit = Layout.findViewById(R.id.bCommit);

        tvTitle.setText(cTitle);

        // OnClickListener
        bUnknown.setOnClickListener(this);
        bCommit.setOnClickListener(this);

        // Recycler View
        rvList = Layout.findViewById(R.id.rvList);
        rvList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        aListSimple = new Adapter_List_Simple(getActivity(), this);
        rvList.setAdapter(aListSimple);

        // EditText
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    loadData(editable.toString());
                } else {
                    reset();
                }

            }
        });

        // Keyboard
        Utility_Keyboard.openKeyboard(getActivity(), etName);
    }

    public void loadData(String sName) {

    }

    public void reset() {
    }

    public void update() {
        aListSimple.notifyDataSetChanged();
    }


    public int getCount() {
        return 0;
    }

    @Override
    public void onClick(int position) {

    }

    public void onCommit(String cName) {

    }

    public void onUnknown() {

    }

    @Override
    public String getName(int position) {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bCommit:
                if (!etName.getText().toString().equals("")) {
                    onCommit(etName.getText().toString());
                } else {
                    Utility_Toast.show(getActivity(), R.string.invalid_input);
                }
                break;
            case R.id.bUnknown:
                onUnknown();
                break;
        }
    }
}
