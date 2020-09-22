package br.com.enterprise.pytaco.activity;

import android.app.Activity;

import com.android.volley.VolleyError;

import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public interface IActivity {
    void setPytacoRequest(PytacoRequestEnum value);

    void onSucess(String response);

    void onError(VolleyError error);

    void onStartRequest();

    Activity getActivity();
}
