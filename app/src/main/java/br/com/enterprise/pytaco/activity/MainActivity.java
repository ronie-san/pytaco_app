package br.com.enterprise.pytaco.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class MainActivity extends BaseActivity implements IActivity {

    private Usuario usuario;
    private TextView lblQtdPytacoGlobal;
    private TextView lblQtdFichaGlobal;
    private AlertDialog dialogNovoClube;
    private ListView lsvClubes;
    private List<Clube> lstClube;
    private ClubeItemAdapter clubeItemAdapter;
    private PytacoRequestEnum pytacoRequestEnum = PytacoRequestEnum.NONE;

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
        pytacoRequestEnum = PytacoRequestEnum.LISTA_CLUBES;
        PytacoRequestDAO request = new PytacoRequestDAO(MainActivity.this);
        request.listaClubes(usuario.getId(), usuario.getChaveAcesso());
    }


    private void btnNovoClubeClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_criar_clube, null);
        final ImageButton btnVoltar = view.findViewById(R.id.criar_clube_btnVoltar);

        dialogNovoClube = builder.create();
        dialogNovoClube.setCancelable(true);
        dialogNovoClube.setCanceledOnTouchOutside(true);
        dialogNovoClube.setView(view, 0, 0, 0, 0);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNovoClube.cancel();
            }
        });

        final TextView edtNomeClube = view.findViewById(R.id.criar_clube_edtNomeClube);
        final TextView edtDescricaoClube = view.findViewById(R.id.criar_clube_edtDescricaoClube);

        final ImageButton btnCriarClube = view.findViewById(R.id.criar_clube_btnCriarClube);
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

        dialogNovoClube.show();
    }

    private void pTrataRespostaCriarClube(@NotNull String response) {
        if (dialogNovoClube.isShowing()) {
            dialogNovoClube.cancel();
        }

        switch (response) {
            case "Insuficiente":
                Toast.makeText(this, "Saldo de pytacos insuficiente para criar um novo clube.", Toast.LENGTH_LONG).show();
                break;
            case "":
                Toast.makeText(this, "Criado.", Toast.LENGTH_LONG).show();
                break;
            default:

                break;
        }
    }

    private void pTrataRespostaListaClubes(@NotNull String response) {
        try {
            JSONArray resp = new JSONObject(response).getJSONArray("entry");

            for (int i = 0; i < resp.length(); i++) {
                Clube clube = new Clube();
                clube.setId(Integer.parseInt(resp.getJSONObject(i).getString("id_clube")));
                clube.setNome(resp.getJSONObject(i).getString("nomeclube"));
                clube.setDescricao(resp.getJSONObject(i).getString("descricaoclube"));
                clube.setQtdFicha(Integer.parseInt(resp.getJSONObject(i).getString("qtdfichas")));
                lstClube.add(clube);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Não foi possível retornar a lista de clubes. " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        ClubeItemAdapter adapter = (ClubeItemAdapter) lsvClubes.getAdapter();
        lsvClubes.setAdapter(adapter);
    }

    @Override
    public PytacoRequestEnum getPytacoRequest() {
        return pytacoRequestEnum;
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
            default:
                break;
        }
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
            default:
                break;
        }
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
    }

    @Override
    public void onStartRequest() {
        pDisableScreen();
        pShowProgress();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
