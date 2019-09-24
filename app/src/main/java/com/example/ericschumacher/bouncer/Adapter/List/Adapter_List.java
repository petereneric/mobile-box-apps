package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Objects.Ann;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Density;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Adapter_List extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_LIST = 1;


    Context Context;

    boolean bHeader;
    JSONArray lJson;
    ArrayList<Ann> lAnn;
    Interface_Click iClick;

    public Adapter_List(Context context, boolean bHeader, Interface_Click iClick, JSONArray lJson, ArrayList<Ann> lAnn) {
        Context = context;
        this.bHeader = bHeader;
        this.lJson = lJson;
        this.lAnn = lAnn;
        this.iClick = iClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View Layout = LayoutInflater.from(Context).inflate(R.layout.item_list, parent, false);

        RelativeLayout relativeLayout = Layout.findViewById(R.id.RelativeLayout);
        LinearLayout linearLayout = new LinearLayout(Context);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)Context.getResources().getDimension(R.dimen.item_height_default));
        linearLayout.setLayoutParams(llp);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setBackgroundColor(ResourcesCompat.getColor(Context.getResources(), R.color.color_white, null));
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);

        ArrayList<TextView> lTextView = new ArrayList<>();
        Log.i("size Ann", Integer.toString(lAnn.size()));
        for (int i = 0; i < lAnn.size(); i++) {
            Ann ann = lAnn.get(i);
            TextView textView = new TextView(Context);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, ann.getnWeight());
            textView.setLayoutParams(lp);
            textView.setTextAppearance(Context, R.style.tv_itemRow);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(Utility_Density.getDp(Context,8), Utility_Density.getDp(Context,8),Utility_Density.getDp(Context,8),Utility_Density.getDp(Context,8));
            linearLayout.addView(textView);
            lTextView.add(textView);
        }

        relativeLayout.addView(linearLayout);

        return new ViewHolder_List(Layout, linearLayout, lTextView, new Interface_Click() {
            @Override
            public void onClick(int position) {
                iClick.onClick(position);
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_List vhList = (ViewHolder_List) holder;

        try {
        if (holder.getItemViewType() == TYPE_LIST) {
                JSONObject oJson = lJson.getJSONObject(bHeader ? position-1 : position);
                for (int i = 0; i<lAnn.size(); i++) {
                    Ann oAnn = lAnn.get(i);
                    if (oAnn.getJsonKeyGrandParent() != null) {
                        JSONObject oJsonTwo = oJson.getJSONObject(oAnn.getJsonKeyGrandParent());
                        JSONObject oJsonThree = oJsonTwo.getJSONObject(oAnn.getJsonKeyParent());
                        vhList.lTextView.get(i).setText(oJsonThree.getString(oAnn.getJsonKey()));
                    } else {
                        if (oAnn.getJsonKeyParent() != null) {
                            JSONObject oJsonFour = oJson.getJSONObject(oAnn.getJsonKeyParent());
                            if (oJsonFour != null) {
                                vhList.lTextView.get(i).setText(oJsonFour.getString(oAnn.getJsonKey()));
                            } else {
                                vhList.lTextView.get(i).setText(Context.getString(R.string.unknown));
                            }
                        } else {
                            if (oJson.getString(oAnn.getJsonKey()) != null) {
                                vhList.lTextView.get(i).setText(oJson.getString(oAnn.getJsonKey()));
                            } else {
                                vhList.lTextView.get(i).setText(Context.getString(R.string.unknown));
                            }
                        }
                    }
                }
        } else {
            for (int i = 0; i<lAnn.size(); i++) {
                Ann oAnn = lAnn.get(i);
                vhList.lTextView.get(i).setText(oAnn.getcTitle());
            }
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (bHeader && position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_LIST;
        }
    }

    @Override
    public int getItemCount() {
        Log.i("LEENGHT: ", Integer.toString(lJson.length()));
        if (bHeader && lJson.length() > 0) {
            return lJson.length() + 1;
        } else {
            return lJson.length();
        }

    }

    public void update(JSONArray lJson) {
        this.lJson = lJson;
        notifyDataSetChanged();
    }

    public class ViewHolder_List extends RecyclerView.ViewHolder {

        Interface_Click iClick;
        public TextView tvBackground;
        public LinearLayout llForeground;
        ArrayList<TextView> lTextView = new ArrayList<>();

        public ViewHolder_List(View itemView, LinearLayout llForeground, ArrayList<TextView> lTextView, final Interface_Click iClick) {
            super(itemView);

            tvBackground = itemView.findViewById(R.id.tvBackground);
            this.llForeground = llForeground;
            this.lTextView = lTextView;

            this.llForeground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClick.onClick(getAdapterPosition());
                }
            });


            this.iClick = iClick;
        }
    }
}
