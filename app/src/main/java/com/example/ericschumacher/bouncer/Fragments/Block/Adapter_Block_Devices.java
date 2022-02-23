package com.example.ericschumacher.bouncer.Fragments.Block;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Devices;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;

public class Adapter_Block_Devices extends RecyclerView.Adapter<ViewHolder_Block_Devices> {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Context
    Context Context;

    // Interfaces
    Interface_Devices iDevices;

    // Data

    public Adapter_Block_Devices(Context context, Interface_Devices iDevices) {
        Context = context;
        this.iDevices = iDevices;
    }

    @Override
    public ViewHolder_Block_Devices onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_Block_Devices vhDevices = new ViewHolder_Block_Devices(LayoutInflater.from(Context).inflate(R.layout.viewholder_block, parent, false), new Interface_Click() {
            @Override
            public void onClick(int position) {
                iDevices.clickModel(iDevices.getModels().get(position));
            }
        });
        return vhDevices;
    }

    @Override
    public void onBindViewHolder(ViewHolder_Block_Devices vhDevices, int position) {
        Model model = iDevices.getModels().get(position);

        // Text
        vhDevices.tvTitle.setText(model.getName());
        vhDevices.tvSubtitle.setText(model.getoManufacturer().getName());
        vhDevices.tvTitleTwo.setText(""+iDevices.numberDevices(model));

        // Design
        if (iDevices.isSelected(model)) {
            Utility_Layout.setBackgroundDrawable(Context, vhDevices.clMain, R.drawable.background_stroke_round_color_primary);
            Utility_Layout.setTextColor(Context, vhDevices.tvTitle, R.color.color_primary);
            Utility_Layout.setTextColor(Context, vhDevices.tvSubtitle, R.color.color_primary);
            Utility_Layout.setTextColor(Context, vhDevices.tvTitleTwo, R.color.color_primary);
            Utility_Layout.setBackgroundColor(Context, vhDevices.vDivider, R.color.color_primary);
        } else {
            Utility_Layout.setBackgroundDrawable(Context, vhDevices.clMain, R.drawable.background_rounded_corners_grey_secondary);
            Utility_Layout.setTextColor(Context, vhDevices.tvTitle, R.color.color_text_secondary);
            Utility_Layout.setTextColor(Context, vhDevices.tvSubtitle, R.color.color_text_secondary);
            Utility_Layout.setTextColor(Context, vhDevices.tvTitleTwo, R.color.color_text_secondary);
            Utility_Layout.setBackgroundColor(Context, vhDevices.vDivider, R.color.color_text_secondary);
        }
    }

    @Override
    public int getItemCount() {
        Log.i("Size", iDevices.getModels().size()+"");
        return iDevices.getModels().size();
    }
}
