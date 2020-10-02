package br.com.enterprise.pytaco.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
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
        ImageButton btnAceitar = findViewById(R.id.membro_selecionado_btnAceitar);
        ImageButton btnAgente = findViewById(R.id.membro_selecionado_btnAgente);
        ImageButton btnDesativar = findViewById(R.id.membro_selecionado_btnDesativar);

        lblCodMembro.setText(membro.getCodClube());
        lblNome.setText(membro.getNome());
        pAtualizaStatusTipo();

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });

        btnAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAceitarClick();
            }
        });

        btnAgente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAgenteClick();
            }
        });

        btnDesativar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDesativarClick();
            }
        });

        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.buscaAgente(membro.getId(), clube.getId(), membro.getCodClube());
    }

    private void pAtualizaStatusTipo() {
        lblTipo.setText(membro.getTipoExt());
        lblStatus.setText(membro.getStatusExt());
    }

    private void btnAceitarClick() {
        pAcaoMembro(PytacoRequestEnum.ACEITAR_MEMBROS);
    }

    private void btnAgenteClick() {
        pAcaoMembro(PytacoRequestEnum.TORNAR_AGENTE);
    }

    private void btnDesativarClick() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    pAcaoMembro(PytacoRequestEnum.DESATIVAR_MEMBRO);
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmação")
                .setMessage("Deseja realmente desativar este membro?")
                .setPositiveButton("Sim", dialogClickListener)
                .setNegativeButton("Não", dialogClickListener)
                .show();
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
                    clube.setTipoUsuario("1");
                    break;
                case TORNAR_AGENTE:
                    membro.setStatus("1");
                    membro.setTipo("2");
                    clube.setTipoUsuario("2");
                    break;
                case DESATIVAR_MEMBRO:
                    membro.setStatus("0");
                    membro.setTipo("1");
                    clube.setTipoUsuario("1");
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