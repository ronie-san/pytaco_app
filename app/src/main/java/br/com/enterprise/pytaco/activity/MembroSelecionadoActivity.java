package br.com.enterprise.pytaco.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Membro;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class MembroSelecionadoActivity extends BaseActivity {

    private TextView lblAgente;
    private TextView lblTipo;
    private TextView lblStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membro_selecionado);

        ImageButton btnVoltar = findViewById(R.id.membro_selecionado_btnVoltar);
        TextView lblCodMembro = findViewById(R.id.membro_selecionado_lblCodMembro);
        TextView lblNome = findViewById(R.id.membro_selecionado_lblNome);
        lblAgente = findViewById(R.id.membro_selecionado_lblAgente);
        lblTipo = findViewById(R.id.membro_selecionado_lblTipo);
        lblStatus = findViewById(R.id.membro_selecionado_lblStatus);
        ImageButton btnAceitarMembros = findViewById(R.id.membro_selecionado_btnAceitarMembros);
        ImageButton btnTornarAgente = findViewById(R.id.membro_selecionado_btnTornarAgente);
        ImageButton btnDesativarMembro = findViewById(R.id.membro_selecionado_btnDesativarMembro);

        lblCodMembro.setText(membro.getCodClube());
        lblNome.setText(membro.getNome());
        pAtualizaStatusTipo();

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });
        btnAceitarMembros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAceitarMembrosClick();
            }
        });
        btnTornarAgente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTornarAgenteClick();
            }
        });
        btnDesativarMembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDesativarMembroClick();
            }
        });
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.buscaAgente(membro.getId(), clube.getId(), membro.getCodClube());
    }

    private void pAtualizaStatusTipo() {
        lblTipo.setText(membro.getTipoExt());
        lblStatus.setText(membro.getStatusExt());
    }

    private void btnAceitarMembrosClick() {
        pAcaoMembro(PytacoRequestEnum.ACEITAR_MEMBROS);
    }

    private void btnTornarAgenteClick() {
        pAcaoMembro(PytacoRequestEnum.TORNAR_AGENTE);
    }

    private void btnDesativarMembroClick() {
        pAcaoMembro(PytacoRequestEnum.DESATIVAR_MEMBRO);
    }

    private void pAcaoMembro(PytacoRequestEnum acao) {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.acaoMembro(membro.getId(), clube.getId(), usuario.getId(), usuario.getChaveAcesso(), acao);
    }

    private void pTrataRespostaBuscaAgente(String response) {
        if (response != null && !response.isEmpty()) {
            lblAgente.setText(response.trim());
        } else {
            lblAgente.setText("");
            makeLongToast("Não foi possível retornar Agente");
        }
    }

    private void pTrataRespostaAcaoMembro(String response) {
        if (response != null && !response.isEmpty()) {
            usuario.setChaveAcesso(response);

            switch (pytacoRequestEnum) {
                case ACEITAR_MEMBROS:
                    membro.setStatus("1");
                    membro.setTipo("1");
                    break;
                case TORNAR_AGENTE:
                    membro.setStatus("1");
                    membro.setTipo("2");
                    break;
                case DESATIVAR_MEMBRO:
                    membro.setStatus("0");
                    membro.setTipo("1");
                    break;
                default:
                    break;
            }

            pAtualizaStatusTipo();
        }
    }

    @Override
    public void onSucess(String response) {
        if (!this.isDestroyed()) {
            pCancelDialog();
            pEnableScreen();

            switch (pytacoRequestEnum) {
                case BUSCA_AGENTE:
                    pTrataRespostaBuscaAgente(response);
                    break;
                case ACEITAR_MEMBROS:
                case TORNAR_AGENTE:
                case DESATIVAR_MEMBRO:
                    pTrataRespostaAcaoMembro(response);
                    break;
            }
        }

        super.onSucess(response);
    }
}