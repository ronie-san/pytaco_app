package br.com.enterprise.pytaco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.adapter.ClubeItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Clube;
import br.com.enterprise.pytaco.pojo.Usuario;
import br.com.enterprise.pytaco.util.DialogView;
import br.com.enterprise.pytaco.util.StringUtil;

public class MainActivity extends BaseActivity implements IActivity {

    private Usuario usuario;
    private DialogView dialogNovoClube;
    private DialogView dialogAlterarSenha;
    private DialogView dialogAssociarClube;
    private ClubeItemAdapter clubeItemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView lblQtdPytacoGlobal = findViewById(R.id.main_lblQtdPytacoGlobal);
        TextView lblQtdFichaGlobal = findViewById(R.id.main_lblQtdFichaGlobal);
        ListView lsvClubes = findViewById(R.id.main_lsvClubes);
        lsvClubes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lsvClubesItemClick(adapterView, view, i, l);
            }
        });

        clubeItemAdapter = new ClubeItemAdapter(new ArrayList<Clube>(), this);
        lsvClubes.setAdapter(clubeItemAdapter);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            usuario = (Usuario) bundle.getSerializable(getString(R.string.usuario));
        } else {
            usuario = (Usuario) savedInstanceState.getSerializable(getString(R.string.usuario));
        }

        lblQtdPytacoGlobal.setText(StringUtil.numberToStr(usuario.getQtdPytaco()));
        lblQtdFichaGlobal.setText(StringUtil.numberToStr(usuario.getQtdFicha()));

        ImageButton btnNovoClube = findViewById(R.id.main_btnNovoClube);
        btnNovoClube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNovoClubeClick();
            }
        });

        ImageButton btnAlterarSenha = findViewById(R.id.main_btnAlterarSenha);
        btnAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAlterarSenhaClick();
            }
        });

        ImageButton btnAssociarClube = findViewById(R.id.main_btnAssociarClube);
        btnAssociarClube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAssociarClubeClick();
            }
        });

        ImageButton btnBolao = findViewById(R.id.main_btnBolao);
        btnBolao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBolaoClick();
            }
        });

        pListaClubes();
    }

    private void btnBolaoClick() {

    }

    private void lsvClubesItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, BolaoActivity.class);
        Clube clube = clubeItemAdapter.getLst().get(i);
        clube.setUsuario(this.usuario);
        intent.putExtra(getString(R.string.clube), clube);
        startActivity(intent);
    }

    private void btnAssociarClubeClick() {
        dialogAssociarClube = createDialog(R.layout.dialog_associar_clube);

        final ImageButton btnVoltar = dialogAssociarClube.findViewById(R.id.associar_clube_btnVoltar);
        final TextView edtCodClube = dialogAssociarClube.findViewById(R.id.associar_clube_edtCodClube);
        final ImageButton btnAssociar = dialogAssociarClube.findViewById(R.id.associar_clube_btnAssociar);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAssociarClube.cancelDialog();
            }
        });

        btnAssociar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtCodClube.getText().toString().trim().isEmpty()) {
                    PytacoRequestDAO request = new PytacoRequestDAO(MainActivity.this);
                    request.associar(usuario.getId(), usuario.getChaveAcesso(), edtCodClube.getText().toString().trim());
                }
            }
        });

        dialogAssociarClube.showDialog();
    }

    private void btnAlterarSenhaClick() {
        dialogAlterarSenha = createDialog(R.layout.dialog_alterar_senha);

        final ImageButton btnVoltar = dialogAlterarSenha.findViewById(R.id.alterar_senha_btnVoltar);
        final TextView edtSenhaAtual = dialogAlterarSenha.findViewById(R.id.alterar_senha_edtSenhaAtual);
        final TextView edtSenhaNova = dialogAlterarSenha.findViewById(R.id.alterar_senha_edtSenhaNova);
        final TextView edtSenhaConfirmada = dialogAlterarSenha.findViewById(R.id.alterar_senha_edtSenhaConfirmada);
        final ImageButton btnAlterarSenha = dialogAlterarSenha.findViewById(R.id.alterar_senha_btnAlterarSenha);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAlterarSenha.cancelDialog();
            }
        });

        btnAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtSenhaAtual.getText().toString().isEmpty() && !edtSenhaNova.getText().toString().isEmpty() && !edtSenhaConfirmada.getText().toString().isEmpty()) {
                    PytacoRequestDAO request = new PytacoRequestDAO(MainActivity.this);
                    request.alteraSenha(usuario.getId(), edtSenhaAtual.getText().toString(), edtSenhaNova.getText().toString(), usuario.getChaveAcesso());
                }
            }
        });

        dialogAlterarSenha.showDialog();
    }

    private void btnNovoClubeClick() {
        dialogNovoClube = createDialog(R.layout.dialog_criar_clube);

        final ImageButton btnVoltar = dialogNovoClube.findViewById(R.id.criar_clube_btnVoltar);
        final TextView edtNomeClube = dialogNovoClube.findViewById(R.id.criar_clube_edtNomeClube);
        final TextView edtDescricaoClube = dialogNovoClube.findViewById(R.id.criar_clube_edtDescricaoClube);
        final ImageButton btnCriarClube = dialogNovoClube.findViewById(R.id.criar_clube_btnCriarClube);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNovoClube.cancelDialog();
            }
        });

        btnCriarClube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtNomeClube.getText().toString().trim().equals("") && !edtDescricaoClube.getText().toString().trim().equals("")) {
                    PytacoRequestDAO request = new PytacoRequestDAO(MainActivity.this);
                    request.criarClube(usuario.getId(), usuario.getChaveAcesso(), edtNomeClube.getText().toString(), edtDescricaoClube.getText().toString());
                }
            }
        });

        dialogNovoClube.showDialog();
    }

    private void pTrataRespostaAlterarSenha(@NotNull String response) {
        if (dialogAlterarSenha.dialogShowing()) {
            dialogAlterarSenha.cancelDialog();
        }

        if (response.equals("ErroLogin")) {
            //senha atual errada
            makeLongToast("Senhas não coincidentes.");
        } else {
            usuario.setChaveAcesso(response);
        }
    }

    private void pTrataRespostaCriarClube(@NotNull String response) {
        if (dialogNovoClube.dialogShowing()) {
            dialogNovoClube.cancelDialog();
        }

        switch (response) {
            case "Cadastrado":
                makeLongToast("Clube já cadastrado");
                break;
            case "Insuficiente":
                makeLongToast("Saldo de pytacos insuficiente");
                break;
            default:
                usuario.setChaveAcesso(response);
                break;
        }
    }

    private void pTrataRespostaListaClubes(@NotNull String response) {
        try {
            JSONArray resp = new JSONObject(response).getJSONArray("entry");

            if (!resp.getJSONObject(0).getString("id_clube").isEmpty()) {
                clubeItemAdapter.getLst().clear();

                for (int i = 0; i < resp.length(); i++) {
                    if (!resp.getJSONObject(i).getString("id_clube").isEmpty()) {
                        Clube clube = new Clube();
                        clube.setId(Integer.parseInt(resp.getJSONObject(i).getString("id_clube")));
                        clube.setNome(resp.getJSONObject(i).getString("nomeclube"));
                        clube.setDescricao(resp.getJSONObject(i).getString("descricaoclube"));
                        clube.setQtdFicha(Double.parseDouble(resp.getJSONObject(i).getString("qtdfichas")));
                        clube.setUsuario(usuario);
                        clubeItemAdapter.getLst().add(clube);
                    }
                }

                clubeItemAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            Log.d(this.getClass().getSimpleName(), response);
            makeLongToast("Não foi possível retornar a lista de clubes. " + e.getMessage());
        }
    }

    private void pTrataRespostaAssociarClube(String response) {
        if (dialogAssociarClube.dialogShowing()) {
            dialogAssociarClube.cancelDialog();
        }

        if (response.equals("NaoAchei")) {
            makeLongToast("Clube não encontrado");
        } else {
            usuario.setChaveAcesso(response);
            makeLongToast("Associação em análise!");
        }
    }

    private void pListaClubes() {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.listaClubes(usuario.getId(), usuario.getChaveAcesso());
    }

    @Override
    public void onSucess(String response) {
        if (!this.isDestroyed()) {
            switch (pytacoRequestEnum) {
                case LISTA_CLUBES:
                    pCancelDialog();
                    pEnableScreen();
                    pTrataRespostaListaClubes(response);
                    super.onSucess(response);
                    break;
                case ASSOCIAR:
                    pCancelDialog();
                    pEnableScreen();
                    pTrataRespostaAssociarClube(response);
                    super.onSucess(response);
                    break;
                case CRIAR_CLUBE:
                    pTrataRespostaCriarClube(response);
                    pListaClubes();
                    break;
                case ALTERAR_SENHA:
                    pCancelDialog();
                    pEnableScreen();
                    pTrataRespostaAlterarSenha(response);
                    super.onSucess(response);
                    break;
                default:
                    pCancelDialog();
                    pEnableScreen();
                    super.onSucess(response);
                    break;
            }
        }
    }
}