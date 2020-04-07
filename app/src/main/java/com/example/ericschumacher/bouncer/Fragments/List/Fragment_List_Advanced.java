package com.example.ericschumacher.bouncer.Fragments.List;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_List;

import org.json.JSONObject;

public class Fragment_List_Advanced extends Fragment_List {

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        /*
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, iActivityList.getSwipeBehaviour(getTag())) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (viewHolder.getAdapterPosition() > 0) {
                    switch (direction) {
                        case ItemTouchHelper.LEFT:

                            //iActivityList.onSwipeLeft(viewHolder.getAdapterPosition(), getTag(), getJsonObject(viewHolder.getAdapterPosition()), (com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_List) Fragment_List_New.this);

                            break;
                        case ItemTouchHelper.RIGHT:
                            //iActivityList.onSwipeRight(viewHolder.getAdapterPosition(), getTag(), getJsonObject(viewHolder.getAdapterPosition()), (com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_List)Fragment_List_New.this);
                            break;
                    }
                }
            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View llForeground = ((Adapter_List.ViewHolder_List) viewHolder).llForeground;
                final TextView tvBackground = ((Adapter_List.ViewHolder_List) viewHolder).tvBackground;
                if (dX > 0) {
                    //iList.setViewSwipeRight(getTag(), tvBackground);
                } else {
                    //iActivityList.setViewSwipeLeft(getTag(), tvBackground);
                }
                getDefaultUIUtil().onDraw(c, recyclerView, llForeground, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                        RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                        int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((Adapter_List.ViewHolder_List) viewHolder).llForeground;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final View foregroundView = ((Adapter_List.ViewHolder_List) viewHolder).llForeground;
                getDefaultUIUtil().clearView(foregroundView);
            }
        };



        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvList);
        */
    }

    public interface Interface_List_Advanced {
        public void onSwipeLeft(int nPosition, String cTag, JSONObject jsonObject, Interface_Fragment_List iFragmentList);
        public void onSwipeRight(int nPosition, String cTag, JSONObject jsonObject, Interface_Fragment_List iFragmentList);
        public void setViewSwipeRight(String cTag, TextView tvBackground);
        public void setViewSwipeLeft(String cTag, TextView tvBackground);
        public int getSwipeBehaviour(String cTag);
    }
}
