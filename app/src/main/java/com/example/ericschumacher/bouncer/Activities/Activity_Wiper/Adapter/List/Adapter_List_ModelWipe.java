package com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Adapter.List;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.ModelCheck;
import com.example.ericschumacher.bouncer.Objects.ModelWipe;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

import static android.view.View.GONE;

public class Adapter_List_ModelWipe extends Adapter_List {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Data
    ArrayList<ModelWipe> lModelWipes;

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    public Adapter_List_ModelWipe(android.content.Context context, Interface_Adapter_List iAdapterList, ArrayList<ModelWipe> lModelWipes) {
        super(context, iAdapterList);
        this.lModelWipes = lModelWipes;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_List vhList = (ViewHolder_List) holder;

        // Item
        if (iAdapterList.getItemViewType(position) == Constants_Intern.ITEM) {
            // Data
            ModelWipe oModelWipe = lModelWipes.get(position);

            // Visibility
            vhList.tvRight.setVisibility(GONE);
            vhList.ivRight.setVisibility(View.GONE);
            vhList.vDividerRight.setVisibility(GONE);

            // WipeProcedure
            if (oModelWipe.getoWipeProcedure() != null) {
                // Left
                vhList.ivLeft.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_lock_24));
                vhList.ivLeft.setColorFilter(Context.getResources().getColor(R.color.color_red));
                vhList.tvLeft.setVisibility(View.GONE);

                // Middle
                vhList.tvTitle.setText(oModelWipe.getoWipeProcedure().getoWipe().getcName());
                String dWipe = oModelWipe.getoWipeProcedure().getoWipe().getcDescription();
                String dWipeProcedure = oModelWipe.getoWipeProcedure().getcDescription();
                String dModelWipe = oModelWipe.getcDescription();
                if (!dModelWipe.equals("") || !dWipe.equals("") || !oModelWipe.equals("")) {
                    vhList.tvSubtitle.setVisibility(View.VISIBLE);
                    vhList.tvSubtitle.setText(dWipe + (!dWipeProcedure.equals("") && !dWipe.equals("") ? ("|" + dWipeProcedure) : dWipeProcedure) + ((!dWipe.equals("") || !dWipeProcedure.equals("")) && !dModelWipe.equals("") ? "|" + dModelWipe : dModelWipe));
                } else {
                    vhList.tvSubtitle.setVisibility(View.GONE);
                }
            }

            // Wipe
            if (oModelWipe.getoWipe() != null) {
                // Left
                vhList.ivLeft.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_lock_open_24));
                vhList.ivLeft.setColorFilter(Context.getResources().getColor(R.color.color_green));
                vhList.tvLeft.setVisibility(View.GONE);

                // Middle
                vhList.tvTitle.setText(oModelWipe.getoWipe().getcName());
                String dWipe = oModelWipe.getoWipe().getcDescription();
                String dModelWipe = oModelWipe.getcDescription();
                if (!dModelWipe.equals("") || !dWipe.equals("")) {
                    vhList.tvSubtitle.setVisibility(View.VISIBLE);
                    vhList.tvSubtitle.setText(dWipe + (!dWipe.equals("") && !dModelWipe.equals("") ? ("|" + dModelWipe) : dModelWipe));
                } else {
                    vhList.tvSubtitle.setVisibility(View.GONE);
                }
            }
        }

        // Add
        if (iAdapterList.getItemViewType(position) == Constants_Intern.ADD) {
            // Left
            vhList.ivLeft.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_add_24dp));
            vhList.tvLeft.setVisibility(View.GONE);

            // Right
            vhList.ivRight.setVisibility(View.GONE);
            vhList.tvRight.setVisibility(View.GONE);
            vhList.vDividerRight.setVisibility(View.GONE);

            // Middle
            vhList.tvTitle.setText(Context.getString(R.string.add));
            vhList.tvSubtitle.setVisibility(View.GONE);
        }
    }

    public void update(ArrayList lData) {
        lModelWipes = lData;
        notifyDataSetChanged();
    }
}
