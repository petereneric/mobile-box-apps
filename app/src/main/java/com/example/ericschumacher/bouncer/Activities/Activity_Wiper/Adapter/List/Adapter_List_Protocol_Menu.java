package com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Adapter.List;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Diagnose;
import com.example.ericschumacher.bouncer.Objects.Protocol;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;

import java.util.ArrayList;

public class Adapter_List_Protocol_Menu extends Adapter_List {

    ArrayList<Protocol> lProtocols = new ArrayList<>();

    public Adapter_List_Protocol_Menu(android.content.Context context, Interface_Adapter_List iAdapterList, ArrayList<Protocol> lProtocols) {
        super(context, iAdapterList);
        this.lProtocols = lProtocols;
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
            Protocol oProtocol = lProtocols.get(position);

            // Left
            vhList.ivLeft.setVisibility(View.GONE);
            vhList.vDividerLeft.setVisibility(View.GONE);

            // Middle
            vhList.tvSubtitle.setVisibility(View.VISIBLE);
            vhList.tvTitle.setText(Utility_DateTime.dateToString((oProtocol.getdCreation())));
            vhList.tvSubtitle.setText(oProtocol.getcUser());

            // Right
            if (oProtocol.isbPassed() != null) {
                if (oProtocol.isbPassed()) {
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
            vhList.tvTitle.setText(Context.getString(R.string.new_protocol));

            // Right
            vhList.ivRight.setVisibility(View.GONE);
            vhList.tvRight.setVisibility(View.GONE);
            vhList.vDividerRight.setVisibility(View.GONE);

            // BackgroundColor
            Utility_Layout.setBackgroundColor(Context, vhList.vDividerLeft, R.color.color_primary);
            Utility_Layout.setRoundedCorners(Context, vhList.clForeground, R.color.color_primary);
            Utility_Layout.setBackgroundColor(Context, vhList.vDividerLeft, R.color.color_primary);

            // TextColor
            Utility_Layout.setTextColor(Context, vhList.tvTitle, R.color.color_primary);
        }
    }
}
