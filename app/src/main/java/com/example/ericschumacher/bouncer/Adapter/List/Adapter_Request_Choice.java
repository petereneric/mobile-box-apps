package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.example.ericschumacher.bouncer.Interfaces.Interface_Request_Choice;
import com.example.ericschumacher.bouncer.Objects.Additive.Additive;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
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
    Interface_Request_Choice iRequestChoice;
    RequestQueue queue;

    public Adapter_Request_Choice(Context context, ArrayList<Additive> list, Interface_Request_Choice i) {
        Context = context;
        lAdditive = list;
        iRequestChoice = i;
        queue = Volley.newRequestQueue(Context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(Context).inflate(R.layout.cardview_choice, parent, false);
        ViewHolder_Request_Choice vh = new ViewHolder_Request_Choice(layout, new Interface_Click() {
            @Override
            public void onClick(int position) {
                iRequestChoice.onChoice(position);
            }
        });
        return vh;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder_Request_Choice h = (ViewHolder_Request_Choice) holder;
        Additive additive = lAdditive.get(position);
        h.tvName.setText(additive.getName());
        Log.i("onBind", additive.getName());

        if (additive instanceof Color) {
            h.ivIcon.setBackgroundColor(android.graphics.Color.parseColor(((Color) additive).getHexCode()));
        } else {
            ImageRequest request = new ImageRequest("http://svp-server.com/svp-gmbh/erp/files/" + additive.getUrlName() + "/" + Integer.toString(additive.getId()) + ".png",
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

            ImageRequest request2 = new ImageRequest("http://svp-server.com/svp-gmbh/erp/files/" + additive.getUrlName() + "/" + Integer.toString(additive.getId()) + ".jpg",
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
        if (lAdditive.get(position) instanceof Manufacturer) {
            return TYPE_MANUFACTURER;
        } else {
            if (lAdditive.get(position) instanceof Charger) {
                return TYPE_CHARGER;
            } else {
                return TYPE_COLOR;
            }
        }
    }

public class ViewHolder_Request_Choice extends RecyclerView.ViewHolder implements View.OnClickListener {

    LinearLayout llCardview;
    ImageView ivIcon;
    ImageView ivIconTwo;
    TextView tvName;

    Interface_Click iClick;

    public ViewHolder_Request_Choice(View itemView, Interface_Click i) {
        super(itemView);

        llCardview = itemView.findViewById(R.id.ll_cardview);
        ivIcon = itemView.findViewById(R.id.iv_icon);
        ivIconTwo = itemView.findViewById(R.id.iv_icon_two);
        tvName = itemView.findViewById(R.id.tv_name);

        //tvName.setVisibility(View.GONE);

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

public interface Interface_Click {
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
