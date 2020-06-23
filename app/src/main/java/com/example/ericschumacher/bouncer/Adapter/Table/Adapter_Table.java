package com.example.ericschumacher.bouncer.Adapter.Table;

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

public class Adapter_Table extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_LIST = 1;


    // Context
    Context Context;

    // Interface
    Interface_Adapter_Table iAdapterTable;


    public Adapter_Table(Context context, Interface_Adapter_Table iAdapterTable) {
        Context = context;
        this.iAdapterTable = iAdapterTable;
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
        for (int i = 0; i < iAdapterTable.getAnn().size(); i++) {
            Ann ann = iAdapterTable.getAnn().get(i);
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
                iAdapterTable.onItemSelected(position);
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_List vhList = (ViewHolder_List) holder;

        try {
        if (holder.getItemViewType() == TYPE_LIST) {
                JSONObject oJson = iAdapterTable.getJsonArray().getJSONObject(iAdapterTable.hasHeader() ? position-1 : position);
                for (int i = 0; i<iAdapterTable.getAnn().size(); i++) {
                    Ann oAnn = iAdapterTable.getAnn().get(i);
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
            for (int i = 0; i<iAdapterTable.getAnn().size(); i++) {
                Ann oAnn = iAdapterTable.getAnn().get(i);
                vhList.lTextView.get(i).setText(oAnn.getcTitle());
            }
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (iAdapterTable.hasHeader() && position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_LIST;
        }
    }

    @Override
    public int getItemCount() {
        Log.i("LEENGHT: ", Integer.toString(iAdapterTable.getJsonArray().length()));
        if (iAdapterTable.hasHeader() && iAdapterTable.getJsonArray().length() > 0) {
            return iAdapterTable.getJsonArray().length() + 1;
        } else {
            return iAdapterTable.getJsonArray().length();
        }

    }

    public void update(JSONArray lJson) {

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

    public interface Interface_Adapter_Table {
        JSONArray getJsonArray();
        boolean hasHeader();
        public ArrayList<Ann> getAnn();
        void onItemSelected(int position);
        JSONObject getJsonObject(int position);
    }
}
