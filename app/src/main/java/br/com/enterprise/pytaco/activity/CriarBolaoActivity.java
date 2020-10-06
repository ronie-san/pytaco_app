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
import br.com.enterprise.pytaco.adapter.PaisItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Pais;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class CriarBolaoActivity extends BaseRecyclerActivity {

    private PaisItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_bolao);

        RecyclerView lsvPaises = getRecyclerView();
        adapter = new PaisItemAdapter(this, new ArrayList<Pais>(), R.layout.lst_pais_item);
        lsvPaises.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!pExisteDialogAberto()) {
            pListaPaises();
        }
    }

    @Override
    public void onSucess(String response) {
        if (!isDestroyed() && pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_PAISES)) {
            pCancelDialog();
            pEnableScreen();
            pTrataRespostaListaPaises(response);
        }
        super.onSucess(response);
    }

    @Override
    public void onError(@NotNull VolleyError error) {
        if (pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_PAISES)) {
            adapter.getLst().clear();
            adapter.notifyDataSetChanged();
        }

        super.onError(error);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return findViewById(R.id.criar_bolao_lsvPaises);
    }

    @Override
    public void onLstItemClick(int position) {
        pais = adapter.getLst().get(position);
        pStartActivity(LigasActivity.class);
    }

    private void pTrataRespostaListaPaises(String response) {
        try {
            pais = null;
            adapter.getLst().clear();
            JSONArray resp = new JSONObject(response).getJSONArray("entry");

            for (int i = 0; i < resp.length(); i++) {
                JSONObject item = resp.getJSONObject(i);
                Pais pais = new Pais();
                pais.setSigla(item.getString("id_pais"));
                pais.setNome(item.getString("nome"));
                adapter.getLst().add(pais);
            }
        } catch (JSONException ignored) {
        } finally {
            adapter.notifyDataSetChanged();
        }
    }

    private void pListaPaises() {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.listaPaises();
    }

    private boolean pExisteDialogAberto() {
        return dialogLoading != null && dialogLoading.getDialog().isShowing();
    }
}