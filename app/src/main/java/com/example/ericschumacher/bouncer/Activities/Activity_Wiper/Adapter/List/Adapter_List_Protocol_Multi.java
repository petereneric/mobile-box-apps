package com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.ModelWipe;
import com.example.ericschumacher.bouncer.Objects.ProtocolWipe;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Image;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;

import java.util.ArrayList;

public class Adapter_List_Protocol_Multi extends Adapter_List {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Data
    ArrayList<ModelWipe> lModelWipes = new ArrayList<>();

    public Adapter_List_Protocol_Multi(Context context, Interface_Adapter_List iAdapterList, ArrayList<ModelWipe> lModelWipes) {
        super(context, iAdapterList);
        this.lModelWipes = lModelWipes;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_List vhList = (ViewHolder_List)holder;
        ModelWipe modelWipe = lModelWipes.get(position);

        // General
        vhList.tvRight.setVisibility(View.GONE);
        vhList.ivRight.setVisibility(View.GONE);

        switch (getItemViewType(position)) {
            case Constants_Intern.ITEM:
                // Background
                Utility_Layout.setBackgroundDrawable(Context, vhList.clForeground, R.drawable.background_rounded_corners_grey_secondary);

                // Left
                vhList.ivLeft.setVisibility(View.GONE);
                vhList.tvLeft.setVisibility(View.VISIBLE);
                Utility_Layout.setBackgroundColor(Context, vhList.vDividerLeft, R.color.color_divider);
                vhList.tvLeft.setText(modelWipe.getnPosition()+1+".");

                // Middle
                Utility_Layout.setTextColor(Context, vhList.tvTitle, R.color.color_text_primary);
                vhList.tvTitle.setText(modelWipe.get_cWipe());
                if (modelWipe.getCompleteDescription() != null) {
                    vhList.tvSubtitle.setVisibility(View.VISIBLE);
                    vhList.tvSubtitle.setText(modelWipe.getCompleteDescription());
                } else {
                    vhList.tvSubtitle.setVisibility(View.GONE);
                }
                break;
            case Constants_Intern.FINISH:
                // Background
                Utility_Layout.setBackgroundDrawable(Context, vhList.clForeground, R.drawable.background_stroke_round_color_primary);

                // Left
                vhList.ivLeft.setVisibility(View.VISIBLE);
                vhList.tvLeft.setVisibility(View.GONE);
                Utility_Layout.setBackgroundColor(Context, vhList.vDividerLeft, R.color.color_primary);
                Utility_Image.setImageResource(Context, vhList.ivLeft, R.drawable.ic_done_all_white_24, R.color.color_primary);

                // Middle
                vhList.tvSubtitle.setVisibility(View.GONE);
                Utility_Layout.setTextColor(Context, vhList.tvTitle, R.color.color_primary);
                vhList.tvTitle.setText(Context.getString(R.string.finish_protocols_successfully));
                break;
            case Constants_Intern.ERROR:
                // Background
                Utility_Layout.setBackgroundDrawable(Context, vhList.clForeground, R.drawable.background_rounded_corners_grey);

                // Left
                vhList.ivLeft.setVisibility(View.VISIBLE);
                vhList.tvLeft.setVisibility(View.GONE);
                Utility_Layout.setBackgroundColor(Context, vhList.vDividerLeft, R.color.color_grey);
                Utility_Image.setImageResource(Context, vhList.ivLeft, R.drawable.ic_clear_24dp, R.color.color_grey);

                // Middle
                vhList.tvSubtitle.setVisibility(View.GONE);
                Utility_Layout.setTextColor(Context, vhList.tvTitle, R.color.color_grey);
                vhList.tvTitle.setText(Context.getString(R.string.finish_protocol_successfully));
                break;
        }
    }

    public void updateData(ArrayList<ModelWipe> lModelWipes) {
        this.lModelWipes = lModelWipes;
        notifyDataSetChanged();
    }
}
