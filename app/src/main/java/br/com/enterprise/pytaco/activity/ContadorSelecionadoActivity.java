package br.com.enterprise.pytaco.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.ContadorSelecionadoItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Membro;
import br.com.enterprise.pytaco.util.StringUtil;

public class ContadorSelecionadoActivity extends BaseRecyclerActivity {

    private EditText edtQtdFicha;
    private TextView lblQtdFicha;
    private ContadorSelecionadoItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador_selecionado);
        List<Membro> lstMembro;

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            lstMembro = (ArrayList<Membro>) bundle.getSerializable(getString(R.string.lst_contador));
        } else {
            lstMembro = (ArrayList<Membro>) savedInstanceState.getSerializable(getString(R.string.lst_contador));
        }

        edtQtdFicha = findViewById(R.id.contador_selecionado_edtQtdFicha);
        lblQtdFicha = findViewById(R.id.contador_selecionado_lblQtdFicha);
        ImageButton btnEnviarFicha = findViewById(R.id.contador_selecionado_btnEnviarFicha);
        ImageButton btnRetirarFicha = findViewById(R.id.contador_selecionado_btnRetirarFicha);
        RecyclerView lsvContadores = getRecyclerView();
        adapter = new ContadorSelecionadoItemAdapter(this, lstMembro, R.layout.lst_contador_selecionado_item);
        lsvContadores.setAdapter(adapter);
        lblQtdFicha.setText(StringUtil.numberToStr(clube.getQtdFicha()));

        btnEnviarFicha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEnviarFichaClick();
            }
        });

        btnRetirarFicha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRetirarFichaClick();
            }
        });
    }

    @NotNull
    private String pGetLstMembros() {
        StringBuilder membros = new StringBuilder();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            membros.append(adapter.getLst().get(i).getId());
            if (i < adapter.getItemCount() - 1) {
                membros.append(",");
            }
        }
        return membros.toString();
    }

    private boolean pValidaEnviarFicha() {
        double qtd = StringUtil.strToNumber(edtQtdFicha.getText().toString().trim());
        double qtdUsuario = StringUtil.strToNumber(lblQtdFicha.getText().toString());
        return qtd <= qtdUsuario * adapter.getItemCount();
    }

    private boolean pValidaRetirarFicha() {
        Double qtd = StringUtil.strToNumber(edtQtdFicha.getText().toString().trim());
        boolean result = true;
        int i = 0;

        while (result && i < adapter.getItemCount()) {
            result = adapter.getLst().get(i).getQtdFicha() >= qtd;
            i++;
        }

        return result;
    }

    private void btnEnviarFichaClick() {
        pHideKeyboard();

        if (!edtQtdFicha.getText().toString().isEmpty()) {
            if (pValidaEnviarFicha()) {
                PytacoRequestDAO request = new PytacoRequestDAO(this);
                request.enviarFichas(usuario.getId(),
                        usuario.getChaveAcesso(),
                        clube.getId(),
                        StringUtil.strToNumber(edtQtdFicha.getText().toString().trim()),
                        pGetLstMembros(),
                        adapter.getLst().size(),
                        StringUtil.strToNumber(lblQtdFicha.getText().toString()));
            } else {
                makeLongToast("Quantidade de fichas insuficiente");
            }
        }
    }

    private void btnRetirarFichaClick() {
        pHideKeyboard();

        if (!edtQtdFicha.getText().toString().isEmpty()) {
            if (pValidaRetirarFicha()) {
                PytacoRequestDAO request = new PytacoRequestDAO(this);
                request.retirarFichas(usuario.getId(),
                        usuario.getChaveAcesso(),
                        clube.getId(),
                        StringUtil.strToNumber(edtQtdFicha.getText().toString().trim()),
                        pGetLstMembros(),
                        adapter.getLst().size(),
                        StringUtil.strToNumber(lblQtdFicha.getText().toString()));
            } else {
                makeLongToast("Quantidade de fichas insuficiente");
            }
        }
    }

    private void pTrataRespostaEnviarFichas(String response) {
        try {
            JSONObject resp = new JSONObject(response).getJSONArray("entry").getJSONObject(0);
            usuario.setChaveAcesso(resp.getString("chaveacesso"));
            clube.setQtdFicha(StringUtil.strToNumber(resp.getString("novosaldoadmin")));
            Double qtd;

            for (Membro membro : adapter.getLst()) {
                qtd = membro.getQtdFicha();
                qtd += StringUtil.strToNumber(edtQtdFicha.getText().toString().trim());
                membro.setQtdFicha(qtd);
            }

            adapter.notifyDataSetChanged();
            lblQtdFicha.setText(StringUtil.numberToStr(clube.getQtdFicha()));
            edtQtdFicha.setText("");
        } catch (JSONException e) {
            makeLongToast("Não foi possível obter a resposta");
        }
    }

    private void pTrataRespostaRetirarFichas(String response) {
        try {
            JSONObject resp = new JSONObject(response).getJSONArray("entry").getJSONObject(0);
            usuario.setChaveAcesso(resp.getString("chaveacesso"));
            clube.setQtdFicha(StringUtil.strToNumber(resp.getString("novosaldoadmin")));
            Double qtd;

            for (Membro membro : adapter.getLst()) {
                qtd = membro.getQtdFicha();
                qtd -= StringUtil.strToNumber(edtQtdFicha.getText().toString().trim());
                membro.setQtdFicha(qtd);
            }

            adapter.notifyDataSetChanged();
            lblQtdFicha.setText(StringUtil.numberToStr(clube.getQtdFicha()));
            edtQtdFicha.setText("");
        } catch (JSONException e) {
            makeLongToast("Não foi possível obter a resposta");
        }
    }

    @Override
    public void onSucess(String response) {
        pCancelDialog();
        pEnableScreen();

        switch (pytacoRequestEnum) {
            case ENVIAR_FICHAS:
                pTrataRespostaEnviarFichas(response);
                break;
            case RETIRAR_FICHAS:
                pTrataRespostaRetirarFichas(response);
                break;
            default:
                break;
        }

        super.onSucess(response);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return findViewById(R.id.contador_selecionado_lsvContadores);
    }

    @Override
    public void onLstItemClick(int position) {

    }
}