package br.com.enterprise.pytaco.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class MainActivity extends BaseActivity implements IActivity {

    private Usuario usuario;
    private TextView lblQtdPytacoGlobal;
    private TextView lblQtdFichaGlobal;
    private DialogView dialogNovoClube;
    private DialogView dialogAlterarSenha;
    private ListView lsvClubes;
    private List<Clube> lstClube;
    private ClubeItemAdapter clubeItemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblQtdPytacoGlobal = findViewById(R.id.main_lblQtdPytacoGlobal);
        lblQtdFichaGlobal = findViewById(R.id.main_lblQtdFichaGlobal);
        lsvClubes = findViewById(R.id.main_lsvClubes);
        lstClube = new ArrayList<>();
        clubeItemAdapter = new ClubeItemAdapter(lstClube, this);
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

        final ImageButton btnNovoClube = findViewById(R.id.main_btnNovoClube);
        btnNovoClube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNovoClubeClick();
            }
        });

        final ImageButton btnAlterarSenha = findViewById(R.id.main_btnAlterarSenha);
        btnAlterarSenha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnAlterarSenhaClick();
            }
        });

        pytacoRequestEnum = PytacoRequestEnum.LISTA_CLUBES;
        PytacoRequestDAO request = new PytacoRequestDAO(MainActivity.this);
        request.listaClubes(usuario.getId(), usuario.getChaveAcesso());
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
                    pytacoRequestEnum = PytacoRequestEnum.ALTERAR_SENHA;
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
                    pytacoRequestEnum = PytacoRequestEnum.CRIAR_CLUBE;
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
            case "Insuficiente":
                makeLongToast("Saldo de pytacos insuficiente para criar um novo clube.");
                break;
            case "":
                makeLongToast("Criado.");
                break;
            default:
                break;
        }
    }

    private void pTrataRespostaListaClubes(@NotNull String response) {
        try {
            JSONArray resp = new JSONObject(response).getJSONArray("entry");

            for (int i = 0; i < resp.length(); i++) {
                if (!resp.getJSONObject(i).getString("id_clube").isEmpty()) {
                    Clube clube = new Clube();
                    clube.setId(Integer.parseInt(resp.getJSONObject(i).getString("id_clube")));
                    clube.setNome(resp.getJSONObject(i).getString("nomeclube"));
                    clube.setDescricao(resp.getJSONObject(i).getString("descricaoclube"));
                    clube.setQtdFicha(Integer.parseInt(resp.getJSONObject(i).getString("qtdfichas")));
                    lstClube.add(clube);
                }
            }
        } catch (JSONException e) {
            Log.d("D", response);
            makeLongToast("Não foi possível retornar a lista de clubes. " + e.getMessage());
        }

        ClubeItemAdapter adapter = (ClubeItemAdapter) lsvClubes.getAdapter();
        lsvClubes.setAdapter(adapter);
    }

    @Override
    public void onJsonSuccess(JSONObject response) {
        pCancelDialog();
        pEnableScreen();

        switch (pytacoRequestEnum) {
            case ASSOCIAR:
                break;
            case LISTA_CLUBES:
                pTrataRespostaListaClubes(response.toString());
                break;
            case CRIAR_CLUBE:
                pTrataRespostaCriarClube(response.toString());
                break;
            case ALTERAR_SENHA:
                pTrataRespostaAlterarSenha(response.toString());
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
