package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_Juicer;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.List.Fragment_List_Juicer;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;

import java.util.ArrayList;

public class Adapter_List_Juicer extends RecyclerView.Adapter<ViewHolder_Juicer> {

    // Context
    Context Context;

    // Data
    ArrayList<Device> lDevicesExtra;

    // Fragment
    Fragment_List_Juicer fListJuicer;

    public Adapter_List_Juicer(Context context, ArrayList<Device> lDevicesExtra, Fragment_List_Juicer fListJuicer) {
        Context = context;
        this.lDevicesExtra = lDevicesExtra;
        this.fListJuicer = fListJuicer;
    }

    @Override
    public ViewHolder_Juicer onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_Juicer vhJuicer = new ViewHolder_Juicer(LayoutInflater.from(Context).inflate(R.layout.viewholder_juicer, parent, false), new Interface_Click() {
            @Override
            public void onClick(int position) {
                    if (fListJuicer.isExtraDevice(lDevicesExtra.get(position))) {
                        fListJuicer.onDeviceExtraSelected(lDevicesExtra.get(position));
                    } else {
                        fListJuicer.onDeviceSelected(lDevicesExtra.get(position));
                    }
            }
        });
        return vhJuicer;
    }

    @Override
    public void onBindViewHolder(ViewHolder_Juicer holder, int position) {
        // Data
        Device device = lDevicesExtra.get(position);

        // Layout
        holder.tvLku.setText(Integer.toString(device.getoStoragePlace().getkLku()));
        if (device.getoStoragePlace().getkStock() == Constants_Intern.STATION_PRIME_STOCK) {
            holder.tvPosition.setVisibility(View.VISIBLE);
            holder.tvPosition.setText(Integer.toString(device.getoStoragePlace().getnPosition()));
        } else {
            holder.tvPosition.setText(device.getoModel().getoManufacturer().getName()+" "+device.getoModel().getName()+" - "+device.getoColor().getcNameDE());
            holder.tvPosition.setVisibility(View.VISIBLE);
        }
        holder.tvStock.setText(device.getoStoragePlace().getcStock());
        holder.tvIdDevice.setText("Id: "+device.getIdDevice());
        holder.tvLoadingStation.setText(device.getoModel().getoCharger().gettLoadingStation());

        // ExtraDevices
        int kColor;
        if (fListJuicer.isExtraDevice(device)) {
            kColor = R.color.color_grey_light;
            Utility_Layout.setTextColor(Context, holder.tvIdDevice, R.color.color_grey_light);
        } else {
            kColor = R.color.color_grey_secondary;
            Utility_Layout.setTextColor(Context, holder.tvIdDevice, R.color.color_grey_secondary);
        }
        Utility_Layout.setRoundedCorners(Context, holder.rlForeground, kColor);
        Utility_Layout.setTextColor(Context, holder.tvLku, kColor);
        Utility_Layout.setTextColor(Context, holder.tvPosition, kColor);
        Utility_Layout.setTextColor(Context, holder.tvStock, kColor);
        Utility_Layout.setTextColor(Context, holder.tvLoadingStation, kColor);
        Utility_Layout.setBackground(Context, holder.vDivicerOne, kColor);
        Utility_Layout.setBackground(Context, holder.vDividerTwo, kColor);
        Utility_Layout.setBackground(Context, holder.vDividerThree, kColor);
    }

    @Override
    public int getItemCount() {
        return lDevicesExtra.size();
    }
}
