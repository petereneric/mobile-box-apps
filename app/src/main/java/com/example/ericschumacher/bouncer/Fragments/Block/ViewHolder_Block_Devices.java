package com.example.ericschumacher.bouncer.Fragments.Block;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.R;

public class ViewHolder_Block_Devices extends RecyclerView.ViewHolder {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Layout
    ConstraintLayout clMain;
    TextView tvTitle;
    TextView tvSubtitle;
    TextView tvTitleTwo;
    View vDivider;


    public ViewHolder_Block_Devices(View itemView, final Interface_Click iClick) {
        super(itemView);

        // Initiate
        clMain = itemView.findViewById(R.id.clMain);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
        tvTitleTwo = itemView.findViewById(R.id.tvTitleTwo);
        vDivider = itemView.findViewById(R.id.vDivider);

        // ClickListener
        clMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClick.onClick(getAdapterPosition());
            }
        });
    }
}
