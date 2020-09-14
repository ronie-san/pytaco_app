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
import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.adapter.ClubeItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Clube;
import br.com.enterprise.pytaco.pojo.Usuario;
import br.com.enterprise.pytaco.util.DialogView;

public class MainActivity extends BaseActivity implements IActivity {

    private Usuario usuario;
    private TextView lblQtdPytacoGlobal;
    private TextView lblQtdFichaGlobal;
    private DialogView dialogNovoClube;
    private DialogView dialogAlterarSenha;
    private DialogView dialogAssociarClube;
    private ClubeItemAdapter clubeItemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblQtdPytacoGlobal = findViewById(R.id.main_lblQtdPytacoGlobal);
        lblQtdFichaGlobal = findViewById(R.id.main_lblQtdFichaGlobal);
        ListView lsvClubes = findViewById(R.id.main_lsvClubes);
        lsvClubes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lsvClubesItemClick(adapterView, view, i, l);
            }
        });

        clubeItemAdapter = new ClubeItemAdapter(new ArrayList<Clube>(), this);
        lsvClubes.setAdapter(clubeItemAdapter);
        usuario = new Usuario();

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            usuario = (Usuario) bundle.getSerializable("usuario");
        } else {
            usuario = (Usuario) savedInstanceState.getSerializable("usuario");
        }

        lblQtdPytacoGlobal.setText(usuario.getQtdPytaco().toString());
        lblQtdFichaGlobal.setText(usuario.getQtdFicha().toString());

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

        PytacoRequestDAO request = new PytacoRequestDAO(MainActivity.this);
        request.listaClubes(usuario.getId(), usuario.getChaveAcesso());
    }

    private void btnBolaoClick() {
        Intent intent = new Intent(this, BolaoActivity.class);
        intent.putExtra("usuario", this.usuario);
        startActivity(intent);
    }

    private void lsvClubesItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
                Log.d("D", "Qtd: " + clubeItemAdapter.getLst().size());
                clubeItemAdapter.getLst().clear();
                Log.d("D", "Qtd: " + clubeItemAdapter.getLst().size());

                for (int i = 0; i < resp.length(); i++) {
                    if (!resp.getJSONObject(i).getString("id_clube").isEmpty()) {
                        Clube clube = new Clube();
                        clube.setId(Integer.parseInt(resp.getJSONObject(i).getString("id_clube")));
                        clube.setNome(resp.getJSONObject(i).getString("nomeclube"));
                        clube.setDescricao(resp.getJSONObject(i).getString("descricaoclube"));
                        clube.setQtdFicha(Integer.parseInt(resp.getJSONObject(i).getString("qtdfichas")));
                        clubeItemAdapter.getLst().add(clube);
                        Log.d("D", "Qtd: " + clubeItemAdapter.getLst().size());
                    }
                }

                clubeItemAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            Log.d("D", response);
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

    @Override
    public void onJsonSuccess(JSONObject response) {
        pCancelDialog();
        pEnableScreen();

        switch (pytacoRequestEnum) {
            case LISTA_CLUBES:
                pTrataRespostaListaClubes(response.toString());
                break;
            case CRIAR_CLUBE:
                pTrataRespostaCriarClube(response.toString());
                break;
            default:
                break;
        }

        super.onJsonSuccess(response);
    }

    @Override
    public void onSucess(String response) {
        pCancelDialog();
        pEnableScreen();

        switch (pytacoRequestEnum) {
            case ASSOCIAR:
                pTrataRespostaAssociarClube(response);
                break;
            case LISTA_CLUBES:
                pTrataRespostaListaClubes(response);
                break;
            case CRIAR_CLUBE:
                pTrataRespostaCriarClube(response);
                break;
            case ALTERAR_SENHA:
                pTrataRespostaAlterarSenha(response);
                break;
            default:
                break;
        }

        super.onSucess(response);
//        PytacoRequestDAO request = new PytacoRequestDAO(MainActivity.this);
//        request.listaClubes(usuario.getId(), usuario.getChaveAcesso());
    }

    @Override
    public void onError(@NotNull VolleyError error) {
        pCancelDialog();
        pEnableScreen();

        switch (pytacoRequestEnum) {
            case ASSOCIAR:
                break;
            case LISTA_CLUBES:
                pTrataRespostaListaClubes(error.toString());
                break;
            case CRIAR_CLUBE:
                pTrataRespostaCriarClube(error.toString());
                break;
            default:
                break;
        }

        super.onError(error);
    }
}
