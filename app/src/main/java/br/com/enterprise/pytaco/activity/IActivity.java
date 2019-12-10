package br.com.enterprise.pytaco.activity;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface IActivity {
    void onJsonSuccess(JSONObject response);
    void onSucess(String response);
    void onError(VolleyError error);
    void onStartRequest();
    Context getBaseContext();
}
