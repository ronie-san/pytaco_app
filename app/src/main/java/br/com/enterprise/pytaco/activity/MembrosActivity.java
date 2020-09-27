package br.com.enterprise.pytaco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.MembroItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Membro;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class MembrosActivity extends BaseActivity {

    private TextView lblMembros;
    private MembroItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membros);

        ListView lsvMembros = findViewById(R.id.membros_lsvMembros);
        adapter = new MembroItemAdapter(new ArrayList<Membro>(), this, R.layout.lst_membro_item);
        lsvMembros.setAdapter(adapter);
        lsvMembros.setEmptyView(findViewById(android.R.id.empty));
        lsvMembros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lsvMembrosItemClick(adapterView, view, i, l);
            }
        });

        ImageButton btnVoltar = findViewById(R.id.membros_btnVoltar);
        lblMembros = findViewById(R.id.membros_lblMembros);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });
    }

    private boolean pExisteDialogAberto() {
        return dialogLoading != null && dialogLoading.getDialog().isShowing();
    }

    private void lsvMembrosItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, MembroSelecionadoActivity.class);
        membro = adapter.getLst().get(i);
        startActivity(intent);
    }

    private void pTrataRespostaListaMembros(String response) {
        try {
            membro = null;
            adapter.getLst().clear();
            JSONArray resp = new JSONObject(response).getJSONArray("entry");

            for (int i = 0; i < resp.length(); i++) {
                Membro membro = new Membro();
                JSONObject membroJson = resp.getJSONObject(i);
                membro.setId(Integer.parseInt(membroJson.getString("id_membro")));
                membro.setNome(membroJson.getString("nomemembro"));
                membro.setStatus(membroJson.getString("statusmembro"));
                membro.setTipo(membroJson.getString("tipomembro"));
                membro.setQtdFicha(Double.parseDouble(membroJson.getString("qtdfichasclube")));
                membro.setCodClube(membroJson.getString("codigoclube"));
                adapter.getLst().add(membro);
            }

            if (adapter.getLst().isEmpty()) {
                lblMembros.setText("Nenhum membro");
            } else if (adapter.getLst().size() == 1) {
                lblMembros.setText("1 membro");
            } else {
                lblMembros.setText(adapter.getLst().size() + " membros");
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
            PytacoRequestDAO request = new PytacoRequestDAO(this);
            request.listaMembros(clube.getId());
        }
    }

    @Override
    public void onError(@NotNull VolleyError error) {
        if (pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_MEMBROS)) {
            adapter.getLst().clear();
            adapter.notifyDataSetChanged();
        }

        super.onError(error);
    }

    @Override
    public void onSucess(String response) {
        if (!this.isDestroyed()) {
            pCancelDialog();
            pEnableScreen();

            if (pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_MEMBROS)) {
                pTrataRespostaListaMembros(response);
            }
        }

        super.onSucess(response);
    }
}