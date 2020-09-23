package br.com.enterprise.pytaco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.adapter.ContadorItemAdapter;
import br.com.enterprise.pytaco.activity.adapter.MembroItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Clube;
import br.com.enterprise.pytaco.pojo.Membro;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class ContadorActivity extends BaseActivity {

    private Clube clube;
    private TextView lblContadores;
    private ContadorItemAdapter contadorItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            clube = (Clube) bundle.getSerializable(getString(R.string.clube));
        } else {
            clube = (Clube) savedInstanceState.getSerializable(getString(R.string.clube));
        }

        ListView lsvContadores = findViewById(R.id.contador_lsvContadores);
        lblContadores = findViewById(R.id.contador_lblContadores);
        ImageButton btnTrocarFichas = findViewById(R.id.contador_btnTrocarFichas);
        ImageButton btnVoltar = findViewById(R.id.contador_btnVoltar);

        contadorItemAdapter = new ContadorItemAdapter(new ArrayList<Membro>(), this);
        lsvContadores.setItemsCanFocus(true);
        lsvContadores.setAdapter(contadorItemAdapter);
        lsvContadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lsvContadoresItemClick(adapterView, view, i, l);
            }
        });

        btnTrocarFichas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTrocarFichasClick();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });

        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.listaMembros(clube.getId());
    }

    private void btnTrocarFichasClick() {
        ArrayList<Membro> lstSelecionado = new ArrayList<>();
        for (Membro item : contadorItemAdapter.getLst()) {
            if (item.isMarcado()) {
                lstSelecionado.add(item);
            }
        }

        if (!lstSelecionado.isEmpty()) {
            Intent intent = new Intent(this, ContadorSelecionadoActivity.class);
            intent.putExtra(getString(R.string.lst_contador), lstSelecionado);
            startActivity(intent);
        }
    }

    private void lsvContadoresItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Membro item = contadorItemAdapter.getLst().get(i);
        item.setMarcado(!item.isMarcado());
        contadorItemAdapter.notifyDataSetChanged();
    }

    private void pTrataRespostaListaMembros(String response) {
        try {
            JSONArray resp = new JSONObject(response).getJSONArray("entry");
            contadorItemAdapter.getLst().clear();

            for (int i = 0; i < resp.length(); i++) {
                JSONObject membroJson = resp.getJSONObject(i);

                if (!membroJson.getString("nomemembro").equals(clube.getUsuario().getNome())) {
                    Membro membro = new Membro();
                    membro.setId(Integer.parseInt(membroJson.getString("id_membro")));
                    membro.setNome(membroJson.getString("nomemembro"));
                    membro.setStatus(membroJson.getString("statusmembro"));
                    membro.setTipo(membroJson.getString("tipomembro"));
                    membro.setQtdFicha(Double.parseDouble(membroJson.getString("qtdfichasclube")));
                    membro.setCodClube(membroJson.getString("codigoclube"));
                    membro.setClube(clube);
                    contadorItemAdapter.getLst().add(membro);
                }
            }

            contadorItemAdapter.notifyDataSetChanged();

            if (contadorItemAdapter.getLst().isEmpty()) {
                lblContadores.setText("Nenhum membro");
            } else if (contadorItemAdapter.getLst().size() == 1) {
                lblContadores.setText("1 membro");
            } else {
                lblContadores.setText(contadorItemAdapter.getLst().size() + " membros");
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