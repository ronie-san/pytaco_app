package br.com.enterprise.pytaco.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.BolaoAbertoItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.DetalheJogo;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

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
    }

    private void btnVerApostasClick() {
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
            JSONArray resp = new JSONObject(response).getJSONArray("entry");
        } catch (JSONException ignored) {
        } finally {
            adapter.notifyDataSetChanged();
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
        if (!isDestroyed() && pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_JOGOS_BOLAO)) {
            pCancelLoading();
            pEnableScreen();
            pTrataRespostaListaJogosBolao(response);
        }

        super.onSucess(response);
    }

    @Override
    public void onError(@NotNull VolleyError error) {
        if (pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_JOGOS_BOLAO)) {
            adapter.getLst().clear();
            adapter.notifyDataSetChanged();
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