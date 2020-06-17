package com.example.ericschumacher.bouncer.Fragments.Table;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.Table.Adapter_Table;
import com.example.ericschumacher.bouncer.Objects.Ann;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Table extends Fragment implements Adapter_Table.Interface_Adapter_Table {

    // vLayout
    View vLayout;
    RecyclerView rvList;

    // Data
    JSONArray lData = new JSONArray();
    ArrayList<Ann> lAnn = new ArrayList<>();
    boolean bHeader;

    // Adapter
    Adapter_Table aTable;

    // Interface
    Interface_Fragment_Table iFragmentTable;

    // Connection
    Volley_Connection cVolley;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // vLayout
        setLayout(inflater, container);

        // Connection
        cVolley = new Volley_Connection(getActivity());

        // Adapter
        aTable = new Adapter_Table(getActivity(), this);

        // Interface
        iFragmentTable = (Interface_Fragment_Table)getActivity();

        return vLayout;
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(getIdLayout(), container, false);

        // RecyclerView
        rvList = vLayout.findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public int getIdLayout() {
        return R.layout.fragment_table_new;
    }

    @Override
    public JSONArray getJsonArray() {
        return lData;
    }

    @Override
    public boolean hasHeader() {
        return false;
    }

    @Override
    public ArrayList<Ann> getAnn() {
        return lAnn;
    }

    @Override
    public void onItemSelected(int position) {
        iFragmentTable.returnTable(getTag(), getJsonObject(position));
    }

    @Override
    public JSONObject getJsonObject(int position) {
        JSONObject oJson = null;
        try {
            oJson = getJsonArray().getJSONObject(hasHeader() ? position-1 : position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oJson;
    }

    public interface Interface_Fragment_Table {
        void returnTable(String cTag, JSONObject oJson);
    }
}
