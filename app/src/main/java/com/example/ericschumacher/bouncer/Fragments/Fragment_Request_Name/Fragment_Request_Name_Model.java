package com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Name;

import android.view.View;

import com.example.ericschumacher.bouncer.R;

/**
 * Created by Eric Schumacher on 23.05.2018.
 */

public class Fragment_Request_Name_Model extends Fragment_Request_Name {

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_commit:
                iSelection.checkName(etModel.getText().toString());
                break;
        }
    }

}