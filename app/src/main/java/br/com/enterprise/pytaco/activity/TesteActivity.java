package br.com.enterprise.pytaco.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;

public class TesteActivity extends BaseActivity {

    private TextView lblTeste;
    private FrameLayout pnlLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);
        findViewById(R.id.main_btnTeste).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTesteClick();
            }
        });

        lblTeste = findViewById(R.id.main_lblTeste);
        pnlLoading = findViewById(R.id.main_pnlLoading);
    }

    private void btnTesteClick() {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
//        request.login("UsuTeste", "12345");

//        request.listaAvisos(7, 5);
        request.listaMembros(5);
    }

    @Override
    public void onSucess(String response) {
        if (!this.isDestroyed()) {
            pCancelDialog();
            pEnableScreen();
            lblTeste.setText(response);
        }
        super.onSucess(response);
    }
}
