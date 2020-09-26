package br.com.enterprise.pytaco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class SairClubeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sair_clube);
        ImageButton btnVoltar = findViewById(R.id.sair_clube_btnVoltar);
        Button btnSair = findViewById(R.id.sair_clube_btnSair);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSairClick();
            }
        });
    }

    private void btnSairClick() {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.sairClube(usuario.getId(), usuario.getChaveAcesso(), clube.getId());
    }

    private void pTrataRespostaSairClube(String response) {
        try {
            JSONObject resp = new JSONObject(response).getJSONArray("entry").getJSONObject(0);
            usuario.setChaveAcesso(resp.getString("chaveacesso"));
            makeLongToast("Saída do clube feita com sucesso");

            Intent intent = new Intent(this, ClubesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } catch (JSONException ignored) {
        }
    }

    @Override
    public void onSucess(String response) {
        pCancelDialog();
        pEnableScreen();

        if (pytacoRequestEnum.equals(PytacoRequestEnum.SAIR_CLUBE)) {
            pTrataRespostaSairClube(response);
        }

        super.onSucess(response);
    }
}