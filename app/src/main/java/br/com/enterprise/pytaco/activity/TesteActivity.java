package br.com.enterprise.pytaco.activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.util.APIFootballRequest;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class TesteActivity extends FragmentActivity implements IActivity {

    private TextView lblTeste;
    private FrameLayout pnlLoading;
    private PytacoRequestEnum pytacoRequestEnum = PytacoRequestEnum.NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);
        findViewById(R.id.main_btnTimeZone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTimeZoneClick();
            }
        });

        findViewById(R.id.main_btnLimpar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLimparClick();
            }
        });

        findViewById(R.id.main_btnSeasons).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSeasonsClick();
            }
        });

        lblTeste = findViewById(R.id.main_lblTeste);
        pnlLoading = findViewById(R.id.main_pnlLoading);
    }

    private void btnSeasonsClick() {
        APIFootballRequest request = new APIFootballRequest(this);
        request.getSeasons();
    }

    private void btnTimeZoneClick() {
        APIFootballRequest request = new APIFootballRequest(this);
        request.getTimeZone();
    }

    private void btnLimparClick() {
        lblTeste.setText("");
    }

    private void pFecharLoading() {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .remove(getSupportFragmentManager()
//                        .findFragmentByTag(LoadingFragment.class.getSimpleName()))
//                .commitAllowingStateLoss();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public PytacoRequestEnum getPytacoRequest() {
        return pytacoRequestEnum;
    }

    @Override
    public void onJsonSuccess(JSONObject response) {
        if(!this.isDestroyed()) {
            lblTeste.setText(response.toString());
            pFecharLoading();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void onSucess(String response) {

    }

    @Override
    public void onError(VolleyError error) {
        if (!this.isDestroyed()) {
            lblTeste.setText(error.getMessage());
            pFecharLoading();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void onStartRequest() {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.main_pnlLoading,
//                        new LoadingFragment(),
//                        LoadingFragment.class.getSimpleName())
//                .commitAllowingStateLoss();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
