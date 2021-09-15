package com.example.ericschumacher.bouncer.Adapter.List.ViewHolder;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Long_Click;
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
    public ConstraintLayout clForeground;
    public ConstraintLayout clBackground;
    public ImageView ivSwipeRight;
    public ImageView ivSwipeLeft;
    public Guideline gOne;
    public Guideline gTwo;
    public Guideline gThree;
    public Guideline gFour;


    public ViewHolder_List(View itemView, final Interface_Click iClick, final Interface_Long_Click iLongClick) {
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
        clBackground = itemView.findViewById(R.id.clBackground);
        clForeground = itemView.findViewById(R.id.clForeground);
        ivSwipeRight = itemView.findViewById(R.id.ivSwipeRight);
        ivSwipeLeft = itemView.findViewById(R.id.ivSwipeLeft);
        gOne = itemView.findViewById(R.id.gOne);
        gTwo = itemView.findViewById(R.id.gTwo);
        gThree = itemView.findViewById(R.id.gThree);
        gFour = itemView.findViewById(R.id.gFour);

        // OnClickListener
        clForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClick.onClick(getAdapterPosition());
            }
        });
        clForeground.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return iLongClick.onLongClick(getAdapterPosition());
            }
        });
    }
}
