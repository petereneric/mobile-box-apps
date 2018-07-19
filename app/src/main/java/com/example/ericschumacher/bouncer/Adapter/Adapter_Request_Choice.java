package com.example.ericschumacher.bouncer.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Objects.Object_Choice;
import com.example.ericschumacher.bouncer.Objects.Object_Choice_Charger;
import com.example.ericschumacher.bouncer.Objects.Object_Choice_Color;
import com.example.ericschumacher.bouncer.Objects.Object_Choice_Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Supplement.Additive;
import com.example.ericschumacher.bouncer.Objects.Supplement.Variation_Color;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 26.05.2018.
 */

public class Adapter_Request_Choice extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Types
    private final static int TYPE_CHARGER = 0;
    private final static int TYPE_MANUFACTURER = 1;
    private final static int TYPE_COLOR = 2;

    // Variables
    Context Context;
    ArrayList<Additive> lAdditive;
    Interface_Selection iSelection;
    RequestQueue queue;

    public Adapter_Request_Choice(Context context, ArrayList<Additive> list, Interface_Selection i) {
        Context = context;
        lAdditive = list;
        iSelection = i;
        queue = Volley.newRequestQueue(Context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(Context).inflate(R.layout.cardview_choice, parent, false);
        if (viewType == TYPE_MANUFACTURER) {
            ViewHolder_Request_Choice vh = new ViewHolder_Request_Choice(layout, new Interface_Click() {
                @Override
                public void onClick(int position) {
                    iSelection.callbackManufacturer(lAdditive.get(position).getId(), lAdditive.get(position).getName());
                }
            });
            return vh;
        }
        if (viewType == TYPE_CHARGER) {
            ViewHolder_Request_Choice vh = new ViewHolder_Request_Choice_Charger(layout, new Interface_Click() {
                @Override
                public void onClick(int position) {
                    iSelection.callbackCharger(lAdditive.get(position).getId(), lAdditive.get(position).getName());
                }
            });
            return vh;
        }
        if (viewType == TYPE_COLOR) {
            ViewHolder_Request_Choice_Color vh = new ViewHolder_Request_Choice_Color(layout, new Interface_Click() {
                @Override
                public void onClick(int position) {
                    Variation_Color vColor = (Variation_Color)lAdditive.get(position);
                    iSelection.callbackColor(vColor.getId(), vColor.getIdColor(), vColor.getName(), vColor.getHexCode());
                }
            });
            return vh;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder_Request_Choice h = (ViewHolder_Request_Choice) holder;
        Additive additive = lAdditive.get(position);
        h.tvName.setText(additive.getName());
        Log.i("onBind", additive.getName());

        if (additive instanceof Variation_Color) {
            h.ivIcon.setBackgroundColor(Color.parseColor(((Variation_Color)additive).getHexCode()));
        } else {
            ImageRequest request = new ImageRequest("http://svp-server.com/svp-gmbh/erp/files/"+additive.getUrlName()+"/" + Integer.toString(additive.getId()) + ".png",
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            h.ivIcon.setImageBitmap(bitmap);
                        }
                    }, 0, 0, ImageView.ScaleType.FIT_CENTER, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });


            queue.add(request);
            queue.getCache().clear();

            ImageRequest request2 = new ImageRequest("http://svp-server.com/svp-gmbh/erp/files/"+additive.getUrlName()+"/" + Integer.toString(additive.getId()) + ".jpg",
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            h.ivIcon.setImageBitmap(bitmap);
                        }
                    }, 0, 0, ImageView.ScaleType.FIT_CENTER, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });


            queue.add(request2);
            queue.getCache().clear();
        }
        // It's suggested to work with singleton class
    }

    @Override
    public int getItemCount() {
        return lAdditive.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (lAdditive.get(position) instanceof Object_Choice_Manufacturer) {
            return TYPE_MANUFACTURER;
        } else {
            if (lAdditive.get(position) instanceof Object_Choice_Charger) {
                return TYPE_CHARGER;
            } else {
                return TYPE_COLOR;
            }
        }
    }

    private class ViewHolder_Request_Choice extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout llCardview;
        ImageView ivIcon;
        TextView tvName;

        Interface_Click iClick;

        public ViewHolder_Request_Choice(View itemView, Interface_Click i) {
            super(itemView);

            llCardview = itemView.findViewById(R.id.ll_cardview);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);

            tvName.setVisibility(View.GONE);

            llCardview.setOnClickListener(this);

            iClick = i;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_cardview:
                    iClick.onClick(getAdapterPosition());
            }
        }
    }

    private interface Interface_Click {
        public void onClick(int position);
    }

    private class ViewHolder_Request_Choice_Charger extends ViewHolder_Request_Choice {

        public ViewHolder_Request_Choice_Charger(View itemView, Interface_Click i) {
            super(itemView, i);
            tvName.setVisibility(View.VISIBLE);
            ivIcon.setVisibility(View.VISIBLE);
        }
    }
    private class ViewHolder_Request_Choice_Color extends ViewHolder_Request_Choice {

        public ViewHolder_Request_Choice_Color(View itemView, Interface_Click i) {
            super(itemView, i);
            tvName.setVisibility(View.VISIBLE);
        }
    }
}
