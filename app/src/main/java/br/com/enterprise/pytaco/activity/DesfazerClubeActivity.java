package br.com.enterprise.pytaco.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class DesfazerClubeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desfazer_clube);
        ImageButton btnDesfazer = findViewById(R.id.desfazer_clube_btnDesfazer);

        btnDesfazer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDesfazerClick();
            }
        });
    }

    private void btnDesfazerClick() {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.desfazerClube(usuario.getId(), usuario.getChaveAcesso(), clube.getId());
    }

    @Override
    public void onSucess(String response) {
        pCancelDialog();
        pEnableScreen();

        if (pytacoRequestEnum.equals(PytacoRequestEnum.DESFAZER_CLUBE)) {
            pTrataRespostaDesfazerClube(response);
        }

        super.onSucess(response);
    }

    private void pTrataRespostaDesfazerClube(String response) {
        try {
            JSONObject resp = new JSONObject(response).getJSONArray("entry").getJSONObject(0);
            usuario.setChaveAcesso(resp.getString("chaveacesso"));
            clube = null;
            makeLongToast("Clube desfeito com sucesso");
            pStartActivityClearTop(ClubesActivity.class);
        } catch (JSONException ignored) {
        }
    }
}