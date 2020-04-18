package com.example.ericschumacher.bouncer.Adapter.List.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Long_Click;
import com.example.ericschumacher.bouncer.R;

public class ViewHolder_Battery extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    RelativeLayout rlItemBattery;
    public TextView tvLku;
    public ImageView ivStorageLevel;
    public TextView tvBatteryName;
    public TextView tvManufacturerName;

    // Interface
    Interface_Click iClick;
    Interface_Long_Click iLongClick;

    public ViewHolder_Battery(View itemView, Interface_Click iClick, Interface_Long_Click iLongClick) {
        super(itemView);

        // Layout
        rlItemBattery = itemView.findViewById(R.id.rlItemBattery);
        tvLku = itemView.findViewById(R.id.tvLku);
        ivStorageLevel = itemView.findViewById(R.id.ivStorageLevel);
        tvBatteryName = itemView.findViewById(R.id.tvBatteryName);
        tvManufacturerName = itemView.findViewById(R.id.tvManufacturerName);

        rlItemBattery.setOnClickListener(this);
        rlItemBattery.setOnLongClickListener(this);

        // Interface
        this.iClick = iClick;
        this.iLongClick = iLongClick;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlItemBattery:
                iClick.onClick(getAdapterPosition());
                break;
        }
    }


    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.rlItemBattery:
                return iLongClick.onLongClick(getAdapterPosition());
            default:
                return false;
        }
    }
}
