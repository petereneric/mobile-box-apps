package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Objects.Object_Id_Model_Color_Shape;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

public class Adapter_Turing extends RecyclerView.Adapter<Adapter_Turing.ViewHolder_Turing> {

    android.content.Context Context;
    ArrayList<Object_Id_Model_Color_Shape> lIdModelColorShape;

    public Adapter_Turing(Context context, ArrayList<Object_Id_Model_Color_Shape> lIdModelColorShape) {
        Context = context;
        this.lIdModelColorShape = lIdModelColorShape;
    }

    @Override
    public ViewHolder_Turing onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_Turing vhIdModelColorShape = new ViewHolder_Turing(LayoutInflater.from(Context).inflate(R.layout.item_id_model_color_shape, parent, false));
        return vhIdModelColorShape;
    }

    @Override
    public void onBindViewHolder(ViewHolder_Turing holder, int position) {
        Object_Id_Model_Color_Shape o = lIdModelColorShape.get(position);
        holder.tvId.setText(Integer.toString(o.getId_model_color_shape()));
        holder.tvIdColor.setText(Integer.toString(o.getId_color()));
        holder.tvIdShape.setText(Integer.toString(o.getId_shape()));
        holder.tvName.setText(o.getName());
        holder.tvRpd.setText(Double.toString(o.getRpd()));

        holder.tvName.setBackgroundColor(Context.getResources().getColor(R.color.color_grey_very_light));
        holder.tvIdShape.setBackgroundColor(Context.getResources().getColor(R.color.color_grey_very_light));
    }

    @Override
    public int getItemCount() {
        return lIdModelColorShape.size();
    }

    public void removeItem(RecyclerView.ViewHolder viewHolder) {
        lIdModelColorShape.remove(lIdModelColorShape.get(viewHolder.getAdapterPosition()));
        notifyDataSetChanged();
    }

    public class ViewHolder_Turing extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvId;
        TextView tvIdColor;
        TextView tvIdShape;
        TextView tvName;
        TextView tvRpd;


        public ViewHolder_Turing(View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvIdModelColorShape);
            tvIdColor = itemView.findViewById(R.id.tvIdColor);
            tvIdShape = itemView.findViewById(R.id.tvIdShape);
            tvName = itemView.findViewById(R.id.tvModel);
            tvRpd = itemView.findViewById(R.id.tvRpd);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
            }
        }
    }
}
