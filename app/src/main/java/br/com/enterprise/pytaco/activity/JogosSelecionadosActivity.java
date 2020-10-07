package br.com.enterprise.pytaco.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.JogoSelecionadoItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class JogosSelecionadosActivity extends BaseRecyclerActivity {

    private EditText edtNomeBolao;
    private double valorBolao;
    private double percentual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogos_selecionados);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            valorBolao = bundle.getDouble(getString(R.string.valor_bolao));
            percentual = bundle.getDouble(getString(R.string.percentual_bolao));
        } else {
            valorBolao = savedInstanceState.getDouble(getString(R.string.valor_bolao));
            percentual = savedInstanceState.getDouble(getString(R.string.percentual_bolao));
        }

        RecyclerView lsvJogosSelecionados = getRecyclerView();
        ImageButton btnConfirmar = findViewById(R.id.jogos_selecionados_btnConfirmar);
        edtNomeBolao = findViewById(R.id.jogos_selecionados_edtNomeBolao);

        JogoSelecionadoItemAdapter adapter = new JogoSelecionadoItemAdapter(this, lstJogoSelecionado, R.layout.lst_jogo_selecionado_item);
        lsvJogosSelecionados.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnConfirmarClick();
            }
        });
    }

    private void btnConfirmarClick() {
        if (lstJogoSelecionado.size() < 6) {
            makeShortToast("Mínimo de 6 jogos por bolão");
        } else if (edtNomeBolao.getText().toString().trim().isEmpty()) {
            makeShortToast("Nome do bolão obrigatório");
        } else {
            PytacoRequestDAO request = new PytacoRequestDAO(this);
            request.criarBolao(usuario.getId(), usuario.getChaveAcesso(), clube.getId(), valorBolao, pGetLstJogos(), percentual, edtNomeBolao.getText().toString().trim());
        }
    }

    private void pTrataRepostaCriarBolao(String response) {
        try {
            JSONObject resp = new JSONObject(response).getJSONArray("entry").getJSONObject(0);
            usuario.setChaveAcesso(resp.getString("chaveacesso"));
            pShowOkDialog("Atenção", "Bolão criado com sucesso", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    lstJogoSelecionado = new ArrayList<>();
                    pStartActivityClearTop(BolaoActivity.class);
                }
            });
        } catch (JSONException ignored) {
        }
    }

    @NotNull
    private String pGetLstJogos() {
        StringBuilder jogos = new StringBuilder();
        for (int i = 0; i < lstJogoSelecionado.size(); i++) {
            jogos.append(lstJogoSelecionado.get(i).getId());

            if (i < lstJogoSelecionado.size() - 1) {
                jogos.append(",");
            }
        }
        return jogos.toString();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return findViewById(R.id.jogos_selecionados_lsvJogosSelecionados);
    }

    @Override
    public void onLstItemClick(int position) {
    }

    @Override
    public void onSucess(String response) {
        if (!isDestroyed() && pytacoRequestEnum.equals(PytacoRequestEnum.CRIAR_BOLAO)) {
            pCancelLoading();
            pEnableScreen();
            pTrataRepostaCriarBolao(response);
        }

        super.onSucess(response);
    }
}