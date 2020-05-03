package com.example.ericschumacher.bouncer.Adapter.List.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.R;

public class ViewHolder_Juicer extends RecyclerView.ViewHolder {

    TextView tvLku;
    TextView tvPosition;
    TextView tvStock;
    TextView tvIdDevice;
    TextView tvLoadingStation;
    RelativeLayout rlMain;

    public ViewHolder_Juicer(View itemView, final Interface_Click iClick) {
        super(itemView);

        // Initiate
        tvLku = itemView.findViewById(R.id.tvLku);
        tvPosition = itemView.findViewById(R.id.tvPosition);
        tvStock = itemView.findViewById(R.id.tvStock);
        tvIdDevice = itemView.findViewById(R.id.tvIdDevice);
        tvLoadingStation = itemView.findViewById(R.id.tvLoadingStation);
        rlMain = itemView.findViewById(R.id.rlMain);

        // OnClickListener
        rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClick.onClick(getAdapterPosition());
            }
        });
    }
}
