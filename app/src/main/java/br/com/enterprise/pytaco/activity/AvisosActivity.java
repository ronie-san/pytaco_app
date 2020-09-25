package br.com.enterprise.pytaco.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.AvisoItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Aviso;
import br.com.enterprise.pytaco.util.DialogView;

public class AvisosActivity extends BaseActivity {

    private AvisoItemAdapter avisoItemAdapter;
    private DialogView dialogCriarAviso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos);

        ListView lsvAvisos = findViewById(R.id.avisos_lsvAvisos);
        avisoItemAdapter = new AvisoItemAdapter(new ArrayList<Aviso>(), this);
        lsvAvisos.setAdapter(avisoItemAdapter);
        lsvAvisos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lsvAvisosItemClick(adapterView, view, i, l);
            }
        });

        ImageButton btnVoltar = findViewById(R.id.avisos_btnVoltar);
        ImageButton btnCriarAviso = findViewById(R.id.avisos_btnCriarAviso);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });
        btnCriarAviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCriarAvisoClick();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!pExisteDialogAberto()) {
            pListaAvisos();
        }
    }

    private boolean pExisteDialogAberto() {
        return (dialogLoading != null && dialogLoading.getDialog().isShowing()) ||
                (dialogCriarAviso != null && dialogCriarAviso.getDialog().isShowing());
    }

    private void pLerAviso(@NotNull Aviso aviso) {
        if (aviso.getStatus().equals("E")) {
            PytacoRequestDAO request = new PytacoRequestDAO(this);
            request.alterarAviso(aviso.getIdTabela());
        } else {
            dialogCriarAviso.cancelDialog();
        }
    }

    private void lsvAvisosItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final Aviso aviso = avisoItemAdapter.getLst().get(i);
        dialogCriarAviso = createDialog(R.layout.dialog_criar_aviso);

        ImageButton btnVoltar = dialogCriarAviso.findViewById(R.id.criar_aviso_btnVoltar);
        EditText edtTituloAviso = dialogCriarAviso.findViewById(R.id.criar_aviso_edtTituloAviso);
        EditText edtDescricaoAviso = dialogCriarAviso.findViewById(R.id.criar_aviso_edtDescricaoAviso);
        ImageButton btnSalvar = dialogCriarAviso.findViewById(R.id.criar_aviso_btnSalvar);
        Button btnExcluir = dialogCriarAviso.findViewById(R.id.criar_aviso_btnExcluir);

        edtTituloAviso.setText(aviso.getTitulo());
        edtTituloAviso.setEnabled(false);

        edtDescricaoAviso.setText(aviso.getDescricao());
        edtDescricaoAviso.setEnabled(false);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pLerAviso(aviso);
            }
        });
        btnSalvar.setVisibility(View.GONE);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            PytacoRequestDAO request = new PytacoRequestDAO(AvisosActivity.this);
                            request.excluirAviso(aviso.getIdTabela());
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(AvisosActivity.this);
                builder.setTitle("Confirmação")
                        .setMessage("Deseja realmente excluir este aviso?")
                        .setPositiveButton("Sim", dialogClickListener)
                        .setNegativeButton("Não", dialogClickListener)
                        .show();
            }
        });

        dialogCriarAviso.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK &&
                        keyEvent.getAction() == KeyEvent.ACTION_UP &&
                        !keyEvent.isCanceled()) {
                    pLerAviso(aviso);
                    return true;
                }

                return false;
            }
        });

        pAddButtonEffect(btnExcluir);
        dialogCriarAviso.showDialog();
    }

    private void btnCriarAvisoClick() {
        dialogCriarAviso = createDialog(R.layout.dialog_criar_aviso);

        ImageButton btnVoltar = dialogCriarAviso.findViewById(R.id.criar_aviso_btnVoltar);
        ImageButton btnSalvar = dialogCriarAviso.findViewById(R.id.criar_aviso_btnSalvar);
        Button btnExcluir = dialogCriarAviso.findViewById(R.id.criar_aviso_btnExcluir);
        final EditText edtTituloAviso = dialogCriarAviso.findViewById(R.id.criar_aviso_edtTituloAviso);
        final EditText edtDescricaoAviso = dialogCriarAviso.findViewById(R.id.criar_aviso_edtDescricaoAviso);

        btnExcluir.setVisibility(View.GONE);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCriarAviso.cancelDialog();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtTituloAviso.getText().toString().isEmpty() && !edtDescricaoAviso.getText().toString().isEmpty()) {
                    PytacoRequestDAO request = new PytacoRequestDAO(AvisosActivity.this);
                    request.criarAviso(usuario.getId(), clube.getId(), edtTituloAviso.getText().toString(), edtDescricaoAviso.getText().toString());
                }
            }
        });

        dialogCriarAviso.showDialog();
    }

    private void pTrataRespostaAlterarAviso() {
        if (dialogCriarAviso != null && dialogCriarAviso.dialogShowing()) {
            dialogCriarAviso.cancelDialog();
        }
    }

    private void pTrataRespostaCriarAviso() {
        if (dialogCriarAviso != null && dialogCriarAviso.dialogShowing()) {
            dialogCriarAviso.cancelDialog();
        }
    }

    private void pTrataRespostaListaAvisos(String response) {
        try {
            avisoItemAdapter.getLst().clear();
            JSONArray resp = new JSONObject(response).getJSONArray("entry");

            for (int i = 0; i < resp.length(); i++) {
                JSONObject item = resp.getJSONObject(i);
                Aviso aviso = new Aviso();
                aviso.setId(Integer.parseInt(item.getString("id_aviso")));
                aviso.setTitulo(item.getString("titulo"));
                aviso.setDescricao(item.getString("descricao"));
                aviso.setData(item.getString("data"));
                aviso.setStatus(item.getString("status"));
                aviso.setIdTabela(Integer.parseInt(item.getString("id_tabela")));
                avisoItemAdapter.getLst().add(aviso);
            }
        } catch (JSONException e) {

        } finally {
            avisoItemAdapter.notifyDataSetChanged();
        }
    }

    private void pTrataRespostaExcluirAviso() {
        if (dialogCriarAviso != null && dialogCriarAviso.dialogShowing()) {
            dialogCriarAviso.cancelDialog();
        }
    }

    private void pListaAvisos() {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.listaAvisos(usuario.getId(), clube.getId());
    }

    @Override
    public void onSucess(String response) {
        if (!this.isDestroyed()) {
            switch (pytacoRequestEnum) {
                case LISTA_AVISOS:
                    pCancelDialog();
                    pEnableScreen();
                    pTrataRespostaListaAvisos(response);
                    super.onSucess(response);
                    break;
                case CRIAR_AVISO:
                    pTrataRespostaCriarAviso();
                    pListaAvisos();
                    break;
                case ALTERAR_AVISO:
                    pTrataRespostaAlterarAviso();
                    pListaAvisos();
                    break;
                case EXCLUIR_AVISO:
                    pTrataRespostaExcluirAviso();
                    pListaAvisos();
                    break;
                default:
                    pCancelDialog();
                    pEnableScreen();
                    super.onSucess(response);
                    break;
            }
        } else {
            super.onSucess(response);
        }
    }
}