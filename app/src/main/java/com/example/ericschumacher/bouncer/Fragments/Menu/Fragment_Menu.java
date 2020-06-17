package com.example.ericschumacher.bouncer.Fragments.Menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Menu extends Fragment implements View.OnClickListener {

    // vLayout
    View vLayout;
    ImageView ivPrint;
    ImageView ivAdd;
    ImageView ivDelete;
    View vDividerOne;
    View vDividerTwo;

    // Interface
    Interface_Fragment_Menu iFragmentMenu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Layout
        setLayout(inflater, container);

        // Interface
        iFragmentMenu = (Interface_Fragment_Menu)getActivity();

        return vLayout;
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_menu, container, false);

        ivPrint = vLayout.findViewById(R.id.ivPrint);
        ivAdd = vLayout.findViewById(R.id.ivAdd);
        ivDelete = vLayout.findViewById(R.id.ivDelete);
        vDividerOne = vLayout.findViewById(R.id.vDividerOne);
        vDividerTwo = vLayout.findViewById(R.id.vDividerTwo);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivPrint:
                iFragmentMenu.returnMenu(getTag(), Constants_Intern.MENU_PRINT);
                break;
            case R.id.ivAdd:
                iFragmentMenu.returnMenu(getTag(), Constants_Intern.MENU_ADD);
                break;
            case R.id.ivDelete:
                iFragmentMenu.returnMenu(getTag(), Constants_Intern.MENU_DELETE);
                break;
        }
    }

    public interface Interface_Fragment_Menu {
        void returnMenu(String cTag, int tAction);
    }
}
