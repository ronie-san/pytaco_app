package br.com.enterprise.pytaco.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.PytacosTrocadosItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.PytacoTrocado;
import br.com.enterprise.pytaco.util.DateUtil;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class PytacosTrocadosActivity extends BaseActivity {

    private EditText edtData;
    private PytacosTrocadosItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pytacos_trocados);

        edtData = findViewById(R.id.pytacos_trocados_edtData);
        ImageButton btnVoltar = findViewById(R.id.pytacos_trocados_btnVoltar);
        TextView lblAnterior = findViewById(R.id.pytacos_trocados_lblAnterior);
        TextView lblProximo = findViewById(R.id.pytacos_trocados_lblProximo);
        ListView lsvPytacosTrocados = findViewById(R.id.pytacos_trocados_lsvPytacosTrocados);

        adapter = new PytacosTrocadosItemAdapter(new ArrayList<PytacoTrocado>(), this, R.layout.lst_pytacos_trocados_item);
        lsvPytacosTrocados.setEmptyView(findViewById(android.R.id.empty));
        lsvPytacosTrocados.setAdapter(adapter);

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

        pListaPytacosTrocados();
    }

    private void lblAnteriorClick() {
        Date date = DateUtil.parse(edtData.getText().toString());
        date = DateUtil.addDay(date, -1);
        edtData.setText(DateUtil.toDefaultFormat(date));
        pListaPytacosTrocados();
    }

    private void edtDataClick() {
        final Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        DatePickerDialog calendario = new DatePickerDialog(this, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                edtData.setText(DateUtil.formarData(i2, i1 + 1, i));
                pListaPytacosTrocados();
            }
        }, ano, mes, dia);

        calendario.show();
    }

    private void lblProximoClick() {
        Date date = DateUtil.parse(edtData.getText().toString());
        date = DateUtil.addDay(date);
        edtData.setText(DateUtil.toDefaultFormat(date));
        pListaPytacosTrocados();
    }

    private void pListaPytacosTrocados() {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.listaPytacosTrocados(clube.getId(), edtData.getText().toString().trim());
    }

    private void pTrataRespostaListaPytacosTrocados(String response) {
        try {
            adapter.getLst().clear();
            JSONArray resp = new JSONObject(response).getJSONArray("entry");

            if (resp.length() > 0 && !resp.getJSONObject(0).getString("id_log").isEmpty()) {
                for (int i = 0; i < resp.length(); i++) {
                    JSONObject item = resp.getJSONObject(i);
                    PytacoTrocado pytacoTrocado = new PytacoTrocado();
                    pytacoTrocado.setId(Integer.parseInt(item.getString("id_log")));
                    pytacoTrocado.setNomeUsuario(item.getString("nomeUsuario"));
                    pytacoTrocado.setQtdFicha(Double.parseDouble(item.getString("qtdFichasNovas")));
                    pytacoTrocado.setQtdPytaco(Double.parseDouble(item.getString("qtdPytacosTrocados")));
                    adapter.getLst().add(pytacoTrocado);
                }
            }
        } catch (JSONException ignored) {
        } finally {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSucess(String response) {
        pCancelDialog();
        pEnableScreen();

        if (pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_PYTACOS_TROCADOS)) {
            pTrataRespostaListaPytacosTrocados(response);
        }

        super.onSucess(response);
    }
}