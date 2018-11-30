package com.example.ericschumacher.bouncer.Interfaces;

import org.json.JSONObject;

public interface Interface_VolleyCallback_FailureResponse {
    public void onSuccess();
    public void onFailure(JSONObject json);
}
