package br.com.enterprise.pytaco.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

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

    private void btnTesteClick(){
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.login("UsuTeste", "12345");
    }

    @Override
    public void onJsonSuccess(JSONObject response) {
        if (!this.isDestroyed()) {
            try {
                if (!response.getJSONArray("entry").getJSONObject(0).getString("id_usuario").equals("")) {
                    this.lblTeste.setText(response.toString());
                    pCancelDialog();
                    pEnableScreen();
                } else {
                    pCancelDialog();
                    pEnableScreen();
                    this.lblTeste.setText("Usuário e/ou senha inválidos.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                pCancelDialog();
                pEnableScreen();
                this.lblTeste.setText("Não foi possível entrar. Houve erro na autenticação.\r\n" + e.getMessage());
            }
        }

        super.onJsonSuccess(response);
    }

    @Override
    public void onError(VolleyError error) {
        if (!this.isDestroyed()) {
            pCancelDialog();
            pEnableScreen();
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
        }

        super.onError(error);
    }
}
