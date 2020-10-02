package br.com.enterprise.pytaco.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.FichaMovimentadaItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.FichaMovimentada;
import br.com.enterprise.pytaco.util.DateUtil;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class FichasMovimentadasActivity extends BaseRecyclerActivity {

    private EditText edtData;
    private FichaMovimentadaItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichas_movimentadas);

        edtData = findViewById(R.id.fichas_movimentadas_edtData);
        ImageButton btnVoltar = findViewById(R.id.fichas_movimentadas_btnVoltar);
        TextView lblAnterior = findViewById(R.id.fichas_movimentadas_lblAnterior);
        TextView lblProximo = findViewById(R.id.fichas_movimentadas_lblProximo);
        RecyclerView lsvFichasMovimentadas = getRecyclerView();

        adapter = new FichaMovimentadaItemAdapter(this, new ArrayList<FichaMovimentada>(), R.layout.lst_ficha_movimentada_item);
        lsvFichasMovimentadas.setAdapter(adapter);

        edtData.setText(DateUtil.getDate());
        edtData.setInputType(InputType.TYPE_NULL);
        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtDataClick();
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });
        lblAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblAnteriorClick();
            }
        });
        lblProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblProximoClick();
            }
        });

        pListaFichasMovimentadas();
    }

    private void lblAnteriorClick() {
        Date date = DateUtil.parse(edtData.getText().toString());
        date = DateUtil.addDay(date, -1);
        edtData.setText(DateUtil.toDefaultFormat(date));
        pListaFichasMovimentadas();
    }

    private void edtDataClick() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.parse(edtData.getText().toString()));
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        DatePickerDialog calendario = new DatePickerDialog(this, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                edtData.setText(DateUtil.formarData(i2, i1 + 1, i));
                pListaFichasMovimentadas();
            }
        }, ano, mes, dia);

        calendario.show();
    }

    private void lblProximoClick() {
        Date date = DateUtil.parse(edtData.getText().toString());
        date = DateUtil.addDay(date);
        edtData.setText(DateUtil.toDefaultFormat(date));
        pListaFichasMovimentadas();
    }

    private void pListaFichasMovimentadas() {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.listaFichasMovimentadas(clube.getId(), edtData.getText().toString());
    }

    private void pTrataRespostaListaFichasMovimentadas(String response) {
        try {
            adapter.getLst().clear();
            JSONArray resp = new JSONObject(response).getJSONArray("entry");

            if (resp.length() > 0 && !resp.getJSONObject(0).getString("id_log").isEmpty()) {
                for (int i = 0; i < resp.length(); i++) {
                    JSONObject item = resp.getJSONObject(i);
                    FichaMovimentada fichaMovimentada = new FichaMovimentada();
                    fichaMovimentada.setId(Integer.parseInt(item.getString("id_log")));
                    fichaMovimentada.setNomeUsuarioEnvio(item.getString("NomeUsuarioQFez"));
                    fichaMovimentada.setNomeUsuarioRecebimento(item.getString("NomeUsuarioQRecebeu"));
                    fichaMovimentada.setQtdFichaAnterior(Double.parseDouble(item.getString("QtdFichasAnteriores")));
                    fichaMovimentada.setQtdFichaAtual(Double.parseDouble(item.getString("QtdFichasAtuais")));
                    fichaMovimentada.setQtdFichaMovimento(Double.parseDouble(item.getString("QtdFichasMovimentadas")));
                    fichaMovimentada.setSaldoAtualAdmin(Double.parseDouble(item.getString("SaldoAtualAdmin")));
                    fichaMovimentada.setSaldoAnteriorAdmin(Double.parseDouble(item.getString("SaldoAnteriorAdmin")));
                    fichaMovimentada.setAcao(item.getString("acao"));
                    adapter.getLst().add(fichaMovimentada);
                }
            }
        } catch (JSONException ignored) {
        } finally {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(@NotNull VolleyError error) {
        if (pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_FICHAS_MOVIMENTADAS)) {
            adapter.getLst().clear();
            adapter.notifyDataSetChanged();
        }

        super.onError(error);
    }

    @Override
    public void onSucess(String response) {
        pCancelDialog();
        pEnableScreen();

        if (pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_FICHAS_MOVIMENTADAS)) {
            pTrataRespostaListaFichasMovimentadas(response);
        }

        super.onSucess(response);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return findViewById(R.id.fichas_movimentadas_lsvFichasMovimentadas);
    }

    @Override
    public void onLstItemClick(int position) {
    }
}