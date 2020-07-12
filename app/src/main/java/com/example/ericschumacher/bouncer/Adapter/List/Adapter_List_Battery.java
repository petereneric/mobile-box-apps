package com.example.ericschumacher.bouncer.Adapter.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_Battery;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Others.Fragment_Batteries;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Battery_Edit;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Long_Click;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Density;
import com.example.ericschumacher.bouncer.Zebra.ManagerPrinter;

import java.util.ArrayList;

public class Adapter_List_Battery extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    // Data
    ArrayList<Battery> lBatteries = new ArrayList<>();

    // Else
    Context Context;
    ManagerPrinter mPrinter;

    // Interface
    Fragment_Batteries iFragmentBatteries;

    public Adapter_List_Battery(Context context, ArrayList<Battery> lBatteries, Fragment_Batteries iFragmentBatteries) {
        Context = context;
        mPrinter = new ManagerPrinter(Context);
        this.lBatteries = lBatteries;
        this.iFragmentBatteries = iFragmentBatteries;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_Battery vhBattery = new ViewHolder_Battery(LayoutInflater.from(Context).inflate(R.layout.item_battery, parent, false), new Interface_Click() {
            @Override
            public void onClick(int position) {
                if (iFragmentBatteries != null) {
                    iFragmentBatteries.requestStorageLevel(position);
                }
            }
        }, new Interface_Long_Click() {
            @Override
            public boolean onLongClick(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Context);
                String[] lChoice = {Context.getString(R.string.print_label), Context.getString(R.string.edit), Context.getString(R.string.delete)};
                builder.setItems(lChoice, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mPrinter.printBattery(lBatteries.get(position));
                                break;
                            case 1:
                                FragmentTransaction ft = iFragmentBatteries.getFragmentManager().beginTransaction();
                                Fragment_Dialog_Battery_Edit fEdit = new Fragment_Dialog_Battery_Edit();
                                Bundle bundle = new Bundle();
                                bundle.putInt(Constants_Intern.POSITION_BATTERY, position);
                                bundle.putString(Constants_Intern.NAME_BATTERY, lBatteries.get(position).getName());
                                fEdit.setArguments(bundle);
                                fEdit.setTargetFragment(iFragmentBatteries, 0);
                                fEdit.show(ft, Constants_Intern.FRAGMENT_DIALOG_BATTERY_EDIT);
                                break;
                            case 2:
                                iFragmentBatteries.delete(position);
                                break;
                        }
                    }
                });
                builder.create().show();

                return true;
            }
        });
        return vhBattery;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("onBind: ", Integer.toString(position));
        ViewHolder_Battery vhBattery = (ViewHolder_Battery)holder;

        Battery oBattery = lBatteries.get(position);

        vhBattery.tvBatteryName.setText(oBattery.getName());
        if (oBattery.getLku() != null) {
            vhBattery.tvLku.setText(oBattery.getoManufacturer().getcShortcut()+" - "+oBattery.getLku());
        } else {
            vhBattery.tvLku.setText("-");
        }

        vhBattery.tvManufacturerName.setText(oBattery.getoManufacturer().getName());
        switch (oBattery.getlStock()) {
            case 0:
                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.RECTANGLE);
                shape.setCornerRadii(new float[] {Utility_Density.getDp(Context, 0),Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 8), Utility_Density.getDp(Context, 8)});
                shape.setColor(ContextCompat.getColor(Context, R.color.color_green));
                //shape.setStroke(3, borderColor);
                shape.setSize(Utility_Density.getDp(Context, 12), Utility_Density.getDp(Context, 12));
                vhBattery.ivStorageLevel.setBackground(shape);
                break;
            case 1:
                GradientDrawable shape2 = new GradientDrawable();
                shape2.setShape(GradientDrawable.RECTANGLE);
                shape2.setCornerRadii(new float[] {Utility_Density.getDp(Context, 0),Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 8), Utility_Density.getDp(Context, 8)});
                shape2.setColor(ContextCompat.getColor(Context, R.color.color_grey));
                //shape2.setStroke(3, borderColor);
                shape2.setSize(Utility_Density.getDp(Context, 12), Utility_Density.getDp(Context, 12));
                vhBattery.ivStorageLevel.setBackground(shape2);
                break;
            case 2:
                GradientDrawable shape3 = new GradientDrawable();
                shape3.setShape(GradientDrawable.RECTANGLE);
                shape3.setCornerRadii(new float[] {Utility_Density.getDp(Context, 0),Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 8), Utility_Density.getDp(Context, 8)});
                shape3.setColor(ContextCompat.getColor(Context, R.color.color_red));
                shape3.setSize(Utility_Density.getDp(Context, 12), Utility_Density.getDp(Context, 12));
                //shape3.setStroke(3, borderColor);
                vhBattery.ivStorageLevel.setBackground(shape3);
                break;
            case 3:
                GradientDrawable shape4 = new GradientDrawable();
                shape4.setShape(GradientDrawable.RECTANGLE);
                shape4.setCornerRadii(new float[] {Utility_Density.getDp(Context, 0),Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 0), Utility_Density.getDp(Context, 8), Utility_Density.getDp(Context, 8)});
                shape4.setColor(ContextCompat.getColor(Context, R.color.color_black));
                shape4.setSize(Utility_Density.getDp(Context, 12), Utility_Density.getDp(Context, 12));
                //shape3.setStroke(3, borderColor);
                vhBattery.ivStorageLevel.setBackground(shape4);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return lBatteries.size();
    }

}
