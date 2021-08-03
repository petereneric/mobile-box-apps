package com.example.ericschumacher.bouncer.Fragments.Table;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    public View vLayout;
    public TextView tvTitle;
    public RecyclerView rvList;
    public RelativeLayout rlMain;

    // Data
    public JSONArray lData = new JSONArray();
    public ArrayList<Ann> lAnn = new ArrayList<>();
    boolean bHeader;

    // Adapter
    public Adapter_Table aTable;

    // Interface
    public Interface_Fragment_Table iFragmentTable;

    // Connection
    public Volley_Connection cVolley;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Interface
        setInterface();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Connection
        cVolley = new Volley_Connection(getActivity());

        // Adapter
        aTable = new Adapter_Table(getActivity(), this);

        // vLayout
        setLayout(inflater, container);

        return vLayout;
    }

    public void setInterface() {
        iFragmentTable = (Interface_Fragment_Table) getActivity();
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(getIdLayout(), container, false);

        tvTitle = vLayout.findViewById(R.id.tvTitle);
        rlMain = vLayout.findViewById(R.id.rlMain);

        // RecyclerView
        rvList = vLayout.findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(aTable);
    }

    public int getIdLayout() {
        return R.layout.fragment_table_new;
    }

    public void update() {
        aTable.notifyDataSetChanged();
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
            oJson = getJsonArray().getJSONObject(hasHeader() ? position - 1 : position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oJson;
    }

    @Override
    public boolean isSelected(int position) {
        return false;
    }

    public interface Interface_Fragment_Table {
        void returnTable(String cTag, JSONObject oJson);
    }

    public interface Interface_Fragment_Table_Select extends Interface_Fragment_Table {
        boolean isSelected(String cTag, int position);
    }

    public interface Interface_Fragment_Table_Select_DataImport extends Interface_Fragment_Table_Select {
        JSONArray getData(String cTag);

    }
}

