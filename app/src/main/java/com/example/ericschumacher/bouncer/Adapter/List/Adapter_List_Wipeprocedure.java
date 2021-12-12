package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Long_Click;
import com.example.ericschumacher.bouncer.Objects.Wipeprocedure;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

public class Adapter_List_Wipeprocedure extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Data
    ArrayList<Wipeprocedure> lWipeprocedure = new ArrayList<>();

    // Context
    Context mContext;

    // Interface
    Interface_Click iClick;

    public Adapter_List_Wipeprocedure(Context context, ArrayList<Wipeprocedure> lWipeprocedure, Interface_Click iClick) {
        this.lWipeprocedure = lWipeprocedure;
        mContext = context;
        this.iClick = iClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_List vhList = new ViewHolder_List(LayoutInflater.from(mContext).inflate(R.layout.viewholder_list, parent, false), new Interface_Click() {
            @Override
            public void onClick(int position) {
                iClick.onClick(position);
            }
        }, new Interface_Long_Click() {
            @Override
            public boolean onLongClick(int position) {
                return false;
            }
        });
        return vhList;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_List vhList = (ViewHolder_List)holder;
        Wipeprocedure oWipeprocedure = lWipeprocedure.get(position);

        // Visibility
        vhList.tvSubtitle.setVisibility(View.GONE);

        // Data
        vhList.tvLeft.setText(oWipeprocedure.getoManufacturer().getcShortcut());
        vhList.tvTitle.setText(oWipeprocedure.getcName());
        vhList.tvRight.setText(Integer.toString(oWipeprocedure.getlWipeProcedure().size()));
    }

    @Override
    public int getItemCount() {
        return lWipeprocedure.size();
    }

    public void update(ArrayList<Wipeprocedure> lWipeprocedure) {
        this.lWipeprocedure = lWipeprocedure;
        notifyDataSetChanged();
    }
}
