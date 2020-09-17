package br.com.enterprise.pytaco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.adapter.MembroItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Clube;
import br.com.enterprise.pytaco.pojo.Membro;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class MembrosActivity extends BaseActivity {

    private Clube clube;
    private TextView lblMembros;
    private MembroItemAdapter membroItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membros);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            clube = (Clube) bundle.getSerializable(getString(R.string.clube));
        } else {
            clube = (Clube) savedInstanceState.getSerializable(getString(R.string.clube));
        }

        ListView lsvMembros = findViewById(R.id.membros_lsvMembros);
        membroItemAdapter = new MembroItemAdapter(new ArrayList<Membro>(), this);
        lsvMembros.setAdapter(membroItemAdapter);
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

        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.listaMembros(clube.getId());
    }

    private void lsvMembrosItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, MembroSelecionadoActivity.class);
        intent.putExtra(getString(R.string.membro), membroItemAdapter.getLst().get(i));
        startActivity(intent);
    }

    private void pTrataRespostaListaMembros(String response) {
        try {
            JSONArray resp = new JSONObject(response).getJSONArray("entry");
            membroItemAdapter.getLst().clear();

            for (int i = 0; i < resp.length(); i++) {
                Membro membro = new Membro();
                JSONObject membroJson = resp.getJSONObject(i);
                membro.setId(Integer.parseInt(membroJson.getString("id_membro")));
                membro.setNome(membroJson.getString("nomemembro"));
                membro.setStatus(membroJson.getString("statusmembro"));
                membro.setTipo(membroJson.getString("tipomembro"));
                membro.setQtdFicha(Double.parseDouble(membroJson.getString("qtdfichasclube")));
                membro.setCodClube(membroJson.getString("codigoclube"));
                membro.setClube(clube);
                membroItemAdapter.getLst().add(membro);
            }

            membroItemAdapter.notifyDataSetChanged();

            if (membroItemAdapter.getLst().isEmpty()) {
                lblMembros.setText("Nenhum membro");
            } else if (membroItemAdapter.getLst().size() == 1) {
                lblMembros.setText("1 membro");
            } else {
                lblMembros.setText(membroItemAdapter.getLst().size() + " membros");
            }
        } catch (JSONException e) {

        }
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