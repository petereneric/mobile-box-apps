package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Diagnose;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;

import java.util.ArrayList;
import java.util.Date;

public class Adapter_List_Diagnose_Menu extends Adapter_List {

    // Data
    ArrayList<Diagnose> lDiagnoses = new ArrayList<>();

    public Adapter_List_Diagnose_Menu(android.content.Context context, Interface_Adapter_List iAdapterList, ArrayList<Diagnose> lDiagnoses) {
        super(context, iAdapterList);

        // Data
        this.lDiagnoses = lDiagnoses;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_List vhList = (ViewHolder_List) holder;

        // General
        vhList.tvLeft.setVisibility(View.GONE);
        vhList.tvRight.setVisibility(View.GONE);

        // Item
        if (iAdapterList.getItemViewType(position) == Constants_Intern.ITEM) {
            // Data
            Diagnose oDiagnose = lDiagnoses.get(position);

            // Left
            if (oDiagnose.isbFinished()) {
                vhList.ivLeft.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_flag_finished_24));
                vhList.ivLeft.setColorFilter(Context.getResources().getColor(R.color.color_green));
            } else {
                vhList.ivLeft.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_flag_not_finished_24));
                vhList.ivLeft.setColorFilter(Context.getResources().getColor(R.color.color_divider));
            }

            // Middle
            vhList.tvSubtitle.setVisibility(View.VISIBLE);
            vhList.tvTitle.setText(Utility_DateTime.dateToString((oDiagnose.getdCreation())));
            vhList.tvSubtitle.setText(oDiagnose.getcUser());

            // Right
            if (oDiagnose.isbPassed() != null) {
                if (oDiagnose.isbPassed()) {
                    vhList.ivRight.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_thumb_up_24));
                    vhList.ivRight.setColorFilter(Context.getResources().getColor(R.color.color_green));
                } else {
                    vhList.ivRight.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_thumb_down_24));
                    vhList.ivRight.setColorFilter(Context.getResources().getColor(R.color.color_red));
                }
            } else {
                vhList.ivRight.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_line_white_24));
                vhList.ivRight.setColorFilter(Context.getResources().getColor(R.color.color_divider));
            }
        }

        // Add
        if (iAdapterList.getItemViewType(position) == Constants_Intern.ADD) {
            // Left
            vhList.ivLeft.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_add_24dp));
            vhList.ivLeft.setColorFilter(Context.getResources().getColor(R.color.color_primary));

            // Middle
            vhList.tvSubtitle.setVisibility(View.GONE);
            vhList.tvTitle.setText(Context.getString(R.string.add));

            // Right
            vhList.ivRight.setVisibility(View.GONE);
            vhList.tvRight.setVisibility(View.GONE);
            vhList.vDividerRight.setVisibility(View.GONE);

            // BackgroundColor
            Utility_Layout.setRoundedCorners(Context, vhList.lMain, R.color.color_primary);
            Utility_Layout.setBackground(Context, vhList.vDividerLeft, R.color.color_primary);

            // TextColor
            Utility_Layout.setTextColor(Context, vhList.tvTitle, R.color.color_primary);
        }
    }

    public void update(ArrayList<Diagnose> lDiagnoses) {
        this.lDiagnoses = lDiagnoses;
        notifyDataSetChanged();
    }
}
