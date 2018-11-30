package com.example.ericschumacher.bouncer.Interfaces;

import com.example.ericschumacher.bouncer.Objects.Object_SearchResult;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 12.06.2018.
 */

public interface Interface_VolleyCallback_ArrayList_Input {
    public void onSuccess(ArrayList<Object_SearchResult> list);
    public void onFailure();
}
