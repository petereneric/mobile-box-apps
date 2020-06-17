package com.example.ericschumacher.bouncer.Fragments.Parent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.R;

public class Fragment_Object extends Fragment implements View.OnClickListener {

    // Layout
    View vLayout;
    TextView tvTitle;
    public ImageView ivPrint;
    public ImageView ivAdd;
    public ImageView ivDone;
    public ImageView ivClear;
    public ImageView ivDelete;

    // Interface
    Interface_Fragment_Object_Menu iFragmentObjectMenu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Interface
        iFragmentObjectMenu = (Interface_Fragment_Object_Menu)getActivity();

        // Layout
        setLayout(inflater, container);

        return vLayout;
    }


    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(getIdLayout(), container, false);

        tvTitle = vLayout.findViewById(R.id.tvTitle);
        ivPrint = vLayout.findViewById(R.id.ivPrint);
        ivAdd = vLayout.findViewById(R.id.ivAdd);
        ivDone = vLayout.findViewById(R.id.ivDone);
        ivClear = vLayout.findViewById(R.id.ivClear);
        ivDelete = vLayout.findViewById(R.id.ivDelete);

        // ClickListener
        ivPrint.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        ivDone.setOnClickListener(this);
        ivClear.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
    }

    public int getIdLayout() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivPrint:
                iFragmentObjectMenu.returnPrint(getTag());
                break;
            case R.id.ivAdd:
                iFragmentObjectMenu.returnAdd(getTag());
                break;
            case R.id.ivDone:
                iFragmentObjectMenu.returnDone(getTag());
                break;
            case R.id.ivClear:
                iFragmentObjectMenu.returnClear(getTag());
                break;
            case R.id.ivDelete:
                iFragmentObjectMenu.returnDelete(getTag());
                break;
        }
    }


    public interface Interface_Fragment_Object_Menu{
        void returnPrint(String cTag);
        void returnAdd(String cTag);
        void returnDone(String cTag);
        void returnClear(String cTag);
        void returnDelete(String cTag);
    }
}
