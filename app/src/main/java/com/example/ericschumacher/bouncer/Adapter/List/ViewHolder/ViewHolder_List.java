package com.example.ericschumacher.bouncer.Adapter.List.ViewHolder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.R;

public class ViewHolder_List extends RecyclerView.ViewHolder {

    // Layout
    public ImageView ivLeft;
    public TextView tvLeft;
    public View vDividerLeft;
    public ImageView ivRight;
    public TextView tvRight;
    public View vDividerRight;
    public TextView tvTitle;
    public TextView tvSubtitle;
    public ConstraintLayout lMain;


    public ViewHolder_List(View itemView, final Interface_Click iClick) {
        super(itemView);

        // Initiate
        ivLeft = itemView.findViewById(R.id.ivLeft);
        tvLeft = itemView.findViewById(R.id.tvLeft);
        vDividerLeft = itemView.findViewById(R.id.vDividerLeft);
        ivRight = itemView.findViewById(R.id.ivRight);
        tvRight = itemView.findViewById(R.id.tvRight);
        vDividerRight = itemView.findViewById(R.id.vDividerRight);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
        lMain = itemView.findViewById(R.id.lMain);

        // OnClickListener
        lMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClick.onClick(getAdapterPosition());
            }
        });
    }
}
