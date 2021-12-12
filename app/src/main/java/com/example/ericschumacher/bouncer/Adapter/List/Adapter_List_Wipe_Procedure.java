package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Long_Click;
import com.example.ericschumacher.bouncer.Objects.Wipe_Procedure;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Image;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;

import java.util.ArrayList;

public class Adapter_List_Wipe_Procedure extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Data
    ArrayList<Wipe_Procedure> lWipeProcedure = new ArrayList<>();

    // Context
    Context mContext;

    // Interfaces
    Interface_Click_List iClickList;

    public Adapter_List_Wipe_Procedure(Context context, ArrayList<Wipe_Procedure> lWipeProcedure, Interface_Click_List iClickList) {
        mContext = context;
        this.lWipeProcedure = lWipeProcedure;
        this.iClickList = iClickList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_List vhList = new ViewHolder_List(LayoutInflater.from(mContext).inflate(R.layout.viewholder_list, parent, false), new Interface_Click() {
            @Override
            public void onClick(int position) {
                iClickList.onClickList(position, getItemViewType(position));
            }
        }, new Interface_Long_Click() {
            @Override
            public boolean onLongClick(int position) {
                return false;
            }
        });
        vhList.tvRight.setVisibility(View.GONE);
        vhList.vDividerRight.setVisibility(View.GONE);
        return vhList;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_List vhList = (ViewHolder_List)holder;

        // ITEM
        if (getItemViewType(position) == Constants_Intern.ITEM) {
            Wipe_Procedure wipeProcedure = lWipeProcedure.get(position);

            // Middle
            vhList.tvTitle.setText(wipeProcedure.getoWipe().getcName());
            if (wipeProcedure.getcDescription().equals("")) {
                vhList.tvSubtitle.setVisibility(View.GONE);
            } else {
                vhList.tvSubtitle.setVisibility(View.VISIBLE);
                vhList.tvSubtitle.setText(wipeProcedure.getcDescription());
            }
            Utility_Layout.setTextColor(mContext, vhList.tvTitle, R.color.color_text_primary);

            // Left
            vhList.tvLeft.setVisibility(View.VISIBLE);
            vhList.tvLeft.setText(Integer.toString(position+1)+".");
            vhList.ivLeft.setVisibility(View.GONE);
            Utility_Layout.setBackgroundColor(mContext, vhList.vDividerLeft, R.color.color_divider);

            // Background
            Utility_Layout.setBackgroundDrawable(mContext, vhList.clBackground, R.drawable.background_stroke_round_grey_secondary);
        }

        // ADD
        if (getItemViewType(position) == Constants_Intern.ADD) {
            // Middle
            vhList.tvSubtitle.setVisibility(View.GONE);
            vhList.tvTitle.setText(mContext.getString(R.string.add));
            Utility_Layout.setTextColor(mContext, vhList.tvTitle, R.color.color_primary);

            // Left
            vhList.tvLeft.setVisibility(View.GONE);
            vhList.ivLeft.setVisibility(View.VISIBLE);
            Utility_Image.setImageResource(mContext, vhList.ivLeft, R.drawable.ic_add_24dp, R.color.color_primary);
            Utility_Layout.setBackgroundColor(mContext, vhList.vDividerLeft, R.color.color_primary);

            // Background
            Utility_Layout.setBackgroundDrawable(mContext, vhList.clBackground, R.drawable.background_stroke_round_color_primary);
        }
    }

    @Override
    public int getItemCount() {
        return lWipeProcedure.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < lWipeProcedure.size()) {
            return Constants_Intern.ITEM;
        } else {
            return Constants_Intern.ADD;
        }
    }

    public void update(ArrayList<Wipe_Procedure> lWipeProcedure) {
        this.lWipeProcedure = lWipeProcedure;
        notifyDataSetChanged();
    }
}
