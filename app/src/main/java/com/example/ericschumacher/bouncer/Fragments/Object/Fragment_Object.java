package com.example.ericschumacher.bouncer.Fragments.Object;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Object extends Fragment implements View.OnClickListener {

    // Layout
    public View vLayout;
    public TextView tvTitle;
    public ImageView ivPrint;
    public ImageView ivMail;
    public ImageView ivAdd;
    public ImageView ivPause;
    public ImageView ivDone;
    public ImageView ivUnlink;
    public ImageView ivClear;
    public ImageView ivDelete;
    public LinearLayout llLayout;

    // Interface
    public Interface_Fragment_Object_Menu iFragmentObjectMenu;

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
        ivMail = vLayout.findViewById(R.id.ivMail);
        ivAdd = vLayout.findViewById(R.id.ivAdd);
        ivPause = vLayout.findViewById(R.id.ivPause);
        ivDone = vLayout.findViewById(R.id.ivDone);
        ivUnlink = vLayout.findViewById(R.id.ivUnlink);
        ivClear = vLayout.findViewById(R.id.ivClear);
        ivDelete = vLayout.findViewById(R.id.ivDelete);

        // ClickListener
        ivPrint.setOnClickListener(this);
        ivMail.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        ivPause.setOnClickListener(this);
        ivDone.setOnClickListener(this);
        ivUnlink.setOnClickListener(this);
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
                Log.i("tag", getTag());
                iFragmentObjectMenu.returnMenu(Constants_Intern.TYPE_ACTION_MENU_PRINT, getTag());
                break;
            case R.id.ivMail:
                iFragmentObjectMenu.returnMenu(Constants_Intern.TYPE_ACTION_MENU_MAIL, getTag());
                break;
            case R.id.ivAdd:
                iFragmentObjectMenu.returnMenu(Constants_Intern.TYPE_ACTION_MENU_ADD, getTag());
                break;
            case R.id.ivPause:

                iFragmentObjectMenu.returnMenu(Constants_Intern.TYPE_ACTION_MENU_PAUSE, getTag());
                break;
            case R.id.ivDone:
                iFragmentObjectMenu.returnMenu(Constants_Intern.TYPE_ACTION_MENU_DONE, getTag());
                break;
            case R.id.ivUnlink:
                iFragmentObjectMenu.returnMenu(Constants_Intern.TYPE_ACTION_MENU_UNLINK, getTag());
                break;
            case R.id.ivClear:
                iFragmentObjectMenu.returnMenu(Constants_Intern.TYPE_ACTION_MENU_CLEAR, getTag());
                break;
            case R.id.ivDelete:
                iFragmentObjectMenu.returnMenu(Constants_Intern.TYPE_ACTION_MENU_DELETE, getTag());
                break;
        }
    }


    public interface Interface_Fragment_Object_Menu{
        void returnMenu(int tAction, String cTag);
    }
}
