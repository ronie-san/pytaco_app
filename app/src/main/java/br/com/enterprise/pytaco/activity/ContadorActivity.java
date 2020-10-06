package br.com.enterprise.pytaco.activity;

import android.content.Intent;
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
import br.com.enterprise.pytaco.adapter.ContadorItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Membro;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;
import br.com.enterprise.pytaco.util.StringUtil;

public class ContadorActivity extends BaseRecyclerActivity {

    private TextView lblContadores;
    private ContadorItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);

        RecyclerView lsvContadores = getRecyclerView();
        lblContadores = findViewById(R.id.contador_lblContadores);
        ImageButton btnTrocarFichas = findViewById(R.id.contador_btnTrocarFichas);

        adapter = new ContadorItemAdapter(this, new ArrayList<Membro>(), R.layout.lst_contador_item);
        lsvContadores.setAdapter(adapter);

        btnTrocarFichas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTrocarFichasClick();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!pExisteDialogAberto()) {
            PytacoRequestDAO request = new PytacoRequestDAO(this);
            request.listaMembros(clube.getId());
        }
    }

    private boolean pExisteDialogAberto() {
        return dialogLoading != null && dialogLoading.getDialog().isShowing();
    }

    private void btnTrocarFichasClick() {
        ArrayList<Membro> lstSelecionado = new ArrayList<>();
        for (Membro item : adapter.getLst()) {
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

    private void pTrataRespostaListaMembros(String response) {
        try {
            membro = null;
            adapter.getLst().clear();
            JSONArray resp = new JSONObject(response).getJSONArray("entry");

            for (int i = 0; i < resp.length(); i++) {
                JSONObject membroJson = resp.getJSONObject(i);

                if (!membroJson.getString("nomemembro").equals(usuario.getNome())) {
                    Membro membro = new Membro();
                    membro.setId(Integer.parseInt(membroJson.getString("id_membro")));
                    membro.setNome(membroJson.getString("nomemembro"));
                    membro.setStatus(membroJson.getString("statusmembro"));
                    membro.setTipo(membroJson.getString("tipomembro"));
                    membro.setQtdFicha(StringUtil.strToNumber(membroJson.getString("qtdfichasclube")));
                    membro.setCodClube(membroJson.getString("codigoclube"));
                    adapter.getLst().add(membro);
                }
            }

            StringBuilder sb = new StringBuilder();

            if (adapter.getLst().isEmpty()) {
                sb.append("Sem membros");
            } else if (adapter.getLst().size() == 1) {
                sb.append("1 membro");
            } else {
                sb.append(adapter.getLst().size())
                        .append(" membros");
            }

            lblContadores.setText(sb.toString());
        } catch (JSONException ignored) {

        } finally {
            adapter.notifyDataSetChanged();
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

    @Override
    public RecyclerView getRecyclerView() {
        return findViewById(R.id.contador_lsvContadores);
    }

    @Override
    public void onLstItemClick(int position) {
        Membro item = adapter.getLst().get(position);
        item.setMarcado(!item.isMarcado());
        adapter.notifyDataSetChanged();
    }
}