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
import br.com.enterprise.pytaco.adapter.LigaItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.League;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class LigasActivity extends BaseRecyclerActivity {

    private LigaItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ligas);

        RecyclerView lsvLigas = getRecyclerView();
        adapter = new LigaItemAdapter(this, new ArrayList<League>(), R.layout.lst_liga_item);
        lsvLigas.setAdapter(adapter);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return findViewById(R.id.ligas_lsvLigas);
    }

    @Override
    public void onLstItemClick(int position) {
        liga = adapter.getLst().get(position);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!pExisteDialogAberto()) {
            pListaLigas();
        }
    }

    @Override
    public void onSucess(String response) {
        if (!isDestroyed() && pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_LIGAS)) {
            pCancelDialog();
            pEnableScreen();
            pTrataRespostaListaLigas(response);
        }
        super.onSucess(response);
    }

    @Override
    public void onError(@NotNull VolleyError error) {
        if (pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_LIGAS)) {
            adapter.getLst().clear();
            adapter.notifyDataSetChanged();
        }

        super.onError(error);
    }

    private void pTrataRespostaListaLigas(String response) {
        try {
            liga = null;
            adapter.getLst().clear();
            JSONArray resp = new JSONObject(response).getJSONArray("entry");

            for (int i = 0; i < resp.length(); i++) {
                JSONObject item = resp.getJSONObject(i);
                League league = new League();
                league.setId(Integer.parseInt(item.getString("id_liga")));
                league.setName(item.getString("nome"));
                adapter.getLst().add(league);
            }
        } catch (JSONException ignored) {
        } finally {
            adapter.notifyDataSetChanged();
        }
    }

    private void pListaLigas() {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.listaLigas(pais.getSigla());
    }

    private boolean pExisteDialogAberto() {
        return dialogLoading != null && dialogLoading.getDialog().isShowing();
    }
}