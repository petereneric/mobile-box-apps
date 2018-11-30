package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

public class Adapter_Juicer_Charger extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Charger> lChargers;
    private Interface_Adapter_Juicer_Charger iListener;
    private Context mContext;

    public Adapter_Juicer_Charger(Context context, ArrayList<Charger> chargers, Interface_Adapter_Juicer_Charger listener) {
        mContext = context;
        lChargers = chargers;
        iListener = listener;
        iListener.onChargerChanged(lChargers);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_Juicer_Charger holder = new ViewHolder_Juicer_Charger(LayoutInflater.from(mContext).inflate(R.layout.item_juicer_charger, parent, false), new Listener_CheckBox() {
            @Override
            public void onCheckedChanged(int position, boolean checked) {
                Charger charger = lChargers.get(position);
                charger.setSelected(checked);
                ArrayList<Charger> chargersUnselected = new ArrayList<>();
                for (Charger charger1 : lChargers) {
                    if (!charger1.isSelected()) {
                        chargersUnselected.add(charger1);
                    }
                }
                iListener.onChargerChanged(chargersUnselected);
                //notifyDataSetChanged();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Charger charger = lChargers.get(position);
        ViewHolder_Juicer_Charger h = (ViewHolder_Juicer_Charger)holder;
        h.tvCharger.setText(charger.getName());
        h.cbCharger.setChecked(charger.isSelected());
    }

    @Override
    public int getItemCount() {
        return lChargers.size();
    }

    private class ViewHolder_Juicer_Charger extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        TextView tvCharger;
        CheckBox cbCharger;

        Listener_CheckBox iListener;

        public ViewHolder_Juicer_Charger(View itemView, Listener_CheckBox listener) {
            super(itemView);

            iListener = listener;

            tvCharger = itemView.findViewById(R.id.tvCharger);
            cbCharger = itemView.findViewById(R.id.cbCharger);
            cbCharger.setOnCheckedChangeListener(this);
        }


        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()) {
                case R.id.cbCharger:
                    iListener.onCheckedChanged(getAdapterPosition(), b);
                    break;
            }
        }


    }

    public void updateData(ArrayList<Charger> chargers) {
        lChargers = chargers;
        notifyDataSetChanged();
    }

    public interface Interface_Adapter_Juicer_Charger {
        public void onChargerChanged(ArrayList<Charger> chargerUnselected);
    }

    public interface Listener_CheckBox {
        public void onCheckedChanged(int position, boolean checked);
    }
}
