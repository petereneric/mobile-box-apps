package com.example.ericschumacher.bouncer.Fragments.Instruction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Instruction extends Fragment implements View.OnClickListener {

    // Layout
    View vLayout;
    TextView tvTitle;
    TextView tvInstruction;

    // Data
    public Bundle bData;
    String cTitle;
    String cInstruction;

    // Interface
    public Fragment_Instruction.Interface_Instruction iInstruction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Arguments
        bData = getArguments();
        cTitle = bData.getString(Constants_Intern.TITLE);
        cInstruction = bData.getString(Constants_Intern.TEXT_INSTRUCTION);

        // Interface
        iInstruction = (Fragment_Instruction.Interface_Instruction)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Layout
        setLayout(inflater, container);

        return vLayout;
    }

    // Layout
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // Initiate
        vLayout = inflater.inflate(R.layout.fragment_select, container, false);
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        tvInstruction = vLayout.findViewById(R.id.tvInstruction);

        // OnClickListener
        tvInstruction.setOnClickListener(this);

        // Fill with arguments
        tvTitle.setText(cTitle);
        tvInstruction.setText(cInstruction);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvInstruction:
                iInstruction.returnInstruction(getTag());
        }
    }

    public interface Interface_Instruction {
        void returnInstruction(String cTag);
    }
}
