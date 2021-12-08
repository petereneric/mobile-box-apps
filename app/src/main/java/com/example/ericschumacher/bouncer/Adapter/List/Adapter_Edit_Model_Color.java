package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_ModelColor;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click_Icon;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Objects.ModelColor;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Density;
import com.example.ericschumacher.bouncer.Utilities.Utility_Image;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;

import java.io.Console;
import java.util.ArrayList;

public class Adapter_Edit_Model_Color extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    // Data
    ArrayList<ModelColor> lModelColor = new ArrayList<>();
    ArrayList<Color> lColor;

    // Interface
    Interface_Click iClick;

    // Context
    Context mContext;

    public Adapter_Edit_Model_Color(android.content.Context context, ArrayList<ModelColor> lModelColor, ArrayList<Color> lColor, Interface_Click iClick) {
        this.lModelColor = lModelColor;
        this.lColor = lColor;
        this.iClick = iClick;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_ModelColor vhModel = new ViewHolder_ModelColor(mContext, LayoutInflater.from(mContext).inflate(R.layout.viewholder_model_color, parent, false), new Interface_Click() {
            @Override
            public void onClick(int position) {
                iClick.onClick(position);
            }
        });
        return vhModel;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_ModelColor vhModelColor = (ViewHolder_ModelColor)holder;

        // Measure
        // 1. ivContent
        int width = mContext.getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0).getInt(Constants_Intern.WIDTH_LIST_MODEL_COLOR, 0);
        if (width > 0) {
            RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(width, width);

            rlParams.addRule(RelativeLayout.BELOW, R.id.tvTitle);
            rlParams.topMargin = Utility_Density.getDp(mContext, 16);
            rlParams.bottomMargin = Utility_Density.getDp(mContext, 16);
            //rlParams.addRule(RelativeLayout.ABOVE, R.id.llIcons);
            vhModelColor.ivContent.setLayoutParams(rlParams);
        }

        // 2. rlBackground
        int widthBackground = mContext.getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0).getInt(Constants_Intern.WIDTH_LIST_MODEL_COLOR_BACKGROUND, 0);
        int heightBackground = mContext.getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0).getInt(Constants_Intern.HEIGHT_LIST_MODEL_COLOR_BACKGROUND, 0);
        if (widthBackground > 0 && heightBackground > 0) {
            RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(widthBackground, heightBackground-2);
            rlParams.topMargin = 1;
            rlParams.bottomMargin = 1;
            rlParams.rightMargin = 1;
            rlParams.leftMargin = 1;
            vhModelColor.rlBackground.setLayoutParams(rlParams);
        }

        // Item
        if (getItemViewType(position) == Constants_Intern.ITEM) {
            ModelColor oModelColor = lModelColor.get(position);

            // Visibility
            vhModelColor.llIcons.setVisibility(View.VISIBLE);
            vhModelColor.tvTitle.setVisibility(View.VISIBLE);
            vhModelColor.ivMatch.setVisibility(View.VISIBLE);
            vhModelColor.ivExploitation.setVisibility(View.VISIBLE);

            // Text
            vhModelColor.tvTitle.setText(oModelColor.getoColor().getcNameDE());
            setTitleTextSize(vhModelColor.tvTitle, oModelColor.getoColor().getcNameDE().length());

            // Image
            //int ivContentWidth = vhModelColor.vMain.getLayoutParams().width;
            //vhModelColor.ivContent.setLayoutParams(new RelativeLayout.LayoutParams(ivContentWidth, ivContentWidth));

            if (oModelColor.isbMatch() && oModelColor.getiPreview() != null) {
                // Image
                vhModelColor.ivContent.setImageBitmap(oModelColor.getiPreview());
                Utility_Layout.setBackgroundColor(mContext, vhModelColor.ivContent, R.color.color_transparent);
            } else {
                // Hex
                vhModelColor.ivContent.setImageBitmap(null);
                try {
                    vhModelColor.ivContent.setBackgroundColor(android.graphics.Color.parseColor((oModelColor.getoColor()).getHexCode()));
                } catch (IllegalArgumentException e) {
                    vhModelColor.ivContent.setBackgroundColor(android.graphics.Color.parseColor(("#ffffff")));
                }
            }

            // Icons
            // 1. Match
            if (oModelColor.isbMatch()) {
                Utility_Image.setImageColor(mContext, vhModelColor.ivMatch, R.color.color_green);
            } else {
                Utility_Image.setImageColor(mContext, vhModelColor.ivMatch, R.color.color_divider);
            }

            // 2. Auto
            if (oModelColor.isbAutoExploitation()) {
                Utility_Image.setImageColor(mContext, vhModelColor.ivAuto, R.color.color_green);
            } else {
                Utility_Image.setImageColor(mContext, vhModelColor.ivAuto, R.color.color_divider);
            }

            // 3. Exploitation
            switch (oModelColor.gettExploitation()) {
                case Constants_Intern.EXPLOITATION_NULL:
                    Utility_Image.setImageColor(mContext, vhModelColor.ivExploitation, R.color.color_divider);
                    break;
                case Constants_Intern.EXPLOITATION_RECYCLING:
                    Utility_Image.setImageColor(mContext, vhModelColor.ivExploitation, R.color.color_recycling);
                    break;
                case Constants_Intern.EXPLOITATION_REUSE:
                    Utility_Image.setImageColor(mContext, vhModelColor.ivExploitation, R.color.color_intact_reuse);
                    break;
            }
        }

        // Add
        if (getItemViewType(position) == Constants_Intern.ADD) {
            // Visibility
            vhModelColor.llIcons.setVisibility(View.INVISIBLE);
            vhModelColor.tvTitle.setVisibility(View.INVISIBLE);

            //
            Utility_Image.setImageResource(mContext, vhModelColor.ivContent, R.drawable.ic_add_24dp, R.color.color_text_secondary);
        }

        // Add Color
        if (getItemViewType(position) == Constants_Intern.ADD_COLOR) {
            // Data
            Color oColor = lColor.get(position-lModelColor.size());

            // Visibility
            vhModelColor.llIcons.setVisibility(View.VISIBLE);
            vhModelColor.tvTitle.setVisibility(View.VISIBLE);
            vhModelColor.ivMatch.setVisibility(View.INVISIBLE);
            vhModelColor.ivExploitation.setVisibility(View.INVISIBLE);

            // Title
            vhModelColor.tvTitle.setText(oColor.getcNameDE());
            setTitleTextSize(vhModelColor.tvTitle, oColor.getcNameDE().length());

            // Content
            vhModelColor.ivContent.setImageBitmap(null);
            vhModelColor.ivContent.setBackgroundColor(android.graphics.Color.parseColor(oColor.getHexCode()));

            // Icons
            Utility_Image.setImageResource(mContext, vhModelColor.ivAuto, R.drawable.ic_add_24dp, R.color.color_text_secondary);
        }

    }

    @Override
    public int getItemCount() {
        if (lColor == null) {
            return lModelColor.size()+1;
        } else {
            return lModelColor.size()+lColor.size();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position < lModelColor.size()) {
            return Constants_Intern.ITEM;
        } else {
            if (lColor != null) {
                return Constants_Intern.ADD_COLOR;
            } else {
                return Constants_Intern.ADD;
            }
        }
    }

    public void update(ArrayList<ModelColor> lModelColor, ArrayList<Color> lColor) {
        this.lModelColor = lModelColor;
        this.lColor = lColor;
        notifyDataSetChanged();
    }

    public void setTitleTextSize(TextView tvTitle, int length) {
        if (length > 6) {
            tvTitle.setTextSize(14);
            if (length > 12) {
                tvTitle.setTextSize(12);
            }
        } else {
            tvTitle.setTextSize(16);
        }
    }
}
