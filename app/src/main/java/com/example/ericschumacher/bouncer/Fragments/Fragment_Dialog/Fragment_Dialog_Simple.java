package com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Dialog;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Dialog_Simple extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString(Constants_Intern.TITLE);
        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_attention_red_24dp)
                .setTitle(title)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((Interface_Dialog)getActivity()).onYes(getArguments().getInt(Constants_Intern.TYPE_FRAGMENT_DIALOG, Constants_Intern.TYPE_FRAGMENT_DIALOG_TOTAL_RESET));
                            }
                        }
                )
                .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((Interface_Dialog)getActivity()).onNo();
                            }
                        }
                )
                .create();
    }
}
