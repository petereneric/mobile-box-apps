package com.example.ericschumacher.bouncer.Fragments.Fragment_Record;

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
import android.widget.Button;
import android.widget.EditText;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_SearchResults;
import com.example.ericschumacher.bouncer.Interfaces.Interface_SearchResults;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Input;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Int;
import com.example.ericschumacher.bouncer.Objects.Object_SearchResult;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

public class Fragment_Record_New extends Fragment implements View.OnClickListener {

    // Layout
    View Layout;
    Button bStart;
    Button bBack;
    EditText etCollector;
    RecyclerView rvCollector;

    // Data
    Adapter_SearchResults aSearchResults;
    ArrayList<Object_SearchResult> lSearchResults;

    // Interface
    Interface_Selection iBouncer;

    // Utility
    Utility_Network uNetwork;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = inflater.inflate(R.layout.fragment_record_new, container, false);
        setLayout();

        // Interface
        iBouncer = (Interface_Selection)getActivity();

        // Data
        lSearchResults = new ArrayList<>();
        aSearchResults = new Adapter_SearchResults(getActivity(), lSearchResults, new Interface_SearchResults() {
            @Override
            public void onResultClick(final Object_SearchResult o) {
                uNetwork.createRecord(o.getId(), new Interface_VolleyCallback_Int() {
                    @Override
                    public void onSuccess(int i) {
                        //iBouncer.setRecord(new Record(i, o.getName()));
                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        });
        rvCollector.setAdapter(aSearchResults);

        uNetwork = new Utility_Network(getActivity());

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        return Layout;
    }

    private void setLayout() {
        bStart = Layout.findViewById(R.id.bStart);
        bBack = Layout.findViewById(R.id.bBack);
        etCollector = Layout.findViewById(R.id.etCollector);
        rvCollector = Layout.findViewById(R.id.rvCollector);

        rvCollector.setLayoutManager(new LinearLayoutManager(getActivity()));

        bStart.setOnClickListener(this);
        bBack.setOnClickListener(this);

        etCollector.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                uNetwork.getCollectorsByName(editable.toString(), new Interface_VolleyCallback_ArrayList_Input() {
                    @Override
                    public void onSuccess(ArrayList<Object_SearchResult> list) {
                        lSearchResults = list;
                        aSearchResults.update(list);
                        aSearchResults.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bStart:
                // to be deleted
                break;
            case R.id.bBack:
                iBouncer.showFragmentRecordMenu();
                break;
        }
    }
}
