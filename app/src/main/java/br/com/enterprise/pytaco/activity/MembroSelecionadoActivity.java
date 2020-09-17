package br.com.enterprise.pytaco.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Clube;
import br.com.enterprise.pytaco.pojo.Membro;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class MembroSelecionadoActivity extends BaseActivity {

    private Membro membro;
    TextView lblAgente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membro_selecionado);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            membro = (Membro) bundle.getSerializable(getString(R.string.membro));
        } else {
            membro = (Membro) savedInstanceState.getSerializable(getString(R.string.membro));
        }

        ImageButton btnVoltar = findViewById(R.id.membro_selecionado_btnVoltar);
        TextView lblCodMembro = findViewById(R.id.membro_selecionado_lblCodMembro);
        TextView lblNome = findViewById(R.id.membro_selecionado_lblNome);
        lblAgente = findViewById(R.id.membro_selecionado_lblAgente);
        TextView lblTipo = findViewById(R.id.membro_selecionado_lblTipo);
        TextView lblStatus = findViewById(R.id.membro_selecionado_lblStatus);

        lblCodMembro.setText(membro.getCodClube());
        lblNome.setText(membro.getNome());
        lblTipo.setText(membro.getTipoExt());
        lblStatus.setText(membro.getStatusExt());

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.buscaAgente(membro.getId(), membro.getClube().getId(), membro.getCodClube());
    }

    private void pTrataRespostaBuscaAgente(String response) {
        makeLongToast(response);
    }

    @Override
    public void onSucess(String response) {
        if (!this.isDestroyed()) {
            pCancelDialog();
            pEnableScreen();

            if (pytacoRequestEnum.equals(PytacoRequestEnum.BUSCA_AGENTE)) {
                pTrataRespostaBuscaAgente(response);
            }
        }

        super.onSucess(response);
    }
}