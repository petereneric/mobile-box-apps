package com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Dialog_Image extends DialogFragment implements View.OnClickListener {

    // Bitmap
    String sBitmap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sBitmap = getArguments().getString(Constants_Intern.STRING_BITMAP);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vLayout = inflater.inflate(R.layout.fragment_dialog_image, container, false);
        ImageView ivImage = vLayout.findViewById(R.id.ivImage);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        byte[] decodedString = Base64.decode(sBitmap, Base64.DEFAULT);
        Bitmap bitmapOne = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        ivImage.setImageBitmap(bitmapOne);

        ivImage.setOnClickListener(this);
        return vLayout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivImage:
                dismiss();
        }
    }
}
