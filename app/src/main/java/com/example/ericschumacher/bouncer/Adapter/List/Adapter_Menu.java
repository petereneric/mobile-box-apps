package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Objects.Object_Menu;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 08.07.2018.
 */

public class Adapter_Menu extends RecyclerView.Adapter<Adapter_Menu.View_Holder_Menu> {

    ArrayList<Object_Menu> lMenu;
    Context Context;

    public Adapter_Menu(Context c, ArrayList<Object_Menu> l) {
        lMenu = l;
        Context = c;
    }

    @Override
    public Adapter_Menu.View_Holder_Menu onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(Context).inflate(R.layout.cardview_menu, parent, false);
        View_Holder_Menu vh = new View_Holder_Menu(layout, new Interface_Click() {
            @Override
            public void onClick(int i) {
                Context.startActivity(lMenu.get(i).getIntentActivity());
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(Adapter_Menu.View_Holder_Menu holder, int position) {
        holder.tvMenu.setText(lMenu.get(position).getName());
        holder.tvMenu.setBackgroundColor(ResourcesCompat.getColor(Context.getResources(), lMenu.get(position).getIdColor(), null));
    }

    @Override
    public int getItemCount() {
        return lMenu.size();
    }

    public class View_Holder_Menu extends RecyclerView.ViewHolder implements View.OnClickListener {

        Interface_Click iClick;
        TextView tvMenu;

        public View_Holder_Menu(View itemView, Interface_Click i) {
            super(itemView);

            iClick = i;

            tvMenu = itemView.findViewById(R.id.tvMenu);
            tvMenu.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvMenu:
                    iClick.onClick(getAdapterPosition());
            }
        }
    }

    public interface Interface_Click {
        public void onClick(int i);
    }
}
