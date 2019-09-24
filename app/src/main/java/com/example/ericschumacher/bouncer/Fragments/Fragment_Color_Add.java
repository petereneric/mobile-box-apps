package com.example.ericschumacher.bouncer.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_SearchResults;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_DeviceManager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_SearchResults;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Search_Manufacturer;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Search_Model;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.Objects.Object_SearchResult;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

public class Fragment_Color_Add extends Fragment implements View.OnClickListener, Interface_Search_Manufacturer, Interface_Search_Model {

    // Layout
    View Layout;
    Button bCommit;
    EditText etModelName;
    EditText etHex;
    EditText etManufacturer;
    EditText etColorName;
    ImageView ivDeleteModelName;
    ImageView ivDeleteManufacturer;
    RecyclerView rvManufacturer;
    RecyclerView rvModel;

    // Interface
    Interface_DeviceManager iManager;

    // Objects
    Manufacturer oManufacturer;
    Model oModel;
    Color oColor;

    // Adapter
    Adapter_SearchResults aManufacturer;
    Adapter_SearchResults aModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = inflater.inflate(R.layout.fragment_color_edit, container, false);
        setLayout();

        // Interface
        iManager = (Interface_DeviceManager) getActivity();
        oColor = new Color();

        // Layout
        aManufacturer = new Adapter_SearchResults(getActivity(), new ArrayList<Object_SearchResult>(), new Interface_SearchResults() {
            @Override
            public void onResultClick(Object_SearchResult o) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etManufacturer.getWindowToken(), 0);
                oColor.setManufacturer(new Manufacturer(o.getId(), o.getName()));
                updateUI();
                aManufacturer.update(new ArrayList<Object_SearchResult>());
            }
        });
        rvManufacturer.setAdapter(aManufacturer);
        aModel = new Adapter_SearchResults(getActivity(), new ArrayList<Object_SearchResult>(), new Interface_SearchResults() {
            @Override
            public void onResultClick(Object_SearchResult o) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etColorName.getWindowToken(), 0);
                //oColor.setModel(new Model(o.getId(), o.getName()));
                updateUI();
                aModel.update(new ArrayList<Object_SearchResult>());
            }
        });
        rvModel.setAdapter(aModel);

        etManufacturer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) iManager.searchMatchingManufacturer(editable.toString(), (Fragment_Color_Add.this));
            }
        });

        etModelName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) iManager.searchMatchingModel(editable.toString(), (Fragment_Color_Add.this));
            }
        });

        etHex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                oColor.setHexCode(editable.toString());
            }
        });

        etColorName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                oColor.setName(editable.toString());
            }
        });

        // Objects
        oColor.setManufacturer(new Manufacturer(iManager.getModel().getoManufacturer().getId(), iManager.getModel().getoManufacturer().getName()));
        //oColor.setModel(getActivity(), new Model(iManager.getModel().getkModel(), iManager.getModel().getName()));
        updateUI();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        return Layout;
    }

    private void setLayout() {
        bCommit = Layout.findViewById(R.id.bCommit);
        etColorName = Layout.findViewById(R.id.etName);
        etHex = Layout.findViewById(R.id.etHex);
        etManufacturer = Layout.findViewById(R.id.etManufacturer);
        etModelName = Layout.findViewById(R.id.etModel);
        ivDeleteManufacturer = Layout.findViewById(R.id.ivDeleteManufacturer);
        ivDeleteModelName = Layout.findViewById(R.id.ivDeleteModelName);
        rvManufacturer = Layout.findViewById(R.id.rvManufacturer);
        rvModel = Layout.findViewById(R.id.rvModel);
        rvManufacturer.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvModel.setLayoutManager(new LinearLayoutManager(getActivity()));



        bCommit.setOnClickListener(this);
        ivDeleteModelName.setOnClickListener(this);
        ivDeleteManufacturer.setOnClickListener(this);
    }

    private void updateUI() {
        if (oColor.getManufacturer() != null) etManufacturer.setText(oColor.getManufacturer().getName());
        else etManufacturer.setText("");
        if (oColor.getModel() != null) etModelName.setText(oColor.getModel().getName());
        else etModelName.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivDeleteManufacturer:
                oColor.setManufacturer(null);
                updateUI();
                break;
            case R.id.ivDeleteModelName:
                oColor.setModel(null);
                updateUI();
                break;
            case R.id.bCommit:
                if (!oColor.getName().equals(Constants_Intern.NAME_NULL) && !oColor.getHexCode().equals(Constants_Intern.NAME_NULL)) {
                    iManager.addColor(oColor);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.missing_color_information), Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    @Override
    public void returnSearchResultsForManufacturer(ArrayList<Object_SearchResult> list) {
        if (list.size() > 1) aManufacturer.update(list);

    }

    @Override
    public void returnSearchResultsForModel(ArrayList<Object_SearchResult> list) {
        if (list.size() > 1) aModel.update(list);

    }
}
