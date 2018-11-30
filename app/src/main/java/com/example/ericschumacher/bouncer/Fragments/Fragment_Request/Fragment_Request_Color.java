package com.example.ericschumacher.bouncer.Fragments.Fragment_Request;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Request_Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Additive;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;

public class Fragment_Request_Color extends Fragment_Request_Choice {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setAdapter() {
        Adapter_Request_Color adapter = new Adapter_Request_Color(getActivity(), lAdditive, this);
        RecyclerView.setAdapter(adapter);
    }

    @Override
    public void onChoice(int position) {
        Additive additive = lAdditive.get(position);
        if (additive instanceof Color) iManager.returnColor((Color) lAdditive.get(position));
    }

    @Override
    public void onAdd() {
        iManager.fragmentColorAdd();
    }
}
