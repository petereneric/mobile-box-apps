package com.example.ericschumacher.bouncer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Objects.Object_Manufacturer;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 26.05.2018.
 */

public class Adapter_Request_Choice extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Variables
    Context Context;
    ArrayList<Object_Manufacturer> lManufacturers;
    Interface_Selection iSelection;

    public Adapter_Request_Choice(Context context, ArrayList<Object_Manufacturer> list, Interface_Selection i) {
        Context = context;
        lManufacturers = list;
        iSelection = i;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(Context).inflate(R.layout.cardview_choice, parent, false);
        ViewHolder_Request_Choice vh = new ViewHolder_Request_Choice(layout, new Interface_Click() {
            @Override
            public void onClick(int position) {
                iSelection.callbackManufacturer(lManufacturers.get(position).getId());
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_Request_Choice h = (ViewHolder_Request_Choice)holder;
        Object_Manufacturer oManufacturer = lManufacturers.get(position);
        h.tvName.setText(oManufacturer.getName());
        Log.i("onBind", oManufacturer.getName());
        // Icon
    }

    @Override
    public int getItemCount() {
        return lManufacturers.size();
    }

    private class ViewHolder_Request_Choice extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivIcon;
        TextView tvName;

        Interface_Click iClick;

        public ViewHolder_Request_Choice(View itemView, Interface_Click i) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);

            iClick = i;

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_cardview:
                    iClick.onClick(getAdapterPosition());
            }
        }
    }

    private interface Interface_Click {
        public void onClick(int position);
    }
}
