package br.com.enterprise.pytaco.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.ApostaRealizadaAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Aposta;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;
import br.com.enterprise.pytaco.util.StringUtil;

public class ApostaRealizadaActivity extends BaseRecyclerActivity {

    ApostaRealizadaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aposta_realizada);
        RecyclerView lsvApostas = getRecyclerView();
        adapter = new ApostaRealizadaAdapter(this, new ArrayList<Aposta>(), R.layout.lst_aposta_realizada_item);
        lsvApostas.setAdapter(adapter);
    }

    private void pListaApostas() {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.listaAposta(bolao.getId());
    }

    private boolean pExisteDialogAberto() {
        return dialogLoading != null && dialogLoading.dialogShowing();
    }

    private void pTrataRespostaListaApostas(String response) {
        try {
            adapter.getLst().clear();
            JSONArray resp = new JSONObject(response).getJSONArray("entry");

            for (int i = 0; i < resp.length(); i++) {
                JSONObject item = resp.getJSONObject(i);
                Aposta aposta = new Aposta();
                aposta.setId(Integer.parseInt(item.getString("id_aposta")));
                aposta.setPontos(Integer.parseInt(item.getString("totalpontos")));
                aposta.setNomeUsuario(item.getString("usuario"));
                aposta.setValorBolao(StringUtil.strToNumber(item.getString("valorbolao")));
                aposta.setPercentualPremiacao(StringUtil.strToNumber(item.getString("percentual_premiacao")));
                adapter.getLst().add(aposta);
            }
        } catch (JSONException ignored) {
        } finally {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!pExisteDialogAberto()) {
            pListaApostas();
        }
    }

    @Override
    public void onSucess(String response) {
        if (!isDestroyed() && pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_APOSTAS)) {
            pCancelLoading();
            pEnableScreen();
            pTrataRespostaListaApostas(response);
        }

        super.onSucess(response);
    }

    @Override
    public void onError(@NotNull VolleyError error) {
        if (pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_APOSTAS)) {
            adapter.getLst().clear();
            adapter.notifyDataSetChanged();
        }
        super.onError(error);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return findViewById(R.id.aposta_realizada_lsvApostas);
    }

    @Override
    public void onLstItemClick(int position) {
    }
}