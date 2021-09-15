package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Long_Click;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

public class Adapter_List extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Interface
    public Interface_Adapter_List iAdapterList;

    // Context
    public Context Context;

    public Adapter_List(Context context, Interface_Adapter_List iAdapterList) {
        this.iAdapterList = iAdapterList;
        Context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_List vhList = new ViewHolder_List(LayoutInflater.from(Context).inflate(R.layout.viewholder_list, parent, false), new Interface_Click() {
            @Override
            public void onClick(int position) {
                iAdapterList.clickList(position);
            }
        }, new Interface_Long_Click() {
            @Override
            public boolean onLongClick(int position) {
                return iAdapterList.longClickList(position);
            }
        });
        return vhList;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return iAdapterList.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return iAdapterList.getItemViewType(position);
    }

    public interface Interface_Adapter_List {
        int getItemCount();
        void clickList(int position);
        boolean longClickList(int position);
        int getItemViewType(int position);
    }
}
