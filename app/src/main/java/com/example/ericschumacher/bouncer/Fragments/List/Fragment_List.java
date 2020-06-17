package com.example.ericschumacher.bouncer.Fragments.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.Table.Adapter_Table;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fragment_List extends Fragment implements Interface_Click {

    // vLayout
    View vLayout;
    RecyclerView rvList;

    // Data
    JSONArray lData = new JSONArray();
    boolean bHeader;

    // Adapter
    Adapter_Table aList;

    // Connection
    Volley_Connection cVolley;

    // Interface
    Interface_List iList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Interface
        iList = (Interface_List) getActivity();

        // Connection
        cVolley = new Volley_Connection(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // vLayout
        setLayout(inflater, container);

        return vLayout;
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_table_new, container, false);

        // RecyclerView
        rvList = vLayout.findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // Class methods
    public int getPosition(int position) {
        if (bHeader) {
            return position - 1;
        } else {
            return position;
        }
    }

    public JSONObject getJsonObject(int position) {
        JSONObject jsonObject = null;
        try {
            jsonObject = lData.getJSONObject(getPosition(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    // Interface methods
    @Override
    public void onClick(int position) {
        iList.returnList(getTag(), getJsonObject(position));
    }

    // Interface
    public interface Interface_List {
        void returnList(String cTag, Object object);
    }
}
