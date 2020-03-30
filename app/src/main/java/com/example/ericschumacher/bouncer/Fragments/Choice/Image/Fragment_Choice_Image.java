package com.example.ericschumacher.bouncer.Fragments.Choice.Image;

import com.example.ericschumacher.bouncer.Adapter.List.Choice.Adapter_List_Choice;
import com.example.ericschumacher.bouncer.Fragments.Choice.Fragment_Choice;

public class Fragment_Choice_Image extends Fragment_Choice {

    public interface Interface_Adapter_Choice_Image extends Adapter_List_Choice.Interface_Adapter_Choice {
        public String getUrlIconOne(int position);
        public String getUrlIconTwo(int position);
    }
}
