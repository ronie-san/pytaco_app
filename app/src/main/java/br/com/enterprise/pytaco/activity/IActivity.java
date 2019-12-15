package br.com.enterprise.pytaco.activity;

import android.app.Activity;
import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public interface IActivity {
    PytacoRequestEnum getPytacoRequest();
    void onJsonSuccess(JSONObject response);
    void onSucess(String response);
    void onError(VolleyError error);
    void onStartRequest();
    Context getContext();
}
