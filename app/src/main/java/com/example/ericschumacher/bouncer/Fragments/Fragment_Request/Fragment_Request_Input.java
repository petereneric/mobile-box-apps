package com.example.ericschumacher.bouncer.Fragments.Fragment_Request;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_SearchResults;
import com.example.ericschumacher.bouncer.Interfaces.Interface_DeviceManager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_SearchResults;
import com.example.ericschumacher.bouncer.Objects.Object_SearchResult;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 22.05.2018.
 */

public class Fragment_Request_Input extends Fragment implements View.OnClickListener {

    // Layout
    View Layout;
    EditText etInput;
    ImageView ivHelp;
    Button bCommit;
    RecyclerView rSearchResults;

    // Adapter
    Adapter_SearchResults aSearchResults;

    // Interface
    Interface_DeviceManager iManager;

    // Utility
    Utility_Network uNetwork;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Layout
        Layout = inflater.inflate(R.layout.fragment_request_input, container, false);
        setLayout();
        handleInteraction();

        // Utility
        uNetwork = new Utility_Network(getActivity());

        // Interface
        iManager = (Interface_DeviceManager) getActivity();

        // RecyclerView
        rSearchResults.setLayoutManager(new LinearLayoutManager(getActivity()));
        aSearchResults = new Adapter_SearchResults(getActivity(), new ArrayList<Object_SearchResult>(), new Interface_SearchResults() {
            @Override
            public void onResultClick(Object_SearchResult o) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);

                itemSelected(o);
            }
        });
        rSearchResults.setAdapter(aSearchResults);

        // EditText
        etInput.setOnClickListener(this);

        return Layout;
    }

    // Layout
    private void setLayout() {
        etInput = Layout.findViewById(R.id.et_name);
        bCommit = Layout.findViewById(R.id.b_commit);
        ivHelp = Layout.findViewById(R.id.ivHelp);
        rSearchResults = Layout.findViewById(R.id.recycler_view);
    }

    private void handleInteraction() {
        bCommit.setOnClickListener(this);
        ivHelp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_name:
                Log.i("JO", "JO");
                InputMethodManager imm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                break;
        }
    }

    public void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);
    }

    public void itemSelected(Object_SearchResult oSearchResult) {
        etInput.setText(oSearchResult.getName());
    }

}
