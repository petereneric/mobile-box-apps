package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_Choice;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Long_Click;
import com.example.ericschumacher.bouncer.R;

import java.io.Serializable;
import java.util.ArrayList;

public class Adapter_Single_Choice extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Data
    ArrayList<Serializable> lData;

    // Else
    Context Context;

    public Adapter_Single_Choice(Context context, ArrayList<Serializable> lData) {
        this.Context = context;
        this.lData = lData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View lViewHolder = LayoutInflater.from(Context).inflate(R.layout.cardview_choice, parent, false);
        ViewHolder_Choice vhChoice = new ViewHolder_Choice(lViewHolder, new ViewHolder_Choice.Interface_Click() {
            @Override
            public void onClick(int position, ImageView ivIconOne) {
                onClick(position, ivIconOne);
            }
        }, new Interface_Long_Click() {
            @Override
            public boolean onLongClick(int position) {
                return false;
            }
        });

        return vhChoice;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder_Choice vhChoice = (ViewHolder_Choice)holder;

    }

    private void onClick(int position) {

    }

    @Override
    public int getItemCount() {
        return lData.size();
    }



}
