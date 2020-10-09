package br.com.enterprise.pytaco.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.BolaoItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Bolao;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;
import br.com.enterprise.pytaco.util.StringUtil;

public class MeusBoloesActivity extends BaseRecyclerActivity {

    private int filtro = 1;
    private TextView lblQtdFicha;
    private BolaoItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_boloes);

        lblQtdFicha = findViewById(R.id.meus_boloes_lblQtdFicha);
        TextView lblAbertos = findViewById(R.id.meus_boloes_lblAbertos);
        TextView lblEmAndamento = findViewById(R.id.meus_boloes_lblEmAndamento);
        TextView lblFinalizados = findViewById(R.id.meus_boloes_lblFinalizados);
        RecyclerView lsvBoloes = getRecyclerView();
        adapter = new BolaoItemAdapter(this, new ArrayList<Bolao>(), R.layout.lst_bolao_item);
        lsvBoloes.setAdapter(adapter);

        lblAbertos.setText(StringUtil.textoSublinhado(lblAbertos.getText().toString()));
        lblAbertos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblAbertosClick();
            }
        });

        lblEmAndamento.setText(StringUtil.textoSublinhado(lblEmAndamento.getText().toString()));
        lblEmAndamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblEmAndamentoClick();
            }
        });

        lblFinalizados.setText(StringUtil.textoSublinhado(lblFinalizados.getText().toString()));
        lblFinalizados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblFinalizadosClick();
            }
        });
    }

    private void lblFinalizadosClick() {
        filtro = 0;
        pListaBoloes();
    }

    private void lblEmAndamentoClick() {
        filtro = 2;
        pListaBoloes();
    }

    private void lblAbertosClick() {
        filtro = 1;
        pListaBoloes();
    }

    private boolean pExisteDialogAberto() {
        return dialogLoading != null && dialogLoading.dialogShowing();
    }

    private void pTrataRespostaListaBoloes(String response) {
        try {
            adapter.getLst().clear();
            JSONArray resp = new JSONObject(response).getJSONArray("entry");

            for (int i = 0; i < resp.length(); i++) {
                JSONObject item = resp.getJSONObject(i);
                Bolao b = new Bolao();
                b.setId(Integer.parseInt(item.getString("id_bolao")));
                b.setValor(StringUtil.strToNumber(item.getString("valor")));
                b.setPerc(StringUtil.strToNumber(item.getString("percentual_premiacao")));
                b.setStatus(item.getString("status"));
                b.setNome(item.getString("nome_bolao"));
                adapter.getLst().add(b);
            }
        } catch (JSONException ignored) {
        } finally {
            adapter.notifyDataSetChanged();
        }
    }

    private void pListaBoloes() {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.listaBoloes(clube.getId(), filtro);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lblQtdFicha.setText(StringUtil.numberToStr(clube.getQtdFicha()));

        if (!pExisteDialogAberto()) {
            pListaBoloes();
        }
    }

    @Override
    public void onSucess(String response) {
        if (!isDestroyed() && pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_BOLOES)) {
            pCancelLoading();
            pEnableScreen();
            pTrataRespostaListaBoloes(response);
        }

        super.onSucess(response);
    }

    @Override
    public void onError(@NotNull VolleyError error) {
        if (pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_BOLOES)) {
            adapter.getLst().clear();
            adapter.notifyDataSetChanged();
        }

        super.onError(error);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return findViewById(R.id.meus_boloes_lsvBoloes);
    }

    @Override
    public void onLstItemClick(int position) {
        if (adapter.getLst().get(position).getStatus().equals("1")) {
            bolao = adapter.getLst().get(position);
            pStartActivity(BolaoAbertoActivity.class);
        }
    }
}