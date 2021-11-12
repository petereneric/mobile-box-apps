package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.ModelCheck;
import com.example.ericschumacher.bouncer.Objects.Object_Model_Damage;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

import static android.view.View.GONE;

public class Adapter_Edit_Model_Damages extends Adapter_List {

    // Data
    ArrayList<Object_Model_Damage> lModelDamages = new ArrayList<>();

    public Adapter_Edit_Model_Damages(android.content.Context context, Interface_Adapter_List iAdapterList, ArrayList<Object_Model_Damage> lModelDamages) {
        super(context, iAdapterList);
        this.lModelDamages = lModelDamages;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_List vhList = (ViewHolder_List) holder;

        // GENERAL
        // Middle
        vhList.vDividerLeft.setVisibility(GONE);

        // Right
        vhList.ivRight.setVisibility(GONE);
        vhList.tvRight.setVisibility(GONE);
        vhList.vDividerRight.setVisibility(GONE);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) vhList.gTwo.getLayoutParams();
        params.guidePercent = 1;
        vhList.gTwo.setLayoutParams(params);

        // ITEM
        if (iAdapterList.getItemViewType(position) == Constants_Intern.ITEM) {
            // Data
            Object_Model_Damage oModelDamage = lModelDamages.get(position);

            // Left
            vhList.ivLeft.setVisibility(GONE);
            vhList.tvLeft.setVisibility(GONE);

            ConstraintLayout.LayoutParams paramsTwo = (ConstraintLayout.LayoutParams) vhList.gOne.getLayoutParams();
            paramsTwo.guideBegin = 0;
            vhList.gOne.setLayoutParams(paramsTwo);

            // Middle
            vhList.tvTitle.setText(oModelDamage.getoDamage().getcName());
            vhList.tvTitle.setGravity(Gravity.CENTER);
            vhList.tvSubtitle.setVisibility(GONE);
        }

        // ADD
        if (iAdapterList.getItemViewType(position) == Constants_Intern.ADD) {
            // Left
            vhList.ivLeft.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_add_24dp));
            vhList.tvTitle.setGravity(Gravity.START);
            vhList.tvLeft.setVisibility(View.GONE);

            ConstraintLayout.LayoutParams paramsTwo = (ConstraintLayout.LayoutParams) vhList.gOne.getLayoutParams();
            paramsTwo.guidePercent = 1;
            vhList.gOne.setLayoutParams(paramsTwo);

            // Middle
            vhList.tvTitle.setVisibility(GONE);
            vhList.tvSubtitle.setVisibility(View.GONE);
        }
    }

    public void update(ArrayList lData) {
        lModelDamages = lData;
        notifyDataSetChanged();
    }
}
