package com.example.ericschumacher.bouncer.Fragments.Fragment_Request;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ericschumacher.bouncer.Adapter.Table.Adapter_Table;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Ann;
import com.example.ericschumacher.bouncer.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Fragment_Request_Input_New extends Fragment implements View.OnClickListener {

    // Layout
    View Layout;
    EditText etInput;
    ImageView ivUnknown;
    ImageView ivCommit;
    RecyclerView rvSuggestion;

    // Interface
    Interface_Request_Input iRequestInput;

    // Data
    String cHint;
    String cConstantInput;
    JSONArray jsonArrayData;

    // Adapter
    Adapter_Table aList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Data
        cHint = getArguments().getString(Constants_Intern.HINT);
        cConstantInput = getArguments().getString(Constants_Intern.CONSTANT_INPUT);

        try {
            jsonArrayData = new JSONArray(getArguments().getString(Constants_Intern.STRING_JSON_ARRAY));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Interface
        iRequestInput = (Interface_Request_Input)getActivity();

        // Layout
        setLayout(inflater, container);

        // Adapter


        /*
        aList = new Adapter_Table(getContext(), false, new Interface_Click() {
            @Override
            public void onClick(int position) {
                try {
                    iRequestInput.returnInput(getTag(), jsonArrayData.getJSONObject(position).getString(cConstantInput));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, jsonArrayData, iRequestInput.getAnn(getTag()));

         */

        return null;
    }

    // Layout
    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        Layout = inflater.inflate(R.layout.fragment_request_input_new, container, false);

        etInput = Layout.findViewById(R.id.etInput);
        ivUnknown = Layout.findViewById(R.id.ivUnknown);
        ivCommit = Layout.findViewById(R.id.ivCommit);
        rvSuggestion = Layout.findViewById(R.id.rvSuggestion);

        ivUnknown.setOnClickListener(this);
        ivCommit.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivCommit:
                iRequestInput.returnInput(getTag(), etInput.getText().toString());
                break;
            case R.id.ivUnknown:
                iRequestInput.unknownInput(getTag());
                break;
        }
    }

    public interface Interface_Request_Input {
        void returnInput(String tagFragment, String cInput);
        void unknownInput(String tagFragment);
        ArrayList<Ann> getAnn(String tagFragment);
    }
}
