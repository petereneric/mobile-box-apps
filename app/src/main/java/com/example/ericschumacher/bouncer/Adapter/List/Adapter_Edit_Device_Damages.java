package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Object_Device_Damage;
import com.example.ericschumacher.bouncer.Objects.Object_Model_Damage;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;

import java.util.ArrayList;

import static android.view.View.GONE;

public class Adapter_Edit_Device_Damages extends Adapter_List {

    // Data
    ArrayList<Object_Device_Damage> lDeviceDamages = new ArrayList<>();
    ArrayList<Object_Model_Damage> lModelDamages = new ArrayList<>();

    public Adapter_Edit_Device_Damages(android.content.Context context, Interface_Adapter_List iAdapterList, ArrayList<Object_Model_Damage> lModelDamages, ArrayList<Object_Device_Damage> lDeviceDamages) {
        super(context, iAdapterList);
        this.lDeviceDamages = lDeviceDamages;
        this.lModelDamages = lModelDamages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_List vhList = (ViewHolder_List)super.onCreateViewHolder(parent, viewType);
        Utility_Layout.setImageDrawable(Context, vhList.ivSwipeLeft, R.drawable.ic_repair);
        Utility_Layout.setImageDrawable(Context, vhList.ivSwipeRight, R.drawable.ic_unlink_24);
        return vhList;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_List vhList = (ViewHolder_List) holder;

        // GENERAL
        // Right
        vhList.ivRight.setVisibility(GONE);
        vhList.tvRight.setVisibility(GONE);
        vhList.vDividerRight.setVisibility(GONE);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) vhList.gTwo.getLayoutParams();
        params.guidePercent = 1;
        vhList.gTwo.setLayoutParams(params);
        // Left
        vhList.ivLeft.setVisibility(GONE);
        vhList.tvLeft.setVisibility(GONE);
        vhList.vDividerLeft.setVisibility(GONE);
        ConstraintLayout.LayoutParams paramsTwo = (ConstraintLayout.LayoutParams) vhList.gOne.getLayoutParams();
        paramsTwo.guideBegin = 0;
        vhList.gOne.setLayoutParams(paramsTwo);
        // Middle
        vhList.tvSubtitle.setVisibility(GONE);
        vhList.tvTitle.setGravity(Gravity.CENTER);

        if (iAdapterList.getItemViewType(position) == Constants_Intern.ITEM) {

            // ITEM
            // Data
            Object_Model_Damage oModelDamage = lModelDamages.get(position);

            // Middle
            vhList.tvTitle.setText(oModelDamage.getoDamage().getcName());

            // Color
            Object_Device_Damage deviceDamage = ((Interface_Adapter_List_Edit_Device_Damages)iAdapterList).getDeviceDamage(position);
            if (deviceDamage != null) {
                switch (deviceDamage.gettStatus()) {
                    case 1: // Repairable
                        Utility_Layout.setBackgroundDrawable(Context, vhList.clForeground, R.drawable.background_rounded_corners_repair);
                        Utility_Layout.setTextColor(Context, vhList.tvTitle, R.color.color_defect_repair);
                        break;
                    case 2: // Repaired
                        Utility_Layout.setBackgroundDrawable(Context, vhList.clForeground, R.drawable.background_rounded_corners_positive);
                        Utility_Layout.setTextColor(Context, vhList.tvTitle, R.color.color_choice_positive);
                        break;
                    case 3: // Not repairable
                        Utility_Layout.setBackgroundDrawable(Context, vhList.clForeground, R.drawable.background_rounded_corners_negative);
                        Utility_Layout.setTextColor(Context, vhList.tvTitle, R.color.color_choice_negative);
                        break;
                }
            } else {
                Utility_Layout.setBackgroundDrawable(Context, vhList.clForeground, R.drawable.background_rounded_corners_grey);
                Utility_Layout.setTextColor(Context, vhList.tvTitle, R.color.color_grey);
            }
        }
        if (iAdapterList.getItemViewType(position) == Constants_Intern.TYPE_DAMAGE_OVERBROKEN) {
            // Middle
            vhList.tvTitle.setText(Context.getString(R.string.damage_overbroken));
        }
        if (iAdapterList.getItemViewType(position) == Constants_Intern.TYPE_DAMAGE_OTHER) {
            // Middle
            vhList.tvTitle.setText(Context.getString(R.string.damage_not_listed));
        }



    }

    public void update(ArrayList lModelDamages, ArrayList lDeviceDamages) {
        this.lModelDamages = lModelDamages;
        this.lDeviceDamages = lDeviceDamages;
        notifyDataSetChanged();
    }

    public interface Interface_Adapter_List_Edit_Device_Damages extends Adapter_List.Interface_Adapter_List {
        public Object_Device_Damage getDeviceDamage(int position);
    }
}
