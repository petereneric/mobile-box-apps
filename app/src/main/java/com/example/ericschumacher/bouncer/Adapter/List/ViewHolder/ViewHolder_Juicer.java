package com.example.ericschumacher.bouncer.Adapter.List.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.R;

public class ViewHolder_Juicer extends RecyclerView.ViewHolder {

    public TextView tvLku;
    public TextView tvPosition;
    public TextView tvStock;
    public TextView tvIdDevice;
    public TextView tvLoadingStation;
    public FrameLayout rlMain;
    public RelativeLayout rlBackground;
    public LinearLayout llForeground;
    public RelativeLayout rlForeground;
    public View vDivicerOne;
    public View vDividerTwo;
    public View vDividerThree;

    public ViewHolder_Juicer(View itemView, final Interface_Click iClick) {
        super(itemView);

        // Initiate
        tvLku = itemView.findViewById(R.id.tvLku);
        tvPosition = itemView.findViewById(R.id.tvPosition);
        tvStock = itemView.findViewById(R.id.tvStock);
        tvIdDevice = itemView.findViewById(R.id.tvIdDevice);
        tvLoadingStation = itemView.findViewById(R.id.tvLoadingStation);
        rlMain = itemView.findViewById(R.id.rlMain);
        rlBackground = itemView.findViewById(R.id.rlBackground);
        llForeground = itemView.findViewById(R.id.llForeground);
        rlForeground = itemView.findViewById(R.id.rlForeground);
        vDivicerOne = itemView.findViewById(R.id.vDividerOne);
        vDividerTwo = itemView.findViewById(R.id.vDividerTwo);
        vDividerThree = itemView.findViewById(R.id.vDividerThree);

        // OnClickListener
        rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClick.onClick(getAdapterPosition());
            }
        });
    }
}
