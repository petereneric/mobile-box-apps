package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_Choice_Simple;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Adapter_List_Simple;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Long_Click;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

public class Adapter_List_Simple extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Data
    ArrayList<Object> lObjects;

    // Interface
    Interface_Adapter_List_Simple iListSimple;

    // Else
    Context Context;

    public Adapter_List_Simple(Context context, Interface_Adapter_List_Simple iListSimple) {
        Context = context;
        this.lObjects = lObjects;
        this.iListSimple = iListSimple;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_Choice_Simple vhChoiceSimple = new ViewHolder_Choice_Simple(LayoutInflater.from(Context).inflate(R.layout.item_choice, parent, false), new Interface_Click() {
            @Override
            public void onClick(int position) {
                iListSimple.onClick(position);
            }
        }, new Interface_Long_Click() {
            @Override
            public boolean onLongClick(int position) {
                return false;
            }
        });
        return vhChoiceSimple;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_Choice_Simple vhChoiceSimple = (ViewHolder_Choice_Simple)holder;
        vhChoiceSimple.tvName.setText(iListSimple.getName(position));
    }

    @Override
    public int getItemCount() {
        return iListSimple.getCount();
    }
}
