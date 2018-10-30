package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Devices;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

public class Adapter_Devices extends RecyclerView.Adapter<Adapter_Devices.ViewHolder_Device> {

    Context Context;
    ArrayList<Device> Devices;
    Interface_Fragment_Devices iDevices;

    public Adapter_Devices(Context context, ArrayList<Device> devices, Interface_Fragment_Devices iDevices) {
        Log.i("Why", "not working"+Integer.toString(devices.size()));
        Context = context;
        Devices = devices;

        this.iDevices = iDevices;
    }

    @Override
    public ViewHolder_Device onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_Device vhDevice = new ViewHolder_Device(LayoutInflater.from(Context).inflate(R.layout.item_device, parent, false), new Interface_Device() {
            @Override
            public void remove(int position) {
                iDevices.delete(Devices.get(position));
            }
        });
        return vhDevice;
    }

    @Override
    public void onBindViewHolder(ViewHolder_Device holder, int position) {
        Log.i("works", "like"+Integer.toString(Devices.get(position).getIdDevice()));
        holder.tvDeviceId.setText(Integer.toString(Devices.get(position).getIdDevice()));
    }

    @Override
    public int getItemCount() {
        return Devices.size();
    }

    public void updateList(ArrayList<Device> devices) {
        Devices = devices;
        notifyDataSetChanged();
    }

    public class ViewHolder_Device extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvDeviceId;
        ImageView ivRemove;

        Interface_Device iDevice;

        public ViewHolder_Device(View itemView, Interface_Device iDevice) {
            super(itemView);

            this.iDevice = iDevice;

            tvDeviceId = itemView.findViewById(R.id.tvDevice);
            ivRemove = itemView.findViewById(R.id.ivDelete);

            tvDeviceId.setOnClickListener(this);
            ivRemove.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvDevice:

                    break;
                case R.id.ivDelete:
                    iDevice.remove(getAdapterPosition());
                    break;
            }
        }
    }

    interface Interface_Device {
        void remove(int position);
    }
}
