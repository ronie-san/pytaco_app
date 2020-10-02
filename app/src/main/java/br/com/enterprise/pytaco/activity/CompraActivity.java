package br.com.enterprise.pytaco.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.PacoteCompraItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.PacoteCompra;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;
import br.com.enterprise.pytaco.util.StringUtil;

public class CompraActivity extends BaseRecyclerActivity {

    private TextView lblQtdPytaco;
    private PacoteCompraItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        ImageButton btnVoltar = findViewById(R.id.compras_btnVoltar);
        RecyclerView lsvPacotes = getRecyclerView();
        lblQtdPytaco = findViewById(R.id.compras_lblQtdPytaco);

        adapter = new PacoteCompraItemAdapter(this, new ArrayList<PacoteCompra>(), R.layout.lst_pacote_compra_item);
        lsvPacotes.setAdapter(adapter);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });
    }

    private void pAtualizaPytacos() {
        lblQtdPytaco.setText(StringUtil.numberToStr(usuario.getQtdPytaco()));
    }

    private void pListaPacoteCompra() {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.listaPacoteCompra();
    }

    private boolean pExisteDialogAberto() {
        return dialogLoading != null && dialogLoading.getDialog().isShowing();
    }

    private void pTrataRespostaListaPacoteCompra(String response) {
        try {
            adapter.getLst().clear();
            JSONArray resp = new JSONObject(response).getJSONArray("entry");

            if (resp.length() > 0 && !resp.getJSONObject(0).getString("id_pacote").isEmpty()) {
                for (int i = 0; i < resp.length(); i++) {
                    JSONObject item = resp.getJSONObject(i);
                    PacoteCompra pacoteCompra = new PacoteCompra();
                    pacoteCompra.setId(Integer.parseInt(item.getString("id_pacote")));
                    pacoteCompra.setNome(item.getString("nomepacote"));
                    pacoteCompra.setResumo(item.getString("resumopacote"));
                    pacoteCompra.setValor(Double.parseDouble(item.getString("valorpacote").substring(3).replace(",", ".")));
                    pacoteCompra.setDescricao(item.getString("descricaopacote"));
                    adapter.getLst().add(pacoteCompra);
                }
            }
        } catch (JSONException ignored) {
        } finally {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        pAtualizaPytacos();

        if (!pExisteDialogAberto()) {
            pListaPacoteCompra();
        }
    }

    @Override
    public void onError(@NotNull VolleyError error) {
        if (pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_PACOTE_COMPRA)) {
            adapter.getLst().clear();
            adapter.notifyDataSetChanged();
        }

        super.onError(error);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return findViewById(R.id.compras_lsvPacotes);
    }

    @Override
    public void onLstItemClick(int position) {
        /* TODO: FAZER A ROTINA DE COMPRA AQUI! */
    }

    @Override
    public void onSucess(String response) {
        if (!this.isDestroyed()) {
            if (pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_PACOTE_COMPRA)) {
                pCancelDialog();
                pEnableScreen();
                pTrataRespostaListaPacoteCompra(response);
            }
        }
        super.onSucess(response);
    }
}