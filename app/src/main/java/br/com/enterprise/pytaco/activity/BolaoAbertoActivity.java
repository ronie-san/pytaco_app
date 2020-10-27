package br.com.enterprise.pytaco.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.BolaoAbertoItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.DetalheJogo;
import br.com.enterprise.pytaco.util.StringUtil;

public class BolaoAbertoActivity extends BaseRecyclerActivity {

    private BolaoAbertoItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolao_aberto);
        ImageButton btnVerApostas = findViewById(R.id.bolao_aberto_btnVerApostas);
        RecyclerView lsvJogos = getRecyclerView();
        ImageButton btnConfirmar = findViewById(R.id.bolao_aberto_btnConfirmar);
        adapter = new BolaoAbertoItemAdapter(this, new ArrayList<DetalheJogo>(), R.layout.lst_bolao_aberto_item);
        lsvJogos.setAdapter(adapter);

        btnVerApostas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVerApostasClick();
            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnConfirmarClick();
            }
        });
    }

    private void btnConfirmarClick() {
        if (clube.getQtdFicha() < bolao.getValor()) {
            makeShortToast("Saldo insuficiente");
        }

        List<String> lstAposta = new ArrayList<>();

        for (DetalheJogo item : adapter.getLst()) {
            if (!item.getIdAposta().equals("")) {
                lstAposta.add(item.getIdAposta());
            }
        }

        if (lstAposta.size() < adapter.getLst().size()) {
            makeShortToast("É necessário selecionar todos os jogos");
            return;
        }

        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.novaAposta(clube.getId(), bolao.getId(), usuario.getId(), usuario.getChaveAcesso(), bolao.getValor(), clube.getQtdFicha(), lstAposta.toArray(new String[0]));
    }

    private void btnVerApostasClick() {
        pStartActivity(ApostaRealizadaActivity.class);
    }

    private void pListaJogos() {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.listaJogosBolao(bolao.getId(), usuario.getId(), usuario.getChaveAcesso());
    }

    private boolean pExisteDialogAberto() {
        return dialogLoading != null && dialogLoading.dialogShowing();
    }

    private void pTrataRespostaListaJogosBolao(String response) {
        try {
            adapter.getLst().clear();
            JSONObject resp = new JSONObject(response).getJSONArray("entry").getJSONObject(0);
            int qtdJogos = resp.length() / 10;

            for (int i = 1; i <= qtdJogos; i++) {
                DetalheJogo detalheJogo = new DetalheJogo();
                detalheJogo.setIdTeam(resp.getString("id_T1J" + i));
                detalheJogo.setStatus(resp.getString("statusJ" + i));
                detalheJogo.setNome(resp.getString("nomeT1J" + i));
                detalheJogo.setIdOtherTeam(resp.getString("id_T2J" + i));
                detalheJogo.setNomeOtherTeam(resp.getString("nomeT2J" + i));
                detalheJogo.setGols(resp.getString("GolsT1J" + i));
                detalheJogo.setGolsOtherTeam(resp.getString("GolsT2J" + i));
                detalheJogo.setPontosJogo(resp.getString("scoreFulltimeJ" + i));
                detalheJogo.setPontosProrrogacao(resp.getString("scoreExtraTimeJ" + i));
                detalheJogo.setPontosPenaltis(resp.getString("scorePenaltyJ" + i));

                if (resp.getString("id_T1J" + i).equals("")) {
                    detalheJogo.setIdAposta("0");
                }

                adapter.getLst().add(detalheJogo);
            }
        } catch (JSONException ignored) {
        } finally {
            adapter.notifyDataSetChanged();
        }
    }

    private void pTrataRespostaNovaAposta(String response) {
        try {
            JSONObject resp = new JSONObject(response).getJSONArray("entry").getJSONObject(0);
            usuario.setChaveAcesso(resp.getString("chaveacesso"));
            clube.setQtdFicha(StringUtil.strToNumber(resp.getString("novosaldo")));
            makeShortToast("Aposta realizada");
            onBackPressed();
        } catch (JSONException ignored) {
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!pExisteDialogAberto()) {
            pListaJogos();
        }
    }

    @Override
    public void onSucess(String response) {
        if (!isDestroyed()) {
            switch (pytacoRequestEnum) {
                case LISTA_JOGOS_BOLAO:
                    pCancelLoading();
                    pEnableScreen();
                    pTrataRespostaListaJogosBolao(response);
                    break;
                case NOVA_APOSTA:
                    pCancelLoading();
                    pEnableScreen();
                    pTrataRespostaNovaAposta(response);
                    break;
                default:
                    break;
            }
        }

        super.onSucess(response);
    }

    @Override
    public void onError(@NotNull VolleyError error) {
        switch (pytacoRequestEnum) {
            case LISTA_JOGOS_BOLAO:
                adapter.getLst().clear();
                adapter.notifyDataSetChanged();
                break;
            case NOVA_APOSTA:
                break;
            default:
                break;
        }

        super.onError(error);
    }


    @Override
    public RecyclerView getRecyclerView() {
        return findViewById(R.id.bolao_aberto_lsvJogos);
    }

    @Override
    public void onLstItemClick(int position) {

    }
}