package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Checker;
import com.example.ericschumacher.bouncer.Objects.DiagnoseCheck;
import com.example.ericschumacher.bouncer.Objects.ModelCheck;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;

import java.util.ArrayList;

import static android.view.View.GONE;

public class Adapter_List_Diagnose extends Adapter_List {

    // Data
    ArrayList<ModelCheck> lModelChecks = new ArrayList<>();
    ArrayList<DiagnoseCheck> lDiagnoseChecks = new ArrayList<>();
    Integer sFails = null;

    // Interface
    Interface_Fragment_Checker iChecker;

    public Adapter_List_Diagnose(android.content.Context context, Interface_Adapter_List iAdapterList, ArrayList<ModelCheck> lModelChecks, ArrayList<DiagnoseCheck> lDiagnoseChecks, Interface_Fragment_Checker iChecker) {
        super(context, iAdapterList);

        // Data
        this.lModelChecks = lModelChecks;
        this.lDiagnoseChecks = lDiagnoseChecks;

        // Interfaces
        this.iChecker = iChecker;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_List vhList = (ViewHolder_List)super.onCreateViewHolder(parent, viewType);
        vhList.ivSwipe.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_unlink_24));
        vhList.clBackground.setBackground(ResourcesCompat.getDrawable(Context.getResources(), R.drawable.background_rounded_corners_solid_orange, null));
        Utility_Layout.setBackground(Context, vhList.ivSwipe, R.color.color_orange);
        return vhList;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_List vhList = (ViewHolder_List) holder;

        // General
        vhList.tvRight.setVisibility(GONE);

        // Item
        if (iAdapterList.getItemViewType(position) == Constants_Intern.ITEM) {
            // Data
            ModelCheck oModelCheck = lModelChecks.get(position);

            // General
            Utility_Layout.setRoundedCorners(Context, vhList.lMain, R.color.color_text_secondary);
            Utility_Layout.setBackground(Context, vhList.vDividerLeft, R.color.color_text_secondary);

            // Left
            vhList.vDividerLeft.setVisibility(View.VISIBLE);
            vhList.tvLeft.setVisibility(View.VISIBLE);
            vhList.ivLeft.setVisibility(GONE);
            vhList.tvLeft.setText(Integer.toString((oModelCheck.getnPosition()+1)));


            // Middle
            vhList.tvTitle.setText(oModelCheck.getoCheck().getcName());
            vhList.tvSubtitle.setText(oModelCheck.getoCheck().getcDescription() + ((!oModelCheck.getoCheck().getcDescription().equals("") && !oModelCheck.getcDescription().equals("")) ? " | " : "") + oModelCheck.getcDescription());

            if (!oModelCheck.getoCheck().getcDescription().equals("") || !oModelCheck.getcDescription().equals("")) {
                vhList.tvSubtitle.setVisibility(View.VISIBLE);
            } else {
                vhList.tvSubtitle.setVisibility(GONE);
            }
            Utility_Layout.setTextColor(Context, vhList.tvTitle, R.color.color_text_secondary);


            // Right
            int tDiagnoseCheck = 0; // 0: not checked, 1: passed, 2: failed
            for (DiagnoseCheck oDiagnoseCheck : lDiagnoseChecks) {
                if (oDiagnoseCheck.getkCheck() == oModelCheck.getoCheck().getId()) {
                    tDiagnoseCheck = oDiagnoseCheck.gettStatus();
                }
            }
            switch (tDiagnoseCheck) {
                case 0:
                    vhList.ivRight.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_line_white_24));
                    vhList.ivRight.setColorFilter(Context.getResources().getColor(R.color.color_divider));
                    break;
                case 1:
                    vhList.ivRight.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_done_white_24dp));
                    vhList.ivRight.setColorFilter(Context.getResources().getColor(R.color.color_green));
                    break;
                case 2:
                    vhList.ivRight.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_clear_24dp));
                    vhList.ivRight.setColorFilter(Context.getResources().getColor(R.color.color_red));
                    break;
            }


        }

        // Special
        if (iAdapterList.getItemViewType(position) == Constants_Intern.SPECIAL) {
            vhList.ivLeft.setColorFilter(Context.getResources().getColor(R.color.color_primary));

            // BackgroundColor
            Utility_Layout.setRoundedCorners(Context, vhList.clForeground, R.color.color_primary);
            Utility_Layout.setBackground(Context, vhList.vDividerLeft, R.color.color_primary);

            // TextColor
            Utility_Layout.setTextColor(Context, vhList.tvTitle, R.color.color_primary);

            // Visibility
            vhList.tvSubtitle.setVisibility(GONE);
            vhList.tvRight.setVisibility(GONE);
            vhList.vDividerRight.setVisibility(GONE);

            // Variable
            vhList.tvLeft.setVisibility(GONE);
            vhList.ivLeft.setVisibility(View.VISIBLE);
            if (iChecker.getDiagnose() != null && iChecker.getDiagnose().isbFinished()) {
                // Finished
                vhList.ivLeft.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_airplane_24));
                vhList.tvTitle.setText(Context.getString(R.string.show_handler));
            } else {
                // Not finished
                vhList.ivLeft.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_done_all_white_24));
                if (lDiagnoseChecks.size() > 0) {
                    vhList.tvTitle.setText(Context.getString(R.string.finish_diagnose_with_success_rest));
                } else {
                    vhList.tvTitle.setText(Context.getString(R.string.finish_diagnose_with_success));
                }
            }
        }
    }

    public void updateData(ArrayList<ModelCheck> lModelChecks, ArrayList<DiagnoseCheck> lDiagnoseChecks) {
        this.lModelChecks = lModelChecks;
        this.lDiagnoseChecks = lDiagnoseChecks;
        notifyDataSetChanged();
    }


}
