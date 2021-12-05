package com.example.ericschumacher.bouncer.Adapter.List.ViewHolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click_Icon;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Density;

public class ViewHolder_ModelColor extends RecyclerView.ViewHolder implements View.OnClickListener {

    // Context
    Context mContext;

    // Layout
    public TextView tvTitle;
    public ImageView ivMatch;
    public ImageView ivAuto;
    public ImageView ivExploitation;
    public ImageView ivContent;
    public View vMain;
    public LinearLayout llIcons;
    public RelativeLayout rlBackground;
    public RelativeLayout rlMain;

    // Interface
    Interface_Click iClick;

    public ViewHolder_ModelColor(Context mContext, View itemView, final Interface_Click iClick) {
        super(itemView);

        // Interface
        this.iClick = iClick;

        // Initiate
        tvTitle = itemView.findViewById(R.id.tvTitle);
        ivMatch = itemView.findViewById(R.id.ivMatch);
        ivAuto = itemView.findViewById(R.id.ivAuto);
        ivExploitation = itemView.findViewById(R.id.ivExploitation);
        ivContent = itemView.findViewById(R.id.ivContent);
        vMain = itemView.findViewById(R.id.vMain);
        llIcons = itemView.findViewById(R.id.llIcons);
        rlBackground = itemView.findViewById(R.id.rlBackground);
        rlMain = itemView.findViewById(R.id.rlMain);

        //Click
        vMain.setOnClickListener(this);

        // Measure
        ViewTreeObserver viewObserver = ivContent.getViewTreeObserver();
        viewObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ivContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = ivContent.getMeasuredWidth();
                mContext.getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0).edit().putInt(Constants_Intern.WIDTH_LIST_MODEL_COLOR, width).commit();
                Log.i("Width",""+width);
                RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(width, width);

                rlParams.addRule(RelativeLayout.BELOW, R.id.tvTitle);
                rlParams.topMargin = Utility_Density.getDp(mContext, 16);
                rlParams.bottomMargin = Utility_Density.getDp(mContext, 16);
                //rlParams.addRule(RelativeLayout.ABOVE, R.id.llIcons);
                ivContent.setLayoutParams(rlParams);
            }
        });

        ViewTreeObserver viewObserverTwo = rlMain.getViewTreeObserver();
        viewObserverTwo.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rlMain.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = rlMain.getMeasuredHeight();
                int width = rlMain.getMeasuredWidth();

                mContext.getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0).edit().putInt(Constants_Intern.WIDTH_LIST_MODEL_COLOR_BACKGROUND, width).commit();
                if (height > width) {
                    mContext.getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0).edit().putInt(Constants_Intern.HEIGHT_LIST_MODEL_COLOR_BACKGROUND, height).commit();
                }

                RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(width, height-2);
                rlParams.topMargin = 1;
                rlParams.bottomMargin = 1;
                rlParams.rightMargin = 1;
                rlParams.leftMargin = 1;
                rlBackground.setLayoutParams(rlParams);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vMain:
                iClick.onClick(getAdapterPosition());
                break;
        }
    }
}
