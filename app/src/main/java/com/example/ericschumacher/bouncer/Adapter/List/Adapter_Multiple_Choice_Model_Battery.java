package com.example.ericschumacher.bouncer.Adapter.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_Choice_Simple;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_Choice_Simple_Add;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Interaction_Multiple_Choice_Model_Battery;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Long_Click;
import com.example.ericschumacher.bouncer.Objects.Model_Battery;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Density;

import java.util.ArrayList;

public class Adapter_Multiple_Choice_Model_Battery extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Else
    Context Context;

    // Data
    ArrayList<Model_Battery> lModelBatteries;
    int tMode;

    // Instance
    Fragment_Interaction_Multiple_Choice_Model_Battery iFragment;

    public Adapter_Multiple_Choice_Model_Battery(Context context, ArrayList<Model_Battery> lModelBatteries, Fragment_Interaction_Multiple_Choice_Model_Battery iFragment, int tMode) {
        Context = context;
        this.tMode = tMode;
        this.iFragment = iFragment;
        this.lModelBatteries = lModelBatteries;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constants_Intern.VIEW) {
            ViewHolder_Choice_Simple vhChoiceSimple = new ViewHolder_Choice_Simple(LayoutInflater.from(Context).inflate(R.layout.item_choice, parent, false), new Interface_Click() {
                @Override
                public void onClick(int position) {
                    Model_Battery oModelBattery = lModelBatteries.get(position);
                    iFragment.onClick(oModelBattery);
                }
            }, new Interface_Long_Click() {
                @Override
                public boolean onLongClick(final int position) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Context);
                    final String[] lChoice = {Context.getString(R.string.disconnect)};
                    builder.setItems(lChoice, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    iFragment.deleteModelBattery(lModelBatteries.get(position));
                                    break;
                            }
                        }
                    });
                    builder.create().show();
                    return true;
                }
            });
            return vhChoiceSimple;
        }
        if (viewType == Constants_Intern.ADD) {
            ViewHolder_Choice_Simple_Add vhChoiceSimpleAdd = new ViewHolder_Choice_Simple_Add(LayoutInflater.from(Context).inflate(R.layout.item_choice_simple_add, parent, false), new Interface_Click() {
                @Override
                public void onClick(int position) {
                    iFragment.showFragmentRequestNameBattery();

                }
            });
            return vhChoiceSimpleAdd;
        }
        return null;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == Constants_Intern.VIEW) {
            Model_Battery oModelBattery = lModelBatteries.get(position);
            ViewHolder_Choice_Simple vhChoiceSimple = (ViewHolder_Choice_Simple)holder;

            vhChoiceSimple.tvName.setText(oModelBattery.getoBattery().getName());

            if (tMode == Constants_Intern.TYPE_MODE_EDIT) {
                switch (oModelBattery.gettStatus()) {
                    case Constants_Intern.MODEL_BATTERY_STATUS_NORMAL:
                        GradientDrawable shape = new GradientDrawable();
                        shape.setShape(GradientDrawable.RECTANGLE);
                        shape.setCornerRadii(new float[] {Utility_Density.getDp(Context, 16),Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16)});
                        //shape.setColor(ContextCompat.getColor(Context, R.color.color_green));
                        shape.setStroke(1, ResourcesCompat.getColor(Context.getResources(), R.color.color_grey, null));
                        vhChoiceSimple.llChoiceSimple.setBackground(shape);
                        break;
                    case Constants_Intern.MODEL_BATTERY_STATUS_PRIME:
                        GradientDrawable shape2 = new GradientDrawable();
                        shape2.setShape(GradientDrawable.RECTANGLE);
                        shape2.setCornerRadii(new float[] {Utility_Density.getDp(Context, 16),Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16)});
                        //shape.setColor(ContextCompat.getColor(Context, R.color.color_green));
                        shape2.setStroke(1, ResourcesCompat.getColor(Context.getResources(), R.color.color_green, null));
                        vhChoiceSimple.llChoiceSimple.setBackground(shape2);
                        break;
                    case Constants_Intern.MODEL_BATTERY_STATUS_NOT_WANTED:
                        GradientDrawable shape3 = new GradientDrawable();
                        shape3.setShape(GradientDrawable.RECTANGLE);
                        shape3.setCornerRadii(new float[] {Utility_Density.getDp(Context, 16),Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16)});
                        //shape.setColor(ContextCompat.getColor(Context, R.color.color_green));
                        shape3.setStroke(1, ResourcesCompat.getColor(Context.getResources(), R.color.color_red, null));
                        vhChoiceSimple.llChoiceSimple.setBackground(shape3);
                        break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return lModelBatteries.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == lModelBatteries.size()) {
            return Constants_Intern.ADD;
        } else {
            return Constants_Intern.VIEW;
        }
    }
}
