package com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Dialog_Simple;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Dialog_Simple extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Data
        String title = getArguments().getString(Constants_Intern.TITLE);
        Integer position = getArguments().getInt(Constants_Intern.POSITION);

        // Interface
        Interface_Dialog_Simple iDialogSimple;
        iDialogSimple = ((Interface_Dialog_Simple)getTargetFragment());
        if (iDialogSimple == null) {
            iDialogSimple = ((Interface_Dialog_Simple)getActivity());
        }
        Interface_Dialog_Simple finalIDialogSimple = iDialogSimple;
        return new AlertDialog.Builder(getActivity())
                //.setIcon(R.drawable.ic_attention_red_24dp)
                .setTitle(title)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finalIDialogSimple.onYes(getTag(), position);
                            }
                        }
                )
                .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finalIDialogSimple.onNo(getTag());
                            }
                        }
                )
                .create();
    }
}
