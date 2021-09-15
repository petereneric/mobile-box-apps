package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.ModelCheck;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

import static android.view.View.GONE;

public class Adapter_List_ModelChecks extends Adapter_List {

    // Data
    ArrayList<ModelCheck> lModelChecks = new ArrayList<>();
    Integer sFails = null;

    public Adapter_List_ModelChecks(android.content.Context context, Adapter_List.Interface_Adapter_List iAdapterList, ArrayList<ModelCheck> lModelChecks) {
        super(context, iAdapterList);
        this.lModelChecks = lModelChecks;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_List vhList = (ViewHolder_List)holder;

        // Item
        if (iAdapterList.getItemViewType(position) == Constants_Intern.ITEM) {
            // Data
            ModelCheck oModelCheck = lModelChecks.get(position);

            // Left
            if (oModelCheck.isbPositionFixed()) {
                vhList.ivLeft.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_lock_24));
                vhList.ivLeft.setColorFilter(Context.getResources().getColor(R.color.color_red));
                vhList.tvLeft.setVisibility(View.GONE);
            } else {
                vhList.ivLeft.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_lock_open_24));
                vhList.ivLeft.setColorFilter(Context.getResources().getColor(R.color.color_green));
                vhList.tvLeft.setVisibility(View.GONE);
            }


            // Right
            vhList.ivRight.setVisibility(View.GONE);
            Double qFail = getFailQuote(oModelCheck);
            vhList.tvRight.setText(sFails != null ? String.format("%.0f", qFail) + " %" : "-");

            // Middle
            vhList.tvTitle.setText(oModelCheck.getoCheck().getcName());


            if (!oModelCheck.getoCheck().getcDescription().equals("") || !oModelCheck.getcDescription().equals("")) {
                vhList.tvSubtitle.setVisibility(View.VISIBLE);
                vhList.tvSubtitle.setText(oModelCheck.getoCheck().getcDescription() + ((!oModelCheck.getoCheck().getcDescription().equals("") && !oModelCheck.getcDescription().equals("")) ? " | " : "") + oModelCheck.getcDescription());
            } else {
                vhList.tvSubtitle.setVisibility(GONE);
            }
        }

        // Add
        if (iAdapterList.getItemViewType(position) == Constants_Intern.ADD) {
            // Left
            vhList.ivLeft.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_add_24dp));
            vhList.tvLeft.setVisibility(View.GONE);

            // Right
            vhList.ivRight.setVisibility(View.GONE);
            vhList.tvRight.setVisibility(View.GONE);
            vhList.vDividerRight.setVisibility(View.GONE);

            // Middle
            vhList.tvTitle.setText(Context.getString(R.string.add));
            vhList.tvSubtitle.setVisibility(View.GONE);
        }
    }

    public void update(ArrayList lData) {
        sFails = null;
        lModelChecks = lData;
    }

    private Double getFailQuote(ModelCheck oModelCheck) {
        if (sFails == null) {
            for (ModelCheck modelCheck : lModelChecks) {
                if (sFails == null && modelCheck.getnCount() > 0) {
                    sFails = 0;
                }
                if (sFails != null) sFails = sFails + modelCheck.getnCount();
            }
        }
        return sFails != null ? (double)oModelCheck.getnCount()/(double)sFails*100 : null;
    }

}
