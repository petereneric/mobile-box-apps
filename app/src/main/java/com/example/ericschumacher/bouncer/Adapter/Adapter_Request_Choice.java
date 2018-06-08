package com.example.ericschumacher.bouncer.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Objects.Object_Choice;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 26.05.2018.
 */

public class Adapter_Request_Choice extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Variables
    Context Context;
    ArrayList<Object_Choice> lManufacturers;
    Interface_Selection iSelection;

    public Adapter_Request_Choice(Context context, ArrayList<Object_Choice> list, Interface_Selection i) {
        Context = context;
        lManufacturers = list;
        iSelection = i;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(Context).inflate(R.layout.cardview_choice, parent, false);
        ViewHolder_Request_Choice vh = new ViewHolder_Request_Choice(layout, new Interface_Click() {
            @Override
            public void onClick(int position) {
                iSelection.callbackManufacturer(lManufacturers.get(position).getId());
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder_Request_Choice h = (ViewHolder_Request_Choice)holder;
        Object_Choice oManufacturer = lManufacturers.get(position);
        h.tvName.setText(oManufacturer.getName());
        h.tvName.setVisibility(View.GONE);
        Log.i("onBind", oManufacturer.getName());

        ImageRequest request = new ImageRequest("http://svp-server.com/svp-gmbh/erp/files/icon_manufactures/"+Integer.toString(oManufacturer.getId())+".png",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        h.ivIcon.setImageBitmap(bitmap);
                    }
                },  0, 0, ImageView.ScaleType.FIT_CENTER,null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        RequestQueue queue;
        queue = Volley.newRequestQueue(Context);
        queue.add(request);
        queue.getCache().clear();

        ImageRequest request2 = new ImageRequest("http://svp-server.com/svp-gmbh/erp/files/icon_manufactures/"+Integer.toString(oManufacturer.getId())+".jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        h.ivIcon.setImageBitmap(bitmap);
                    }
                },  0, 0, ImageView.ScaleType.FIT_CENTER,null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        RequestQueue queue2;
        queue2 = Volley.newRequestQueue(Context);
        queue2.add(request2);
        queue2.getCache().clear();
        // It's suggested to work with singleton class
    }

    @Override
    public int getItemCount() {
        return lManufacturers.size();
    }

    private class ViewHolder_Request_Choice extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout rlCardview;
        ImageView ivIcon;
        TextView tvName;

        Interface_Click iClick;

        public ViewHolder_Request_Choice(View itemView, Interface_Click i) {
            super(itemView);

            rlCardview = itemView.findViewById(R.id.rl_cardview);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);

            rlCardview.setOnClickListener(this);

            iClick = i;


        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_cardview:
                    iClick.onClick(getAdapterPosition());
            }
        }
    }

    private interface Interface_Click {
        public void onClick(int position);
    }
}
